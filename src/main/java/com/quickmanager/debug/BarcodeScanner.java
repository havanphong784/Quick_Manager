package com.quickmanager.debug;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.DecodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import javafx.application.Platform;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class BarcodeScanner {
    private BarcodeScanner() {
    }

    public static void scan(Consumer<String> onResult, Runnable onCancel) {
        new Thread(() -> {
            Webcam webcam = Webcam.getDefault();
            if (webcam == null) {
                System.out.println("No webcam detected");
                if (onCancel != null) Platform.runLater(onCancel);
                return;
            }
            webcam.setViewSize(WebcamResolution.VGA.getSize());
            webcam.open(true);

            WebcamPanel panel = new WebcamPanel(webcam);
            panel.setFPSDisplayed(true);
            panel.setMirrored(false);

            JFrame window = new JFrame("Scan barcode - press ESC to cancel");
            window.add(panel);
            window.pack();
            window.setVisible(true);
            window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            AtomicBoolean running = new AtomicBoolean(true);

            // stop scan and release webcam when window is closed via X button
            window.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    running.set(false);
                    webcam.close();
                    if (onCancel != null) Platform.runLater(onCancel);
                }
            });

            // close on ESC
            window.addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyPressed(java.awt.event.KeyEvent e) {
                    if (e.getKeyCode() == java.awt.event.KeyEvent.VK_ESCAPE) {
                        running.set(false);
                        window.dispose();
                        webcam.close();
                        if (onCancel != null) Platform.runLater(onCancel);
                    }
                }
            });

            MultiFormatReader reader = new MultiFormatReader();
            Map<DecodeHintType, Object> hints = new EnumMap<>(DecodeHintType.class);
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            reader.setHints(hints);
            try {
                while (running.get() && webcam.isOpen()) {
                    BufferedImage img = webcam.getImage();
                    if (img == null) continue;
                    try {
                        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(img)));
                        Result result = reader.decodeWithState(bitmap);
                        if (result != null) {
                            String text = result.getText();
                            // notify on EDT (JavaFX thread will handle further)
                            onResult.accept(text);
                            running.set(false);
                            SwingUtilities.invokeLater(() -> {
                                window.dispose();
                                webcam.close();
                            });
                            break;
                        }
                    } catch (com.google.zxing.NotFoundException e) {
                        // no code in this frame
                    } catch (Exception e) {
                        System.out.println("Barcode decode error: " + e.getMessage());
                    }
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ignored) {
                    }
                }
            } finally {
                if (webcam.isOpen()) webcam.close();
                if (window.isDisplayable()) window.dispose();
            }
        }, "Barcode-Scanner-Thread").start();
    }
}

