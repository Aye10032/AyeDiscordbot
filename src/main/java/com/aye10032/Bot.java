package com.aye10032;

import com.aye10032.command.TestCommand;
import com.jagrosh.jdautilities.command.CommandClient;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class Bot {
    private TimeStack timeStack;
    private Bot(String token, String id) throws LoginException {
        final JDA jda = new JDABuilder(AccountType.BOT)
                .setToken(token).build();

        CommandClientBuilder builder = new CommandClientBuilder();
        builder.setPrefix(".");
        builder.setOwnerId(id);
//        builder.setHelpWord("help");
        builder.setActivity(Activity.playing("BanG Dream!"));

        CommandClient client = builder.build();
        client.addCommand(new TestCommand(jda));
        jda.addEventListener(client);

        timeStack = new TimeStack(jda);
        timeStack.start();
    }

    public static void main(String[] args) throws LoginException {
        long enable = System.currentTimeMillis();
        Bot bot = new Bot(args[0], args[1]);

        System.out.println("bot enabled in" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis() - enable) + "ms");
    }
}
