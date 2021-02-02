package com.karpuzdev.parcel.main.commands;

import com.karpuzdev.parcel.main.Main;
import com.karpuzdev.parcel.main.util.Util;
import me.koply.kcommando.integration.impl.jda.JDACommand;
import me.koply.kcommando.internal.annotations.Commando;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jetbrains.annotations.NotNull;

@Commando(name="Yardım",aliases={"help", "yardım"}, description = "Komut listesini görmenize yarar.")
public final class HelpCommand extends JDACommand {
    @Override
    public final boolean handle(@NotNull MessageReceivedEvent e) {
        e.getChannel().sendMessage(Main.getHelpEmbed()
                .setAuthor(e.getAuthor().getName(), null, e.getAuthor().getAvatarUrl())
                .setColor(Util.randomColor())
                .build()).queue();
        return true;
    }
}