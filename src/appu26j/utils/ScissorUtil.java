package appu26j.utils;

import org.lwjgl.opengl.GL11;

public class ScissorUtil
{
    public static void scissor(float x, float y, float width, float height)
    {
        GL11.glScissor((int) x, (int) (800 - height), (int) (width - x), (int) (height - y));
    }
}
