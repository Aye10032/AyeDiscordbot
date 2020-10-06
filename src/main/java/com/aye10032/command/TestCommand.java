package com.aye10032.command;

import com.aye10032.util.BiliUtil;
import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.MessageChannel;
import org.jetbrains.annotations.NotNull;

public class TestCommand extends Command {
    JDA jda;

    public TestCommand(JDA jda){
        super.name = "nmsl";
        this.jda = jda;
    }
    @Override
    protected void execute(CommandEvent event) {
        event.reply("pua");
//        MessageChannel channel = event.getChannel();
//        System.out.println(channel.getIdLong());
//        jda.getTextChannelById(753968932519411784L).sendMessage("aaaaa").queue();

    }
}
