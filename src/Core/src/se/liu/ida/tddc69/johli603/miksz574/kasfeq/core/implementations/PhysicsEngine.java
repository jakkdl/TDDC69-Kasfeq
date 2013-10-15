package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.List;

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

    public void dumbCollisions(List<GameObject> objects, int time) {

        for (int i=0; i < objects.size(); i++) {
	    GameObject obj = objects.get(i);
            for (int j=i+1; j < objects.size(); j++) {
		GameObject obj2 = objects.get(j);
                if (collision(obj, obj2, time)) {
                    obj.collision(obj2);
                }
            }

        }
    }

    private boolean collision(GameObject obj1, GameObject obj2, int time) {
        double t=1;
        Vector2d pos1 = obj1.getPosition().copy().add(obj1.getVelocity().copy().scale((float)t));
        Vector2d pos2 = obj2.getPosition().copy().add(obj2.getVelocity().copy().scale((float)t));
        if (rectangle_collision(pos1.getX(), pos1.getY(), obj1.getHeight(), obj1.getWidth(), pos2.getX(), pos2.getY(), obj2.getHeight(), obj2.getWidth())) {
            return true;
        }
        return false;
    }

    private boolean rectangle_collision(double x1, double y1, double height1, double width1, double x2, double y2, double height2, double width2) {
        return !(x1 > x2 + width2 || x1 + width1 < x2 || y1 > y2 + height2 || y1 + height1 < y2);
    }

    public void updateObject(GameObject object, int time) {
        //calculate forces, acceleration and velocity

        //add gravity
        object.addInstantForce(new Vector2d(0, playingField.getGravity()));

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
            object.collision();


            object.setPosition(move(object.getPosition(), new Vector2d(result.xDistance, result.yDistance)));
            if (result.xCollision && result.yCollision) {
                object.setVelocity(new Vector2d(0, 0));
            }
            else if (result.xCollision) {
                object.setVelocity(new Vector2d(0, object.getVelocity().getY()));
            }
            else {
                object.setVelocity(new Vector2d(object.getVelocity().getX(), 0));
            }
        } else {
            object.setPosition(move(object.getPosition(), object.getVelocity()));
        }
    }

    private MapCollisionResult checkMapCollision(final GameObject obj) {
        Vector2d vel = obj.getVelocity().copy();
        MapCollisionResult result = new MapCollisionResult(vel.getX(), vel.getY());

        double absDeltaV = 0.01;
        double deltav;
        if (vel.getX() < 0) {
            deltav = -absDeltaV;
        } else {
            deltav = absDeltaV;
        }

        loops:
        for (double dv = deltav; Math.abs(dv) < Math.abs(vel.getX()); dv += deltav) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Point point = new Point((int) Math.floor(obj.getPosition().getX() + obj.getWidth() * i + dv),
                            (int) Math.floor(obj.getPosition().getY() + obj.getHeight() * j));
                    //if (point.getX() < 0 || point.getX() >= playingField.getPixelWidth() ||
                    if (point.getX() < 0 || point.getX() >= playingField.getWidth() ||
                            //point.getY() < 0 ||  point.getY() >= playingField.getPixelHeight() ||
                            playingField.getPixel(point) != MapBlock.States.EMPTY) {
                        result.xDistance = dv - deltav;
                        result.xCollision = true;
                        break loops;
                    }

                }
            }
        }
        if (vel.getY() < 0) {
            deltav = -absDeltaV;
        } else {
            deltav = absDeltaV;
        }

        for (double dv = deltav; Math.abs(dv) <= Math.abs(vel.getY()); dv += deltav) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    Point point = new Point((int) Math.floor(obj.getPosition().getX() + obj.getWidth() * i),
                            (int) Math.floor(obj.getPosition().getY() + obj.getHeight() * j + dv));
                    if (//point.getX() < 0 || point.getX() >= playingField.getPixelWidth() ||
                        //point.getY() < 0 ||  point.getY() >= playingField.getPixelHeight() ||
                            point.getY() < 0 || point.getY() >= playingField.getHeight() ||
                                    playingField.getPixel(point) != MapBlock.States.EMPTY) {
                        result.yDistance = dv - deltav;
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

    public Vector2f getAvailablePosition(Player player) {
	return new Vector2f(20 + 20 * player.getPlayerId(), 100);
    }
}
