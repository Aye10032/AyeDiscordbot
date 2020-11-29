package com.aye10032;

import com.aye10032.util.BiliUtil;
import net.dv8tion.jda.api.JDA;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static java.lang.Thread.sleep;

/**
 * @Author Aye10032
 * @Date Created in 9:58 10.6
 */
public class TimeStack extends Thread {

    private JDA jda;
    private BiliUtil biliUtil;
    private Map<String, Long> map;

    public TimeStack(JDA jda) {
        this.jda = jda;
        biliUtil = new BiliUtil();
    }

    @Override
    public void run() {
        while (true) {
            try {
                biliUtil.update();
                map = biliUtil.getList_map();
                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                System.out.println(format.format(date) + " " + map.size());
                if (!map.isEmpty()) {
                    for (Map.Entry<String, Long> entry : map.entrySet())
                        Objects.requireNonNull(jda.getTextChannelById(751065044518830090L)).sendMessage(entry.getKey()).queue();
                }
                sleep(30 * 60 * 1000);
            } catch (InterruptedException | NullPointerException | IllegalArgumentException e) {
                e.printStackTrace();
            }
        }
    }
}
