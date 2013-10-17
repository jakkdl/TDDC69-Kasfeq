package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.List;

/**
 * \class PhysicsEngine
 * \brief A physics engine implementation for the game
 */
public class PhysicsEngine {
    /**
     * \enum Direction
     * \brief Used by the PhysicsEngine
     */
    public enum Direction {
        /** \brief Describes the left direction */
        LEFT,

        /** \brief Describes the right direction */
        RIGHT,

        /** \brief Describes the up direction */
        // Supressing the warning since this is a good name for a direction
        @SuppressWarnings("EnumeratedConstantNamingConvention")
        UP,

        /** \brief Describes the down direction */
        DOWN
    }

    private class CollisionResult {
        double distance;
        Vector2d point;
        boolean collision;

        CollisionResult() {
            point = new Vector2d();
        }
    }
    final static double DELTAV = 0.5;

    private PlayingField playingField;

    public PhysicsEngine(PlayingField playingField) {
        this.playingField = playingField;
    }

    // TODO: Use the time parameter for something good
    @SuppressWarnings("UnusedParameters")
    public void dumbCollisions(List<GameObject> objects, int time) {
        for (int i=0; i < objects.size(); i++) {
            GameObject obj = objects.get(i);
            for (int j=i+1; j < objects.size(); j++) {
                GameObject obj2 = objects.get(j);
                if (checkCollision(obj, obj2)) {
                    obj2.collision(obj);
                    obj.collision(obj2);
                }
            }

        }
    }

    private boolean checkCollision(GameObject obj1, GameObject obj2) {
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

    public void updateObject(GameObject object) {
        //calculate forces, acceleration and velocity

        //add gravity
        object.addInstantForce(new Vector2d(0, playingField.getGravity()*object.getMass()));

        //sum continuous and instant forces
        Vector2d totalForce = object.getContForce().add(object.getInstantForce());

        //account for newton's third law
        boolean collision = false;
        if ((totalForce.getX() > 0 && checkSolidCollision(object, Direction.RIGHT)) ||
                (totalForce.getX() < 0 && checkSolidCollision(object, Direction.LEFT))) {
            totalForce.setX(0);
            collision = true;
        }
        if ((totalForce.getY() > 0 && checkSolidCollision(object, Direction.DOWN)) ||
                (totalForce.getY() < 0 && checkSolidCollision(object, Direction.UP))) {
            totalForce.setY(0);
            collision = true;
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
        CollisionResult result = mapCollision(object);



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
            if (collision) {
                object.collision(object.getPosition().getX(), object.getPosition().getY());
            }
        }
    }

    private CollisionResult mapCollision(GameObject obj) {
        Vector2d vel = obj.getVelocity();
        CollisionResult result = new CollisionResult();

        List<Vector2d> border = obj.getBorder();

        for (double dv = DELTAV; dv < vel.length() + DELTAV; dv += DELTAV) {
            for (Vector2d point : border) {
                Vector2d pointToCheck = obj.getPosition().add(point).add(vel.scale(dv / vel.length()));
                if (playingField.getPixel(pointToCheck) != PlayingField.MapTile.EMPTY) {
                    result.collision = true;
                    result.distance = dv - DELTAV;
                    result.point = pointToCheck;
                    return result;
                }
            }
        }
        result.collision = false;
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

    public boolean checkSolidCollision(GameObject object, Direction direction) {
        switch (direction) {
            case LEFT:
                for (int y=0; y < object.getHeight(); y++) {
                    if (playingField.getPixel(object.getPosition().getX() - 1, object.getPosition().getY() + y) != PlayingField.MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
            case RIGHT:
                for (int y=0; y < object.getHeight(); y++) {
                    if (playingField.getPixel(object.getPosition().getX()+object.getWidth()+1, object.getPosition().getY()+y) != PlayingField.MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
            case UP:
                for (int x=0; x < object.getWidth(); x++) {
                    if (playingField.getPixel(object.getPosition().getX()+x, object.getPosition().getY()-1) != PlayingField.MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
            case DOWN:
                for (int x=0; x < object.getWidth(); x++) {
                    if (playingField.getPixel(object.getPosition().getX()+x, object.getPosition().getY()+object.getHeight()+1) != PlayingField.MapTile.EMPTY) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }
}
