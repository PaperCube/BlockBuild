package studio.papercube.blockbuild.view3d

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import studio.papercube.blockbuild.edgesupport.binlevel.Level
import studio.papercube.blockbuild.edgesupport.binlevel.LevelUtil
import studio.papercube.blockbuild.edgesupport.binlevel.Vector
import studio.papercube.blockbuild.view3d.renderableelements.*

/**
 * Created by PaperCube on 2017/1/28.
 */
open class LevelView() : EdgeAxisCompatibleBlockGroup() {
    private var currentLevelGroup: Group? = null
    var focusPositionX = 0
        set(value) {
            field = value
            cameraTranslateX = focusPositionX.toDouble()
        }

    var focusPositionY = 0
        set(value) {
            field = value
            cameraTranslateY = focusPositionY.toDouble()
        }

    var focusPositionZ = 0
        set(value) {
            field = value
            cameraTranslateZ = focusPositionZ.toDouble()
        }

    constructor(level: Level?) : this() {
        this.level = level

    }

    var level: Level? = null
        set(value) {
            field = value
            if (currentLevelGroup != null) children.remove(currentLevelGroup)

            val viewGroup = Group()
            val nodes = viewGroup.children

            value?.run {
                val red = PhongMaterial(Color.RED)
                val blue = PhongMaterial(Color.LIMEGREEN)
                val lightBlack = PhongMaterial(Color.rgb(30, 30, 30))


                collisionMap.duplicateVectors().forEach { vector -> nodes.add(RenderableStaticBlock(vector).toNode()) }
                movingPlatforms.forEach { movingPlatform -> nodes.add(LastWaypointRenderedMovingPlatform(movingPlatform).toNode()) }
                fallingPlatforms.forEach { fallingPlatform -> nodes.add(RenderableFallingPlatform(fallingPlatform).toNode()) }
                prisms.forEach { prism -> nodes.add(RenderablePrism(prism).toNode()) }
                val spawn = VectorBox(spawnPoint)
                spawn.material = red

                val end = VectorBox(exitPoint)
                end.material = blue

                nodes.addAll(spawn, end)
            }

            currentLevelGroup = viewGroup
            children.add(currentLevelGroup)
        }

    protected fun reload() {
        level = level
    }

    @Deprecated("USE THE ONE IN LEVEL_EDITOR--TEST ONLY.")
    fun addStaticBlock() {
        level?.run {
            collisionMap.addVector(Vector(focusPositionX.toShort(), focusPositionY.toShort(), focusPositionZ.toShort()))
            reload()
        }
    }

}
