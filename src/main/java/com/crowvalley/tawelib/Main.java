package com.crowvalley.tawelib;

import com.crowvalley.tawelib.controller.LoginController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application {

    public static final String LOGIN_PAGE_FXML = "/fxml/login.fxml";

    public static final String APPLICATION_CONTEXT_LOCATION = "/spring/applicationContext.xml";

    public static final String TAWELIB = "TaweLib";

    public static String currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(LOGIN_PAGE_FXML));

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_LOCATION);
        loader.setControllerFactory(applicationContext::getBean);

        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.setTitle(TAWELIB);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
