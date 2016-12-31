package studio.papercube.blockbuild.edgesupport.binlevel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by imzhy on 2016/12/3.
 */
@SuppressWarnings({"unused"})
public class Level implements Cloneable,Serializable {
    LevelHeader header;
    byte sizeZ;
    short sizeX;
    short sizeY;
    LegacyMinimap legacyMinimap;
    CollisionMap collisionMap;
    Vector spawnPoint;
    short zoom;
    /**
     * Optional, only when zoom<0
     */
    short value;
    /**
     * Optional, only when zoom<0
     */
    boolean valueIsAngle;

    Vector exitPoint;

    List<MovingPlatform> movingPlatforms = new ArrayList<>();

    List<Bumper> bumpers = new ArrayList<>();

    List<FallingPlatform> fallingPlatforms = new ArrayList<>();

    List<CheckPoint> checkPoints = new ArrayList<>();

    List<CameraTrigger> cameraTriggers = new ArrayList<>();

    List<Prism> prisms = new ArrayList<>();

    List<BlockEvent> blockEvents = new ArrayList<>();

    List<Button> buttons = new ArrayList<>();

    List<OtherCube> otherCubes = new ArrayList<>();

    List<Resizer> resizers = new ArrayList<>();

    byte theme;
    byte musicJ2ME;
    byte music;


    public List<Bumper> getBumpers() {
        return bumpers;
    }

    public void setBumpers(List<Bumper> bumpers) {
        this.bumpers = bumpers;
    }


    public List<FallingPlatform> getFallingPlatforms() {
        return fallingPlatforms;
    }

    public void setFallingPlatforms(List<FallingPlatform> fallingPlatforms) {
        this.fallingPlatforms = fallingPlatforms;
    }


    public List<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public void setCheckPoints(List<CheckPoint> checkPoints) {
        this.checkPoints = checkPoints;
    }


    public List<CameraTrigger> getCameraTriggers() {
        return cameraTriggers;
    }

    public void setCameraTriggers(List<CameraTrigger> cameraTriggers) {
        this.cameraTriggers = cameraTriggers;
    }


    public List<Prism> getPrisms() {
        return prisms;
    }

    public void setPrisms(List<Prism> prisms) {
        this.prisms = prisms;
    }


    public List<BlockEvent> getBlockEvents() {
        return blockEvents;
    }

    public void setBlockEvents(List<BlockEvent> blockEvents) {
        this.blockEvents = blockEvents;
    }


    public List<Button> getButtons() {
        return buttons;
    }

    public void setButtons(List<Button> buttons) {
        this.buttons = buttons;
    }


    public List<OtherCube> getOtherCubes() {
        return otherCubes;
    }

    public void setOtherCubes(List<OtherCube> otherCubes) {
        this.otherCubes = otherCubes;
    }


    public List<Resizer> getResizers() {
        return resizers;
    }

    public void setResizers(List<Resizer> resizers) {
        this.resizers = resizers;
    }

    public byte getTheme() {
        return theme;
    }

    public void setTheme(byte theme) {
        this.theme = theme;
    }

    public byte getMusicJ2ME() {
        return musicJ2ME;
    }

    public void setMusicJ2ME(byte musicJ2ME) {
        this.musicJ2ME = musicJ2ME;
    }

    public byte getMusic() {
        return music;
    }

    public void setMusic(byte music) {
        this.music = music;
    }

    public LevelHeader getHeader() {
        return header;
    }

    public byte getSizeZ() {
        return sizeZ;
    }

    public short getSizeX() {
        return sizeX;
    }

    public short getSizeY() {
        return sizeY;
    }

    public LegacyMinimap getLegacyMinimap() {
        return legacyMinimap;
    }

    public CollisionMap getCollisionMap() {
        return collisionMap;
    }

    public Vector getSpawnPoint() {
        return spawnPoint;
    }

    public short getZoom() {
        return zoom;
    }

    public short getValue() {
        return value;
    }

    public boolean isValueIsAngle() {
        return valueIsAngle;
    }

    public Vector getExitPoint() {
        return exitPoint;
    }

    public void setHeader(LevelHeader header) {
        this.header = header;
    }

    public void setSizeZ(byte sizeZ) {
        this.sizeZ = sizeZ;
    }

    public void setSizeX(short sizeX) {
        this.sizeX = sizeX;
    }

    public void setSizeY(short sizeY) {
        this.sizeY = sizeY;
    }

    public void setLegacyMinimap(LegacyMinimap legacyMinimap) {
        this.legacyMinimap = legacyMinimap;
    }

