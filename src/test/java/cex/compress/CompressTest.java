package cex.compress;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.zip.DataFormatException;

public class CompressTest {

    private static final int SIZE = 1 * 1024 * 1024;

    @Test
    public void testOnRandom() throws IOException, DataFormatException {
        byte[] data = new byte[SIZE];
        new Random().nextBytes(data);
        doTest(data);
    }

    @Test
    public void testOnText() throws IOException, DataFormatException {
        String text = "How one dull get busy dare far. An concluded sportsman offending so provision mr education. " +
                "Limits far yet turned highly repair parish talked six. Advantages entreaties mr he apartments do. " +
                "So by colonel hearted ferrars. Words to up style of since world. " +
                "Their saved linen downs tears son add music. In expression an solicitude principles";
        byte[] data = text.getBytes(Charset.forName("UTF-8"));
        doTest(data);
    }

    private void doTest(byte[] data) throws IOException, DataFormatException {
        byte[] compressed = CompressUtils.compress(data);
        byte[] decompressed = CompressUtils.decompress(compressed);

        Assert.assertArrayEquals(data, decompressed);
    }
}
