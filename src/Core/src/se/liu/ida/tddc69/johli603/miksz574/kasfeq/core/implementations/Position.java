package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

/**
 * Created with IntelliJ IDEA. User: johli603 Date: 2013-10-01 Time: 11:42 To change this template use File | Settings | File
 * Templates.
 */
public class Position implements Component
{
    private float x;
    private float y;

    public Position() {

    }

    public Position(float x, float y) {
	this.x = x;
	this.y = y;
    }

    public float getX() {
	return x;
    }

    public float getY() {
	return y;
    }

    public void setX(float x) {
	this.x = x;
    }

    public void setY(float y) {
	this.y = y;
    }
}
