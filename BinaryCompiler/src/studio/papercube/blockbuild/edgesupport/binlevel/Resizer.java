package studio.papercube.blockbuild.edgesupport.binlevel;

public class Resizer {
    public static final byte DIRECTION_SHRINK = 0;
    public static final byte DIRECTION_GROW = 1;

    Vector position;
    boolean visible;
    byte direction;

    public Vector getPosition() {
        return position;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public byte getDirection() {
        return direction;
    }

    public void setDirection(byte direction) {
        this.direction = direction;
    }
}
