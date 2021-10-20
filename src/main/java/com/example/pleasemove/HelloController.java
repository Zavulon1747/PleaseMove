package com.example.pleasemove;

import javafx.application.Platform;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
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
    @FXML
    Label scorePoint = new Label("Score: ");
    Image playerImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/kiwiPlayer.png");
    Image foodImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/foodKiwi.png");
    Image butcherImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/chefCook.png");
    Image backgroundImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/Background1.png");
    @FXML
    ImageView playerView = new ImageView(playerImage);
    @FXML
    ImageView foodView = new ImageView(foodImage);
    @FXML
    ImageView butcherView = new ImageView(butcherImage);
    @FXML
    ImageView backgroundView = new ImageView(backgroundImage);
    Player player = new Player(playerView);
    Food food = new Food(foodView);


    @FXML
    public void playerUp() {
        if (isGameStarted) playerView.setY(playerView.getY() - 25);
    }

    public void playerDown() {
        if (isGameStarted) playerView.setY(playerView.getY() + 25);
    }

    public void playerLeft() {
        if (isGameStarted) playerView.setX(playerView.getX() - 25);
    }

    public void playerRight() {
        if (isGameStarted) playerView.setX(playerView.getX() + 25);
    }

    // Prevents the player's picture from going beyond the established frames
    public void outOfBorder() {
        if ((playerView.getX() + 50) > 800) {
            playerView.setX(750);
        } else if (playerView.getX()<0) {
            playerView.setX((0));
        } else if ((playerView.getY()+50) > 600) {
            playerView.setY(550);
        } else if (playerView.getY()<0) {
            playerView.setY(0);
        }
    }

    //Just init Method
    public void initMethod() {
        isGameStarted = true;
        setScorePoint();
        backgroundView.setX(0);
        backgroundView.setX(0);
        foodView.setX(random.nextInt(700) + 50);
        foodView.setY(random.nextInt(500) + 50);
        butcherView.setX(750);
        butcherView.setY(550);
        playStartSound();
    }

    //Method checks if food has been eaten
    public void eatKiwiFood() {
        if (playerView.getBoundsInParent().intersects(foodView.getBoundsInParent())) {
            countScore++;
            System.out.println(countScore);
            update();
        }
    }

    //New random position of food
    public void update() {
        while (playerView.getBoundsInParent().intersects(foodView.getBoundsInParent())) {
            foodView.setX(random.nextInt(750));
            foodView.setY(random.nextInt(550));
        }
        setScorePoint();
        playNyam();
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
        gameOver.setTextFill(Color.RED);
        playGameOverMusic();
    }

    public void setScorePoint() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                scorePoint.setText("Score: "+countScore);
            }
        });
        scorePoint.setPrefWidth(100);
        scorePoint.setPrefHeight(25);
        scorePoint.setLayoutX(700);
        scorePoint.setLayoutY(0);
        scorePoint.setFont(new Font(20));
        scorePoint.setTextFill(Color.YELLOWGREEN);
    }

    public void playNyam() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("D:\\Work\\PleaseMove\\src\\Sounds\\nyamWAV.wav"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playStartSound() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("D:\\Work\\PleaseMove\\src\\Sounds\\wouldYouLikeWAV.wav"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playGameOverMusic() {
        try {
            ais = AudioSystem.getAudioInputStream(new File("D:\\Work\\PleaseMove\\src\\Sounds\\GameOverWAV.wav"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
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