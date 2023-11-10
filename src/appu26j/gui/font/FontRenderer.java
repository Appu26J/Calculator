package appu26j.gui.font;

import appu26j.gui.GlStateManager;
import org.lwjgl.BufferUtils;
import org.lwjgl.stb.STBTTAlignedQuad;
import org.lwjgl.stb.STBTTBakedChar;
import org.lwjgl.stb.STBTTFontinfo;
import org.lwjgl.stb.STBTruetype;
import org.lwjgl.system.MemoryStack;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

import static org.lwjgl.opengl.GL11.*;
 
public class FontRenderer
{
    private final HashMap<Character, Float> cachedWidths = new HashMap<>();
    public static final int CHAR_DATA_MALLOC_SIZE = 96;
    public static final int FONT_TEX_W = 512;
    public static final int FONT_TEX_H = FONT_TEX_W;
    public static final int BAKE_FONT_FIRST_CHAR = 32;
    public static final int GLYPH_COUNT = CHAR_DATA_MALLOC_SIZE;
    protected final STBTTBakedChar.Buffer charData;
    protected final STBTTFontinfo fontInfo;
    protected final int fontSize, textureID;
    protected final float ascent, descent, lineGap;
     
    public FontRenderer(File font, int fontSize)
    {
        this.fontSize = fontSize;
        this.charData = STBTTBakedChar.malloc(CHAR_DATA_MALLOC_SIZE);
        this.fontInfo = STBTTFontinfo.create();
        int textureID = 0;
        float ascent = 0, descent = 0, lineGap = 0;
        
        try
        {
            ByteBuffer ttfFileData = this.getByteBuffer(font);
            ByteBuffer texData = BufferUtils.createByteBuffer(FONT_TEX_W * FONT_TEX_H);
            STBTruetype.stbtt_BakeFontBitmap(ttfFileData, fontSize, texData, FONT_TEX_W, FONT_TEX_H, BAKE_FONT_FIRST_CHAR, charData);
            
            try (MemoryStack stack = MemoryStack.stackPush())
            {
                STBTruetype.stbtt_InitFont(this.fontInfo, ttfFileData);
                float pixelScale = STBTruetype.stbtt_ScaleForPixelHeight(this.fontInfo, fontSize);
                IntBuffer ascentBuffer = stack.ints(0);
                IntBuffer descentBuffer = stack.ints(0);
                IntBuffer lineGapBuffer = stack.ints(0);
                STBTruetype.stbtt_GetFontVMetrics(this.fontInfo, ascentBuffer, descentBuffer, lineGapBuffer);
                ascent = ascentBuffer.get(0) * pixelScale;
                descent = descentBuffer.get(0) * pixelScale;
            }
            
            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_ALPHA, FONT_TEX_W, FONT_TEX_H, 0, GL_ALPHA, GL_UNSIGNED_BYTE, texData);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glBindTexture(GL_TEXTURE_2D, 0);
        }
        
        catch (Exception e)
        {
        	e.printStackTrace();
        }
        
