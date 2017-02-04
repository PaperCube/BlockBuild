package studio.papercube.blockbuild.edgesupport.binlevel;

public class FallingPlatform {
    Vector position;
    short floatTime;

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setFloatTime(short floatTime) {
        this.floatTime = floatTime;
    }

    public Vector getPosition() {

        return position;
    }

    public short getFloatTime() {
        return floatTime;
    }
}
