package studio.papercube.blockbuild.edgesupport.binlevel

import studio.papercube.blockbuild.compatibility.BinaryReader
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.InputStream

/**
 * 关于自动修正关卡Z轴：
 *
 * MovingPlatform和FallingPlatform，虽然在游戏中显示的不是一个平面，
 * 而是一个模型顶面和玩家方块底面的接触面为平面，向上影响的元素。
 *
 * 储存在文件中的规则，请参考如下：
 *
 * 当FP的存储坐标为X,Y,Z，RAINBOW的坐标为X,Y,Z时，RAINBOW站在FP上
 *
 * 当MP首个WAYPOINT的存储坐标为X,Y,Z，RAINBOW的坐标为X,Y,Z时，RAINBOW站在MP上
 *
 * 当RESIZER的存储坐标为X,Y,Z，RAINBOW的坐标为X,Y,Z时，RAINBOW变大/变小
 *
 * 当终点的存储坐标为X,Y,Z，RAINBOW的坐标为X,Y,Z时，过关
 *
 * 但是，当BUMPER的存储坐标为X,Y,Z，RAINBOW的坐标为X,Y,Z时，RAINBOW卡进BUMPER里！
 *
 * 因此，在读取时，MovingPlatform和FallingPlatform的高度都会自动减去1.这也就是说，使用[LevelReader]读取的 FallingPlatform
 * 和 MovingPlatform , 当坐标为X,Y,Z时，和一个坐标为X,Y,Z的静态方块重合。这和EdgeTool 是不同的
 *
 * Created by imzhy on 2016/12/3.
 *
 * @see LevelWriter
 */

open class LevelReader(inputStream: InputStream) {
    private val r: BinaryReader
    private val level = Level()

    init {
        r = BinaryReader(inputStream)
    }

    constructor(levelFile: File) : this(BufferedInputStream(FileInputStream(levelFile), 32767))


    fun read(): Level {

        with(level) {
            header = readLevelHeader()
            sizeZ = r.readByte()
            sizeX = r.readShort()
            sizeY = r.readShort()

            val unknownShort1 = r.readShort()
            val unknownShort2 = r.readShort()
            val unknownShort3 = r.readShort()
            val unknownShort4 = r.readShort()
            val unknownByte1 = r.readByte()
            val unknownShort5 = r.readShort()
            val unknownShort6 = r.readShort()

            val legmap = ByteArray(legacyMinimapLength(unknownShort3, unknownShort4))
            r.readFully(legmap)
            legacyMinimap = Level.LegacyMinimap(legmap)

            val colmap = ByteArray(collisionMapLength(sizeX, sizeY, sizeZ))
            r.readFully(colmap)
            collisionMap = Level.CollisionMap(colmap, sizeX, sizeY, sizeZ.toShort())

            spawnPoint = readVector()

            zoom = r.readShort()
            if (zoom < 0) {
                value = r.readShort()
                valueIsAngle = r.readBoolean()
            }

            exitPoint = readVector()

            movingPlatforms = readStructure { readMovingPlatform() }

            bumpers = readStructure { readBumper() }

            fallingPlatforms = readStructure { readFallingPlatform() }

            checkPoints = readStructure { readCheckPoint() }

            cameraTriggers = readStructure { readCameraTrigger() }

            prisms = readStructure { readPrism() }

            val fansCount = r.readShort()

            blockEvents = readStructure { readBlockEvent() }

            buttons = readStructure { readButton() }

            otherCubes = readStructure { readOtherCube() }

            resizers = readStructure { readResizer() }

            val miniBlocksCount = r.readShort()

            theme = r.readByte()
            musicJ2ME = r.readByte()
            music = r.readByte()

            //不标准的代码。只是为了防止未使用的警告。都怪KOTLIN这里要求太严格了。
            supressUnusedWarnings(unknownShort1, unknownShort2, unknownByte1, unknownShort5, unknownShort6, fansCount, miniBlocksCount)
        }
        r.close()

        return level;
    }

    private inline fun <reified T> readStructure(noinline read: () -> T) = mutableList(r.readShort().toInt()) { read() }

    private fun readLevelHeader(): LevelHeader {
        val header = LevelHeader()
        with(header) {
            levelId = r.readInt()
            titleLength = r.readInt()

            title = ByteArray(titleLength)
            for (i in 0 until titleLength) title[i] = r.readByte()

            timeThresholds = ShortArray(5)
            for (i in 0 until 5) timeThresholds[i] = r.readShort()

            r.readShort() //levelHeader.prismCount

        }
        return header
    }