    public void setCollisionMap(CollisionMap collisionMap) {
        this.collisionMap = collisionMap;
    }

    public void setSpawnPoint(Vector spawnPoint) {
        this.spawnPoint = spawnPoint;
    }

    public void setZoom(short zoom) {
        this.zoom = zoom;
    }

    public void setValue(short value) {
        this.value = value;
    }

    public void setValueIsAngle(boolean valueIsAngle) {
        this.valueIsAngle = valueIsAngle;
    }

    public void setExitPoint(Vector exitPoint) {
        this.exitPoint = exitPoint;
    }

    public List<MovingPlatform> getMovingPlatforms() {
        return movingPlatforms;
    }

    public void setMovingPlatforms(List<MovingPlatform> movingPlatforms) {
        this.movingPlatforms = movingPlatforms;
    }

    public String titleToString() {
        return header.titleToString();
    }


    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }

    public static class LegacyMinimap {
        private byte[] legacyMinimap;

        public LegacyMinimap(byte[] data) {
            legacyMinimap = data;
        }

        public LegacyMinimap(int length) {
            this(new byte[length]);
        }

        /**
         * short可以自动转换成int, 所以这里使用int作为参数也无所谓了
         *
         * @param unknownShort3 (unknown_short_1 + 9) / 10
         * @param unknownShort4 (unknown_short_2 + 9) / 10
         */
        public LegacyMinimap(int unknownShort3, int unknownShort4) {
            this(((unknownShort3 * unknownShort4) + 7) / 8);
        }

        public byte[] toByteArray() {
            return legacyMinimap;
        }
    }

    /**
     * 想要证明它运行正常，考虑以下代码。<br/>
     * <code>
     * Level l = lists.get(0);<br/>
     * Level.CollisionMap collisionMap = l.collisionMap;<br/>
     * byte[] a = collisionMap.toByteArray(l.sizeX, l.sizeY, l.sizeZ);<br/>
     * byte[] b = collisionMap.originalData;<br/>
     * int breakIndex = -1;<br/><br/>
     * for (int i = 0; i < Math.max(a.length, b.length); i++)<br/>
     * if (a[i] != b[i]) {<br/>
     * breakIndex = i;<br/>
     * break;<br/>
     * }<br/>
     * return breakIndex;<br/>
     * </code>
     * 看完后我告诉你，这个不能证明它运行正常。
     */
    public static class CollisionMap {
        private byte[] originalData;
        private List<Vector> vectorList = new ArrayList<>();

        public CollisionMap(byte[] data, short sizeX, short sizeY, short sizeZ) {
            originalData = data;
            int index = 0;
            for (short z = 0; z < sizeZ; z++) {
//                index = z * ((sizeX * sizeY + 7) / 8 * 8); //More Exact
                for (short y = 0; y < sizeY; y++) {
                    for (short x = 0; x < sizeX; x++) {
                        if (bitOf(data, index++) == 1) vectorList.add(new Vector(x, y, z));
                    }
                }

                index = (index + 7) / 8 * 8; //读完一层，不到一字节就补0
            }

        }

        public boolean addVector(Vector vector) {
            if (vectorList.contains(vector)) return false;
            else return vectorList.add(vector);
        }

        public boolean removeVector(Vector vector) {
            return vectorList.removeIf(vec -> vec.equals(vector));
        }

        public byte[] toByteArray(short sizeX, short sizeY, short sizeZ) {
            int bytesEveryLayer = (sizeX * sizeY + 7) / 8;
            byte[] data = new byte[sizeZ * bytesEveryLayer];
            for (Vector v : vectorList) {
                if (!v.fitsSize(sizeX, sizeY, sizeZ))
                    throw new VectorOutOfSizeException(
                            String.format(
                                    "Vector %d,%d,%d is out of size %d,%d,%d", v.x, v.y, v.z, sizeX, sizeY, sizeZ
                            )
                    );


                modifyBit(data, v.z * (bytesEveryLayer * 8) + v.y * sizeX + v.x, 1);
            }

            return data;
        }

//        public byte[] toByteArray(short sizeX, short sizeY, short sizeZ) {
//            byte[]
//        }

        private int bitOf(byte[] data, int index) {
            return (data[index / 8] >>> (7 - (index % 8))) & 1;
        }

        private void modifyBit(byte[] data, int index, int value) {
            byte originalByte = data[index / 8];
            data[index / 8] = (byte) (originalByte | (1 << (7 - index % 8)));
        }

        public static class VectorOutOfSizeException extends IndexOutOfBoundsException {
            public VectorOutOfSizeException(String msg) {
                super(msg);
            }
        }
    }

}
