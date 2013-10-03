package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.world;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.MapBlock;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.PlayingField;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.Point;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations.ReadXmlFile;
import se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.interfaces.GameComponent;

public class MapComponent implements GameComponent {
    private PlayingField playingField;

    public MapComponent(PlayingField playingField) {
        this.playingField = playingField;
    }

    @Override
    public void init(GameContainer gameContainer) throws SlickException {
    }

    @Override
    public void update(GameContainer gameContainer, int i) throws SlickException {
    }

    @Override
    public void render(GameContainer gameContainer, Graphics graphics) throws SlickException {
        int pixelsPerBlock = (Integer)playingField.getOption(ReadXmlFile.Options.PIXELSPERBLOCK);

        for(int x = 0; x < playingField.getWidth(); x++) {
            for(int y = 0; y < playingField.getHeight(); y++) {
                MapBlock block = playingField.getBlock(new Point(x,y));
                graphics.setColor(block.getColor());
                graphics.fillRect(x*pixelsPerBlock,y*pixelsPerBlock,pixelsPerBlock,pixelsPerBlock);
            }
        }
    }

    @Override
    public void dispose() throws Exception {
    }
}
