package studio.papercube.blockbuild.edgesupport.binlevel

data class MovingPlatform(
        var autoStart: Byte = EdgeConstants.MOVING_PLATFORM_NO_AUTO_START,
        var loopStartIndex: Byte = 0,
        var clones: Short = -1,
        var fullBlock: Boolean = true,
        var waypoints: MutableList<Waypoint> = ArrayList()
)