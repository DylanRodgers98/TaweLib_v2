package com.crowvalley.tawelib;

import com.crowvalley.tawelib.controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main extends Application {

    public static String currentUser;

    @Override
    public void start(Stage primaryStage) throws Exception {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("/spring/applicationContext.xml");
        LoginController loginController = applicationContext.getBean("loginController", LoginController.class);
        loginController.showLoginPage(applicationContext, primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }

}
