package studio.papercube.blockbuild.compatibility;

import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 这个Writer按照小端序的方式来写入基本类型。
 * 文档请参见{@link DataOutputStream} <br/>
 * Created by imzhy on 2016/12/11.
 */
public class BinaryWriter implements DataOutput{
    private DataOutputStream out;

    public BinaryWriter(OutputStream outputStream) {
        out = new DataOutputStream(outputStream);
    }

    public void write(int b) throws IOException {
        out.write(b);
    }

    public void write(@NotNull byte[] b, int off, int len) throws IOException {
        out.write(b, off, len);
    }

    public void flush() throws IOException {
        out.flush();
    }

    public void writeBoolean(boolean v) throws IOException {
        out.writeBoolean(v);
    }

    public void writeByte(int v) throws IOException {
        out.writeByte(v);
    }

    public void writeShort(int v) throws IOException {
        out.write((v >>> 0) & 0xFF);
        out.write((v >>> 8) & 0xFF);
    }

    public void writeChar(int v) throws IOException {
        this.writeShort(v);
    }

    public void writeInt(int v) throws IOException {
        out.write((v >>> 0) & 0xFF);
        out.write((v >>> 8) & 0xFF);
        out.write((v >>>  16) & 0xFF);
        out.write((v >>>  24) & 0xFF);
    }

    public void writeLong(long v) throws IOException {
        byte writeBuffer[] = new byte[8];

        writeBuffer[7] = (byte)(v >>> 56);
        writeBuffer[6] = (byte)(v >>> 48);
        writeBuffer[5] = (byte)(v >>> 40);
        writeBuffer[4] = (byte)(v >>> 32);
        writeBuffer[3] = (byte)(v >>> 24);
        writeBuffer[2] = (byte)(v >>> 16);
        writeBuffer[1] = (byte)(v >>>  8);
        writeBuffer[0] = (byte)(v >>>  0);
        out.write(writeBuffer, 0, 8);
    }

    public void writeFloat(float v) throws IOException {
        writeInt(Float.floatToIntBits(v));
    }

    public void writeDouble(double v) throws IOException {
        writeLong(Double.doubleToLongBits(v));
    }

    public void writeBytes(@NotNull String s) throws IOException {
        out.writeBytes(s);
    }

    public void writeChars(@NotNull String s) throws IOException {
        out.writeChars(s);
    }

    public void writeUTF(@NotNull String str) throws IOException {
        out.writeUTF(str);
    }

    public int size() {
        return out.size();
    }

    public void write(@NotNull byte[] b) throws IOException {
        out.write(b);
    }

    public void close() throws IOException {
        out.close();
    }

}
