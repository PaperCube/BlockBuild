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

package studio.papercube.blockbuild.view3d

import javafx.application.Application
import javafx.scene.Scene
import javafx.scene.SceneAntialiasing
import javafx.scene.input.KeyCode
import javafx.scene.paint.Color
import javafx.stage.FileChooser
import javafx.stage.Stage
import studio.papercube.blockbuild.edgesupport.binlevel.Level
import studio.papercube.blockbuild.edgesupport.binlevel.LevelReader
import studio.papercube.blockbuild.view3d.InteractiveInputController.ModifiersForKeyEvents.CTRL
import java.io.File

/**
 * @author cmcastil, PaperCube
 */
class LevelViewer : Application() {

    private val levelView = LevelEditingView()
    private lateinit var inputHandler: InteractiveInputController

    override fun start(primaryStage: Stage) {

        // setUserAgentStylesheet(STYLESHEET_MODENA);
        println("start()")

        //        levelView.getChildren().add(world);
        levelView.getAxisGroup().isVisible = true
        //        levelView.setZoom(-20);

        val scene = Scene(levelView, 1366.0, 768.0, true, SceneAntialiasing.BALANCED)
        scene.fill = Color(69 / 255.0, 90 / 255.0, 100 / 255.0, 1.0)

        inputHandler = InteractiveInputController(scene, levelView)
        inputHandler.handleKeyboard()
        inputHandler.handleMouse()


        primaryStage.title = "Edge Model Sample Application"
        primaryStage.scene = scene
        primaryStage.show()

        scene.camera = levelView.getCamera()
        inputHandler.run {
            registerKeyPressEvent(KeyCode.O, CTRL) { loadLevelMap() }
            registerKeyPressEvent(KeyCode.S) { levelView.addStaticBlock() }
            registerKeyPressEvent(KeyCode.DELETE) { levelView.deleteCurrentStaticBlock() }
        }
        loadLevelMap()

    }

    private fun loadLevelMap() {
        try {
            //"E:\\Applications\\Entertainment\\EdgeTest\\levels\\level309_winter.bin"
            val path = if (parameters.raw.size > 0) parameters.raw[0] else FileChooser().showOpenDialog(null)?.absolutePath ?: return
            val level = LevelReader(File(path)).read()
            load(level)
        } catch (throwable: Throwable) {
            throwable.printStackTrace()
        }

    }

    private fun load(level: Level) {
        println(level.header.title)
        levelView.level = level
    }

}

fun main(args: Array<String>) = Application.launch(LevelViewer::class.java, *args)
