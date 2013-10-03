package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import org.newdawn.slick.Color;

public class MapBlock {
    public enum States {
        EMPTY, //AIR
        SOLID,
        DESTRUCTABLE
    }

    Color color;
    States state;

    public Color getColor() {
        return color;
    }

    public States getState() {
        return state;
    }

    MapBlock(States state, Color color) {
        this.state = state;
        this.color = color;
    }
}
