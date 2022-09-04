package net.lydia7635.screenshottodiscord.uploader;

import net.lydia7635.screenshottodiscord.ScreenshotToDiscord;
import net.lydia7635.screenshottodiscord.config.DiscordBotInfo;
import net.minecraft.client.MinecraftClient;

import java.io.*;
import java.net.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DiscordUploader {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static String createMessage(File screenshotDir, String filename, String username) {
        String message = "{" +
                "\"content\":\"" + username + " test by Lyu7 >>\"," +
                "\"type\":0," +
                "\"sticker_ids\":[]}";
                /*"\"attachments\":[{" +
                    "\"id\":\"0\",\"filename\":\"" + filename + "\"" +
                "}]}";*/
        return message;
    }
    public static void uploadImage(File screenshotDir, String filename) {
        String username = MinecraftClient.getInstance().getSession().getUsername();
        ScreenshotToDiscord.LOGGER.info("Player " + username + " is sending screenshot to Discord server...");
        String message = createMessage(screenshotDir, filename, username);

        cachedThreadPool.execute(() -> {
            Thread.currentThread().setName("Image Uploading to Discord");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                ScreenshotToDiscord.LOGGER.info("Thread interrupted");
            } finally {
                sendMessage(DiscordBotInfo.botToken, DiscordBotInfo.channelId, message);
            }
        });
    }

    public static void sendMessage(String botToken, String channelId, String message) {
        ScreenshotToDiscord.LOGGER.info("thread");
        try {

            URL url = new URL("https://discord.com/api/v9/channels/" + channelId + "/messages");
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bot " + botToken);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");

            OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(message);
            ScreenshotToDiscord.LOGGER.info(message);
            wr.flush();

            StringBuilder sb = new StringBuilder();
            int HttpResult = conn.getResponseCode();
            if (HttpResult == HttpURLConnection.HTTP_OK) {
                BufferedReader br = new BufferedReader(
                        new InputStreamReader(conn.getInputStream(), "utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line + "\n");
                }
                br.close();
                ScreenshotToDiscord.LOGGER.info("" + sb.toString());
            } else {
                ScreenshotToDiscord.LOGGER.info(conn.getResponseCode() + conn.getResponseMessage());
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
