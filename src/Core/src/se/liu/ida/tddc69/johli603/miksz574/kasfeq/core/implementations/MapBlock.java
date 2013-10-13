package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

public class MapBlock {
    public enum States {
        EMPTY, //AIR
        SOLID,
        DESTRUCTABLE
    }

    States state;

    /*public States getState() {
        return state;
    }*/

    MapBlock(States state) {
        this.state = state;
    }
}
