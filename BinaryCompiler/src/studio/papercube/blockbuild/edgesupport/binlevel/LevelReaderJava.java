package studio.papercube.blockbuild.edgesupport.binlevel;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by imzhy on 2016/12/3.
 */
@Deprecated
public class LevelReaderJava {
    DataInputStream dis;
    Level level = new Level();

    public LevelReaderJava(File levelFile) throws IOException {
        dis = new DataInputStream(new FileInputStream(levelFile));
    }

    public Level read() throws IOException {
        level.header = readLevelHeader();
        return level;
    }

    LevelHeader readLevelHeader() throws IOException {
        LevelHeader header = new LevelHeader();
        header.levelId = dis.readInt();
        return header;
    }
}
