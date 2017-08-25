import org.junit.Assert;
import org.junit.Test;
import studio.papercube.blockbuild.edgesupport.libtwotribes.CRC32;

public class CRCTest {
    @Test
    public void testCRC() {
        CRC32 crc32 = new CRC32(~CRC32.DEFAULT_POLYNOMIAL_COEFFICIENT);
        Assert.assertEquals(Integer.toHexString(crc32.calculateCRC("levels".getBytes())).toUpperCase(), "050DB82A");
    }

    @Test
    public void testNormalCRC() {
        CRC32 crc32 = new CRC32();
        Assert.assertEquals(Integer.toHexString(crc32.calculateCRC("levels".getBytes())), "");
    }
}
