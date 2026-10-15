package com.betterframes;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
public class FrameScreen extends Screen {
 private final int id; private boolean hidden,hideName; private Button frameButton,nameButton;
 public FrameScreen(int id,boolean hidden,boolean hideName){super(Component.literal("Item Frame Settings"));this.id=id;this.hidden=hidden;this.hideName=hideName;}
 @Override protected void init(){
  int x=width/2-110,y=height/2-48;
  frameButton=Button.builder(Component.empty(),b->{hidden=!hidden;change();}).bounds(x,y,220,20).build();
  nameButton=Button.builder(Component.empty(),b->{hideName=!hideName;change();}).bounds(x,y+26,220,20).build();
  addRenderableWidget(frameButton);addRenderableWidget(nameButton);
  addRenderableWidget(Button.builder(Component.literal("Done"),b->onClose()).bounds(x+60,y+62,100,20).build());
  refresh();
 }
 private void refresh(){frameButton.setMessage(Component.literal("Hide Item Frame: "+(hidden?"ON":"OFF")));nameButton.setMessage(Component.literal("Hide Item Name: "+(hideName?"ON":"OFF")));}
 private void change(){refresh();BetterFramesClient.change(id,hidden,hideName);}
 @Override public void extractRenderState(GuiGraphicsExtractor graphics,int mouseX,int mouseY,float delta){super.extractRenderState(graphics,mouseX,mouseY,delta);graphics.centeredText(font,title,width/2,height/2-76,0xffffff);}
 @Override public boolean isPauseScreen(){return false;}
}
