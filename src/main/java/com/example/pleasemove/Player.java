package com.example.pleasemove;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Player extends Pane {
    int posX = 0;
    int posY = 0;
    int width = 50;
    int height = 50;

    ImageView playerImage;

    Player(ImageView imageView) {
        this.playerImage = imageView;
        this.playerImage.setViewport(new Rectangle2D(posX, posY, width, height));
        this.getChildren().addAll(imageView);
    }

}
