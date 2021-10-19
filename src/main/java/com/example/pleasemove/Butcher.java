package com.example.pleasemove;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Butcher extends Pane {

    final private int width = 50;
    final private int height = 50;

    ImageView butcherView;

    Butcher(ImageView butcherView) {
        this.butcherView = butcherView;
        this.butcherView.setViewport(new Rectangle2D(0, 0, width, height));
        this.getChildren().addAll(butcherView);
    }
}
