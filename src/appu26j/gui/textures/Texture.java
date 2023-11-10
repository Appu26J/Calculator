package appu26j.gui.textures;

public class Texture
{
    private final int id, width, height;

    public Texture(int id, int width, int height)
    {
        this.id = id;
        this.width = width;
        this.height = height;
    }
    
    public int getId()
    {
        return this.id;
    }

    public int getWidth()
    {
        return this.width;
    }
    
    public int getHeight()
    {
        return this.height;
    }
}
