package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.geom.Vector2f;

public class PhysicsEngine {
    private PlayingField playingField;

    public PhysicsEngine() {

    }

    public PhysicsEngine(PlayingField playingField) {
        this.playingField = playingField;
    }

    public void dumbCollisions(GameObject[] objects) {
        for (GameObject obj : objects) {

        }
    }


    public void update(GameObject object, int time) {
        //calculate forces, acceleration and velocity

        //add gravity
        object.addInstantForce(new Vector2f(0, 0.02f));

        //calculate acceleration from forces
        Vector2f totalForce = object.getContForce().copy().add(object.getInstantForce());
        Vector2f acceleration = addForces(totalForce, object.getMass());

        //update velocities according to acceleration
        addAcceleration(object.getVelocity(), acceleration);

        //add friction
        object.setVelocity(object.getVelocity().scale(0.97f));

        object.setInstantForce(new Vector2f(0,0));



        //then see how far the object can travel
        float distance = checkMapCollision(object);
        //System.out.println(distance);
        //System.out.println(object.getVelocity().length());

        //travel that length
        object.setPosition(move(object.getPosition(), object.getVelocity().scale(distance/object.getVelocity().length())));

        //if we collided, remove velocity
        if (distance != object.getVelocity().length()) {
            System.out.println("collision");
            object.setVelocity(new Vector2f(0,0));
        }


    }

    private float checkMapCollision(GameObject obj) {
        //TODO: BROKEN
        float objVel = obj.getVelocity().length();
        for (float dv=1; dv < objVel; dv+=0.01) {
            float dvx = obj.getVelocity().scale(dv/objVel).getX();
            float dvy = obj.getVelocity().scale(dv/objVel).getY();
            for (int i=0; i<2; i++) {
                for (int j=0; j<2; j++) {
                    Point point = new Point((int)(obj.getPosition().getX() + obj.getWidth() *i + dvx),
                                            (int)(obj.getPosition().getY() + obj.getHeight()*j + dvy));
                    if (point.getX() < 0 || point.getY() < 0 || point.getX() > playingField.getPixelWidth() || point.getY() > playingField.getPixelHeight()) {
                        return dv-0.01f;
                    }
                    if (playingField.getPixel(point).getState() != MapBlock.States.EMPTY) {
                        return dv-0.01f;
                    }
                }
            }
        }
        return objVel;
    }


    private Vector2f move(final Vector2f point, final Vector2f velocity) {
        point.add(velocity);
        return point;
        /*Vector2f endPoint = point.copy();
        endPoint.add(velocity);
        if (endPoint.getX() > playingField.getPixelWidth() || endPoint.getX() < 0) {
            return point.copy();
        }
        if (endPoint.getY() > playingField.getPixelWidth() || endPoint.getY() < 0) {
            return point.copy();
        }
        if (playingField.getPixel( new Point((int)endPoint.getX(), (int)endPoint.getY())).getState() != MapBlock.States.EMPTY) {
            return point.copy();
        }
        return endPoint.copy();
        //else move as far as possible*/
    }

    private Vector2f addForces(Vector2f force, float mass) {
        return force.scale(1/mass);
    }

    private void addAcceleration(Vector2f velocity, Vector2f acceleration) {
        velocity.add(acceleration);
    }
}
