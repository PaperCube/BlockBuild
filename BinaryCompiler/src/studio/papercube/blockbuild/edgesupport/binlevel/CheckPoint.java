package studio.papercube.blockbuild.edgesupport.binlevel;

/**
 * Created by imzhy on 2016/12/4.
 */
public class CheckPoint {
    Vector position;
    short respawnZ;
    byte radiusX;
    byte radiusY;

    public Vector getPosition() {
        return position;
    }

    public short getRespawnZ() {
        return respawnZ;
    }

    public byte getRadiusX() {
        return radiusX;
    }

    public byte getRadiusY() {
        return radiusY;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setRespawnZ(short respawnZ) {
        this.respawnZ = respawnZ;
    }

    public void setRadiusX(byte radiusX) {
        this.radiusX = radiusX;
    }

    public void setRadiusY(byte radiusY) {
        this.radiusY = radiusY;
    }
}
