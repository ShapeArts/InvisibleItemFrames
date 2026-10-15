package com.betterframes.mixin;
import com.betterframes.BetterFramesClient;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.world.entity.decoration.ItemFrame;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
@Mixin(ItemFrameRenderer.class)
public abstract class FrameNameMixin {
 @Inject(method="shouldShowName",at=@At("HEAD"),cancellable=true)
 private void invisibleitemframes$hideName(ItemFrame frame,double distance,CallbackInfoReturnable<Boolean> cir){
  if(BetterFramesClient.hideName(frame.getId()))cir.setReturnValue(false);
 }
}
