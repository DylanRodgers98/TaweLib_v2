package com.crowvalley.tawelib.util;

import static javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;

public class FXMLUtils {

    public static final Logger LOGGER = LoggerFactory.getLogger(FXMLUtils.class);

    public static final String ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE = "Error Loading New Scene";

    public static final String PATH_TO_FXML_FILE_DOES_NOT_EXIST_ERROR_MESSAGE =
            "Couldn't load new scene. Path to FXML file doesn't exist";

    public static void loadNewScene(Node arbitraryNodeFromCurrentScene, String fxmlOfNewScene) {
        Stage stage = (Stage) arbitraryNodeFromCurrentScene.getScene().getWindow();
        stage.close();
        URL fxml = FXMLUtils.class.getResource(fxmlOfNewScene);
        try {
            Scene scene = new Scene(FXMLLoader.load(fxml));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML {}", fxml, e);
            displayErrorDialogBox(ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        } catch (NullPointerException e) {
            LOGGER.error(PATH_TO_FXML_FILE_DOES_NOT_EXIST_ERROR_MESSAGE, e);
            displayErrorDialogBox(ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, PATH_TO_FXML_FILE_DOES_NOT_EXIST_ERROR_MESSAGE);
        }
    }

    public static void displayErrorDialogBox(String dialogBoxTitle, String causeOfError) {
        displayDialogBox(AlertType.ERROR, dialogBoxTitle, causeOfError);
    }

    private static void displayDialogBox(AlertType alertType, String dialogBoxTitle, String causeOfError) {
        Alert alert = new Alert(alertType);
        alert.setTitle(dialogBoxTitle);
        alert.setHeaderText(null);
        alert.setContentText(causeOfError);
        alert.showAndWait();
    }
}
