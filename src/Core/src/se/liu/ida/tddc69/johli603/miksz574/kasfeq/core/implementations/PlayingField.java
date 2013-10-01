package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

public class PlayingField {
    MapPixel[][] mapPixels; //X, Y ; 0,0 in bottom left
    int width;
    int height;

    PlayingField(int width, int height) {
        mapPixels = new MapPixel[width][height];
    }

    public void setPixel(Point point, MapPixel mapPixel) {
        mapPixels[point.getX()][point.getY()] = mapPixel;
    }

    public MapPixel getPixel(Point point) {
        return mapPixels[point.getX()][point.getY()];
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

}
