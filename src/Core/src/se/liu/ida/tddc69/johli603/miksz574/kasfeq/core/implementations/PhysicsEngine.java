package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.geom.Vector2f;
import org.newdawn.slick.tiled.TiledMap;

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
    //private PlayingField playingField;
    private TiledMap playingField;


    public PhysicsEngine(TiledMap playingField) {
        this.playingField = playingField;
    }

    public void dumbCollisions(List<GameObject> objects, int time) {
        for (GameObject obj : objects) {
            for (GameObject obj2 : objects) {
                if (obj.equals(obj2)) {
                    continue;
                }
                if (collision(obj, obj2, time)) {
                    obj.collision(obj2);
                }
            }

        }
    }

    private boolean collision(GameObject obj1, GameObject obj2, int time) {
        final double deltat = 0.1;
        double maxDist = maxDist(obj1, obj2);
        for (double t=0; t <= time; t += 0.01) {
            if (obj1.getPosition().copy().add(obj1.getVelocity().copy().scale((float)t)).length() - obj2.getPosition().copy().add(obj2.getInstantForce().copy().scale((float)t)).length() < maxDist) {
                for (int dx = 0; dx <= obj1.getWidth(); dx++) {
                    int x = (int)Math.floor(obj1.getPosition().getX()+dx);
                    if ( x > (int)Math.floor(obj2.getPosition().getX()) && x < (int)Math.floor(obj2.getPosition().getX()) + obj2.getWidth()   )
                    {
                        for (int dy=0; dy <= obj1.getHeight(); dy++) {
                            int y = (int)Math.floor(obj1.getPosition().getY()+dy);
                            if ( y > (int)Math.floor(obj2.getPosition().getY()) && y < (int)Math.floor(obj2.getPosition().getY()) + obj2.getHeight()   ) {
                                return true;
                            }
                        }
                    }
                }
            }

        }
        return false;
    }

    private double maxDist(GameObject obj1, GameObject obj2) {
        double diag1 = new Vector2f(obj1.getWidth(), obj1.getHeight()).length();
        double diag2 = new Vector2f(obj2.getWidth(), obj2.getHeight()).length();
        if (diag1 < diag2) {
            return diag2;
        }
        return diag1;
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


            object.setPosition(move(object.getPosition(), new Vector2f((float)result.xDistance, (float)result.yDistance)));
            if (result.xCollision && result.yCollision) {
                object.setVelocity(new Vector2f(0, 0));
            }
            else if (result.xCollision) {
                object.setVelocity(new Vector2f(0, object.getVelocity().getY()));
            }
            else {
                object.setVelocity(new Vector2f(object.getVelocity().getX(), 0));
            }
        }
        else {
            object.setPosition(move(object.getPosition(), object.getVelocity()));
        }

    }

    private MapBlock.States getPixel(Point point) {
        int layerID = playingField.getLayerIndex("Tile Layer 1");
        int tileWidth = playingField.getTileWidth();
        int tileHeight = playingField.getTileHeight();

        int tileX = (int)Math.floor((double)point.getX()/(double)tileWidth);
        int tileY = (int)Math.floor((double)point.getY()/(double)tileHeight);
        int id = playingField.getTileId(tileX, tileY, layerID);

        if(id == 2) {
            return MapBlock.States.EMPTY;
        }
        else if(id == 3) {
            return MapBlock.States.DESTRUCTABLE;
        }
        else {
            return MapBlock.States.SOLID;
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
                    //if (point.getX() < 0 || point.getX() >= playingField.getPixelWidth() ||
                    if (point.getX() < 0 || point.getX() >= playingField.getWidth()*playingField.getTileWidth() ||
                            //point.getY() < 0 ||  point.getY() >= playingField.getPixelHeight() ||
                            //playingField.getPixel(point).getState() != MapBlock.States.EMPTY) {
                            getPixel(point) != MapBlock.States.EMPTY) {
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
                            //point.getY() < 0 ||  point.getY() >= playingField.getPixelHeight() ||
                            point.getY() < 0 ||  point.getY() >= playingField.getHeight()*playingField.getTileHeight() ||
                            //playingField.getPixel(point).getState() != MapBlock.States.EMPTY) {
                            getPixel(point) != MapBlock.States.EMPTY) {
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
