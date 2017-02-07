package studio.papercube.blockbuild.edgesupport.binlevel;

public class Prism {
    Vector position;
    byte energy = 1;

    Prism() {
        this(new Vector());
    }

    public Prism(Vector position) {
        this.position = position;
    }

    public byte getEnergy() {
        return energy;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    public void setEnergy(byte energy) {
        this.energy = energy;
    }

    public Vector getPosition() {
        return position;
    }
}
