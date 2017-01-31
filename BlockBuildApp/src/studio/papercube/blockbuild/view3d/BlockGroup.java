package studio.papercube.blockbuild.view3d;

import javafx.collections.ObservableList;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

/**
 * Created by imzhy on 2017/1/22.
 */
public class BlockGroup extends Group {
    final Xform axisGroup = new Xform();
    final Xform objectGroup = new Xform();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();


    public static final double CAMERA_INITIAL_X_ANGLE = -22.0;
    public static final double CAMERA_INITIAL_Y_ANGLE = -196.0;
    public static final double CAMERA_INITIAL_ZOOM = -54;
    public static final double CAMERA_NEAR_CLIP = 0.1;
    public static final double CAMERA_FAR_CLIP = 10000.0;
    public static final double AXIS_LENGTH = 250.0;
    public static final double DEFAULT_FIELD_OF_VIEW = 42.0;


    public BlockGroup() {
        buildCamera();
        buildAxes();
//        buildMolecule();
        getChildren().add(world);
        setDepthTest(DepthTest.ENABLE);

        resetView();

    }

    //   void buildScene() {
    //       getChildren().add(world);
    //   }
    private void buildCamera() {
        System.out.println("buildCamera()");
        getChildren().add(cameraXform);
        cameraXform.getChildren().add(camera);
        cameraXform.setRotateZ(180);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_ZOOM);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    private void buildAxes() {
        System.out.println("buildAxes()");
        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial greenMaterial = new PhongMaterial();
        greenMaterial.setDiffuseColor(Color.DARKGREEN);
        greenMaterial.setSpecularColor(Color.GREEN);

        final PhongMaterial blueMaterial = new PhongMaterial();
        blueMaterial.setDiffuseColor(Color.DARKBLUE);
        blueMaterial.setSpecularColor(Color.BLUE);

        final Box xAxis = new Box(AXIS_LENGTH, 1, 1);
        final Box yAxis = new Box(1, AXIS_LENGTH, 1);
        final Box zAxis = new Box(1, 1, AXIS_LENGTH);

        xAxis.setMaterial(redMaterial);
        yAxis.setMaterial(greenMaterial);
        zAxis.setMaterial(blueMaterial);

        axisGroup.getChildren().addAll(xAxis, yAxis, zAxis);
        axisGroup.setVisible(false);
        world.getChildren().addAll(axisGroup);
    }

    public void resetView() {
        camera.setFieldOfView(DEFAULT_FIELD_OF_VIEW);
        setAngleRotateX(CAMERA_INITIAL_X_ANGLE);
        setAngleRotateY(CAMERA_INITIAL_Y_ANGLE);
        setZoom(CAMERA_INITIAL_ZOOM);
    }


    public PerspectiveCamera getCamera() {
        return camera;
    }

    public Xform getAxisGroup() {
        return axisGroup;
    }

    public Xform getObjectGroup() {
        return objectGroup;
    }

    public void setAngleRotateX(double value) {
        cameraXform.rx.setAngle(value);
    }

    public double getAngleRotateX() {
        return cameraXform.rx.getAngle();
    }

    public void setAngleRotateY(double value) {
        cameraXform.ry.setAngle(value);
    }

    public double getAngleRotateY() {
        return cameraXform.ry.getAngle();
    }

    public void setCameraTranslateX(double value) {
        cameraXform.t.setX(value);
    }

    public double getCameraTranslateX() {
        return cameraXform.t.getX();
    }

    public void setCameraTranslateY(double value) {
        cameraXform.t.setY(value);
    }

    public double getCameraTranslateY() {
        return cameraXform.t.getY();
    }

    public void setCameraTranslateZ(double value) {
        cameraXform.t.setZ(value);
    }

    public double getCameraTranslateZ() {
        return cameraXform.t.getZ();
    }

    public void setZoom(double value) {
        camera.setTranslateZ(value);
    }

    public double getZoom() {
        return camera.getTranslateZ();
    }


    public ObservableList<Node> getWorldChildren() {
        return world.getChildren();
    }


}
