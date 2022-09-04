# Fabric Example Mod

## Setup

For setup instructions please see the [fabric wiki page](https://fabricmc.net/wiki/tutorial:setup) that relates to the IDE that you are using.

## Build
Create file name `DiscordBotInfo.java` under `src/main/java/net/lydia7635/screenshottodiscord/config` folder.
```
package net.lydia7635.screenshottodiscord.config;

public class DiscordBotInfo {
    public static final String botToken = "YOUR-BOT-TOKEN";
    public static final String channelId = "DISCORD-CHANNEL-ID";
}
```
And run `./gradlew build` or build it in IntelliJ.