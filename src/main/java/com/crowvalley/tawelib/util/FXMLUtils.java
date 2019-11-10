package com.crowvalley.tawelib.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class FXMLUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(FXMLUtils.class);

    public static void loadNewScene(Node arbitraryNodeFromCurrentScene, String fxmlOfNewScene) {
        Stage stage = (Stage) arbitraryNodeFromCurrentScene.getScene().getWindow();
        stage.close();

        URL fxmlUrl = FXMLUtils.class.getResource(fxmlOfNewScene);
        try {
            Scene scene = new Scene(FXMLLoader.load(fxmlUrl));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML {}", fxmlUrl, e);
        }
    }
}
