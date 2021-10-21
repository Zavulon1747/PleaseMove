package com.example.pleasemove;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Rat extends Pane {

    final private int width = 25;
    final private int height = 25;

    ImageView ratView;

    Rat(ImageView imageView) {
        this.ratView = imageView;
        this.ratView.setViewport(new Rectangle2D(0, 0, width, height));
        this.getChildren().addAll(imageView);
    }
}
