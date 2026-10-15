package com.betterframes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
public final class FramePackets {
 private FramePackets() {}
 public record State(int id, boolean hidden, boolean hideName, boolean open) implements CustomPacketPayload {
  public static final Type<State> TYPE = new Type<>(Identifier.fromNamespaceAndPath("invisibleitemframes","state"));
  public static final StreamCodec<FriendlyByteBuf,State> CODEC = StreamCodec.of(
   (buf,p)->{buf.writeVarInt(p.id());buf.writeBoolean(p.hidden());buf.writeBoolean(p.hideName());buf.writeBoolean(p.open());},
   buf->new State(buf.readVarInt(),buf.readBoolean(),buf.readBoolean(),buf.readBoolean()));
  public Type<? extends CustomPacketPayload> type(){return TYPE;}
 }
 public record Set(int id,boolean hidden,boolean hideName) implements CustomPacketPayload {
  public static final Type<Set> TYPE = new Type<>(Identifier.fromNamespaceAndPath("invisibleitemframes","set"));
  public static final StreamCodec<FriendlyByteBuf,Set> CODEC = StreamCodec.of(
   (buf,p)->{buf.writeVarInt(p.id());buf.writeBoolean(p.hidden());buf.writeBoolean(p.hideName());},
   buf->new Set(buf.readVarInt(),buf.readBoolean(),buf.readBoolean()));
  public Type<? extends CustomPacketPayload> type(){return TYPE;}
 }
}
