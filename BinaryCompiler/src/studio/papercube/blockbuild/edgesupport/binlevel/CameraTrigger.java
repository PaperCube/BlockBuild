package studio.papercube.blockbuild.edgesupport.binlevel;

public class CameraTrigger {
    Vector position;
    short zoom;
    byte radiusX;
    byte radiusY;

    // if zoom == -1
    boolean reset;
    short startDelay;
    short duration;
    short value;
    boolean isSingleUse;

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setZoom(short zoom) {
        this.zoom = zoom;
    }

    public void setRadiusX(byte radiusX) {
        this.radiusX = radiusX;
    }

    public void setRadiusY(byte radiusY) {
        this.radiusY = radiusY;
    }

    public void setReset(boolean reset) {
        this.reset = reset;
    }

    public void setStartDelay(short startDelay) {
        this.startDelay = startDelay;
    }

    public void setDuration(short duration) {
        this.duration = duration;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public void setSingleUse(boolean singleUse) {
        isSingleUse = singleUse;
    }

    public void setValueIsAngle(boolean valueIsAngle) {
        this.valueIsAngle = valueIsAngle;
    }
// endif

    public Vector getPosition() {
        return position;
    }

    boolean valueIsAngle;

    public short getZoom() {
        return zoom;
    }

    public byte getRadiusX() {
        return radiusX;
    }

    public byte getRadiusY() {
        return radiusY;
    }

    public boolean isReset() {
        return reset;
    }

    public short getStartDelay() {
        return startDelay;
    }

    public short getDuration() {
        return duration;
    }

    public short getValue() {
        return value;
    }

    public boolean isSingleUse() {
        return isSingleUse;
    }

    public boolean isValueIsAngle() {
        return valueIsAngle;
    }
}
