package com.crowvalley.tawelib;

import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main extends Application {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    public static final String LOGIN_PAGE_FXML = "/fxml/login.fxml";

    public static final String TAWELIB_STAGE_TITLE = "TaweLib";

    private static Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Main.primaryStage = primaryStage;
        FXMLLoader loader = FXMLUtils.prepareFXMLLoader(LOGIN_PAGE_FXML);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(TAWELIB_STAGE_TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void main(String[] args) {
        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        launch(args);
    }

    private static class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable throwable) {
            LOGGER.error("An uncaught exception occurred on thread: {}", thread, throwable);
            FXMLUtils.displayErrorDialogBox("System Error", "A fatal system error has occurred!" + System.lineSeparator() + throwable);
            System.exit(1);
        }

    }

}
