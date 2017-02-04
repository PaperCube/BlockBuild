package studio.papercube.blockbuild.edgesupport.binlevel;

@SuppressWarnings("unused")
public class LevelHeader {
    int levelId;
    int titleLength;
    byte[] title;
    short[] timeThresholds;

    public int getLevelId() {
        return levelId;
    }

    public int getTitleLength() {
        return titleLength;
    }

    public byte[] getTitle() {
        return title;
    }

    public short[] getTimeThresholds() {
        return timeThresholds;
    }


    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public void setTitleLength(int titleLength) {
        this.titleLength = titleLength;
    }

    public void setTitle(byte[] title) {
        this.title = title;
    }

    public void setTimeThresholds(short[] timeThresholds) {
        this.timeThresholds = timeThresholds;
    }

    public String titleToString() {
        return new String(title);
    }
}
