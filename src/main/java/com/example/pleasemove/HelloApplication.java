package com.example.pleasemove;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.io.InputStream;

public class HelloApplication extends Application {


    double width = 800;
    double height = 600;
    static Pane root = new Pane();
    HelloController hl = new HelloController();


    @Override
    public void start(Stage stage)  {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        root.setPrefSize(width, height);
        root.getChildren().addAll(hl.backgroundView, hl.player, hl.food, hl.butcherView, hl.gameOver, hl.scorePoint);
        Scene scene = new Scene(root);
        hl.initMethod();

        //Butcher is moving to Player
        Thread butcherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (hl.getGameStarted()) {
                    hl.enemyStep();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (hl.isCaught()) {
                        hl.setGameStarted(false);
                        hl.setGameOver();
                    }
                }
            }
        });
        butcherThread.start();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case W:
                        hl.playerUp();
                        hl.eatKiwiFood();
                        hl.outOfBorder();
                        break;
                    case S:
                        hl.playerDown();
                        hl.eatKiwiFood();
                        hl.outOfBorder();
                        break;
                    case A:
                        hl.playerLeft();
                        hl.eatKiwiFood();
                        hl.outOfBorder();
                        break;
                    case D:
                        hl.playerRight();
                        hl.eatKiwiFood();
                        hl.outOfBorder();
                        break;
                }
            }
        });

        stage.setTitle("Eat, Kiwi! Eat!");
        stage.setScene(scene);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                hl.setGameStarted(false);
            }
        });
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}