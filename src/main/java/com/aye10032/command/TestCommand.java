package com.aye10032.command;

import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandEvent;

public class TestCommand extends Command {

    public TestCommand(){
        super.name = "nmsl";
    }
    @Override
    protected void execute(CommandEvent event) {
        event.reply("nmsl " + event.getChannel().getName());
    }
}
