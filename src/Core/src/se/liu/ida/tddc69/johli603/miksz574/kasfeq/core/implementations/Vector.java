package se.liu.edia.tddc69.johli603.miksz574.kasfeq.core.implementations;


public class Vector
{
    private int x;
    private int y;

    public Vector() {
        this.x = 0;
        this.y = 0;
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public void setY(int y) {
        this.y = y;
    }

    public void changeX(int x) {
        this.x += x;
    }

    public void changeY(int y) {
        this.y += y;
    }

    public int length() {
        return (int) Math.sqrt(x*x+y*y);
    }

    public float direction() { //0 to the right, pi/2 upwards
        return Math.atan2(y, x);
    }

    public void add(Vector vector) {
        x += vector.x;
        y += vector.y;
    }

    public void multiply(int constant) {
        x *= constant;
        y *= constant;
    }
}
