package studio.papercube.blockbuild.view3d.renderableelements;

import javafx.scene.Node;
import org.jetbrains.annotations.NotNull;

public abstract class RenderableElement {
    @NotNull public abstract Node toNode();
}