    private fun readVector(): Vector {
        with(Vector()) {
            x = r.readShort()
            y = r.readShort()
            z = r.readShort()
            return this
        }
    }

    private fun readMovingPlatform(): MovingPlatform {

        fun readWaypoint(): Waypoint {
            with(Waypoint()) {
                position = readVector().downBlock()
                travelTime = r.readShort()
                pauseTime = r.readShort()
                return this
            }
        }

        with(MovingPlatform()) {
            autoStart = r.readByte()
            loopStartIndex = r.readByte()
            clones = r.readShort()
            fullBlock = r.readBoolean()

            waypoints = mutableList(r.readByte().toInt()) { readWaypoint() }

            return this
        }


    }

    private fun readBumper(): Bumper {
        fun readSide(): Bumper.Side {
            with(Bumper.Side()) {
                startDelay = r.readShort()
                pulseRate = r.readShort()
                return this
            }
        }

        with(Bumper()) {
            enabled = r.readBoolean()
            position = readVector()
            north = readSide()
            east = readSide()
            south = readSide()
            west = readSide()
            return this
        }
    }

    private fun readFallingPlatform(): FallingPlatform {
        with(FallingPlatform()) {
            position = readVector().downBlock()
            floatTime = r.readShort()
            return this
        }
    }

    private fun readCheckPoint(): CheckPoint {
        with(CheckPoint()) {
            position = readVector()
            respawnZ = r.readShort()
            radiusX = r.readByte()
            radiusY = r.readByte()
            return this
        }
    }

    private fun readCameraTrigger(): CameraTrigger {
        with(CameraTrigger()) {
            position = readVector()
            zoom = r.readShort()
            radiusX = r.readByte()
            radiusY = r.readByte()

            if (zoom.toInt() == -1) {
                reset = r.readBoolean()
                startDelay = r.readShort()
                duration = r.readShort()
                value = r.readShort()
                isSingleUse = r.readBoolean()
                valueIsAngle = r.readBoolean()
            }
            return this
        }
    }

    private fun readPrism(): Prism {
        with(Prism()) {
            position = readVector()
            energy = r.readByte()
            return this
        }
    }

    private fun readBlockEvent(): BlockEvent {
        with(BlockEvent()) {
            type = r.readByte()
            blockId = r.readShort()
            payload = r.readShort()
            return this
        }
    }

    private fun readButton(): Button {
        with(Button()) {
            visibility = r.readByte()
            disableCount = r.readByte()
            mode = r.readByte()
            parentId = r.readShort()
            sequenceInOrder = r.readBoolean()
            siblingsCount = r.readByte()
            isMoving = r.readBoolean()

            if (isMoving) {
                movingPlatformId = r.readShort()
            } else {
                position = readVector()
            }

            events = readStructure { r.readShort() }

            return this
        }
    }

    private fun readOtherCube(): OtherCube {
        fun readKeyEvent(): OtherCube.KeyEvent {
            with(OtherCube.KeyEvent()) {
                timeOffset = r.readShort()
                direction = r.readByte()
                eventType = r.readByte()
                return this
            }
        }

        with(OtherCube()) {
            positionTrigger = readVector()
            movingBlockSync = r.readShort()

            if (movingBlockSync.toInt() == -2) {
                darkcubeRadiusX = r.readByte()
                darkCubeRadiusY = r.readByte()
                darkcubeMovingBlockSync = r.readShort()
            }

            val keyEventCount = r.readShort()
            positionCube = readVector()
            keyEvents = mutableList(keyEventCount.toInt()) { readKeyEvent() }
            return this
        }
    }

    private fun readResizer(): Resizer {
        with(Resizer()) {
            position = readVector()
            visible = r.readBoolean()
            direction = r.readByte()
            return this
        }
    }

    private fun legacyMinimapLength(unknownShort3: Short, unknownShort4: Short) = ((unknownShort3 * unknownShort4) + 7) / 8

    private fun collisionMapLength(x: Short, y: Short, z: Byte) = z * (((x * y) + 7) / 8)

    private inline fun <reified T> mutableList(length: Int, noinline init: (Int) -> T) = Array(length, init).toMutableList()

    /**
     * 毛用没有。不标准的代码。只是为了防止未使用的警告。都怪KOTLIN这里要求太严格了。
     */
    private inline fun supressUnusedWarnings(vararg any: Any) = any
}