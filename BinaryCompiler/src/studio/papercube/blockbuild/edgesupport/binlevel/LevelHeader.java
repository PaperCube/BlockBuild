package studio.papercube.blockbuild.edgesupport.binlevel;

@SuppressWarnings("unused")
public class LevelHeader {
    int levelId;
    String title;
    short[] timeThresholds;

    public int getLevelId() {
        return levelId;
    }

    public String getTitle() {
        return title;
    }

    public short[] getTimeThresholds() {
        return timeThresholds;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTimeThresholds(short[] timeThresholds) {
        this.timeThresholds = timeThresholds;
    }

}
