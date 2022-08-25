package com.sergioajara.racko;

import java.util.Objects;

public class RackoCard implements Comparable<RackoCard>{
    private int faceValue;
    private boolean faceDown;

    public RackoCard(int value, boolean faceDown) {
        this.faceValue = value;
        this.faceDown = faceDown;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public boolean getFaceDown() {
        return faceDown;
    }

    public void setFaceDown(boolean faceDown) {
        this.faceDown = faceDown;
    }

    @Override
    public int compareTo(RackoCard o) {
        return this.faceValue - o.getFaceValue();
    }

    @Override
    public String toString() {
        return "[" +
                (faceDown ? "?" : faceValue)+
                "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RackoCard rackoCard = (RackoCard) o;
        return getFaceValue() == rackoCard.getFaceValue();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFaceValue());
    }
}
