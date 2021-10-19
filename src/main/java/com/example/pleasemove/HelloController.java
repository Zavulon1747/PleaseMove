package com.example.pleasemove;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Clip;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HelloController {


    private boolean isGameStarted = false;
    Random random = new Random();
    int countScore = 0;
    Map<String, File> sounds = new HashMap<>();

    AudioInputStream ais;
    Clip clip;

    @FXML
    Label gameOver = new Label("");
    Image playerImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/kiwiPlayer.png");
    Image foodImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/foodKiwi.png");
    Image butcherImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/chefCook.png");
    @FXML
    ImageView playerView = new ImageView(playerImage);
    @FXML
    ImageView foodView = new ImageView(foodImage);
    @FXML
    ImageView butcherView = new ImageView(butcherImage);
    Player player = new Player(playerView);
    Food food = new Food(foodView);


    @FXML
    public void playerUp() {
        playerView.setY(playerView.getY() - 25);
    }

    public void playerDown() {
        playerView.setY(playerView.getY() + 25);
    }

    public void playerLeft() {
        playerView.setX(playerView.getX() - 25);
    }

    public void playerRight() {
        playerView.setX(playerView.getX() + 25);
    }

    public void outOfBorder() {                         //Dodelat'
        if ((playerView.getX() + 50) > 800) {
            playerView.setX(750);
            System.out.println("X");                    //Test, print X if it work
        }
    }

    public void initMethod() {              //Set XY of gaming objects
        isGameStarted = true;
        foodView.setX(random.nextInt(700) + 50);
        foodView.setY(random.nextInt(500) + 50);
        butcherView.setX(750);
        butcherView.setY(550);
        sounds.put("Start", new File("D:\\Work\\PleaseMove\\src\\Sounds\\wouldYouLike.mp3"));
        sounds.put("FoodWasEaten", new File("D:\\Work\\PleaseMove\\src\\Sounds\\nyam.mp3"));


    }

    public void eatKiwiFood() {
        if (playerView.getBoundsInParent().intersects(foodView.getBoundsInParent())) {
            countScore++;
            System.out.println(countScore);
            update();
        }
    }

    public void update() {
        while (playerView.getBoundsInParent().intersects(foodView.getBoundsInParent())) {
            foodView.setX(random.nextInt(750));
            foodView.setY(random.nextInt(550));
        }

    }

    public void enemyStep() {
        if (Math.abs(playerView.getX() - butcherView.getX()) >= Math.abs(playerView.getY() - butcherView.getY())) {
            if (playerView.getX() - butcherView.getX() < 0) {
                butcherView.setX(butcherView.getX() - 1);
            } else {
                butcherView.setX(butcherView.getX() + 1);
            }
        } else {
            if (playerView.getY() - butcherView.getY() < 0) {
                butcherView.setY(butcherView.getY() - 1);
            } else {
                butcherView.setY(butcherView.getY() + 1);
            }
        }
    }

    public void setGameOver() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameOver.setText("GAME OVER");
            }
        }); //For avoid Exception
        gameOver.setPrefWidth(380);
        gameOver.setPrefHeight(50);
        gameOver.setLayoutX(112);
        gameOver.setLayoutY(150);
        gameOver.setFont(new Font(68));
    }

    public Boolean isCaught() {
        return playerView.getBoundsInParent().intersects(butcherView.getBoundsInParent());
    }

    public boolean getGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }
}