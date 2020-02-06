package com.crowvalley.tawelib.util;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ImageUtils {

    private static final ExtensionFilter IMAGE_FILTER = new ExtensionFilter("Image Files (.png, .jpg, .gif)", "*.png", "*.jpg", "*.gif");

    private static final String IMAGES_DIRECTORY = "img";

    private static final String FILE_PROTOCOL = "file:";

    private static final int IMAGE_WIDTH = 240;

    private static final int IMAGE_HEIGHT = 250;

    public static void chooseImage(String fileChooserTitle, String copiedImageDirectory, ImageView imageView) {
        Optional<Image> image = chooseAndCopyImage(fileChooserTitle, copiedImageDirectory, imageView);
        if (image.isPresent() && !isSameAsCurrentImage(image.get(), imageView)) {
            deleteOldImage(imageView);
            imageView.setImage(image.get());
        }
    }

    private static Optional<Image> chooseAndCopyImage(String fileChooserTitle, String copiedImageDirectory, ImageView imageView) {
        File selectedImageFile = getImageFile(fileChooserTitle, imageView);

        Image image = null;
        if (selectedImageFile != null) {
            try {
                File destinationDirectory = getDestinationDirectory(copiedImageDirectory);
                File copiedFile = new File(destinationDirectory, selectedImageFile.getName());

                Path pathToCopiedFile = Paths.get(copiedFile.getPath());
                Path pathToSelectedFile = Paths.get(selectedImageFile.getPath());

                Files.copy(pathToSelectedFile, pathToCopiedFile, StandardCopyOption.REPLACE_EXISTING);

                image = new Image(FILE_PROTOCOL + pathToCopiedFile, IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
            } catch (IOException e) {
                FXMLUtils.displayErrorDialogBox("Error Choosing Image", e.getMessage());
            }
        }

        return Optional.ofNullable(image);
    }

    private static File getImageFile(String fileChooserTitle, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.getExtensionFilters().add(IMAGE_FILTER);

        Stage stage = (Stage) imageView.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    private static File getDestinationDirectory(String destinationDirectory) throws IOException {
        String destination = new StringBuilder(IMAGES_DIRECTORY)
                .append(File.separator)
                .append(destinationDirectory)
                .toString();
        return new ClassPathResource(destination).getFile();
    }

    private static boolean isSameAsCurrentImage(Image newImage, ImageView imageView) {
        Image currentImage = imageView.getImage();
        if (currentImage == null) {
            return false;
        } else {
            String currentImageUrl = currentImage.getUrl();
            String newImageUrl = newImage.getUrl();
            return currentImageUrl.equals(newImageUrl);
        }
    }

    private static void deleteOldImage(ImageView imageView) {
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