        this.textureID = textureID;
        this.ascent = ascent;
        this.descent = descent;
        this.lineGap = lineGap;
        char[] allLetters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789~`!@#$%^&*()_+-={}[];':\"<>?,./ ".toCharArray();

        for (char letter : allLetters)
        {
            this.cachedWidths.put(letter, getCharWidth(letter));
        }
    }
    
    public void shutdown()
    {
        this.charData.free();
        this.fontInfo.free();
        
        if (this.textureID != 0)
        {
            glDeleteTextures(this.textureID);
        }
    }
    
    public ByteBuffer getByteBuffer(File file) throws IOException 
    {
        ByteBuffer buffer;
        
        try (FileInputStream fis = new FileInputStream(file); FileChannel fc = fis.getChannel())
        {
            buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        }
        
        return buffer;
    }
    
    public void drawStringWithShadow(String text, float x, float y, Color color)
    {
    	this.drawString(text, x + ((float) this.fontSize / 12), y + ((float) this.fontSize / 12), color.darker().darker().darker().darker());
    	this.drawString(text, x, y, color);
    }
    
    public void drawString(String text, float x, float y, Color color)
    {
        y += this.ascent;

        try (MemoryStack stack = MemoryStack.stackPush())
        {
            FloatBuffer xPosition = stack.mallocFloat(1);
            FloatBuffer yPosition = stack.mallocFloat(1);
            xPosition.put(x);
            yPosition.put(y);
            xPosition.flip();
            yPosition.flip();
            STBTTAlignedQuad stbttAlignedQuad = STBTTAlignedQuad.malloc(stack);
            glBindTexture(GL_TEXTURE_2D, this.textureID);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
			GlStateManager.enableBlend();
			GlStateManager.tryBlendFuncSeparate(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA, 1, 0);
			GlStateManager.alphaFunc(516, 0);
			GlStateManager.color(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
            glBegin(GL_TRIANGLES);
            int firstCP = BAKE_FONT_FIRST_CHAR;
            int lastCP = BAKE_FONT_FIRST_CHAR + GLYPH_COUNT - 1;
            
            for (int i = 0; i < text.length(); i++)
            {
                int codePoint = text.codePointAt(i);
                
                if (codePoint == '§')
                {
                	GlStateManager.color(color.getRed() / 340F, color.getGreen() / 340F, color.getBlue() / 340F, color.getAlpha() / 340F);
                	continue;
                }
                
                if (codePoint == '\n')
                {
                	xPosition.put(0, x);
                    yPosition.put(0, yPosition.get(0) + fontSize);
                    continue;
                }
                
                else if (codePoint < firstCP || codePoint > lastCP)
                {
                    continue;
                }
                
                STBTruetype.stbtt_GetBakedQuad(this.charData, FONT_TEX_W, FONT_TEX_H, codePoint - firstCP, xPosition, yPosition, stbttAlignedQuad, true);
                glTexCoord2f(stbttAlignedQuad.s0(), stbttAlignedQuad.t0());
                glVertex2f(stbttAlignedQuad.x0(), stbttAlignedQuad.y0());
                glTexCoord2f(stbttAlignedQuad.s0(), stbttAlignedQuad.t1());
                glVertex2f(stbttAlignedQuad.x0(), stbttAlignedQuad.y1());
                glTexCoord2f(stbttAlignedQuad.s1(), stbttAlignedQuad.t1());
                glVertex2f(stbttAlignedQuad.x1(), stbttAlignedQuad.y1());
                glTexCoord2f(stbttAlignedQuad.s1(), stbttAlignedQuad.t1());
                glVertex2f(stbttAlignedQuad.x1(), stbttAlignedQuad.y1());
                glTexCoord2f(stbttAlignedQuad.s1(), stbttAlignedQuad.t0());
                glVertex2f(stbttAlignedQuad.x1(), stbttAlignedQuad.y0());
                glTexCoord2f(stbttAlignedQuad.s0(), stbttAlignedQuad.t0());
                glVertex2f(stbttAlignedQuad.x0(), stbttAlignedQuad.y0());
            }
            
            glEnd();
			GlStateManager.disableBlend();
			GlStateManager.disableAlpha();
			GlStateManager.disableTexture2D();
            glBindTexture(GL_TEXTURE_2D, 0);
        }
    }

    public float getStringWidth(String text)
    {
        float length = 0;

        for (char character : text.toCharArray())
        {
            if (this.cachedWidths.containsKey(character))
            {
                length += this.cachedWidths.get(character);
            }

            else
            {
                float charWidth = this.getCharWidth(character);
                this.cachedWidths.put(character, charWidth);
                length += charWidth;
            }
        }

        return length;
    }

    private float getCharWidth(char character)
    {
        float length = 0;

        try (MemoryStack memoryStack = MemoryStack.stackPush())
        {
            IntBuffer advancedWidth = memoryStack.mallocInt(1);
            IntBuffer leftSideBearing = memoryStack.mallocInt(1);
            STBTruetype.stbtt_GetCodepointHMetrics(this.fontInfo, character, advancedWidth, leftSideBearing);
            length += advancedWidth.get(0);
        }

        return length * STBTruetype.stbtt_ScaleForPixelHeight(this.fontInfo, this.fontSize);
    }
}