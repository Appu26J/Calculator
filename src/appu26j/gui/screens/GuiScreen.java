package appu26j.gui.screens;

import appu26j.assets.Assets;
import appu26j.gui.Gui;
import appu26j.gui.font.FontRenderer;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GuiScreen extends Gui
{
	protected FontRenderer fontRendererExtraBig, fontRendererBig, fontRendererMid, fontRendererMidSmall, fontRenderer;
	protected float width = 0, height = 0;
	
	public abstract void drawScreen(float mouseX, float mouseY);
	
	public void mouseClicked(int mouseButton, float mouseX, float mouseY)
	{
		;
	}
	
	public void mouseReleased(int mouseButton, float mouseX, float mouseY)
	{
		;
	}

	public void charTyped(char character, float mouseX, float mouseY)
	{
		;
	}

	public void keyPressed(int key, float mouseX, float mouseY)
	{
		;
	}
	
	public void initGUI(float width, float height)
	{
		this.width = width;
		this.height = height;
		this.fontRenderer = new FontRenderer(Assets.getAsset("segoeui.ttf"), 28);
		this.fontRendererMidSmall = new FontRenderer(Assets.getAsset("segoeui.ttf"), 36);
		this.fontRendererMid = new FontRenderer(Assets.getAsset("segoeui.ttf"), 54);
		this.fontRendererBig = new FontRenderer(Assets.getAsset("segoeui.ttf"), 72);
		this.fontRendererExtraBig = new FontRenderer(Assets.getAsset("segoeui.ttf"), 88);
	}
	
	protected boolean isInsideBox(float mouseX, float mouseY, float x, float y, float width, float height)
	{
		return mouseX > x && mouseX < width && mouseY > y && mouseY < height;
	}
	
	public ArrayList<FontRenderer> getFontRenderers()
	{
		return new ArrayList<>(Arrays.asList(this.fontRenderer, this.fontRendererMidSmall, this.fontRendererMid, this.fontRendererBig, this.fontRendererExtraBig));
	}
}
