package com.betterframes;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import com.mojang.serialization.Codec;
import net.minecraft.resources.Identifier;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.ItemFrame;
public class BetterFrames implements ModInitializer {
 public static final AttachmentType<Boolean> FRAME_TAG=AttachmentRegistry.createPersistent(Identifier.fromNamespaceAndPath("invisibleitemframes","hidden"),Codec.BOOL);
 public static final AttachmentType<Boolean> NAME_TAG=AttachmentRegistry.createPersistent(Identifier.fromNamespaceAndPath("invisibleitemframes","hide_name"),Codec.BOOL);
 public static boolean flagged(ItemFrame f,AttachmentType<Boolean> tag){return Boolean.TRUE.equals(f.getAttached(tag));}
 public static void setFlag(ItemFrame f,AttachmentType<Boolean> tag,boolean value){f.setAttached(tag,value);}
 public static void send(ServerPlayer p,ItemFrame f,boolean open){ServerPlayNetworking.send(p,new FramePackets.State(f.getId(),flagged(f,FRAME_TAG),flagged(f,NAME_TAG),open));}
 @Override public void onInitialize(){
  PayloadTypeRegistry.clientboundPlay().register(FramePackets.State.TYPE,FramePackets.State.CODEC);
  PayloadTypeRegistry.serverboundPlay().register(FramePackets.Set.TYPE,FramePackets.Set.CODEC);
  UseEntityCallback.EVENT.register((p,w,h,e,hit)->{
   if(!(e instanceof ItemFrame f)||!p.isShiftKeyDown())return InteractionResult.PASS;
   if(!w.isClientSide()&&p instanceof ServerPlayer sp)send(sp,f,true);
   return InteractionResult.SUCCESS;
  });
  ServerPlayNetworking.registerGlobalReceiver(FramePackets.Set.TYPE,(packet,ctx)->{
   ServerPlayer p=ctx.player(); Entity e=p.level().getEntity(packet.id());
   if(!(e instanceof ItemFrame f)||p.distanceToSqr(f)>64.0)return;
   setFlag(f,FRAME_TAG,packet.hidden());setFlag(f,NAME_TAG,packet.hideName());
   f.setInvisible(packet.hidden());
   for(ServerPlayer watcher:PlayerLookup.tracking(f))send(watcher,f,false);
   send(p,f,false);
  });
  EntityTrackingEvents.START_TRACKING.register((e,p)->{if(e instanceof ItemFrame f)send(p,f,false);});
 }
}
