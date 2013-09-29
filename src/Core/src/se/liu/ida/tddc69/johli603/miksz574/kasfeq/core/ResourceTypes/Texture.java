package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.ResourceTypes;

import org.lwjgl.opengl.GL11;

import java.io.IOException;

/** \class Texture
 * \brief Represents a loaded texture in the game
 */
public class Texture {
    private final Pixmap pixmap;
    private int textureID;

    public Texture(Pixmap pixmap) {
        this.pixmap = pixmap;
        GL11.glEnable(getTarget());
        this.textureID = GL11.glGenTextures();
        bind();
    }

    public Texture(String filename) throws IOException {
        this(Pixmap.fromFile(filename));
    }

    public int getTarget() {
        return GL11.GL_TEXTURE_2D;
    }

    public int getID() {
        return textureID;
    }

    public void upload() {
        bind();
        GL11.glTexImage2D(getTarget(), 0, GL11.GL_RGBA, pixmap.getWidth(), pixmap.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_INT, pixmap.getData());
    }

    public void bind() {
        if(!valid()) {
            throw new IllegalStateException("Trying to bind a disposed texture");
        }

        GL11.glBindTexture(getTarget(), getID());
    }

    public void dispose() {
        if(valid()) {
            GL11.glDeleteTextures(getID());
            textureID = 0;
        }
    }

    public boolean valid() {
        return textureID != 0;
    }
}