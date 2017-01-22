package api;

import javafx.scene.Scene;

import static api.BlockGroup.*;

/**
 * Created by imzhy on 2017/1/22.
 */
public class DraggableBlockGroupInputHandler {
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

    public DraggableBlockGroupInputHandler(Scene scene, BlockGroup blockGroup) {
        this.scene = scene;
        this.blockGroup = blockGroup;
    }

    void handleMouse() {
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
                blockGroup.setAngleRotateY(blockGroup.getAngleRotateY() - mouseDeltaX * MOUSE_SPEED * modifier * ROTATION_SPEED);
                blockGroup.setAngleRotateX(blockGroup.getAngleRotateX() + mouseDeltaY * MOUSE_SPEED * modifier * ROTATION_SPEED);
            } else if (me.isSecondaryButtonDown()) {
                double z = blockGroup.getCamera().getTranslateZ();
                double newZ = z + mouseDeltaX * MOUSE_SPEED * modifier;
                blockGroup.setZoom(newZ);
            } else if (me.isMiddleButtonDown()) {
                blockGroup.setPositionTranslateX(blockGroup.getPositionTranslateX() + mouseDeltaX * MOUSE_SPEED * modifier * TRACK_SPEED);
                blockGroup.setPositionTranslateY(blockGroup.getPositionTranslateY() + mouseDeltaY * MOUSE_SPEED * modifier * TRACK_SPEED);
            }
        });
    }

    void handleKeyboard() {
        scene.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Z:
                    blockGroup.setPositionTranslateX(0.0);
                    blockGroup.setPositionTranslateY(0.0);
                    blockGroup.setZoom(CAMERA_INITIAL_DISTANCE);
                    blockGroup.setAngleRotateY(CAMERA_INITIAL_Y_ANGLE);
                    blockGroup.setAngleRotateX(CAMERA_INITIAL_X_ANGLE);
                    break;
                case X:
                    blockGroup.getAxisGroup().setVisible(!blockGroup.getAxisGroup().isVisible());
                    break;
                case V:
                    blockGroup.getObjectGroup().setVisible(!blockGroup.getObjectGroup().isVisible());
                    break;
            }
        });
    }
}
