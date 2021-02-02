package com.karpuzdev.parcel.main;

import com.karpuzdev.parcel.lang.ParcelAPI;
import com.karpuzdev.parcel.lang.internal.IParcelSource;
import com.karpuzdev.parcel.main.commando.CommandHandlerWrapper;
import com.karpuzdev.parcel.main.commands.HelpCommand;
import me.koply.kcommando.CommandToRun;
import me.koply.kcommando.KCommando;
import me.koply.kcommando.KInitializer;
import me.koply.kcommando.Parameters;
import me.koply.kcommando.integration.impl.jda.JDAIntegration;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.SelfUser;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.HashSet;
import java.util.Map;

public class Main extends JDAIntegration implements IParcelSource {

    public Main(JDA jda) {
        super(jda);
        File out = new File("out/");
        ParcelAPI.build(this, out);
    }

    @Override
    public File[] getParcels() {
        File parcelsFolder = new File("parcels/");
        parcelsFolder.mkdir();
        return parcelsFolder.listFiles();
    }

    private static final EmbedBuilder helpEmbed = new EmbedBuilder();
    public static EmbedBuilder getHelpEmbed() {
        return new EmbedBuilder(helpEmbed);
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        final String token = System.getenv("TOKEN");
        JDA jda = JDABuilder.createLight(token)
                .setAutoReconnect(true)
                .build();
        jda.awaitReady();

        Parameters<MessageReceivedEvent> params = new Parameters<>();
        KCommando<MessageReceivedEvent> kcm = new KCommando<>(new Main(jda), new KInitializer<>(params, CommandHandlerWrapper.class))
                .setOwners("291168238140653578","269140308208517130")
                .setPackage(HelpCommand.class.getPackage().getName())
                .setPrefix(".")
                .build();

        initHelpEmbed(kcm.getParameters().getCommandMethods(), jda.getSelfUser());
    }

    private static void initHelpEmbed(Map<String, CommandToRun<MessageReceivedEvent>> map, SelfUser self) {
        final HashSet<CommandToRun<MessageReceivedEvent>> set = new HashSet<>();
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CommandToRun<MessageReceivedEvent>> entry : map.entrySet()) {
            if (set.contains(entry.getValue())) continue;
            sb.append("`").append(entry.getKey()).append("` -> ").append(entry.getValue().getClazz().getInfo().getDescription()).append("\n");
            set.add(entry.getValue());
        }
        helpEmbed.addField("❯ Komutlar", sb.toString(), false)
                .setFooter(self.getName() + " by karpuz", self.getAvatarUrl());
    }

}