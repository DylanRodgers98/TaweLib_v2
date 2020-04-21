package com.crowvalley.tawelib.util;

import com.crowvalley.tawelib.Main;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.apache.commons.lang3.reflect.FieldUtils;

import static org.assertj.core.api.Assertions.assertThat;

public class FXMLTestUtils {

    public static final String LOGIN_PAGE_WINDOW_ID = "loginPage";

    public static final String USER_HOME_WINDOW_ID = "userHome";

    public static final String USER_BROWSE_RESOURCES_WINDOW_ID = "userBrowseResources";

    public static final String USER_VIEW_RESOURCE_WINDOW_ID = "userViewResource";

    public static final String USER_LOANS_WINDOW_ID = "userLoans";

    public static final String USER_FINES_AND_PAYMENTS_WINDOW_ID = "userFinesAndPayments";

    public static final String USER_PROFILE_WINDOW_ID = "userProfile";

    public static void initTest(Stage stage, String pathToFxml) throws IllegalAccessException, InstantiationException {
        FieldUtils.writeDeclaredField(Main.class.newInstance(), "primaryStage", stage, true);
        FXMLUtils.loadNewScene(stage, FXMLUtils.prepareFXMLLoader(pathToFxml));
    }

    public static void verifyWindowShowing(Window targetWindow, String expectedWindowId) {
        String actualWindowID = targetWindow.getScene().getRoot().getId();

        assertThat(actualWindowID)
                .as("Displayed window has ID '" + expectedWindowId + "' set from FXML definition")
                .isEqualTo(expectedWindowId);
    }

}
