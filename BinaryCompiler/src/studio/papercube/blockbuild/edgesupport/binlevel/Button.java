package studio.papercube.blockbuild.edgesupport.binlevel;

import java.util.ArrayList;
import java.util.List;

public class Button {
    /**
     * visibility == 0: invisible
     * visibility == 1: visible, solid
     * visibility == 2: visible, ghosted
     */
    byte visibility;
    byte disableCount;
    byte mode;
    short parentId;
    boolean sequenceInOrder;
    byte siblingsCount;

    boolean isMoving;
    short movingPlatformId;
    Vector position;

    List<Short> events = new ArrayList<>();

    public byte getVisibility() {
        return visibility;
    }

    public void setVisibility(byte visibility) {
        this.visibility = visibility;
    }

    public byte getDisableCount() {
        return disableCount;
    }

    public void setDisableCount(byte disableCount) {
        this.disableCount = disableCount;
    }

    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public short getParentId() {
        return parentId;
    }

    public void setParentId(short parentId) {
        this.parentId = parentId;
    }

    public boolean isSequenceInOrder() {
        return sequenceInOrder;
    }

    public void setSequenceInOrder(boolean sequenceInOrder) {
        this.sequenceInOrder = sequenceInOrder;
    }

    public byte getSiblingsCount() {
        return siblingsCount;
    }

    public void setSiblingsCount(byte siblingsCount) {
        this.siblingsCount = siblingsCount;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public short getMovingPlatformId() {
        return movingPlatformId;
    }

    public void setMovingPlatformId(short movingPlatformId) {
        this.movingPlatformId = movingPlatformId;
    }

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public List<Short> getEvents() {
        return events;
    }

    public void setEvents(List<Short> events) {
        this.events = events;
    }
}
