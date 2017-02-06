package studio.papercube.blockbuild.edgesupport.binlevel;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Vector implements Comparable<Vector> {
    short x;
    short y;
    short z;

    public Vector() {

    }

    public Vector(short ix, short iy, short iz) {
        x = ix;
        y = iy;
        z = iz;
    }

    public void setX(short x) {
        this.x = x;
    }

    public void setY(short y) {
        this.y = y;
    }

    public void setZ(short z) {
        this.z = z;
    }

    public short getX() {

        return x;
    }

    public short getY() {
        return y;
    }

    public short getZ() {
        return z;
    }


    public boolean fitsSize(short sx, short sy, short sz) {
        return !((x | y | z) < 0 || x >= sx || y >= sy || z >= sz);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vector) {
            Vector v = (Vector) obj;
            return v.x == x && v.y == y && v.z == z;
        } else return false;
    }

    /**
     * 和另一个Vector比较。
     *
     * @param anotherVector 要比较的另一个Vector
     * @return 如果另一个Vector和现在这个是相同的（anotherVector.equals(this))那么返回0，否则返回1
     */
    @Override
    public int compareTo(@NotNull Vector anotherVector) {
        return Objects.requireNonNull(anotherVector).equals(this) ? 0 : 1;
    }

    /**
     * 创建一个新的Vector，并高度+1
     *
     * @return 新的Vector
     * @see #downBlock()
     */
    public Vector upBlock() {
        return new Vector(x, y, ((short) (z + 1)));
    }

    /**
     * 创建一个新的Vector，并高度-1
     *
     * @return 新的Vector
     * @see #upBlock()
     */
    public Vector downBlock() {
        return new Vector(x, y, ((short) (z - 1)));
    }

    public short component1() {
        return x;
    }

    public short component2(){
        return y;
    }

    public short component3(){
        return z;
    }
}
