package studio.papercube.blockbuild.edgesupport.libtwotribes;

import org.jetbrains.annotations.Contract;

/**
 * Java implementation of
 * <a href = https://github.com/Mygod/EDGE/blob/master/EdgeTool/Core/LibTwoTribes/Util/CRC32.cs>libTwoTribes CRC32 C# impelementation</a>
 */
public class CRC32 {
    public static final int DEFAULT_POLYNOMIAL_COEFFICIENT = 0xEDB88320;

    private final int[] crcTable = new int[256];
    private final int polynomialCoefficient;

    public CRC32(int polynomialCoefficient) {
        this.polynomialCoefficient = polynomialCoefficient;
        calculateCRCTable();
    }

    public CRC32() {
        this(DEFAULT_POLYNOMIAL_COEFFICIENT);
    }

    private void calculateCRCTable() {
        int c;

        for (int i = 0; i < 256; i++) {
            c = i;
            for (int k = 0; k < 8; k++) {
                if ((c & 1) == 0) {
                    c = c >>> 1;
                } else {
                    c = polynomialCoefficient ^ (c >>> 1);
                }
            }

            crcTable[i] = c;
        }
    }

    @Contract(pure = true)
    private int updateCRC(/*unsigned*/ int crc, byte[] data) {
        for (byte aData : data) crc = crcTable[(crc ^ aData) & 0xFF] ^ (crc >>> 8);
        return crc;
    }

    public int calculateCRC(byte[] data) {
        return updateCRC(0x0, data);
    }
}
