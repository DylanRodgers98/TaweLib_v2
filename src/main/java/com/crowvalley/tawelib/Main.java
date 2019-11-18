package com.crowvalley.tawelib;

import com.crowvalley.tawelib.util.FXMLUtils;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

public class Main extends Application {

    public static final String LOGIN_PAGE_FXML = "/fxml/login.fxml";

    public static final String TAWELIB_STAGE_TITLE = "TaweLib";

    public static String currentUser = StringUtils.EMPTY;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = FXMLUtils.prepareFXMLLoader(LOGIN_PAGE_FXML);
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(TAWELIB_STAGE_TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
