package appu26j;

import org.lwjgl.system.MemoryStack;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.stb.STBImage.stbi_load;

public class ImageParser
{
    private final int width, height;
    private final ByteBuffer image;

    public int getWidth()
    {
        return this.width;
    }

    public int getHeight()
    {
        return this.height;
    }

    public ByteBuffer getImage()
    {
        return this.image;
    }

    public ImageParser(int width, int height, ByteBuffer image)
    {
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public static ImageParser loadImage(File path)
    {
        try (MemoryStack stack = MemoryStack.stackPush())
        {
            IntBuffer component = stack.mallocInt(1);
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            ByteBuffer image = stbi_load(path.getAbsolutePath(), width, height, component, 4);
            return new ImageParser(width.get(), height.get(), image);
        }
    }
}