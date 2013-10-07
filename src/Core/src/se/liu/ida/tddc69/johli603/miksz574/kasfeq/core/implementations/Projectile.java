package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world.World;

public class Projectile extends GameObject {

    public Projectile(World world) {
        super(world, 0.1f, 2, 2);
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
        super.update(gameContainer, i);
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        Vector2f position = getPosition();
        graphics.setColor(Color.white);
        graphics.fillRect(position.getX(), position.getY(), 5, 5);
    }

    @Override
    public void dispose() throws Exception {
        super.dispose();
    }
}
