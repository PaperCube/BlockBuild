package studio.papercube.blockbuild.view3d.renderableelements

import javafx.geometry.Point3D
import javafx.scene.Node
import javafx.scene.paint.Color
import javafx.scene.paint.Material
import javafx.scene.paint.PhongMaterial
import javafx.scene.shape.Box
import studio.papercube.blockbuild.edgesupport.binlevel.*

private fun Vector.toBox(): Box {
    return VectorBox(this)
}

private fun Vector.toBox(material: Material): Box {
    val material = PhongMaterial()
    val box = VectorBox(this)
    box.material = material
    return box
}

class VectorBox(vector: Vector) : Box(1.0, 1.0, 1.0) {
    init {
        with(vector) {
            translateX = x.toDouble()
            translateY = z.toDouble()
            translateZ = y.toDouble()
        }
        material = Materials.staticBlockMaterial
    }
}

class FirstWaypointRenderedMovingPlatform(val movingPlatform: MovingPlatform) : RenderableElement() {
    override fun toNode(): Node {
        with(movingPlatform.waypoints[0].position.toBox()) {
            material = PhongMaterial(Color.rgb(30, 30, 30))
            return this
        }
    }
}

class LastWaypointRenderedMovingPlatform(val movingPlatform: MovingPlatform) : RenderableElement() {
    override fun toNode(): Node {
        with(movingPlatform.waypoints.last().position.toBox()) {
            material = PhongMaterial(Color.rgb(30, 30, 30))
            return this
        }
    }
}

class RenderableStaticBlock(val position: Vector) : RenderableElement() {
    override fun toNode(): Node {
        return position.toBox()
    }
}

class RenderableFallingPlatform(val fallingPlatform: FallingPlatform) : RenderableElement() {
    override fun toNode(): Node {
        with(fallingPlatform.position.toBox()) {
            height = 0.1
            translateY += 0.45
            material = PhongMaterial(Color.WHITE)
            return this
        }
    }
}

class RenderableBumper(val bumper:Bumper) :RenderableElement(){
    override fun toNode(): Node {
        return bumper.position.toBox()
    }
}

class RenderablePrism(val prism: Prism) : RenderableElement() {
    override fun toNode(): Node {
        with(prism.position) {
            val box = Box(0.3, 0.3, 0.3)
            box.translateX = x.toDouble()
            box.translateY = z.toDouble()
            box.translateZ = y.toDouble()
            box.material = PhongMaterial(Color.YELLOW)
            box.rotationAxis = Point3D(0.0, 1.0, 2.0).normalize()
            box.rotate = 80.0

            return box
        }
    }
}


internal fun Vector.makeAxisOrderNormal():Vector{
    with(this){
        return Vector(x,z,y)
    }
}

