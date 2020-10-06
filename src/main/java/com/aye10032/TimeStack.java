package com.aye10032;

import com.aye10032.util.BiliUtil;
import net.dv8tion.jda.api.JDA;

import java.util.Map;

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
                if (!map.isEmpty()) {
                    for (Map.Entry<String, Long> entry : map.entrySet()) {
                        jda.getTextChannelById(753968932519411784L).sendMessage(entry.getKey()).queue();
                    }
                }
                Thread.sleep(30 * 6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
//                e.printStackTrace();
            }
        }
    }
}
