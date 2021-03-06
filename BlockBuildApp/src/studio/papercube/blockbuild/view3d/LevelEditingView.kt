package studio.papercube.blockbuild.view3d

import javafx.scene.Group
import javafx.scene.paint.Color
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import studio.papercube.blockbuild.edgesupport.binlevel.LevelEditor
import studio.papercube.blockbuild.edgesupport.binlevel.LevelWriter
import studio.papercube.blockbuild.edgesupport.binlevel.Vector
import java.io.OutputStream

class LevelEditingView : LevelView() {
    private var levelEditor: LevelEditor = LevelEditor(null)

    init {
        children.add(PositionIndicator)
    }

    override fun onLoadLevel() {
        super.onLoadLevel()
        levelEditor.level = level
    }

    fun addStaticBlock() {
        levelEditor.addStaticBlock(currentPositionToVector())
        reload()
    }

    fun deleteCurrent() {
        levelEditor.run {
            currentPositionToVector().let {
                removePrism(it)
                removeStaticBlock(it)
            }
        }
        reload()
    }

    fun addPrism() {
        levelEditor.addPrism(currentPositionToVector())
        reload()
    }

    override fun onFocusChanged() {
        PositionIndicator.run {
            translateX = focusPositionX.toDouble()
            translateY = focusPositionZ.toDouble()
            translateZ = focusPositionY.toDouble()
        }
    }

    override fun reload() {
        level = levelEditor.level
    }

    fun save(outputStream: OutputStream) {
        levelEditor.autoAdjustSize()
        levelEditor.level?.let { LevelWriter(it, outputStream).write() }
    }

    private fun currentPositionToVector() = Vector(focusPositionX.toShort(), focusPositionY.toShort(), focusPositionZ.toShort())


    object PositionIndicator : Group() {
        val coloredMaterial = PhongMaterial(Color.LIGHTBLUE)
        val box: Box
            get() = Box().run { material = coloredMaterial; return this }

        private inline fun Box.add(init: Box.() -> Unit) {
            this.init()
            children.add(this)
        }

        init {
            fun boxOnWidth(): Box {
                box.run {
                    width = 1.1
                    height = 0.1
                    depth = 0.1
                    return this
                }
            }

            fun boxOnHeight(): Box {
                box.run {
                    width = 0.1
                    height = 1.1
                    depth = 0.1
                    return this
                }
            }

            fun boxOnDepth(): Box {
                box.run {
                    width = 0.1
                    height = 0.1
                    depth = 1.1
                    return this
                }
            }

            forDualHalves { a, b ->
                boxOnWidth().add {
                    translateZ = a
                    translateY = b
                }
            }

            forDualHalves { a, b ->
                boxOnHeight().add {
                    translateX = a
                    translateZ = b
                }
            }

            forDualHalves { a, b ->
                boxOnDepth().add {
                    translateX = a
                    translateY = b
                }
            }


        }

        inline fun forDualHalves(operation: (Double, Double) -> Unit) {
            val halves = doubleArrayOf(-0.5, 0.5)
            for (a in halves)
                for (b in halves)
                    operation(a, b)
        }
    }
}

