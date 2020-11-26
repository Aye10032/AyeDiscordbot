package com.aye10032.util;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author Aye10032
 * @Date Created in 12:42 10.6
 */
public class BiliUtil {
    private OkHttpClient client;

    private Map<String, Long> list_map;

    public static void main(String[] args) {
//        Date date = new Date(TimeUnit.SECONDS.toMillis(1601732156));
//        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//
//        System.out.println(ft.format(date));
//        Date now = new Date();
//        long l = now.getTime() - date.getTime();
//        long min = ((l / (60 * 1000)));
//        System.out.println(min);
        BiliUtil biliUtil = new BiliUtil();
        biliUtil.update();
        Map<String, Long> map = biliUtil.getList_map();
        System.out.println(map.size());
    }

    public BiliUtil() {
        client = new OkHttpClient().newBuilder()
                .build();
        list_map = new HashMap<>();
    }

    public void update() {
        list_map.clear();
        int i = 1;
        int count = 0;
        boolean is_new = true;
        Date now = new Date();
        while (is_new) {
            Map<String, Long> map = update(i);
//            System.out.println(map.size());
            if (!map.isEmpty()) {
                for (Map.Entry<String, Long> entry : map.entrySet()) {
                    long l = now.getTime() - new Date(entry.getValue()).getTime();
                    long min = ((l / (60 * 1000)));

//                    System.out.println(min + entry.getKey());

                    if (min <= 30) {
                        add_video(entry.getKey(), entry.getValue());
                    }
                    count++;
                }
                if (count == list_map.size()) {
                    i++;
                }else {
                    is_new = false;
                }
            } else {
                is_new = false;
                i = 1;
                break;
            }
        }
    }

    public Map<String, Long> update(int page) {
        Map<String, Long> temp_map = new HashMap<>();
        try {
            String body = "";
            Request request = new Request.Builder()
                    .url("http://api.bilibili.com/x/space/arc/search?mid=1311124&ps=5&pn=" + page)
                    .method("GET", null)
                    .build();
            Response response = client.newCall(request).execute();

//            System.out.println(response.body().string());
            if (response.body() != null) {
                body = new String(response.body().bytes());

                JsonParser jsonParser = new JsonParser();
                JsonElement element = jsonParser.parse(body);

                if (element.isJsonObject()) {
//                    System.out.println(element);
                    JsonObject jsonObject = element.getAsJsonObject();

                    JsonArray video_list = jsonObject
                            .get("data").getAsJsonObject()
                            .get("list").getAsJsonObject()
                            .get("vlist").getAsJsonArray();

                    for (JsonElement jsonElement : video_list) {
                        JsonObject object = jsonElement.getAsJsonObject();
                        long time = TimeUnit.SECONDS.toMillis(object.get("created").getAsLong());
                        String url = "https://www.bilibili.com/video/" + object.get("bvid").getAsString();
//                        System.out.println(url + " " + time);
                        temp_map.put(url, time);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return temp_map;
    }

    public void add_video(String url, long time_stack) {
        this.list_map.put(url, time_stack);
    }

    public Map<String, Long> getList_map() {
        return list_map;
    }
}
