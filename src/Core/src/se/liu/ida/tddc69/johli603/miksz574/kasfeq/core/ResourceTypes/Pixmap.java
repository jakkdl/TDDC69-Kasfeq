package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.ResourceTypes;

import org.lwjgl.BufferUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;

/** \class Pixmap
 * \brief A pixmap is the raw data of the colors in an image
 */
public class Pixmap {
    private final ByteBuffer pixels;
    private final int width;
    private final int height;
    private final int bytesPerPixel;

    public static final int ARGB_BYTES_PER_PIXEL = 4;
    public static final int RGB_BYTES_PER_PIXEL = 3;

    /**
     * Main constructor for the pixmap
     * @param width The width of the image
     * @param height The height of the image
     * @param bytesPerPixel The Bytes Per Pixel of the image
     */
    public Pixmap(int width, int height, int bytesPerPixel) {
        pixels = BufferUtils.createByteBuffer(width*height*bytesPerPixel);
        this.width = width;
        this.height = height;
        this.bytesPerPixel = bytesPerPixel;
    }

    /**
     * \brief Sets the pixel data to the given array
     * @param pixels Array containing the nex pixel data
     */
    public void set(byte[] pixels) {
        this.pixels.clear();
        this.pixels.put(pixels);
        this.pixels.flip();
    }

    /**
     * \brief Get the pixel data
     * @return ByteBuffer containing the pixel data
     */
    public ByteBuffer getData() {
        return pixels;
    }

    /**
     * \brief Get the width of the image
     * @return Width of the image
     */
    public int getWidth() {
        return width;
    }

    /**
     * \brief Get the height of the image
     * @return Height of the image
     */
    public int getHeight() {
        return height;
    }

    /**
     * \brief Get the size of each pixel in bytes
     * @return Bytes per pixel
     */
    public int getBytesPerPixel() {
        return bytesPerPixel;
    }

    /**
     * \brief Loads a pixmap object from an image file
     * @param filename The filename of the image to load
     * @return Pixmap object with the image data from the file
     * @throws IOException Thrown by ImageIO.read
     */
    public static Pixmap fromFile(String filename) throws IOException {
        final BufferedImage image = ImageIO.read(new File(filename));
        final int width = image.getWidth();
        final int height = image.getHeight();
        final boolean hasAlphaChannel = image.getAlphaRaster() != null;
        final byte[] pixels = ((DataBufferByte)image.getRaster().getDataBuffer()).getData();

        // 8 bits per byte
        final int bytesPerPixel = image.getColorModel().getPixelSize()/8;

        Pixmap pixmap = new Pixmap(image.getWidth(), image.getHeight(), bytesPerPixel);
        pixmap.set(pixels);

        return pixmap;
    }
}
