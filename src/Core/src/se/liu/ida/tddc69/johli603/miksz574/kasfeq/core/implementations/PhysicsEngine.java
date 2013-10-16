package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.List;

public class PhysicsEngine {
    private class CollisionResult {
        double distance;
        Vector2d point;
        boolean collision;

        CollisionResult() {
            point = new Vector2d();
        }

        CollisionResult(double distance, Vector2d point) {
            this.distance = distance;
            this.point = point;
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
                if (checkCollision(obj, obj2, time)) {
                    obj2.collision(obj);
                    obj.collision(obj2);
                }
            }

        }
    }

    private boolean checkCollision(GameObject obj1, GameObject obj2, int time) {
        double t=1;
        Vector2d pos1 = obj1.getPosition().add(obj1.getVelocity().copy().scale(t));
        Vector2d pos2 = obj2.getPosition().add(obj2.getVelocity().copy().scale(t));
        if (checkRectangleCollision(pos1.getX(), pos1.getY(), obj1.getHeight(), obj1.getWidth(), pos2.getX(), pos2.getY(), obj2.getHeight(), obj2.getWidth())) {
            return true;
        }
        return false;
    }

    private boolean checkRectangleCollision(double x1, double y1, double height1, double width1, double x2, double y2, double height2, double width2) {
        return !(x1 > x2 + width2 || x1 + width1 < x2 || y1 > y2 + height2 || y1 + height1 < y2);
    }

    public void updateObject(GameObject object, int time) {
        //calculate forces, acceleration and velocity

        //add gravity
        object.addInstantForce(new Vector2d(0, playingField.getGravity()*object.getMass()));

        //sum continuous and instant forces
        Vector2d totalForce = object.getContForce().add(object.getInstantForce());

        //account for newton's third law
        if ((totalForce.getX() > 0 && touchesSolid(object, Direction.RIGHT)) ||
                (totalForce.getX() < 0 && touchesSolid(object, Direction.LEFT))) {
            totalForce.setX(0);
            //collision
        }
        if ((totalForce.getY() > 0 && touchesSolid(object, Direction.DOWN)) ||
                (totalForce.getY() < 0 && touchesSolid(object, Direction.UP))) {
            totalForce.setY(0);
            //collision
        }

        //calculate acceleration from forces
        Vector2d acceleration = addForces(totalForce, object.getMass());

        //update velocities according to acceleration
        object.setVelocity(addAcceleration(object.getVelocity(), acceleration));

        //add friction
        object.setVelocity(object.getVelocity().scale(1-playingField.getFriction()));

        //remove all instant forces
        object.setInstantForce(new Vector2d(0,0));

        //then see how far the object can travel
        //MapCollisionResult result = checkMapCollision(object);
        CollisionResult result = mapCollision(object, object.getVelocity().getTheta(), 0, 0);



        //if we collided
        if (result.collision) {
            //move as far as we could
            object.setPosition(move(object.getPosition(), new Vector2d(result.distance*Math.cos(object.getVelocity().getTheta()),
                    result.distance*Math.sin(object.getVelocity().getTheta()))));
            //notify object what it collided with, for terrain destruction
            object.collision(result.point.getX(), result.point.getY());
            //lose all velocity (fully static collision)
            object.setVelocity(new Vector2d(0, 0));
        } else {
            //else move normally
            object.setPosition(move(object.getPosition(), object.getVelocity()));
        }
    }

    private CollisionResult mapCollision(GameObject obj, double direction, double xOffset, double yOffset) {
        double theta= obj.getVelocity().getTheta();
        double deltav;
        Vector2d directionVector = new Vector2d(direction);
        Vector2d vel = obj.getVelocity().add(new Vector2d(xOffset,yOffset).negate());
        CollisionResult result = new CollisionResult();

        if (Math.atan2(Math.sin(direction-theta), Math.cos(direction-theta)) > Math.PI/2) {
            deltav = -ABSDELTAV;
        } else {
            deltav = ABSDELTAV;
        }

        for (double dv = deltav; Math.abs(dv) <= Math.abs(vel.projectOntoUnit(directionVector).length()); dv += deltav) {
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 2; j++) {
                    double x = obj.getPosition().getX() + xOffset + obj.getWidth()  * i + dv*Math.cos(direction);
                    double y = obj.getPosition().getY() + yOffset + obj.getHeight() * j + dv*Math.sin(direction);
                    if (x < 0 || x >= playingField.getWidth() ||
                            y < 0 || y >= playingField.getHeight() ||
                            playingField.getPixel(x, y) != MapTile.EMPTY) {
                        result.distance = dv - deltav;
                        result.point = new Vector2d(x, y);
                        result.collision = true;
                        return result;
                    }
                }
            }
        }
        result.collision = false;
        result.distance = vel.projectOntoUnit(directionVector).length();
        result.point = obj.getPosition().add(vel).projectOntoUnit(directionVector);
        return result;
    }

    private Vector2d move(final Vector2d point, final Vector2d velocity) {
        return point.add(velocity);
    }

    private Vector2d addForces(final Vector2d force, double mass) {
        return force.scale(1 / mass);
    }

    private Vector2d addAcceleration(Vector2d velocity, Vector2d acceleration) {
        return velocity.add(acceleration);
    }

    public boolean touchesSolid(GameObject object, Direction direction) {
        switch (direction) {
            case LEFT:
                for (int y=0; y <= object.getHeight(); y++) {
                    if (playingField.getPixel(object.getPosition().getX() - 1, object.getPosition().getY() + y) != MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
            case RIGHT:
                for (int y=0; y <= object.getHeight(); y++) {
                    if (playingField.getPixel(object.getPosition().getX()+object.getWidth()+1, object.getPosition().getY()+y) != MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
            case UP:
                for (int x=0; x <= object.getWidth(); x++) {
                    if (playingField.getPixel(object.getPosition().getX()+x, object.getPosition().getY()-1) != MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
            case DOWN:
                for (int x=0; x <= object.getWidth(); x++) {
                    if (playingField.getPixel(object.getPosition().getX()+x, object.getPosition().getY()+object.getHeight()+1) != MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }
}
