package com.karpuzdev.parcel.main;

import com.karpuzdev.parcel.lang.ParcelAPI;
import com.karpuzdev.parcel.main.commands.HelpCommand;
import com.karpuzdev.parcel.lang.internal.IParcelSource;
import me.koply.kcommando.CommandToRun;
import me.koply.kcommando.KCommando;
import me.koply.kcommando.integration.impl.jda.JDAIntegration;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.SelfUser;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

public class Main extends JDAIntegration implements IParcelSource {

    public Main(JDA jda) {
        super(jda);
        ParcelAPI.build(this);
    }

    @Override
    public File[] getParcels() {
        return new File("parcels/").listFiles();
    }

    private static final EmbedBuilder helpEmbed = new EmbedBuilder();
    public static EmbedBuilder getHelpEmbed() {
        return new EmbedBuilder(helpEmbed);
    }

    public static void main(String[] args) throws LoginException, InterruptedException {
        final Scanner sc = new Scanner(System.in);
        System.out.println("Token: ");
        final String token = sc.nextLine();
        JDA jda = JDABuilder.createLight(token)
                .setAutoReconnect(true)
                .build();
        jda.awaitReady();

        KCommando kcm = new KCommando(new Main(jda))
                .setOwners("291168238140653578","269140308208517130")
                .setPackage(HelpCommand.class.getPackage().getName())
                .setPrefix(".")
                .build();

        initHelpEmbed(kcm.getParameters().getCommandMethods(), jda.getSelfUser());
    }

    private static void initHelpEmbed(Map<String, CommandToRun> map, SelfUser self) {
        final HashSet<CommandToRun> set = new HashSet<>();
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, CommandToRun> entry : map.entrySet()) {
            if (set.contains(entry.getValue())) continue;
            sb.append("`").append(entry.getKey()).append("` -> ").append(entry.getValue().getClazz().getInfo().getDescription()).append("\n");
            set.add(entry.getValue());
        }
        helpEmbed.addField("❯ Komutlar", sb.toString(), false)
                .setFooter(self.getName() + " by karpuz", self.getAvatarUrl());
    }

}