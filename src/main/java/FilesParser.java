import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FilesParser {
    public final static String RESOURCES_PATH = "src/main/resources/";
    public final static String STATIC_FILE_NAME = "static.txt";

    public static int timeStep;

    public static final Logger LOGGER = LoggerFactory.getLogger(FilesParser.class);

    public static void readInputFile() {
        File staticFile = new File(RESOURCES_PATH + STATIC_FILE_NAME);
        try (Scanner scanner = new Scanner(staticFile)) {
            timeStep = scanner.nextInt();
        } catch (FileNotFoundException e) {
            LOGGER.error("File not found: {}", staticFile.getAbsolutePath());
        }
    }
}
