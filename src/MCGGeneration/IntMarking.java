package MCGGeneration;

import java.io.Serializable;

public class IntMarking implements Marking,Serializable {
    private int value;

    public IntMarking(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }


    public String toString() {
        return String.valueOf(value);
    }
}
