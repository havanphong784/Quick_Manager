package com.quickmanager.ui;

import javafx.animation.*;
import javafx.scene.Node;
import javafx.util.Duration;

public final class Animation {
    public static void phongTo(Node node) {
        ScaleTransition scale = new ScaleTransition(Duration.millis(250), node);
        scale.setFromX(0.9);
        scale.setFromY(0.9);
        scale.setToX(1);
        scale.setToY(1);
        scale.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(250), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(scale, fade).playFromStart();
    }

    public static void traiVao(Node node) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(350), node);
        translate.setFromX(-50);
        translate.setToX(0);
        translate.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(350), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(translate, fade).playFromStart();
    }

    public static void duoiLen(Node node) {
        TranslateTransition translate = new TranslateTransition(Duration.millis(400), node);
        translate.setFromY(50);
        translate.setToY(0);
        translate.setInterpolator(Interpolator.EASE_OUT);

        FadeTransition fade = new FadeTransition(Duration.millis(400), node);
        fade.setFromValue(0);
        fade.setToValue(1);
        fade.setInterpolator(Interpolator.EASE_OUT);

        new ParallelTransition(translate, fade).playFromStart();
    }
}
