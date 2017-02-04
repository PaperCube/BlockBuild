package studio.papercube.blockbuild.view3d

import javafx.scene.Group
import javafx.scene.shape.Box
import studio.papercube.blockbuild.edgesupport.binlevel.Vector

class LevelEditingView : LevelView() {
    fun addStaticBlock() {
        level?.run {
            collisionMap.addVector(Vector(focusPositionX.toShort(), focusPositionY.toShort(), focusPositionZ.toShort()))
            reload()
        }
    }

    private object PositionIndicator : Group() {
        val box: Box
            get() = Box()

        init {
            box.run {
                width=1.0
                height = 0.1
                depth=0.1
            }
        }
    }
}

