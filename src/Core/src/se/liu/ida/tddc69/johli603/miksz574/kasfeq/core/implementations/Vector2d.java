package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.geom.Vector2f;

/**
 * \class Vector2d
 * \brief A wrapper for the org.newdawn.slick.geom.Vector2f that uses doubles and radians in function parameters
 * this is to reduce the number of casts needed in other parts of the code.
 */
public class Vector2d {
    private Vector2f vector;

    public Vector2d() {
        this.vector = new Vector2f();
    }

    public Vector2d(Vector2f v) {
        this.vector = v;
    }

    /**
     * \brief Creates a Vector2d given two coordinates
     *
     * @param x X coordinate of vector.
     * @param y Y coordinate of vector.
     */
    public Vector2d(double x, double y) {
        this.vector = new Vector2f((float) x, (float) y);
    }

    /**
     * \brief Creates a Vector2d of length 1 in theta direction
     *
     * @param theta Angle of vector.
     */
    public Vector2d(double theta) {
        this.vector = new Vector2f((float) Math.toDegrees(theta));
    }

    /**
     * \brief Adds a vector2d to another.
     *
     * @param v The vector2d to be added.
     * @return A new vector2d consisting of v and this.
     */
    public Vector2d add(Vector2d v) {
        return new Vector2d(vector.copy().add(v.vector));
    }

    /**
     * \brief Return X coordinate of vector.
     *
     * @return The X coordinate of this.
     */
    public double getX() {
        return vector.getX();
    }

    /**
     * \brief Set X coordinate of this.
     *
     * @param x X coordinate to set the vectors X value to.
     */
    public void setX(double x) {
        vector.set((float)x, vector.getY());
    }

    /**
     * \brief set Y coordinate of this.
     *
     * @param y Y coordinate to set the vector's Y value to.
     */
    public void setY(double y) {
        vector.set(vector.getX(), (float)y);
    }

    /**
     * \brief Scales the length of this vector.
     *
     * @param a What to scale the vector with.
     *
     * @return Returns a scaled copy of the vector2d instance.
     */
    public Vector2d scale(double a) {
        return new Vector2d(vector.copy().scale((float) a));
    }

    /**
     * \brief Returns the length of the vector2d.
     *
     * @return Returns the length of the vector2d.
     */
    public double length() {
        return vector.length();
    }

    /**
     * \brief Get the Y coordinate of the vector2d.
     *
     * @return Y coordinate of the vector2d.
     */
    public double getY() {
        return vector.getY();
    }

    /**
     * \brief Get the angle in radians of the vector2d.
     * @return Angle in radions of the vector2d.
     */
    public double getTheta() {
        return Math.toRadians(vector.getTheta());
    }
}
