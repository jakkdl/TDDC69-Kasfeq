package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;
import java.awt.*;

final class ReadXmlFile {
    enum Options {
        GRAVITY,
        PIXELSPERBLOCK,
        MAPNAME,
        MAPTYPE
    }






    public static PlayingField loadResource(String fileName) {

        //TODO: Read file

        PlayingField playingField = new PlayingField(10, 10);
        for (int x=0; x < 10; x++) {
            for (int y=0; y < 2; y++) {
                playingField.setBlock(new Point(x, y), new MapBlock(MapBlock.States.SOLID, Color.BLACK));
            }
            for (int y=2; y < 10; y++) {
                playingField.setBlock(new Point(x, y), new MapBlock(MapBlock.States.EMPTY, Color.BLUE));
            }
        }
        //for option in filename do shit;
        for (Options option : Options.values()) {
            playingField.setOption(option, setDefaults(option));
        }


        return playingField;

    }


    private static Object setDefaults(Options option) {
        switch (option) {
            case GRAVITY:
                return 20;
            case PIXELSPERBLOCK:
                return 10;
            case MAPNAME:
                return "defaultMapName";
            case MAPTYPE:
                return "text";
        }
        return null; //throw exception
    }

}
