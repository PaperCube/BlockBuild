package studio.papercube.blockbuild.view3d;

import javafx.collections.ObservableList;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import studio.papercube.blockbuild.edgesupport.binlevel.Level;
import studio.papercube.blockbuild.view3d.renderableelements.*;

/**
 * Created by PaperCube on 2017/1/28.
 */
public class LevelView extends BlockGroup {
    private Group currentLevelGroup = null;

    public LevelView() {

    }

    public LevelView(Level level) {
        this();
        setLevel(level);
    }

    public void setLevel(Level level) {
        if (currentLevelGroup != null) getChildren().remove(currentLevelGroup);

        Group viewGroup = new Group();
        ObservableList<Node> nodes = viewGroup.getChildren();

        final PhongMaterial red = new PhongMaterial(Color.RED);
        final PhongMaterial blue = new PhongMaterial(Color.LIMEGREEN);
        final PhongMaterial lightBlack = new PhongMaterial(Color.rgb(30, 30, 30));


        level.getCollisionMap().duplicateVectors().forEach(vector -> nodes.add(new RenderableStaticBlock(vector).toNode()));
        level.getMovingPlatforms().forEach(movingPlatform -> nodes.add(new LastWaypointRenderedMovingPlatform(movingPlatform).toNode()));
        level.getFallingPlatforms().forEach(fallingPlatform -> nodes.add(new RenderableFallingPlatform(fallingPlatform).toNode()));
        level.getPrisms().forEach(prism -> nodes.add(new RenderablePrism(prism).toNode()));
        Box spawn = new VectorBox(level.getSpawnPoint());
        spawn.setMaterial(red);

        Box end = new VectorBox(level.getExitPoint());
        end.setMaterial(blue);

        nodes.addAll(spawn, end);

        currentLevelGroup = viewGroup;
        getChildren().add(currentLevelGroup);
    }


}
