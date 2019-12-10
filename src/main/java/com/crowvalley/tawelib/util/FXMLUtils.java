package com.crowvalley.tawelib.util;

import static javafx.scene.control.Alert.AlertType;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
import java.net.URL;

public class FXMLUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(FXMLUtils.class);

    private static final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");

    public static final String ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE = "Error Loading New Scene";

    private static final String PATH_TO_FXML_FILE_DOES_NOT_EXIST_ERROR_MESSAGE =
            "Couldn't load new scene. Path to FXML file doesn't exist";

    public static void loadNewScene(Node arbitraryNodeFromCurrentScene, String fxmlOfNewScene) {
        Stage stage = getStage(arbitraryNodeFromCurrentScene);
        stage.close();
        loadNewScene(stage, prepareFXMLLoader(fxmlOfNewScene));
    }

    private static Stage getStage(Node arbitraryNodeFromCurrentScene) {
        return (Stage) arbitraryNodeFromCurrentScene.getScene().getWindow();
    }

    public static FXMLLoader prepareFXMLLoader(String pathToFxml) {
        URL fxml = FXMLUtils.class.getResource(pathToFxml);
        FXMLLoader loader = new FXMLLoader(fxml);
        loader.setControllerFactory(applicationContext::getBean);
        return loader;
    }

    private static void loadNewScene(Stage stage, FXMLLoader loader) {
        try {
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            LOGGER.error("IOException caught when loading new scene from FXML", e);
            displayErrorDialogBox(ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, e.toString());
        }catch (NullPointerException e) {
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

    public static void makeNodesVisible(Node... nodes) {
        setVisibilityOnNodes(true, nodes);
    }

    public static void makeNodesNotVisible(Node... nodes) {
        setVisibilityOnNodes(false, nodes);
    }

    private static void setVisibilityOnNodes(boolean isVisible, Node... nodes) {
        for (Node node : nodes) {
            node.setVisible(isVisible);
        }
    }

}
