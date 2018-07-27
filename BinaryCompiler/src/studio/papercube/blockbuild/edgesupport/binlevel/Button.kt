package studio.papercube.blockbuild.edgesupport.binlevel

data class Button(
        var visibility: Byte = EdgeConstants.BUTTON_INVISIBLE,
        var disableCount: Byte = 0,
        var mode: Byte = EdgeConstants.BUTTON_STAY_DOWN,
        var parentId: Short = 0,
        var sequenceInOrder: Boolean = false,
        var siblingsCount: Byte = 0,
        var isMoving:Boolean = false,
        var movingPlatformId: Short = -1,
        var position: Vector = Vector(),
        var events: MutableList<Short> = ArrayList()
)