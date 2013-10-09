package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.geom.Vector2f;

public class PhysicsEngine {
    private class MapCollisionResult {
        double xDistance;
        double yDistance;
        boolean xCollision;
        boolean yCollision;

        MapCollisionResult(double xDistance, double yDistance) {
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            xCollision = false;
            yCollision = false;
        }

        MapCollisionResult(double xDistance, double yDistance, boolean xCollision, boolean yCollision) {
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            this.xCollision = xCollision;
            this.yCollision = yCollision;
        }
    }
    private PlayingField playingField;


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
        object.addInstantForce(new Vector2f(0, 0.05f));

        //calculate acceleration from forces
        Vector2f totalForce = object.getContForce().copy().add(object.getInstantForce());
        Vector2f acceleration = addForces(totalForce, object.getMass());

        //update velocities according to acceleration
        addAcceleration(object.getVelocity(), acceleration);

        //add friction
        object.setVelocity(object.getVelocity().scale(0.97f));

        //remove all instant forces
        object.setInstantForce(new Vector2f(0,0));


        //then see how far the object can travel
        MapCollisionResult result = checkMapCollision(object);


        //travel that length

        //if we collided
        if (result.xCollision || result.yCollision ) {
            object.collision();

            //System.out.println(result.distance);
            //System.out.println(object.getPosition());

            object.setPosition(move(object.getPosition(), new Vector2f((float)result.xDistance, (float)result.yDistance)));
            //System.out.println(object.getPosition());
            if (result.xCollision && result.yCollision) {
                System.out.println("XYcollision");
                object.setVelocity(new Vector2f(0, 0));
            }
            else if (result.xCollision) {
                System.out.println("Xcollision");
                object.setVelocity(new Vector2f(0, object.getVelocity().getY()));
            }
            else {
                System.out.println("Ycollision");
                object.setVelocity(new Vector2f(object.getVelocity().getX(), 0));
            }
        }
        else {
            System.out.println("No collision");
            //System.out.println(result.istance);
            //System.out.println(object.getPosition());
            object.setPosition(move(object.getPosition(), object.getVelocity()));
            //System.out.println(object.getPosition());
        }

    }

    private MapCollisionResult checkMapCollision(final GameObject obj) {
        Vector2f vel = obj.getVelocity().copy();
        MapCollisionResult result = new MapCollisionResult(vel.getX(), vel.getY());

        int velDir;
        double deltav;
        if (vel.getX() < 0) {
            velDir = -1;
            deltav = -0.01;
        }
        else {
            velDir = 1;
            deltav = 0.01;
        }

        loops:
        for (double dv = deltav; Math.abs(dv) < Math.abs(vel.getX()); dv += deltav) {
            for (int i=0; i<2; i++) {
                for (int j=0; j<2; j++) {
                    Point point = new Point((int)Math.floor(obj.getPosition().getX() + obj.getWidth()  * i + dv),
                                            (int)Math.floor(obj.getPosition().getY() + obj.getHeight() * j));
                    if (point.getX() < 0 || point.getX() >= playingField.getPixelWidth() ||
                            //point.getY() < 0 ||  point.getY() >= playingField.getPixelHeight() ||
                            playingField.getPixel(point).getState() != MapBlock.States.EMPTY) {
                        result.xDistance = dv-deltav;
                        result.xCollision = true;
                        break loops;
                    }

                }
            }
        }
        if (vel.getY() < 0) {
            velDir = -1;
            deltav = -0.01;
        }
        else {
            velDir = 1;
            deltav = 0.01;
        }

        for (double dv=deltav; Math.abs(dv) <= Math.abs(vel.getY()); dv+=deltav) {
            for (int i=0; i<2; i++) {
                for (int j=0; j<2; j++) {
                    Point point = new Point((int)Math.floor(obj.getPosition().getX() + obj.getWidth() * i),
                                            (int)Math.floor(obj.getPosition().getY() + obj.getHeight() * j + dv));
                    if (//point.getX() < 0 || point.getX() >= playingField.getPixelWidth() ||
                            point.getY() < 0 ||  point.getY() >= playingField.getPixelHeight() ||
                            playingField.getPixel(point).getState() != MapBlock.States.EMPTY) {
                        result.yDistance = dv-deltav;
                        result.yCollision = true;
                        return result;
                    }
                }
            }
        }
        return result;
    }

    private Vector2f move(final Vector2f point, final Vector2f velocity) {
        point.add(velocity);
        return point;
    }

    private Vector2f addForces(final Vector2f force, float mass) {
        return force.scale(1/mass);
    }

    private void addAcceleration(Vector2f velocity, Vector2f acceleration) {
        velocity.add(acceleration);
    }
}
