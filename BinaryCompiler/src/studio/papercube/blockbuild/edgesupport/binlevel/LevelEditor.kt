package studio.papercube.blockbuild.edgesupport.binlevel

import java.util.*

class LevelEditor(levelSource: Level?) {
    var level: Level? = levelSource
        set(value){
            field = value
            value?.let { levelHistory.add(it) }
        }

    private val levelHistory = HistoryList()

    init {
        levelHistory.clear()
//        val levelData = levelSource.toByteArray()
//        levelHistory.add(0, Level.build(levelData))
//        this.level = Level.build(levelData)
        val currentLevel = level
        currentLevel?.run {
            val levelData = currentLevel.toByteArray()
            levelHistory.add(0, Level.build(levelData))
        }
    }

    val originalLevel: Level?
        get() = levelHistory.firstOrNull()

    val replica: Level?
        get() = level?.bitwiseReplicate()

    fun addStaticBlock(position: Vector): Boolean {
        return level?.collisionMap?.addVector(position) ?: false
    }


    fun removeStaticBlock(position: Vector): Boolean {
        return level?.collisionMap?.removeVector(position) ?: false
    }

    fun addPrism(position: Vector):Boolean {
        val prism = Prism(position)
        return level?.prisms?.add(prism) ?: false
    }

    fun removePrism(position: Vector):Boolean {
        return level?.prisms?.removeAll { it.position==position } ?: false
    }


    fun autoAdjustSize() {
        val level = this.level ?: return

        var x: Short = 0
        var y = x
        var z = x

        for ((vectorX, vectorY, vectorZ) in level.collisionMap.vectors) {
            if (x < vectorX) x = vectorX
            if (y < vectorY) y = vectorY
            if (z < vectorZ) z = vectorZ
        }

        level.run {
            sizeX = ++x
            sizeY = ++y
            sizeZ = (++z).toByte()
        }
    }

    private inner class HistoryList : ArrayList<Level>()
}