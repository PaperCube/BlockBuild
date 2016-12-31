package studio.papercube.blockbuild.edgesupport.binlevel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by imzhy on 2016/12/4.
 */
public class MovingPlatform {
    byte autoStart;
    byte loopStartIndex;
    short clones = 0;
    boolean fullBlock;
    List<Waypoint> waypoints = new ArrayList<>();

    public byte getAutoStart() {
        return autoStart;
    }

    public byte getLoopStartIndex() {
        return loopStartIndex;
    }

    public short getClones() {
        return clones;
    }

    public boolean isFullBlock() {
        return fullBlock;
    }

    public void setAutoStart(byte autoStart) {
        this.autoStart = autoStart;
    }

    public void setLoopStartIndex(byte loopStartIndex) {
        this.loopStartIndex = loopStartIndex;
    }

    public void setClones(short clones) {
        this.clones = clones;
    }

    public void setFullBlock(boolean fullBlock) {
        this.fullBlock = fullBlock;
    }

    public void setWaypoints(List<Waypoint> waypoints) {
        this.waypoints = waypoints;
    }

    public List<Waypoint> getWaypoints() {
        return waypoints;
    }
}
