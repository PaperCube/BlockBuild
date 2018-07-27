package studio.papercube.blockbuild.edgesupport.binlevel

data class Bumper(
        var enabled: Boolean = false,
        var position: Vector = Vector(),
        val north: Side = Side(),
        val east: Side = Side(),
        val south: Side = Side(),
        val west: Side = Side()

) {
    data class Side(
            var startDelay: Short = -1,
            var pulseRate: Short = -1
    )
}