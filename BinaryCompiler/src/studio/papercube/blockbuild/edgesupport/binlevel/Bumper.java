package studio.papercube.blockbuild.edgesupport.binlevel;

public class Bumper {
    boolean enabled;
    Vector position;
    Side north;
    Side east;
    Side south;
    Side west;

    public boolean isEnabled() {
        return enabled;
    }

    public Vector getPosition() {
        return position;
    }

    public Side getNorth() {
        return north;
    }

    public Side getEast() {
        return east;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setNorth(Side north) {
        this.north = north;
    }

    public void setEast(Side east) {
        this.east = east;
    }

    public void setSouth(Side south) {
        this.south = south;
    }

    public void setWest(Side west) {
        this.west = west;
    }

    public Side getSouth() {
        return south;
    }

    public Side getWest() {
        return west;
    }

    public static class Side {
        short startDelay = -1;
        short pulseRate = -1;

        public void setStartDelay(short startDelay) {
            this.startDelay = startDelay;
        }

        public void setPulseRate(short pulseRate) {
            this.pulseRate = pulseRate;
        }

        public short getStartDelay() {
            return startDelay;
        }

        public short getPulseRate() {
            return pulseRate;
        }
    }
}
