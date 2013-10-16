package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.geom.Vector2f;

/**
 * \class Vector2d
 * \brief A wrapper for the org.newdawn.slick.geom.Vector2f that uses doubles in function parameters
 */
public class Vector2d {
    private Vector2f vector;

    public Vector2d() {
        this.vector = new Vector2f();
    }

    public Vector2d(Vector2f v) {
        this.vector = v;
    }

    public Vector2d(double x, double y) {
        this.vector = new Vector2f((float) x, (float) y);
    }

    public Vector2d(double theta) {
        this.vector = new Vector2f((float) Math.toDegrees(theta));
    }

    public Vector2d add(Vector2d v) {
        return new Vector2d(vector.copy().add(v.vector));
    }

// --Commented out by Inspection START (10/16/13 10:21 PM):
//    public void set(double x, double y) {
//        vector = vector.set((float)x, (float)y);
//    }
// --Commented out by Inspection STOP (10/16/13 10:21 PM)

    public Vector2d copy() {
        return new Vector2d(vector.copy());
    }

    public double getTheta() {
        return Math.toRadians(vector.getTheta());
    }

// --Commented out by Inspection START (10/16/13 10:21 PM):
//    public void setTheta(double theta) {
//        vector.setTheta(Math.toDegrees(theta));
//    }
// --Commented out by Inspection STOP (10/16/13 10:21 PM)

    public double getX() {
        return vector.getX();
    }

    public void setX(double x) {
        vector.set((float)x, vector.getY());
    }

    public void setY(double y) {
        vector.set(vector.getX(), (float)y);
    }

    public Vector2d scale(double a) {
        return new Vector2d(vector.copy().scale((float) a));
    }

// --Commented out by Inspection START (10/16/13 10:21 PM):
//    public Vector2d add(double theta) {
//        return new Vector2d(vector.copy().add(theta));
//    }
// --Commented out by Inspection STOP (10/16/13 10:21 PM)

// --Commented out by Inspection START (10/16/13 10:21 PM):
//    public Vector2d add(double x, double y) {
//        return new Vector2d(vector.copy().add(new Vector2f((float)x, (float)y)));
//    }
// --Commented out by Inspection STOP (10/16/13 10:21 PM)

    public double length() {
        return vector.length();
    }

    public double getY() {
        return vector.getY();
    }

// --Commented out by Inspection START (10/16/13 10:21 PM):
//    public void set(Vector2d other) {
//        vector.set(other.vector);
//    }
// --Commented out by Inspection STOP (10/16/13 10:21 PM)

// --Commented out by Inspection START (10/16/13 10:21 PM):
//    public Vector2d projectOntoUnit(Vector2d target) {
//        Vector2d result = new Vector2d();
//        vector.projectOntoUnit(target.vector, result.vector);
//        return result;
//    }
// --Commented out by Inspection STOP (10/16/13 10:21 PM)

// --Commented out by Inspection START (10/16/13 10:22 PM):
//    public Vector2d negate() {
//        return new Vector2d(vector.negate());
//    }
// --Commented out by Inspection STOP (10/16/13 10:22 PM)

    /*public float dot(Vector2f other) {
        return vector.dot(other);
    }*/

    /*public Vector2f getNormal() {
        return vector.getNormal();
    }*/

    /*public double distance(Vector2f other) {
        return vector.distance(other);
    }*/

    /*public Vector2f normalise() {
        return vector.normalise();
    }*/

    /*public Vector2f sub(Vector2f v) {
        return vector.sub(v);
    }*/

    /*public Vector2f set(float[] pt) {
        return vector.set(pt);
    }*/

    /*public float distanceSquared(Vector2f other) {
        return vector.distanceSquared(other);
    }*/

    /*public Vector2f sub(double theta) {
        return vector.sub(theta);
    }*/

    /*public Vector2f getPerpendicular() {
        return vector.getPerpendicular();
    }*/

    /*public float lengthSquared() {
        return vector.lengthSquared();
    }*/

    /*public Vector2f negateLocal() {
        return vector.negateLocal();
    }*/


/*
    @Override
    public boolean equals(Object other) {
        return vector.equals(other);
    }
    @Override
    public int hashCode() {
        return getNormal().hashCode();
    }*/
}
