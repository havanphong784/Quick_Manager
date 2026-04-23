package com.quickmanager.ui;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public final class Animation {
    public static void phongTo(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(200), node);
        scale.setFromX(0.85);
        scale.setFromY(0.85);
        scale.setToX(1);
        scale.setToY(1);

        FadeTransition fade = new FadeTransition(Duration.millis(200), node);
        fade.setFromValue(0);
        fade.setToValue(1);

        new ParallelTransition(scale, fade).playFromStart();
    }

    public static void traiVao(Node node) {
        double duration = 700;
        double startX = -node.getLayoutBounds().getWidth();

        Timeline tl = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.translateXProperty(), startX)
                ),

                new KeyFrame(Duration.millis(duration * 0.013),
                        new KeyValue(node.translateXProperty(), startX * 0.97)
                ),

                new KeyFrame(Duration.millis(duration * 0.028),
                        new KeyValue(node.translateXProperty(), startX * 0.88)
                ),

                new KeyFrame(Duration.millis(duration * 0.087),
                        new KeyValue(node.translateXProperty(), startX * 0.34)
                ),

                new KeyFrame(Duration.millis(duration * 0.116),
                        new KeyValue(node.translateXProperty(), startX * 0.13)
                ),

                new KeyFrame(Duration.millis(duration * 0.146),
                        new KeyValue(node.translateXProperty(), 10)
                ),

                new KeyFrame(Duration.millis(duration * 0.162),
                        new KeyValue(node.translateXProperty(), 20)
                ),

                new KeyFrame(Duration.millis(duration * 0.179),
                        new KeyValue(node.translateXProperty(), 25)
                ),

                new KeyFrame(Duration.millis(duration * 0.197),
                        new KeyValue(node.translateXProperty(), 28)
                ),

                new KeyFrame(Duration.millis(duration * 0.217),
                        new KeyValue(node.translateXProperty(), 25)
                ),

                new KeyFrame(Duration.millis(duration * 0.314),
                        new KeyValue(node.translateXProperty(), 5)
                ),

                new KeyFrame(Duration.millis(duration * 0.38),
                        new KeyValue(node.translateXProperty(), -2)
                ),

                new KeyFrame(Duration.millis(duration * 0.576),
                        new KeyValue(node.translateXProperty(), 1)
                ),

                new KeyFrame(Duration.millis(duration),
                        new KeyValue(node.translateXProperty(), 0)
                )
        );

        tl.playFromStart();
    }

    public static void duoiLen(Node node) {
        double duration = 600;

        Timeline tl = new Timeline(
                new KeyFrame(Duration.millis(0),
                        new KeyValue(node.translateYProperty(), 200),
                        new KeyValue(node.opacityProperty(), 0)
                ),

                new KeyFrame(Duration.millis(duration * 0.447),
                        new KeyValue(node.translateYProperty(), 0),
                        new KeyValue(node.opacityProperty(), 1)
                ),

                new KeyFrame(Duration.millis(duration * 0.518),
                        new KeyValue(node.translateYProperty(), 10),
                        new KeyValue(node.opacityProperty(), 0.898)
                ),

                new KeyFrame(Duration.millis(duration * 0.551),
                        new KeyValue(node.translateYProperty(), 15),
                        new KeyValue(node.opacityProperty(), 0.874)
                ),

                new KeyFrame(Duration.millis(duration * 0.584),
                        new KeyValue(node.translateYProperty(), 18),
                        new KeyValue(node.opacityProperty(), 0.866)
                ),

                new KeyFrame(Duration.millis(duration * 0.643),
                        new KeyValue(node.translateYProperty(), 10),
                        new KeyValue(node.opacityProperty(), 0.888)
                ),

                new KeyFrame(Duration.millis(duration * 0.774),
                        new KeyValue(node.translateYProperty(), 0),
                        new KeyValue(node.opacityProperty(), 1)
                ),

                new KeyFrame(Duration.millis(duration * 0.845),
                        new KeyValue(node.translateYProperty(), 3),
                        new KeyValue(node.opacityProperty(), 0.98)
                ),

                new KeyFrame(Duration.millis(duration),
                        new KeyValue(node.translateYProperty(), 0),
                        new KeyValue(node.opacityProperty(), 1)
                )
        );

        tl.playFromStart();
    }
}
