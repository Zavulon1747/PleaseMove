package com.example.pleasemove;

import javafx.fxml.FXML;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class HelloController {
    Random random = new Random();
    int countScore = 0;
    Map<String, File> sounds = new HashMap<>();



    Image myImage = new Image("file:///D:/Work/PleaseMove/src/pixelArts/kiwiPlayer.png");
    Image foodKiwi = new Image("file:///D:/Work/PleaseMove/src/pixelArts/foodKiwi.png");
    @FXML
    ImageView imageView = new ImageView(myImage);
    @FXML
    ImageView imageViewKiwi = new ImageView(foodKiwi);
    Player player = new Player(imageView);
    Food food = new Food(imageViewKiwi);



    @FXML
    public void playerUp() {
        imageView.setY(imageView.getY() - 25);
    }

    public void playerDown() {
        imageView.setY(imageView.getY() + 25);
    }

    public void playerLeft() {
        imageView.setX(imageView.getX() - 25);
    }

    public void playerRight() {
        imageView.setX(imageView.getX() + 25);
    }

    public void outOfBorder() {                         //Dodelat'
        if ((imageView.getX() + 50) > 800) {
            imageView.setX(750);
            System.out.println("X");
        }
    }

    public void initMethod() {              //Set XY of gaming objects
        imageViewKiwi.setX(random.nextInt(700) + 50);
        imageViewKiwi.setY(random.nextInt(500) + 50);
        sounds.put("Start", new File("D:\\Work\\PleaseMove\\src\\Sounds\\wouldYouLike.wav"));
        sounds.put("FoodWasEaten", new File("D:\\Work\\PleaseMove\\src\\Sounds\\nyam.wav"));
        sounds.get("Start");
    }

    public void eatKiwiFood() {
        if (imageView.getBoundsInParent().intersects(imageViewKiwi.getBoundsInParent())) {
            countScore++;
            System.out.println(countScore);
            update();
        }
    }

    public void update() {
        while (imageView.getBoundsInParent().intersects(imageViewKiwi.getBoundsInParent())) {

            imageViewKiwi.setX(random.nextInt(750));
            imageViewKiwi.setY(random.nextInt(550));
        }
    }

}