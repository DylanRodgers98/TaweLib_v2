package com.crowvalley.tawelib.util;

import com.crowvalley.tawelib.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
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
        Optional<Image> image = chooseAndCopyImage(fileChooserTitle, copiedImageDirectory);
        if (image.isPresent()) {
            imageView.setImage(image.get());

            // If new image has a different path, delete the old image
            // If the have the same path, the old image would have been overwritten in chooseAndCopyImage
            if (!doImagesHaveSamePath(image.get(), imageView.getImage())) {
                deleteOldImage(imageView);
            }
        }
    }

    private static Optional<Image> chooseAndCopyImage(String fileChooserTitle, String copiedImageDirectory) {
        File selectedImageFile = getImageFile(fileChooserTitle);

        if (selectedImageFile == null) {
            return Optional.empty();
        }

        try {
            // Create destination image directory if it doesn't exist
            File destinationDirectory = getDestinationDirectory(copiedImageDirectory);
            Files.createDirectories(Paths.get(destinationDirectory.getPath()));
            File destinationFile = new File(destinationDirectory, selectedImageFile.getName());

            Path selectedFilePath = Paths.get(selectedImageFile.getPath());
            Path destinationFilePath = Paths.get(destinationFile.getPath());

            Files.copy(selectedFilePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);

            Image image = new Image(FILE_PROTOCOL + destinationFilePath, IMAGE_WIDTH, IMAGE_HEIGHT, true, true);
            return Optional.of(image);
        } catch (IOException e) {
            FXMLUtils.displayErrorDialogBox("Error Choosing Image", e.getMessage());
        }

        return Optional.empty();
    }

    private static File getImageFile(String fileChooserTitle) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(fileChooserTitle);
        fileChooser.getExtensionFilters().add(IMAGE_FILTER);
        return fileChooser.showOpenDialog(Main.primaryStage);
    }

    private static File getDestinationDirectory(String destinationDirectory) throws IOException {
        String destination = new StringBuilder(IMAGES_DIRECTORY)
                .append(File.separator)
                .append(destinationDirectory)
                .toString();
        return new ClassPathResource(destination).getFile();
    }

    private static boolean doImagesHaveSamePath(Image oldImage, Image newImage) {
        if (oldImage == null) {
            return false;
        }
        String oldImageUrl = oldImage.getUrl();
        String newImageUrl = newImage.getUrl();
        return oldImageUrl.equals(newImageUrl);
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
