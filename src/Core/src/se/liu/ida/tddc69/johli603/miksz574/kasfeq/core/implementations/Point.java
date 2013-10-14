package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

/**
 * \class Point
 * \brief Represents a single point on the screen
 */
public class Point {
    private int x;
    private int y;

    /**
     * \brief Point constructor
     *
     * @param x The X coordinate for the point
     * @param y The Y coordinate for the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * @return Returns the X coordinate for the point
     */
    public int getX() {
        return x;
    }

    /**
     * \brief Sets the X coordinate for the point
     *
     * @param x The new X coordinate for the point
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return Returns the Y coordinate for the point
     */
    public int getY() {
        return y;
    }

    /**
     * \brief Sets the Y coordinate for the point
     *
     * @param y The new Y coordinate for the point
     */
    public void setY(int y) {
        this.y = y;
    }
}
