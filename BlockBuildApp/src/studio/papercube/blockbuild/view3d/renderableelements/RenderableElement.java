package studio.papercube.blockbuild.view3d.renderableelements;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

/**
 * Created by PaperCube on 2017/1/28.
 */
public abstract class RenderableElement {
    @NotNull public abstract Node toNode();
}
