package studio.papercube.blockbuild.view3d

import javafx.scene.Scene
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCode.*
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import studio.papercube.blockbuild.view3d.BlockGroup.*
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.function.Consumer
import java.util.function.Predicate


class InteractiveInputController(internal val scene: Scene, private val levelView: LevelView) {
    internal var mousePosX: Double = 0.toDouble()


    internal var mousePosY: Double = 0.toDouble()
    internal var mouseOldX: Double = 0.toDouble()
    internal var mouseOldY: Double = 0.toDouble()
    internal var mouseDeltaX: Double = 0.toDouble()
    internal var mouseDeltaY: Double = 0.toDouble()

    val eventsOnMousePressed = EventList<Consumer<MouseEvent>>()
    val eventsOnMouseDragged = EventList<Consumer<MouseEvent>>()
    val eventsOnKeyPressed = EventList<Consumer<KeyEvent>>()

    enum class ModifiersForKeyEvents(val predicate: (KeyEvent) -> Boolean) {
        ALT(KeyEvent::isAltDown),
        CTRL(KeyEvent::isControlDown),
        SHIFT(KeyEvent::isShiftDown);

        fun real(keyEvent: KeyEvent) = predicate.invoke(keyEvent)
    }

    //registers
    fun registerKeyPressEvent(keyCode: KeyCode, vararg modifiers: ModifiersForKeyEvents, action: () -> Unit): Consumer<KeyEvent> {
        val eventConsumer =  Consumer { keyEvent: KeyEvent -> if (keyEvent.code == keyCode && modifiers.all { it.real(keyEvent) }) action() }
        eventsOnKeyPressed.add(eventConsumer)
        return eventConsumer
    }

    //handlers
    fun handleMouse() {
        scene.setOnMousePressed { me ->
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseOldX = me.sceneX
            mouseOldY = me.sceneY
            eventsOnMousePressed.forEach { action -> action.accept(me) }
        }

        scene.setOnMouseDragged { me ->
            mouseOldX = mousePosX
            mouseOldY = mousePosY
            mousePosX = me.sceneX
            mousePosY = me.sceneY
            mouseDeltaX = mousePosX - mouseOldX
            mouseDeltaY = mousePosY - mouseOldY

            var modifier = 1.0

            if (me.isControlDown) {
                modifier = CONTROL_MULTIPLIER
            }
            if (me.isShiftDown) {
                modifier = SHIFT_MULTIPLIER
            }
            if (me.isPrimaryButtonDown) {
                //改变视角
                levelView.angleRotateY = levelView.angleRotateY + mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED
                levelView.angleRotateX = levelView.angleRotateX - mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED
            } else if (me.isSecondaryButtonDown) {
                //改变视野
                val z = levelView.getCamera().translateZ
                val newZ = z + mouseDeltaX * MOUSE_SPEED * modifier
                levelView.zoom = newZ
            }
            //            else if (me.isMiddleButtonDown()) {
            //                //改变相机位置
            //                levelView.setCameraTranslateX(levelView.getCameraTranslateX() - mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
            //                levelView.setCameraTranslateZ(levelView.getCameraTranslateZ() - mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
            //            }

            eventsOnMouseDragged.forEach { action -> action.accept(me) }

        }
    }

    fun handleKeyboard() {
        scene.setOnKeyPressed { event ->
            when (event.code) {
                Z -> {
                    if (event.isControlDown) {
                        levelView.resetFocus()
                    }
                    levelView.resetView()
                }
                X -> levelView.getAxisGroup().isVisible = !levelView.getAxisGroup().isVisible
                V -> levelView.getObjectGroup().isVisible = !levelView.getObjectGroup().isVisible
                P -> {
                    val value = String.format("AngleRotateX=%s,AngleRotateY=%s,CameraTranslateZ=%s,%n" +
                            "CameraZoom=%s,PositionTranslateX=%s,PositionTranslateY=%s,%n" +
                            "PositionTranslateZ=%s",
                            levelView.angleRotateX, levelView.angleRotateY, levelView.camera.translateZ,
                            levelView.zoom, levelView.cameraTranslateX, levelView.cameraTranslateY,
                            levelView.cameraTranslateZ)
                    println(value)
                }
                PAGE_UP -> levelView.focusPositionZ = levelView.focusPositionZ + (if (event.isShiftDown) 10 else 1)
                PAGE_DOWN -> levelView.focusPositionZ = levelView.focusPositionZ - (if (event.isShiftDown) 10 else 1)
                RIGHT -> levelView.focusPositionX = levelView.focusPositionX + (if (event.isShiftDown) 10 else 1)
                LEFT -> levelView.focusPositionX = levelView.focusPositionX - (if (event.isShiftDown) 10 else 1)
                UP -> levelView.focusPositionY = levelView.focusPositionY - (if (event.isShiftDown) 10 else 1)
                DOWN -> levelView.focusPositionY = levelView.focusPositionY + (if (event.isShiftDown) 10 else 1)
                else->{}
            }
            eventsOnKeyPressed.forEach { action -> action.accept(event) }
        }

    }

    fun handleAll() {
        handleMouse()
        handleKeyboard()
    }

    open class EventList<E> internal constructor() : Iterable<E> {
        private val list = CopyOnWriteArrayList<E>()

        fun add(e: E): Boolean {
            return list.add(e)
        }

        fun add(index: Int, element: E) {
            list.add(index, element)
        }

        fun remove(index: Int): E {
            return list.removeAt(index)
        }

        fun remove(o: E): Boolean {
            return list.remove(o)
        }

        fun removeAll(c: Collection<*>): Boolean {
            return list.removeAll(c)
        }

        fun addAllAbsent(c: Collection<E>): Int {
            return list.addAllAbsent(c)
        }

        fun addAll(c: Collection<E>): Boolean {
            return list.addAll(c)
        }

        fun addAll(index: Int, c: Collection<E>): Boolean {
            return list.addAll(index, c)
        }

        fun removeIf(filter: Predicate<in E>): Boolean {
            return list.removeIf(filter)
        }

        fun clear() {
            list.clear()
        }

        override fun iterator(): Iterator<E> {
            return list.iterator()
        }

        fun forEach(action: Consumer<in E>) {
            list.forEach(action)
        }

        fun duplicate(): List<E> {
            val list = ArrayList<E>()
            list.addAll(this.list)
            return list
        }
    }

    companion object {

        private val CONTROL_MULTIPLIER = 0.1
        private val SHIFT_MULTIPLIER = 10.0
        private val MOUSE_SPEED = 0.1
        private val ROTATION_SPEED = 2.0
        private val TRACK_SPEED = 0.3
    }
}


