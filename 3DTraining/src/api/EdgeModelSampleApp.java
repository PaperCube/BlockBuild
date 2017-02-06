/*
 * Copyright (c) 2013, 2014 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package api;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.paint.Color;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import studio.papercube.blockbuild.edgesupport.binlevel.Level;
import studio.papercube.blockbuild.edgesupport.binlevel.LevelReader;
import studio.papercube.blockbuild.edgesupport.binlevel.Vector;

import java.io.File;

/**
 * @author cmcastil, PaperCube
 */
public class EdgeModelSampleApp extends Application {

    BlockGroup root = new BlockGroup();
    DraggableBlockGroupInputHandler inputHandler;

    @Override
    public void start(Stage primaryStage) {

        // setUserAgentStylesheet(STYLESHEET_MODENA);
        System.out.println("start()");

//        root.getChildren().add(world);
        root.getAxisGroup().setVisible(true);
//        root.setZoom(-20);

        Scene scene = new Scene(root, 1366, 768, true, SceneAntialiasing.BALANCED);
        scene.setFill(new Color(69 / 255.0, 90 / 255.0, 100 / 255.0, 1));

        inputHandler = new DraggableBlockGroupInputHandler(scene, root);
        inputHandler.handleKeyboard();
        inputHandler.handleMouse();

        test();

        primaryStage.setTitle("Edge Model Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setCamera(root.getCamera());
    }

    public void test() {
        try {
            //"E:\\Applications\\Entertainment\\EdgeTest\\levels\\level309_winter.bin"
            String path = getParameters().getRaw().size() > 0 ? getParameters().getRaw().get(0) : new FileChooser().showOpenDialog(null).getAbsolutePath();
            Level level = new LevelReader(new File(path)).read();
            System.out.println(level.getHeader().getTitle());
            ObservableList<Node> children = root.getWorldChildren();

            PhongMaterial red = singleColorMaterial(Color.RED);
            PhongMaterial blue = singleColorMaterial(Color.LIMEGREEN);
            PhongMaterial lightBlack = singleColorMaterial(Color.rgb(30,30,30));


            level.getCollisionMap().duplicateVectors().forEach(vector -> children.add(vectorToBox(vector)));
            level.getMovingPlatforms().forEach(movingPlatform -> children.add(vectorToBox(movingPlatform.getWaypoints().get(0).getPosition(), lightBlack)));

            Box spawn = vectorToBox(level.getSpawnPoint());
            spawn.setMaterial(red);

            Box end = vectorToBox(level.getExitPoint());
            end.setMaterial(blue);


            children.addAll(spawn, end);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

    private Box vectorToBox(Vector vector) {
        Box box = new Box(1, 1, 1);
        box.setTranslateX(vector.getX());
        box.setTranslateZ(vector.getY());
        box.setTranslateY(vector.getZ());
//        box.setEffect(new Lighting(new Light.Distant(0,0,Color.WHITE)));

        return box;
    }

    private Box vectorToBox(Vector vector, Material material) {
        Box box = vectorToBox(vector);
        box.setMaterial(material);
        return box;

    }

    public PhongMaterial singleColorMaterial(Color color) {
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        material.setSpecularColor(color);
        return material;
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
