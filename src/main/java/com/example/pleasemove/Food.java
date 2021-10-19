package com.example.pleasemove;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Food extends Pane {

    final private int width = 50;
    final private int height = 50;

    ImageView foodKiwi;

    Food (ImageView imageView) {
        this.foodKiwi = imageView;
        this.foodKiwi.setViewport(new Rectangle2D(0, 0, width, height));
        this.getChildren().addAll(imageView);
    }

}
