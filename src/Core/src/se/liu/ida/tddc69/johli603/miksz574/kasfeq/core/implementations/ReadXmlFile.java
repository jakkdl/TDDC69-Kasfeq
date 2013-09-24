class ReadXmlFile {
    enum Options {
        GRAVITY,
        PIXELSPERBLOCK,
        MAPNAME,
        MAPTYPE
    } 

    private Map<Options,Object> OptionsDict;
    
    ReadXmlFile() {
        optionsDict = new EnumMap<Options, Object>(Options.class);
    }

    Object getOption(Options option) {
        if (OptionsDict.containsKey(Options.option)) {
            return OptionsDict.get(Options.option);
        }
        else {
            return null; //throw
        }
    }

    public GameMap loadResource(string fileName) {
        setDefaults();
        //for option in filename do shit;
    }


    setDefaults() {
        OptionsDict.put(Options.GRAVITY, 20);
        OptionsDict.put(Options.PIXELSPERBLOCK, 10);
        OptionsDict.put(Options.MAPNAME, "defaultMapName");
        OptionsDict.put(Options.MAPTYPE, "text");
    }


}
