package studio.papercube.blockbuild.edgesupport.binlevel

import studio.papercube.blockbuild.compatibility.BinaryWriter
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream

/**
 *[LevelWriter]自动修正关卡Z轴。关于详细的规则，参见[LevelReader]的文档
 *
 * Created by imzhy on 2016/12/11.
 *
 * @see LevelReader
 */

open class LevelWriter(val level: Level, outputStream: OutputStream) {
    val w = BinaryWriter(BufferedOutputStream(outputStream))

    constructor(level: Level, target: File) : this(level, BufferedOutputStream(FileOutputStream(target), 32767))


    fun write() {
        with(level) {
            header.writeStructure()

            sizeZ.writeAsByte()
            sizeX.writeAsShort()
            sizeY.writeAsShort()

            val unknownShort1 = sizeX + sizeY
            unknownShort1.writeAsShort()

            val unknownShort2 = sizeX + sizeY + 2 * sizeZ
            unknownShort2.writeAsShort()

            val unknownShort3 = (unknownShort1 + 9) / 10
            unknownShort3.writeAsShort()

            val unknownShort4 = (unknownShort2 + 9) / 10
            unknownShort4.writeAsShort()

            10.writeAsByte() //unknownByte1

            val unknownShort5 = sizeY - 1
            unknownShort5.writeAsShort()

            0.writeAsShort() //unknownShort6

            Level.LegacyMinimap(unknownShort3,unknownShort4).toByteArray().writeFully()

            collisionMap.toByteArray(sizeX, sizeY, sizeZ.toShort()).writeFully()

            spawnPoint.writeStructure()

            zoom.writeAsShort()
            if (zoom < 0) {
                value.writeAsShort()
                valueIsAngle.writeAsBoolean()
            }

            exitPoint.writeStructure()

            movingPlatforms.size.writeAsShort()
            movingPlatforms.forEach { it.writeStructure() }

            bumpers.size.writeAsShort()
            bumpers.forEach { it.writeStructure() }

            fallingPlatforms.size.writeAsShort()
            fallingPlatforms.forEach { it.writeStructure() }

            checkPoints.size.writeAsShort()
            checkPoints.forEach { it.writeStructure() }

            cameraTriggers.size.writeAsShort()
            cameraTriggers.forEach { it.writeStructure() }

            prisms.size.writeAsShort()
            prisms.forEach { it.writeStructure() }

            0.writeAsShort() //Deprecated element 'fans'

            blockEvents.size.writeAsShort()
            blockEvents.forEach { it.writeStructure() }

            buttons.size.writeAsShort()
            buttons.forEach { it.writeStructure() }

            otherCubes.size.writeAsShort()
            otherCubes.forEach { it.writeStructure() }

            resizers.size.writeAsShort()
            resizers.forEach { it.writeStructure() }

            0.writeAsShort() //Deprecated element 'MiniBlocks'

            theme.writeAsByte()
            musicJ2ME.writeAsByte()
            music.writeAsByte()

        }

        w.close()
    }

    private fun LevelHeader.writeStructure() {
        levelId.writeAsInt()
        val titleBytes = title.toByteArray()
        titleBytes.size.writeAsInt()
        titleBytes.writeFully()

        for (i in 0..4) timeThresholds[i].writeAsShort()

        val prismCount = level.prisms.size.toShort()
        prismCount.writeAsShort()
    }

    private fun Vector.writeStructure() {
        x.writeAsShort()
        y.writeAsShort()
        z.writeAsShort()
    }

    private fun MovingPlatform.writeStructure() {
        fun Waypoint.writeStructure() {
            position.upBlock().writeStructure()
            travelTime.writeAsShort()
            pauseTime.writeAsShort()
        }

        autoStart.writeAsByte()
        loopStartIndex.writeAsByte()
        clones.writeAsShort()
        fullBlock.writeAsBoolean()

        waypoints.size.writeAsByte()
        waypoints.forEach { it.writeStructure() }
    }

    private fun Bumper.writeStructure() {
        fun Bumper.Side.writeStructure() {
            startDelay.writeAsShort()
            pulseRate.writeAsShort()
        }

        enabled.writeAsBoolean()
        position.writeStructure()

        north.writeStructure()
        east.writeStructure()
        south.writeStructure()
        west.writeStructure()
    }

    private fun FallingPlatform.writeStructure() {
        position.upBlock().writeStructure()
        floatTime.writeAsShort()
    }

    private fun CheckPoint.writeStructure() {
        position.writeStructure()
        respawnZ.writeAsShort()
        radiusX.writeAsByte()
        radiusY.writeAsByte()
    }

    private fun CameraTrigger.writeStructure() {
        position.writeStructure()
        zoom.writeAsShort()
        radiusX.writeAsByte()
        radiusY.writeAsByte()

        if (zoom.toInt() == -1) {
            reset.writeAsBoolean()
            startDelay.writeAsShort()
            duration.writeAsShort()
            value.writeAsShort()
            isSingleUse.writeAsBoolean()
            valueIsAngle.writeAsBoolean()
        }
    }

    private fun Prism.writeStructure() {
        position.writeStructure()
        energy.writeAsByte()
    }

    private fun BlockEvent.writeStructure() {
        type.writeAsByte()
        blockId.writeAsShort()
        payload.writeAsShort()
    }

    private fun Button.writeStructure() {
        visibility.writeAsByte()
        disableCount.writeAsByte()
        mode.writeAsByte()

        parentId.writeAsShort()

        sequenceInOrder.writeAsBoolean()
        siblingsCount.writeAsByte()

        isMoving.writeAsBoolean()

        if (isMoving) {
            movingPlatformId.writeAsShort()
        } else {
            position.writeStructure()
        }

        events.size.writeAsShort()
        events.forEach { it.writeAsShort() }
    }

    private fun OtherCube.writeStructure() {
        fun OtherCube.KeyEvent.writeStructure() {
            timeOffset.writeAsShort()
            direction.writeAsByte()
            eventType.writeAsByte()
        }

        positionTrigger.writeStructure()

        movingBlockSync.writeAsShort()

        if (movingBlockSync.toInt() == -2) {
            darkcubeRadiusX.writeAsByte()
            darkCubeRadiusY.writeAsByte()
            darkcubeMovingBlockSync.writeAsShort()
        }

        keyEvents.size.writeAsShort()
        positionCube.writeStructure()
        keyEvents.forEach { it.writeStructure() }

    }

    private fun Resizer.writeStructure() {
        position.writeStructure()
        visible.writeAsBoolean()
        direction.writeAsByte()
    }
    //=====================================================

    private fun Number.writeAsInt() = w.writeInt(this.toInt())

    private fun ByteArray.writeFully() {
        for (i in 0 until this.size) this[i].writeAsByte()
    }

    private fun Number.writeAsByte() = w.writeByte(this.toInt())

    private fun Number.writeAsShort() = w.writeShort(this.toInt())

    private fun Boolean.writeAsBoolean() = w.writeBoolean(this)


}

