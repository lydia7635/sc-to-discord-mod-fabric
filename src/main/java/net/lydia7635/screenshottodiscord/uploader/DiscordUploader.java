package net.lydia7635.screenshottodiscord.uploader;

import net.lydia7635.screenshottodiscord.ScreenshotToDiscord;
import net.lydia7635.screenshottodiscord.config.DiscordBotInfo;
import net.minecraft.client.MinecraftClient;

import java.io.*;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordUploader {
    private static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

    private static String createMessage(File screenshotFile, String username) {
        return "{" +
                "\"content\":\"" + username + " >>\"," +
                "\"type\":0," +
                "\"sticker_ids\":[]," +
                "\"attachments\":[{" +
                    "\"id\":\"0\"," +
                "   \"filename\":\"" + screenshotFile.getName() + "\"" +
                "}]}";
    }
    public static void uploadImage(File screenshotFile) {
        String username = MinecraftClient.getInstance().getSession().getUsername();
        ScreenshotToDiscord.LOGGER.info("Player " + username + " is sending screenshot to Discord server...");
        String message = createMessage(screenshotFile, username);

        cachedThreadPool.execute(() -> {
            Thread.currentThread().setName("Image Uploading to Discord");
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                ScreenshotToDiscord.LOGGER.info("Thread interrupted");
            } finally {
                sendMessage(DiscordBotInfo.botToken, DiscordBotInfo.channelId, screenshotFile, message);
            }
        });
    }

    public static void sendMessage(String botToken, String channelId, File screenshotFile, String message) {
        ScreenshotToDiscord.LOGGER.info("thread");
        try {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bot " + botToken);

            MultipartUtility multipart = new MultipartUtility("https://discord.com/api/v9/channels/" + channelId + "/messages", "utf-8", headers);

            multipart.addFormField("payload_json", message);
            multipart.addFilePart("files[0]", screenshotFile);

            List<String> response = multipart.finish();
            for (String line : response) {
                ScreenshotToDiscord.LOGGER.info("response: " + line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
