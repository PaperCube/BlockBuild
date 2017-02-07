package studio.papercube.blockbuild.view3d.renderableelements

import javafx.scene.image.Image
import javafx.scene.paint.PhongMaterial
import libpapercube.desktop.kotlin.lazyInit
import studio.papercube.blockbuild.resources.Resource

internal object Materials {
    val staticBlockMaterial by lazyInit(PhongMaterial()) {
        diffuseMap = Image(Resource.javaClass.getResourceAsStream("blockside.png"))
    }
}