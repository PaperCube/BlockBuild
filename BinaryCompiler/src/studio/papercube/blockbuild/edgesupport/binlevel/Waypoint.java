package studio.papercube.blockbuild.edgesupport.binlevel;

public class Waypoint {
    Vector position;
    short travelTime;
    short pauseTime;

    public Vector getPosition() {
        return position;
    }

    public short getTravelTime() {
        return travelTime;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setTravelTime(short travelTime) {
        this.travelTime = travelTime;
    }

    public void setPauseTime(short pauseTime) {
        this.pauseTime = pauseTime;
    }

    public short getPauseTime() {
        return pauseTime;
    }
}
