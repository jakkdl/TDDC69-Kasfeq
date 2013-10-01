package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;
import java.awt.*;
import java.util.*;

class ReadXmlFile {
    enum Options {
        GRAVITY,
        PIXELSPERBLOCK,
        MAPNAME,
        MAPTYPE
    } 

    private Map<Options,Object> optionsDict;
    
    ReadXmlFile() {
        optionsDict = new EnumMap<Options, Object>(Options.class);
    }

    Object getOption(Options option) {
        if (optionsDict.containsKey(option)) {
            return optionsDict.get(option);
        }
        else {
            return null; //throw
        }
    }

    public PlayingField loadResource(String fileName) {
        setDefaults();

        //TODO: Read file

        PlayingField playingField = new PlayingField(10, 10);
        for (int x=0; x < 10; x++) {
            for (int y=0; y < 2; y++) {
                playingField.setPixel(new Point(x, y), new MapPixel(MapPixel.States.SOLID, SystemColor.black));
            }
            for (int y=2; y < 10; y++) {
                playingField.setPixel(new Point(x, y), new MapPixel(MapPixel.States.EMPTY, SystemColor.blue));
            }
        }
        //for option in filename do shit;

        return playingField;

    }


    void setDefaults() {
        optionsDict.put(Options.GRAVITY, 20);
        optionsDict.put(Options.PIXELSPERBLOCK, 10);
        optionsDict.put(Options.MAPNAME, "defaultMapName");
        optionsDict.put(Options.MAPTYPE, "text");
    }


}
