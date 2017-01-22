package api;

import javafx.collections.ObservableList;
import javafx.scene.DepthTest;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;

/**
 * Created by imzhy on 2017/1/22.
 */
public class BlockGroup extends Group {
    final Xform axisGroup = new Xform();
    final Xform objectGroup = new Xform();
    final Xform world = new Xform();
    final PerspectiveCamera camera = new PerspectiveCamera(true);
    final Xform cameraXform = new Xform();
    final Xform cameraXform2 = new Xform();
    final Xform cameraXform3 = new Xform();


    private static final double HYDROGEN_ANGLE = 104.5;
    public static final double CAMERA_INITIAL_X_ANGLE = 70.0;
    public static final double CAMERA_INITIAL_Y_ANGLE = 320.0;
    public static final double CAMERA_NEAR_CLIP = 0.1;
    public static final double CAMERA_FAR_CLIP = 10000.0;
    public static final double AXIS_LENGTH = 250.0;
    public static final double CAMERA_INITIAL_DISTANCE = -450;


    public BlockGroup() {
        buildCamera();
        buildAxes();
//        buildMolecule();
        getChildren().add(world);
        setDepthTest(DepthTest.ENABLE);
    }

    //   void buildScene() {
    //       getChildren().add(world);
    //   }
    void buildCamera() {
        System.out.println("buildCamera()");
        getChildren().add(cameraXform);
        cameraXform.getChildren().add(cameraXform2);
        cameraXform2.getChildren().add(cameraXform3);
        cameraXform3.getChildren().add(camera);
        cameraXform3.setRotateZ(180.0);

        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);
        cameraXform.ry.setAngle(CAMERA_INITIAL_Y_ANGLE);
        cameraXform.rx.setAngle(CAMERA_INITIAL_X_ANGLE);
    }

    void buildAxes() {
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


    void buildMolecule() {
        //======================================================================
        // THIS IS THE IMPORTANT MATERIAL FOR THE TUTORIAL
        //======================================================================

        final PhongMaterial redMaterial = new PhongMaterial();
        redMaterial.setDiffuseColor(Color.DARKRED);
        redMaterial.setSpecularColor(Color.RED);

        final PhongMaterial whiteMaterial = new PhongMaterial();
        whiteMaterial.setDiffuseColor(Color.WHITE);
        whiteMaterial.setSpecularColor(Color.LIGHTBLUE);

        final PhongMaterial greyMaterial = new PhongMaterial();
        greyMaterial.setDiffuseColor(Color.DARKGREY);
        greyMaterial.setSpecularColor(Color.GREY);

        // Molecule Hierarchy
        // [*] moleculeXform
        //     [*] oxygenXform
        //         [*] oxygenSphere
        //     [*] hydrogen1SideXform
        //         [*] hydrogen1Xform
        //             [*] hydrogen1Sphere
        //         [*] bond1Cylinder
        //     [*] hydrogen2SideXform
        //         [*] hydrogen2Xform
        //             [*] hydrogen2Sphere
        //         [*] bond2Cylinder
        Xform moleculeXform = new Xform();
        Xform oxygenXform = new Xform();
        Xform hydrogen1SideXform = new Xform();
        Xform hydrogen1Xform = new Xform();
        Xform hydrogen2SideXform = new Xform();
        Xform hydrogen2Xform = new Xform();

        Sphere oxygenSphere = new Sphere(40.0);
        oxygenSphere.setMaterial(redMaterial);

        Sphere hydrogen1Sphere = new Sphere(30.0);
        hydrogen1Sphere.setMaterial(whiteMaterial);
        hydrogen1Sphere.setTranslateX(0.0);

        Sphere hydrogen2Sphere = new Sphere(30.0);
        hydrogen2Sphere.setMaterial(whiteMaterial);
        hydrogen2Sphere.setTranslateZ(0.0);

        Cylinder bond1Cylinder = new Cylinder(5, 100);
        bond1Cylinder.setMaterial(greyMaterial);
        bond1Cylinder.setTranslateX(50.0);
        bond1Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond1Cylinder.setRotate(90.0);

        Cylinder bond2Cylinder = new Cylinder(5, 100);
        bond2Cylinder.setMaterial(greyMaterial);
        bond2Cylinder.setTranslateX(50.0);
        bond2Cylinder.setRotationAxis(Rotate.Z_AXIS);
        bond2Cylinder.setRotate(90.0);

        moleculeXform.getChildren().add(oxygenXform);
        moleculeXform.getChildren().add(hydrogen1SideXform);
        moleculeXform.getChildren().add(hydrogen2SideXform);
        oxygenXform.getChildren().add(oxygenSphere);
        hydrogen1SideXform.getChildren().add(hydrogen1Xform);
        hydrogen2SideXform.getChildren().add(hydrogen2Xform);
        hydrogen1Xform.getChildren().add(hydrogen1Sphere);
        hydrogen2Xform.getChildren().add(hydrogen2Sphere);
        hydrogen1SideXform.getChildren().add(bond1Cylinder);
        hydrogen2SideXform.getChildren().add(bond2Cylinder);

        hydrogen1Xform.setTx(100.0);
        hydrogen2Xform.setTx(100.0);
        hydrogen2SideXform.setRotateY(HYDROGEN_ANGLE);

        objectGroup.getChildren().add(moleculeXform);

        world.getChildren().addAll(objectGroup);
    }


    public PerspectiveCamera getCamera() {
        return camera;
    }

    public Xform getCameraXform() {
        return cameraXform;
    }

    public Xform getCameraXform2() {
        return cameraXform2;
    }

    public Xform getCameraXform3() {
        return cameraXform3;
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

    public void setPositionTranslateX(double value) {
        cameraXform2.t.setX(value);
    }

    public double getPositionTranslateX() {
        return cameraXform2.t.getX();
    }

    public void setPositionTranslateY(double value) {
        cameraXform2.t.setY(value);
    }

    public double getPositionTranslateY() {
        return cameraXform2.t.getY();
    }

    public void setPositionTranslzteZ(double value) {
        cameraXform2.t.setZ(value);
    }

    public double getPositionTranslzteZ() {
        return cameraXform2.t.getZ();
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
