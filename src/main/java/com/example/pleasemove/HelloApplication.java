package com.example.pleasemove;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {


    double width = 800;
    double height = 600;
    static Pane root = new Pane();
    HelloController hl = new HelloController();



    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        root.setPrefSize(width,height);
        root.getChildren().addAll(hl.player, hl.food);
        Scene scene = new Scene(root);
        hl.initMethod();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()) {
                    case W : hl.playerUp();
                    hl.eatKiwiFood();
                    break;
                    case S : hl.playerDown();
                    hl.eatKiwiFood();
                    break;
                    case A: hl.playerLeft();
                    hl.eatKiwiFood();
                    break;
                    case D: hl.playerRight();
                    hl.eatKiwiFood();
                    break;
                }
            }
        });

        stage.setTitle("Eat, Kiwi! Eat!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}