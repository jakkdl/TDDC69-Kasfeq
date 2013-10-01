/*package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;
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

    public GameMap loadResource(String fileName) {
        setDefaults();
        //for option in filename do shit;

    }


    void setDefaults() {
        OptionsDict.put(Options.GRAVITY, 20);
        OptionsDict.put(Options.PIXELSPERBLOCK, 10);
        OptionsDict.put(Options.MAPNAME, "defaultMapName");
        OptionsDict.put(Options.MAPTYPE, "text");
    }


}
*/