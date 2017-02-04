package studio.papercube.blockbuild.edgesupport.binlevel;

import java.util.ArrayList;
import java.util.List;

public class OtherCube {
    Vector positionTrigger;
    short movingBlockSync;

    byte darkcubeRadiusX;
    byte darkCubeRadiusY;
    short darkcubeMovingBlockSync;

    Vector positionCube;

    List<KeyEvent> keyEvents = new ArrayList<>();

    public static class KeyEvent {
        public static final byte WEST = 0;
        public static final byte EAST = 1;
        public static final byte NORTH = 2;
        public static final byte SOUTH = 3;

        public static final byte KEY_DOWN = 0;
        public static final byte KEY_UP = 1;
        short timeOffset;
        byte direction;
        byte eventType;

        public short getTimeOffset() {
            return timeOffset;
        }

        public void setTimeOffset(short timeOffset) {
            this.timeOffset = timeOffset;
        }

        public byte getDirection() {
            return direction;
        }

        public void setDirection(byte direction) {
            this.direction = direction;
        }

        public byte getEventType() {
            return eventType;
        }

        public void setEventType(byte eventType) {
            this.eventType = eventType;
        }
    }
}
