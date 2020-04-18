package com.crowvalley.tawelib.util;

import com.crowvalley.tawelib.Main;
import javafx.stage.Stage;
import org.apache.commons.lang3.reflect.FieldUtils;

public class FXMLTestUtils {

    public static void initTest(Stage stage, String pathToFxml) throws IllegalAccessException, InstantiationException {
        FieldUtils.writeDeclaredField(Main.class.newInstance(), "primaryStage", stage, true);
        FXMLUtils.loadNewScene(stage, FXMLUtils.prepareFXMLLoader(pathToFxml));
    }

}
