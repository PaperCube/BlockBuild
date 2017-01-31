package studio.papercube.blockbuild.view3d;

import javafx.scene.Scene;

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
                blockGroup.setAngleRotateY(blockGroup.getAngleRotateY() + mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
                blockGroup.setAngleRotateX(blockGroup.getAngleRotateX() - mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isMiddleButtonDown()) {
                double z = blockGroup.getCamera().getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                blockGroup.setZoom(newZ);
            } else if (me.isSecondaryButtonDown()) {
                blockGroup.setCameraTranslateX(blockGroup.getCameraTranslateX() - mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
                blockGroup.setCameraTranslateZ(blockGroup.getCameraTranslateZ() - mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
            }
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
        });
    }

    public void handleAll() {
        handleMouse();
        handleKeyboard();
    }
}
