package com.crowvalley.tawelib.util;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ImageUtils {

    private static final FileChooser.ExtensionFilter IMAGE_FILTER = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif");

    private static final String FILE_PROTOCOL = "file:";

    public static Optional<Image> chooseAndCopyImage(String fileChooserTitle, String copiedImageDirectory, Node arbitraryNodeFromCurrentScene) {
        File selectedImageFile = getImageFile(fileChooserTitle, arbitraryNodeFromCurrentScene);
        Image image = null;
        if (selectedImageFile != null) {
            try {
                File destinationDirectory = new ClassPathResource("/img/" + copiedImageDirectory).getFile();
                File fileToBeCopied = new File(destinationDirectory, selectedImageFile.getName());

                Path pathToFileToBeCopied = Paths.get(fileToBeCopied.getPath());
                Path pathToSelectedFile = Paths.get(selectedImageFile.getPath());

                Files.copy(pathToSelectedFile, pathToFileToBeCopied, StandardCopyOption.REPLACE_EXISTING);

                image = new Image(FILE_PROTOCOL + fileToBeCopied.getAbsolutePath());
            } catch (IOException e) {
                FXMLUtils.displayErrorDialogBox("Error Choosing Image", e.getMessage());
            }
        }
        return Optional.ofNullable(image);
    }

    private static File getImageFile(String fileChooserTitle, Node arbitraryNodeFromCurrentScene) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.getExtensionFilters().add(IMAGE_FILTER);

        Stage stage = (Stage) arbitraryNodeFromCurrentScene.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    public static void deleteOldImage(ImageView imageView) {
        Image oldImage = imageView.getImage();
        if (oldImage != null) {
            String oldImageUrl = oldImage.getUrl();
            File oldImageFile = new File(oldImageUrl.substring(FILE_PROTOCOL.length()));
            try {
                FileUtils.forceDelete(oldImageFile);
            } catch (IOException e) {
                FXMLUtils.displayErrorDialogBox("Error Deleting Old Image", e.getMessage());
            }
        }
    }

}
