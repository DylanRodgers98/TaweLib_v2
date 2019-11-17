package com.crowvalley.tawelib;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

public class Main extends Application {

    public static final String LOGIN_PAGE_FXML = "/fxml/login.fxml";

    public static final String APPLICATION_CONTEXT_LOCATION = "/spring/applicationContext.xml";

    public static final String TAWELIB_STAGE_TITLE = "TaweLib";

    public static String currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL loginPage = getClass().getResource(LOGIN_PAGE_FXML);
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_LOCATION);

        FXMLLoader loader = new FXMLLoader(loginPage);
        loader.setControllerFactory(applicationContext::getBean);

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(TAWELIB_STAGE_TITLE);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
