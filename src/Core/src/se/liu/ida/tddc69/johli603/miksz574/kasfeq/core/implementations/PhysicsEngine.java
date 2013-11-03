package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.util.List;

/**
 * \class PhysicsEngine
 * \brief A physics engine implementation for the game
 */
public class PhysicsEngine {
    /**
     * \enum Direction
     * \brief Used by checkSolidCollision
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

    /**
     * \brief used by mapCollision
     */
    private class CollisionResult {
        double distance;
        Vector2d point;
        boolean collision;

        CollisionResult() {
            point = new Vector2d();
        }
    }

    /**
     * \brief used by mapCollision to determine
     * the size of the increments in velocity to check for collisions.
     * Too low slows the game down and too high makes people stop at a distance from walls
     */
    private final static double DELTAV = 0.5;

    private PlayingField playingField;

    public PhysicsEngine(PlayingField playingField) {
        this.playingField = playingField;
    }

    /**
     * \brief Checks collision between all objects in the provided list
     *
     * @param time is the time since the last frame. Planned feature to use this to make the physics engine
     *             consistent across different fps.
     */
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

    /**
     *
     * @param obj1 The first object to check collision between.
     * @param obj2 The second object to check collision between
     * @param time Time since last frame. Not implemented yet.
     * @return True if the objects collide, false otherwise.
     */
    @SuppressWarnings("UnusedParameters")
    private boolean checkCollision(GameObject obj1, GameObject obj2, int time) {
        Vector2d pos1 = obj1.getPosition().add(obj1.getVelocity());
        Vector2d pos2 = obj2.getPosition().add(obj2.getVelocity());
        if (checkRectangleCollision(pos1.getX(), pos1.getY(), obj1.getHeight(), obj1.getWidth(), pos2.getX(), pos2.getY(), obj2.getHeight(), obj2.getWidth())) {
            return true;
        }
        return false;
    }

    /**
     * \brief Returns whether two rectangles overlap/collide. Used by checkCollision
     *
     * @param x1 X coordinate of object 1.
     * @param y1 Y coordinate of object 1.
     * @param height1 Height of object 1.
     * @param width1 width of object 1.
     * @param x2 X coordinate of object 2.
     * @param y2 Y coordinate of object 2.
     * @param height2 Height of object 2.
     * @param width2 Width of object 2.
     * @return True if the rectangles overlap.
     */
    private boolean checkRectangleCollision(double x1, double y1, double height1, double width1, double x2, double y2, double height2, double width2) {
        return !(x1 > x2 + width2 || x1 + width1 < x2 || y1 > y2 + height2 || y1 + height1 < y2);
    }

    /**
     * \brief Updates forces, velocity and moves the provided object.
     *
     * @param object The object to update.
     */
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

    /**
     * \brief Sees how far an object can travel before colliding with terrain.
     *
     * @param obj The object to move.
     * @return Returns CollisionResult that tells whether it collided, and if so, at what distance and at what point.
     */
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

    /**
     * \brief Adds velocity to (a copy of) point.
     *
     * @param point Starting point.
     * @param velocity Distance to move.
     * @return A Vector2d consisting of point+velocity.
     */
    private Vector2d move(final Vector2d point, final Vector2d velocity) {
        return point.add(velocity);
    }

    /**
     * \brief Calculate acceleration given force and mass
     *
     * @param force The force acting on an object.
     * @param mass The mass of an object.
     * @return Returns the acceleration on the object.
     */
    private Vector2d addForces(final Vector2d force, double mass) {
        return force.scale(1 / mass);
    }

    /**
     * \brief Adds acceleration to (a copy of) velocity.
     *
     * @param velocity Velocity of an object.
     * @param acceleration Acceleration of an object.
     * @return A Vector2d consisting of velocity+acceleration.
     */
    private Vector2d addAcceleration(Vector2d velocity, Vector2d acceleration) {
        return velocity.add(acceleration);
    }

    /**
     * \brief Checks if the side of the object is touching something non-empty on the map.
     *
     * @param object The object to check.
     * @param direction Which direction to check.
     * @return True if the object is touching something non-empty, otherwise false.
     */
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
