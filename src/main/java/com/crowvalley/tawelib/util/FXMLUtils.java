package com.crowvalley.tawelib.util;

import static javafx.scene.control.Alert.AlertType;

import com.crowvalley.tawelib.controller.FXController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;

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
        } catch (NullPointerException e) {
            LOGGER.error(PATH_TO_FXML_FILE_DOES_NOT_EXIST_ERROR_MESSAGE, e);
            displayErrorDialogBox(ERROR_LOADING_NEW_SCENE_ERROR_MESSAGE, PATH_TO_FXML_FILE_DOES_NOT_EXIST_ERROR_MESSAGE);
        }
    }

    public static void displayErrorDialogBox(String dialogBoxTitle, String causeOfError) {
        Alert errorDialogBox = createDialogBox(AlertType.ERROR, dialogBoxTitle, causeOfError);
        errorDialogBox.showAndWait();
    }

    public static Optional<ButtonType> displayConfirmationDialogBox(String dialogBoxTitle, String message) {
        Alert confirmationDialogBox = createDialogBox(AlertType.CONFIRMATION, dialogBoxTitle, message);
        return confirmationDialogBox.showAndWait();
    }

    public static void displayInformationDialogBox(String dialogBoxTitle, String message) {
        Alert confirmationDialogBox = createDialogBox(AlertType.INFORMATION, dialogBoxTitle, message);
        confirmationDialogBox.showAndWait();
    }

    private static Alert createDialogBox(AlertType alertType, String dialogBoxTitle, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(dialogBoxTitle);
        alert.setHeaderText(null);
        Text text = new Text(message);
        text.setWrappingWidth(alert.getDialogPane().getWidth());
        alert.getDialogPane().setContent(text);
        return alert;
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

    public static void makeNodesDisabled(Node... nodes) {
        setDisabilityOnNodes(true, nodes);
    }

    public static void makeNodesEnabled(Node... nodes) {
        setDisabilityOnNodes(false, nodes);
    }

    private static void setDisabilityOnNodes(boolean isDisabled, Node... nodes) {
        for (Node node : nodes) {
            node.setDisable(isDisabled);
        }
    }

    public static FXController getController(String fxml) throws IOException {
        FXMLLoader loader = prepareFXMLLoader(fxml);
        loader.load();
        return loader.getController();
    }

}
