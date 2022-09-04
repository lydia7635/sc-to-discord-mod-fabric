package net.lydia7635.screenshottodiscord.mixin;

import net.lydia7635.screenshottodiscord.ScreenshotToDiscord;
import net.lydia7635.screenshottodiscord.uploader.DiscordUploader;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.util.ScreenshotRecorder;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.io.File;
import java.util.function.Consumer;

@Mixin(ScreenshotRecorder.class)
public class ScreenshotToDiscordMixin {
	@Inject(at = @At("TAIL"),
			method = "saveScreenshotInner(Ljava/io/File;Ljava/lang/String;Lnet/minecraft/client/gl/Framebuffer;Ljava/util/function/Consumer;)V",
			locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	private static void saveScreenshotMixin(File gameDirectory, String fileName, Framebuffer framebuffer, Consumer<Text> messageReceiver, CallbackInfo ci, NativeImage nativeImage, File file, File file2) {
		File screenshotDir = new File(gameDirectory, "screenshots");
		ScreenshotToDiscord.LOGGER.info("screenshot name: " + file2.getName());
		DiscordUploader.uploadImage(screenshotDir, file2.getName());
	}
}
