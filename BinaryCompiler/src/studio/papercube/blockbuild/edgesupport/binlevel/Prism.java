package studio.papercube.blockbuild.edgesupport.binlevel;

/**
 * Created by imzhy on 2016/12/4.
 */
public class Prism {
    Vector position;
    byte energy = 1;

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
