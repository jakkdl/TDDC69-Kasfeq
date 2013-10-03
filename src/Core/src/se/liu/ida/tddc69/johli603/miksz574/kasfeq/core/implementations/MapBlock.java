package se.liu.ida.tddc69.johli603.miksz574.kasfeq.core.implementations;

import java.awt.*;

public class MapBlock {
    enum States {
        EMPTY, //AIR
        SOLID,
        DESTRUCTABLE
    }
    Color color;
    States state;

    MapBlock(States state, Color color) {
        this.state = state;
        this.color = color;
    }
}
