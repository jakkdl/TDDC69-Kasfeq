package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;

public final class ReadXmlFile {
    public enum Options {
        GRAVITY,
        PIXELSPERBLOCK,
        MAPNAME,
        MAPTYPE
    }

    public static PlayingField loadResource(String fileName) {

        //TODO: Read file

        PlayingField playingField = new PlayingField(10, 10);
        for (int x=0; x < 10; x++) {
            for (int y=0; y < 8; y++) {
                playingField.setBlock(new Point(x, y), new MapBlock(MapBlock.States.EMPTY, Color.blue));
            }
            for (int y=8; y < 10; y++) {
                playingField.setBlock(new Point(x, y), new MapBlock(MapBlock.States.SOLID, Color.green));
            }
        }
        //for option in filename do shit;
        for (Options option : Options.values()) {
            playingField.setOption(option, getDefault(option));
        }


        return playingField;

    }


    public static Object getDefault(Options option) {
        switch (option) {
            case GRAVITY:
                return 20;
            case PIXELSPERBLOCK:
                return 32;
            case MAPNAME:
                return "defaultMapName";
            case MAPTYPE:
                return "text";
        }
        return null; //throw exception
    }

}
