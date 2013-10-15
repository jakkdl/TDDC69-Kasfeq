package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.List;

public class PhysicsEngine {
    private class MapCollisionResult {
        double xDistance;
        double yDistance;
        double xPoint;
        double yPoint;
        boolean xCollision;
        boolean yCollision;

        MapCollisionResult(double xDistance, double yDistance, Vector2d target) {
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            xCollision = false;
            yCollision = false;
            this.xPoint = target.getX();
            this.yPoint = target.getY();
        }

        MapCollisionResult(double xDistance, double yDistance, boolean xCollision, boolean yCollision) {
            this.xDistance = xDistance;
            this.yDistance = yDistance;
            this.xCollision = xCollision;
            this.yCollision = yCollision;
        }
    }
    final static double ABSDELTAV = 0.01;

    private PlayingField playingField;

    public PhysicsEngine(PlayingField playingField) {
        this.playingField = playingField;
    }

    public void dumbCollisions(List<GameObject> objects, int time) {

        for (int i=0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);
            for (int j=i+1; j < objects.size(); j++) {
                GameObject obj2 = objects.get(j);
                if (collision(obj, obj2, time)) {
                    obj2.collision(obj);
                    obj.collision(obj2);
                }
            }

        }
    }

    private boolean collision(GameObject obj1, GameObject obj2, int time) {
        double t=1;
        Vector2d pos1 = obj1.getPosition().copy().add(obj1.getVelocity().copy().scale((float)t));
        Vector2d pos2 = obj2.getPosition().copy().add(obj2.getVelocity().copy().scale((float)t));
        if (rectangleCollision(pos1.getX(), pos1.getY(), obj1.getHeight(), obj1.getWidth(), pos2.getX(), pos2.getY(), obj2.getHeight(), obj2.getWidth())) {
            return true;
        }
        return false;
    }

    private boolean rectangleCollision(double x1, double y1, double height1, double width1, double x2, double y2, double height2, double width2) {
        return !(x1 > x2 + width2 || x1 + width1 < x2 || y1 > y2 + height2 || y1 + height1 < y2);
    }

    public void updateObject(GameObject object, int time) {
        //calculate forces, acceleration and velocity

        //add gravity
        object.addInstantForce(new Vector2d(0, playingField.getGravity()*object.getMass()));

        //calculate acceleration from forces
        Vector2d totalForce = object.getContForce().add(object.getInstantForce());
        Vector2d acceleration = addForces(totalForce, object.getMass());

        //update velocities according to acceleration
        object.setVelocity(addAcceleration(object.getVelocity(), acceleration));

        //add friction
        object.setVelocity(object.getVelocity().scale(1-playingField.getFriction()));

        //remove all instant forces
        object.setInstantForce(new Vector2d(0,0));

        //then see how far the object can travel
        MapCollisionResult result = checkMapCollision(object);


        //travel that length

        //if we collided
        if (result.xCollision || result.yCollision) {

            object.setPosition(move(object.getPosition(), new Vector2d(result.xDistance, result.yDistance)));
            if (result.xCollision && result.yCollision) {
                object.collision(result.xPoint, result.yPoint);
                object.setVelocity(new Vector2d(0, 0));
            }
            else if (result.xCollision) {
                object.collision(result.xPoint, result.yPoint);
                object.setVelocity(new Vector2d(0, object.getVelocity().getY()));
            }
            else {
                object.collision(result.xPoint, result.yPoint);
                object.setVelocity(new Vector2d(object.getVelocity().getX(), 0));
            }
        } else {
            object.setPosition(move(object.getPosition(), object.getVelocity()));
        }
    }

    private MapCollisionResult checkMapCollision(final GameObject obj) {
        Vector2d vel = obj.getVelocity().copy();
        MapCollisionResult result = new MapCollisionResult(vel.getX(), vel.getY(),
                obj.getPosition().add(obj.getVelocity()));

        double deltav;
        if (vel.getX() < 0) {
            deltav = -ABSDELTAV;
        } else {
            deltav = ABSDELTAV;
        }

        for (double dv = deltav; Math.abs(dv) < Math.abs(vel.getX()) && !result.xCollision; dv += deltav) {
            for (int i = 0; i < 2 && !result.xCollision; i++) {
                double x = obj.getPosition().getX() + obj.getWidth() * i + dv;
                for (int j = 0; j < 2 && !result.xCollision; j++) {
                    double y = obj.getPosition().getY() + obj.getHeight() * j;
                    if (x < 0 || x >= playingField.getWidth() ||
                            y < 0 ||  y >= playingField.getHeight() ||
                            playingField.getPixel(x, y) != MapTile.EMPTY) {
                        result.xDistance = dv - deltav;
                        result.xPoint = x;
                        result.xCollision = true;
                    }

                }
            }
        }
        if (vel.getY() < 0) {
            deltav = -ABSDELTAV;
        } else {
            deltav = ABSDELTAV;
        }

        for (double dv = deltav; Math.abs(dv) <= Math.abs(vel.getY()); dv += deltav) {
            for (int j = 0; j < 2; j++) {
                double y = obj.getPosition().getY() + obj.getHeight() * j + dv;
                for (int i = 0; i < 2; i++) {
                    double x = obj.getPosition().getX() + obj.getWidth() * i;
                    if (x < 0 || x >= playingField.getWidth() ||
                            y < 0 || y >= playingField.getHeight() ||
                            playingField.getPixel(x, y) != MapTile.EMPTY) {
                        result.yDistance = dv - deltav;
                        result.yPoint = y;
                        result.yCollision = true;
                        return result;
                    }
                }
            }
        }
        return result;
    }

    private Vector2d move(final Vector2d point, final Vector2d velocity) {
        return point.add(velocity);
    }

    private Vector2d addForces(final Vector2d force, double mass) {
        return force.scale(1/(float)mass);
    }

    private Vector2d addAcceleration(Vector2d velocity, Vector2d acceleration) {
        return velocity.add(acceleration);
    }

    public boolean isOnGround(GameObject object) {
        for (int x=0; x < object.getWidth(); x++) {
            if (playingField.getPixel(object.getPosition().getX()+x, object.getPosition().getY()+object.getHeight()+1) != MapTile.EMPTY) {
                return true;
            }
        }
        return false;
    }
}
