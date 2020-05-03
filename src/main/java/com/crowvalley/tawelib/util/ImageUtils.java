package com.crowvalley.tawelib.util;

import com.crowvalley.tawelib.Main;
import com.crowvalley.tawelib.UserContextHolder;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;

public class ImageUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageUtils.class);

    private static final ExtensionFilter IMAGE_FILTER = new ExtensionFilter("Image Files (.png, .jpg, .gif)", "*.png", "*.jpg", "*.gif");

    private static final String IMAGES_DIRECTORY = System.getProperty("user.home") + "/tawelib/img";

    private static final int IMAGE_WIDTH = 240;

    private static final int IMAGE_HEIGHT = 250;

    public static void chooseImage(String fileChooserTitle, String copiedImageDirectory, ImageView imageView) {
        Optional<Image> image = chooseAndCopyImage(fileChooserTitle, copiedImageDirectory);
        if (image.isPresent()) {
            Image newImage = image.get();
            imageView.setImage(newImage);

            // If new image has a different path, delete the old image. If they have the same path,
            // the old image would have been overwritten in chooseAndCopyImage, and so won't need deleting
            Image oldImage = imageView.getImage();
            if (!doImagesHaveSamePath(oldImage, newImage)) {
                deleteImage(oldImage);
            }
        }
    }

    private static Optional<Image> chooseAndCopyImage(String fileChooserTitle, String copiedImageDirectory) {
        File selectedImageFile = getImageFile(fileChooserTitle);

        if (selectedImageFile == null) {
            return Optional.empty();
        }

        try {
            File destinationDirectory = getDestinationDirectory(copiedImageDirectory);
            File destinationFile = new File(destinationDirectory, selectedImageFile.getName());

            Path selectedFilePath = Paths.get(selectedImageFile.getPath());
            Path destinationFilePath = Paths.get(destinationFile.getPath());

            Files.copy(selectedFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            LOGGER.info("New image saved to {}", destinationFilePath);

            Image image = getImage(ResourceUtils.FILE_URL_PREFIX + destinationFilePath);
            return Optional.of(image);
        } catch (IOException e) {
            LOGGER.warn("Error choosing image", e);
            FXMLUtils.displayErrorDialogBox("Error Choosing Image", e.getMessage());
        }

        return Optional.empty();
    }

    public static Image getImage(String url) {
        return new Image(url, IMAGE_WIDTH, IMAGE_HEIGHT, true, true, true);
    }

    private static File getImageFile(String fileChooserTitle) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.getExtensionFilters().add(IMAGE_FILTER);
        return fileChooser.showOpenDialog(Main.getPrimaryStage());
    }

    private static File getDestinationDirectory(String destinationDirectory) throws IOException {
        File destination = new File(IMAGES_DIRECTORY, destinationDirectory);
        FileUtils.forceMkdir(destination);
        return destination;
    }

    private static boolean doImagesHaveSamePath(Image image1, Image image2) {
        if (image1 == null || image2 == null) {
            return false;
        }
        String oldImageUrl = image1.getUrl();
        String newImageUrl = image2.getUrl();
        return oldImageUrl.equals(newImageUrl);
    }

    private static void deleteImage(Image image) {
        if (image != null) {
            String oldImageUrl = image.getUrl();
            File oldImageFile = new File(oldImageUrl.substring(ResourceUtils.FILE_URL_PREFIX.length()));
            try {
                FileUtils.forceDelete(oldImageFile);
                LOGGER.info("Image {} successfully deleted", oldImageFile.getPath());
            } catch (IOException e) {
                LOGGER.warn("Error deleting image {}", oldImageFile.getPath(), e);
                FXMLUtils.displayErrorDialogBox("Error Deleting Old Image", e.getMessage());
            }
        }
    }

}
