package studio.papercube.blockbuild.view3d;

import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static studio.papercube.blockbuild.view3d.BlockGroup.*;

/**
 * Created by imzhy on 2017/1/22.
 */
public class InteractiveInputController {
    final Scene scene;
    final BlockGroup blockGroup;

    private static final double CONTROL_MULTIPLIER = 0.1;
    private static final double SHIFT_MULTIPLIER = 10.0;
    private static final double MOUSE_SPEED = 0.1;
    private static final double ROTATION_SPEED = 2.0;
    private static final double TRACK_SPEED = 0.3;
    double mousePosX;


    double mousePosY;
    double mouseOldX;
    double mouseOldY;
    double mouseDeltaX;
    double mouseDeltaY;

    public final EventList<Consumer<MouseEvent>> eventsOnMousePressed = new EventList<>();
    public final EventList<Consumer<MouseEvent>> eventsOnMouseDragged = new EventList<>();
    public final EventList<Consumer<KeyEvent>> eventsOnKeyPressed = new EventList<>();


    public InteractiveInputController(Scene scene, BlockGroup blockGroup) {
        this.scene = scene;
        this.blockGroup = blockGroup;
    }

    public void handleMouse() {
        scene.setOnMousePressed(me -> {
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseOldX = me.getSceneX();
            mouseOldY = me.getSceneY();
            eventsOnMousePressed.forEach(action -> action.accept(me));
        });

        scene.setOnMouseDragged(me -> {
            mouseOldX = mousePosX;
            mouseOldY = mousePosY;
            mousePosX = me.getSceneX();
            mousePosY = me.getSceneY();
            mouseDeltaX = (mousePosX - mouseOldX);
            mouseDeltaY = (mousePosY - mouseOldY);

            double modifier = 1.0;

            if (me.isControlDown()) {
                modifier = CONTROL_MULTIPLIER;
            }
            if (me.isShiftDown()) {
                modifier = SHIFT_MULTIPLIER;
            }
            if (me.isPrimaryButtonDown()) {
                //改变视角
                blockGroup.setAngleRotateY(blockGroup.getAngleRotateY() + mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
                blockGroup.setAngleRotateX(blockGroup.getAngleRotateX() - mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isSecondaryButtonDown()) {
                //改变视野
                double z = blockGroup.getCamera().getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                blockGroup.setZoom(newZ);
            }
//            else if (me.isMiddleButtonDown()) {
//                //改变相机位置
//                blockGroup.setCameraTranslateX(blockGroup.getCameraTranslateX() - mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
//                blockGroup.setCameraTranslateZ(blockGroup.getCameraTranslateZ() - mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
//            }

            eventsOnMouseDragged.forEach(action -> action.accept(me));

        });
    }

    public void handleKeyboard() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z:
                    if (event.isControlDown()) {
                        blockGroup.setCameraTranslateX(0.0);
                        blockGroup.setCameraTranslateY(0.0);
                        blockGroup.setCameraTranslateZ(0.0);
                    }
                    blockGroup.setZoom(CAMERA_INITIAL_ZOOM);
                    blockGroup.setAngleRotateY(CAMERA_INITIAL_Y_ANGLE);
                    blockGroup.setAngleRotateX(CAMERA_INITIAL_X_ANGLE);
                    break;
                case X:
                    blockGroup.getAxisGroup().setVisible(!blockGroup.getAxisGroup().isVisible());
                    break;
                case V:
                    blockGroup.getObjectGroup().setVisible(!blockGroup.getObjectGroup().isVisible());
                    break;
                case P:
                    String value = String.format("AngleRotateX=%s,AngleRotateY=%s,CameraTranslateZ=%s,%n" +
                                    "CameraZoom=%s,PositionTranslateX=%s,PositionTranslateY=%s,%n" +
                                    "PositionTranslateZ=%s",
                            blockGroup.getAngleRotateX(), blockGroup.getAngleRotateY(), blockGroup.camera.getTranslateZ(),
                            blockGroup.getZoom(), blockGroup.getCameraTranslateX(), blockGroup.getCameraTranslateY(),
                            blockGroup.getCameraTranslateZ());
                    System.out.println(value);
                    break;
                case PAGE_UP:
                    blockGroup.setCameraTranslateY(blockGroup.getCameraTranslateY() + (event.isShiftDown() ? 10 : 1));
                    break;
                case PAGE_DOWN:
                    blockGroup.setCameraTranslateY(blockGroup.getCameraTranslateY() - (event.isShiftDown() ? 10 : 1));
                    break;
                case RIGHT:
                    blockGroup.setCameraTranslateX(blockGroup.getCameraTranslateX() + (event.isShiftDown() ? 10 : 1));
                    break;
                case LEFT:
                    blockGroup.setCameraTranslateX(blockGroup.getCameraTranslateX() - (event.isShiftDown() ? 10 : 1));
                    break;
                case UP:
                    blockGroup.setCameraTranslateZ(blockGroup.getCameraTranslateZ() - (event.isShiftDown() ? 10 : 1));
                    break;
                case DOWN:
                    blockGroup.setCameraTranslateZ(blockGroup.getCameraTranslateZ() + (event.isShiftDown() ? 10 : 1));
                    break;
            }

            eventsOnKeyPressed.forEach(action->action.accept(event));
        });
    }

    public void handleAll() {
        handleMouse();
        handleKeyboard();
    }

    public static class EventList<E> implements Iterable<E> {
        CopyOnWriteArrayList<E> list = new CopyOnWriteArrayList<>();

        private EventList(){

        }

        public boolean add(E e) {
            return list.add(e);
        }

        public void add(int index, E element) {
            list.add(index, element);
        }

        public E remove(int index) {
            return list.remove(index);
        }

        public boolean remove(Object o) {
            return list.remove(o);
        }

        public boolean removeAll(Collection<?> c) {
            return list.removeAll(c);
        }

        public int addAllAbsent(Collection<? extends E> c) {
            return list.addAllAbsent(c);
        }

        public boolean addAll(Collection<? extends E> c) {
            return list.addAll(c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            return list.addAll(index, c);
        }

        public boolean removeIf(Predicate<? super E> filter) {
            return list.removeIf(filter);
        }

        public void clear() {
            list.clear();
        }

        @Override
        public Iterator<E> iterator() {
            return list.iterator();
        }

        public void forEach(Consumer<? super E> action) {
            list.forEach(action);
        }

        public Stream<E> stream() {
            return list.stream();
        }

        public List<E> duplicate() {
            List<E> list = new ArrayList<E>();
            list.addAll(this.list);
            return list;
        }
    }
}
