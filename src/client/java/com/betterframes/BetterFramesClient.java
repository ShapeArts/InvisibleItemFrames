package com.betterframes;
import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
public class BetterFramesClient implements ClientModInitializer {
 private static final Map<Integer,Boolean> HIDDEN_NAMES=new HashMap<>();
 public static boolean hideName(int id){return HIDDEN_NAMES.getOrDefault(id,false);}
 @Override public void onInitializeClient(){
  ClientPlayNetworking.registerGlobalReceiver(FramePackets.State.TYPE,(packet,ctx)->ctx.client().execute(()->{
   HIDDEN_NAMES.put(packet.id(),packet.hideName());
   if(packet.open())ctx.client().gui.setScreen(new FrameScreen(packet.id(),packet.hidden(),packet.hideName()));
  }));
 }
 public static void change(int id,boolean hidden,boolean hideName){
  HIDDEN_NAMES.put(id,hideName);ClientPlayNetworking.send(new FramePackets.Set(id,hidden,hideName));
 }
}
