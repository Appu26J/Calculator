package appu26j.gui;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.GL14;

public class GlStateManager
{
	public static void enableAlpha()
	{
		glEnable(GL_ALPHA);
	}
	
	public static void disableAlpha()
	{
		glDisable(GL_ALPHA);
	}
	
	public static void enableBlend()
	{
		glEnable(GL_BLEND);
	}
	
	public static void disableBlend()
	{
		glDisable(GL_BLEND);
	}
	
	public static void enableTexture2D()
	{
		glEnable(GL_TEXTURE_2D);
	}
	
	public static void disableTexture2D()
	{
		glDisable(GL_TEXTURE_2D);
	}
	
	public static void tryBlendFuncSeparate(int sFactorRGB, int dFactorRGB, int sFactorAlpha, int dFactorAlpha)
	{
		GL14.glBlendFuncSeparate(sFactorRGB, dFactorRGB, sFactorAlpha, dFactorAlpha);
	}
	
	public static void alphaFunc(int func, float ref)
	{
		glAlphaFunc(func, ref);
	}
	
	public static void color(float red, float green, float blue, float alpha)
	{
		glColor4f(red, green, blue, alpha);
	}
	
	public static void scale(float x, float y, float z)
	{
		glScalef(x, y, z);
	}
	
	public static void pushMatrix()
	{
		glPushMatrix();
	}
	
	public static void popMatrix()
	{
		glPopMatrix();
	}
}
