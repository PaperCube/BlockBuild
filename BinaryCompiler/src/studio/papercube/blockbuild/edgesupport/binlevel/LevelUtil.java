package studio.papercube.blockbuild.edgesupport.binlevel;

/**
 * Created by imzhy on 2017/1/1.
 */
public class LevelUtil {
    private Level level;

    public LevelUtil(Level level) {
        this.level = level;
    }

    /**
     * 复制一个关卡。原始关卡必须是有效的，否则将会复制失败。它做的实际工作，是先将原始关卡
     * 解释成二进制数据，这个数据如果被输出到文件，即是一个有效关卡。然后再将二进制数据重新解释成
     * 一个 {@link studio.papercube.blockbuild.edgesupport.binlevel.Level}对象
     *
     * @return 复制的关卡。
     */
    public Level duplicate() {
        return Level.build(level.toByteArray());
    }

//    public Level move(final short offsetX, final short offsetY, final short offsetZ, final Level origin) {
//        class Mover {
//            {
//                Level level = duplicate(origin);
//                level.sizeZ = assertByte(level.sizeZ + offsetZ, "size z");
//            }
//
//            short assertShort(int value, @Nullable String msg) {
//                if (isBetween(value, -32768, 32767)) return (short) value;
//                else throw new OutOfRangeException(msg != null ? msg + " : " + value : value + " is not a short");
//            }
//
//            byte assertByte(int value, @Nullable String msg) {
//                if (isBetween(value, -128, 127)) return (byte) value;
//                else throw new OutOfRangeException(msg != null ? msg + " : " + value : value + " is not a byte");
//            }
//
//            boolean isBetween(int value, int rangeLeft, int rangeRight) {
//                return rangeLeft <= value && value <= rangeRight;
//            }
//        }
//
//    }


    public static class OutOfRangeException extends RuntimeException {
        public OutOfRangeException() {
            super();
        }

        public OutOfRangeException(String message) {
            super(message);
        }
    }


}
