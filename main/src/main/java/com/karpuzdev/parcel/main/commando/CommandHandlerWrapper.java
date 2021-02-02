package com.karpuzdev.parcel.main.commando;

import com.karpuzdev.parcel.lang.ParcelAPI;
import com.karpuzdev.parcel.lang.helpers.EventIdentifier;
import com.karpuzdev.parcel.lang.tiles.TileBytes;
import me.koply.kcommando.*;
import me.koply.kcommando.internal.CommandInfo;
import me.koply.kcommando.internal.KRunnable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.Arrays;

public class CommandHandlerWrapper extends CommandHandler<MessageReceivedEvent> {
    private final Parameters<MessageReceivedEvent> parameters;
    private final long selfUserID;
    public CommandHandlerWrapper(Parameters<MessageReceivedEvent> params) {
        super(params);
        this.parameters = params;
        this.selfUserID = params.getIntegration().getSelfID();
    }

    @Override
    public void processCommand(CProcessParameters<MessageReceivedEvent> params) {
        final long authorID = params.getAuthor().getId();
        if (authorID == selfUserID) return;
        if (!this.parameters.isReadBotMessages() && params.getAuthor().isBot()) return;
        if (params.isWebhookMessage()) return;

        if (blacklistCheck(params.getGuildID(), authorID, params.getChannelID())) {
            KRunnable<MessageReceivedEvent> callback = this.parameters.getIntegration().getBlacklistCallback();
            if (callback != null) {
                callback.run(params.getEvent());
            }
            return;
        }

        final String commandRaw = params.getRawCommand();
        final int resultPrefix = checkPrefix(commandRaw, params.getGuildID());
        if (resultPrefix == -1) return;

        final String prefix = commandRaw.substring(0,resultPrefix);
        final String[] cmdArgs = commandRaw.substring(resultPrefix).split(" ");

        KCommando.logger.info(String.format("Command received | User: %s | Guild: %s | Command: %s", params.getAuthor().getName(), params.getGuildName(), commandRaw));

        final String command = this.parameters.getCaseSensitivity().isPresent() ? cmdArgs[0] : cmdArgs[0].toLowerCase();

        if (!containsCommand(command)) { // native komut yok
            if (!runParcelCommand(cmdArgs)) {
                if (this.parameters.getIntegration().getSuggestionsCallback() != null) {
                    findSimilars(command, params.getEvent());
                }
            }
            return;
        }

        final CommandToRun<MessageReceivedEvent> ctr = parameters.getCommandMethods().get(command);
        final CommandInfo<MessageReceivedEvent> info = ctr.getClazz().getInfo();

        if (commandCheck(info, params) || cooldownCheck(info, params, authorID)) {
            return;
        }

        this.runCommand(info, ctr, params, cmdArgs, prefix);
    }

    private boolean runParcelCommand(String[] args) {
        long millis = System.currentTimeMillis();
        boolean isTileExecuted = ParcelAPI.executeEvent(new EventIdentifier(TileBytes.COMMAND_EVENT, Arrays.asList(args)));
        if (isTileExecuted) KCommando.logger.info("Last parcel took " + (System.currentTimeMillis() - millis) + "ms to execute.");
        return isTileExecuted;
    }
}