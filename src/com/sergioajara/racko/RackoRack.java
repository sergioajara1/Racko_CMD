package com.sergioajara.racko;

import java.util.ArrayList;
import java.util.Iterator;

public class RackoRack extends ArrayList<RackoCard> {

    public RackoRack() {

    }

    public RackoCard getPosition5() {
        return this.get(0);
    }

    public RackoCard getPosition10() {
        return this.get(1);
    }

    public RackoCard getPosition15() {
        return this.get(2);
    }

    public RackoCard getPosition20() {
        return this.get(3);
    }

    public RackoCard getPosition25() {
        return this.get(4);
    }

    public RackoCard getPosition30() {
        return this.get(5);
    }

    public RackoCard getPosition35() {
        return this.get(6);
    }

    public RackoCard getPosition40() {
        return this.get(7);
    }

    public RackoCard getPosition45() {
        return this.get(8);
    }

    public RackoCard getPosition50() {
        return this.get(9);
    }

    public RackoCard removePosition5() {
        return this.remove(0);
    }

    public RackoCard removePosition10() {
        return this.remove(1);
    }

    public RackoCard removePosition15() {
        return this.remove(2);
    }

    public RackoCard removePosition20() {
        return this.remove(3);
    }

    public RackoCard removePosition25() {
        return this.remove(4);
    }

    public RackoCard removePosition30() {
        return this.remove(5);
    }

    public RackoCard removePosition35() {
        return this.remove(6);
    }

    public RackoCard removePosition40() {
        return this.remove(7);
    }

    public RackoCard removePosition45() {
        return this.remove(8);
    }

    public RackoCard removePosition50() {
        return this.remove(9);
    }

    @Override
    public String toString() {
        Iterator<RackoCard> it = iterator();
        if (! it.hasNext())
            return "EMPTY RACK!";

        StringBuilder sb = new StringBuilder();
        for (;;) {
            RackoCard e = it.next();
            sb.append(e);
            if (! it.hasNext())
                return sb.toString();
            sb.append(',').append(' ');
        }
    }
}
