package studio.papercube.blockbuild.compatibility;

import org.jetbrains.annotations.NotNull;

import java.io.*;

/**
 * 这个Reader按照小端序的方式来读取基本类型。
 * 文档请参见{@link DataInputStream} <br/>
 * Created by imzhy on 2016/12/4.
 */
public class BinaryReader implements DataInput {
    private DataInputStream dataInputStream;

    public BinaryReader(InputStream inputStream) {
        dataInputStream = new DataInputStream(inputStream);
    }

    public int read(@NotNull byte[] b) throws IOException {
        return dataInputStream.read(b);
    }

    public int read(byte[] b, int off, int len) throws IOException {
        return dataInputStream.read(b, off, len);
    }

    public static String readUTF(@NotNull DataInput in) throws IOException {
        return DataInputStream.readUTF(in);
    }

    public int read() throws IOException {
        return dataInputStream.read();
    }

    public long skip(long n) throws IOException {
        return dataInputStream.skip(n);
    }

    public int available() throws IOException {
        return dataInputStream.available();
    }

    public void close() throws IOException {
        dataInputStream.close();
    }

    public void mark(int readlimit) {
        dataInputStream.mark(readlimit);
    }

    public void reset() throws IOException {
        dataInputStream.reset();
    }

    public boolean markSupported() {
        return dataInputStream.markSupported();
    }

    @Override
    public void readFully(@NotNull byte[] b) throws IOException {
        dataInputStream.readFully(b);
    }

    @Override
    public void readFully(@NotNull byte[] b, int off, int len) throws IOException {
        dataInputStream.readFully(b, off, len);
    }

    @Override
    public int skipBytes(int n) throws IOException {
        return dataInputStream.skipBytes(n);
    }

    @Override
    public boolean readBoolean() throws IOException {
        return dataInputStream.readBoolean();
    }

    @Override
    public byte readByte() throws IOException {
        return dataInputStream.readByte();
    }

    @Override
    public int readUnsignedByte() throws IOException {
        return dataInputStream.readUnsignedByte();
    }

    @Override
    public short readShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if((ch1|ch2)<0) throw new EOFException();
        return (short) ((ch2 << 8) + (ch1 << 0));
    }

    @Override
    public int readUnsignedShort() throws IOException {
        int ch1 = read();
        int ch2 = read();
        if ((ch1 | ch2) < 0)
            throw new EOFException();
        return (ch2 << 8) + (ch1 << 0);
        
    }

    @Deprecated
    @Override
    public char readChar() throws IOException {
        return (char)readUnsignedShort();
    }

    @Override
    public int readInt() throws IOException {
        int ch1 = read();
        int ch2 = read();
        int ch3 = read();
        int ch4 = read();
        if ((ch1 | ch2 | ch3 | ch4) < 0)
            throw new EOFException();
        return ((ch4 << 24) + (ch3 << 16) + (ch2 << 8) + (ch1 << 0));
    }

    @Override
    public long readLong() throws IOException {
        byte readBuffer[] = new byte[8];
        readFully(readBuffer, 0, 8);
        return (((long)readBuffer[7] << 56) +
                ((long)(readBuffer[6] & 255) << 48) +
                ((long)(readBuffer[5] & 255) << 40) +
                ((long)(readBuffer[4] & 255) << 32) +
                ((long)(readBuffer[3] & 255) << 24) +
                ((readBuffer[2] & 255) << 16) +
                ((readBuffer[1] & 255) <<  8) +
                ((readBuffer[0] & 255) <<  0));
    }

    @Override
    public float readFloat() throws IOException {
        return Float.intBitsToFloat(readInt());
    }

    @Override
    public double readDouble() throws IOException {
        return Double.longBitsToDouble(readLong());
    }

    @Deprecated
    @Override
    public String readLine() throws IOException {
        return dataInputStream.readLine();
    }

    @NotNull
    @Override
    public String readUTF() throws IOException {
        return dataInputStream.readUTF();
    }
    
    
}
