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

    Thread butcherThread;
    Thread ratThread;

    @FXML
    Label gameOver = new Label("");
    @FXML
    Label scorePoint = new Label("Score: ");
    Image playerImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/kiwiPlayer.png");
    Image foodImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/foodKiwi.png");
    Image butcherImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/chefCook.png");
    Image ratImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/rat25.png");
    Image backgroundImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/Background1.png");
    @FXML
    ImageView playerView = new ImageView(playerImage);
    @FXML
    ImageView foodView = new ImageView(foodImage);
    @FXML
    ImageView butcherView = new ImageView(butcherImage);
    @FXML
    ImageView ratView = new ImageView(ratImage);
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
        setGameStarted(true);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameOver.setText("");
            }
        });
        createThreadButcher();
        countScore = 0;
        setScorePoint();
        backgroundView.setX(0);
        backgroundView.setX(0);
        playerView.setX(0);
        playerView.setY(0);
        foodView.setX(random.nextInt(700) + 50);
        foodView.setY(random.nextInt(500) + 50);
        butcherView.setX(750);
        butcherView.setY(550);
        sounds.put("start", new File("D:\\Work\\PleaseMove\\src\\Sounds\\wouldYouLikeWAV.wav"));
        sounds.put("nyam", new File("D:\\Work\\PleaseMove\\src\\Sounds\\nyamWAV.wav"));
        sounds.put("end", new File("D:\\Work\\PleaseMove\\src\\Sounds\\GameOverWAV.wav"));
        playStartSound();
    }

    //Method checks if food has been eaten
    public void eatKiwiFood() {
        if (playerView.getBoundsInParent().intersects(foodView.getBoundsInParent())) {
            countScore++;
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
        //if(countScore == 25) createThreadRat();
        playNyam();
    }

    //AI's butcher
    public void butcherStep() {
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

    //AI's rat
    public void ratStep() {

    }

    public void createThreadRat() {
        ratView.setX(800);
        ratView.setY(0);

        ratThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ratStep();
                try {
                    Thread.sleep(15);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (isCaught()) {
                    setGameStarted(false);
                    setGameOver();
                }
            }
        });
    }

    //Label with words "Game Over" was set on main Frame + gameOver sound is started
    public void setGameOver() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameOver.setText("GAME OVER!\nPress \"ENTER\"\n\tto restart");
            }
        }); //For avoid Exception
        gameOver.setPrefWidth(580);
        gameOver.setPrefHeight(300);
        gameOver.setLayoutX(90);
        gameOver.setLayoutY(100);
        gameOver.setFont(new Font(68));
        gameOver.setTextFill(Color.RED);
        playGameOverMusic();
    }

    //Thread was created, butcher is moving now
    public void createThreadButcher() {
        butcherThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (getGameStarted()) {
                    butcherStep();
                    try {
                        Thread.sleep(17);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (isCaught()) {
                        setGameStarted(false);
                        setGameOver();
                    }
                }
            }
        });
        butcherThread.start();
    }

    //Increase countScore
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

    // Sound of eating
    public void playNyam() {
        try {
            ais = AudioSystem.getAudioInputStream(sounds.get("nyam"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //Sound after initMethod
    public void playStartSound() {
        try {
            ais = AudioSystem.getAudioInputStream(sounds.get("start"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //Sound is beginning, if you were caught
    public void playGameOverMusic() {
        try {
            ais = AudioSystem.getAudioInputStream(sounds.get("end"));
            clip = AudioSystem.getClip();
            clip.open(ais);
            FloatControl gainControl =
                    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-12.0f);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    //If border's pictures is crossing
    public Boolean isCaught() {
        return playerView.getBoundsInParent().intersects(butcherView.getBoundsInParent()) ||
                playerView.getBoundsInParent().intersects(ratView.getBoundsInParent());
    }

    public boolean getGameStarted() {
        return isGameStarted;
    }

    public void setGameStarted(boolean gameStarted) {
        isGameStarted = gameStarted;
    }
}