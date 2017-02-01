package studio.papercube.blockbuild.view3d

/**
 * [EdgeAxisCompatibleBlockGroup] 使用的坐标系不是标准的坐标系，而是EDGE中使用的坐标系，既高度为Z轴的
 */
open class EdgeAxisCompatibleBlockGroup() :BlockGroup(){
    override fun setCameraTranslateX(value: Double) {
        super.setCameraTranslateX(value)
    }

    override fun getCameraTranslateX(): Double {
        return super.getCameraTranslateX()
    }

    override fun setCameraTranslateY(value: Double) {
        super.setCameraTranslateZ(value)
    }

    override fun getCameraTranslateY(): Double {
        return super.getCameraTranslateZ()
    }

    override fun setCameraTranslateZ(value: Double) {
        super.setCameraTranslateY(value)
    }

    override fun getCameraTranslateZ(): Double {
        return super.getCameraTranslateY()
    }
}