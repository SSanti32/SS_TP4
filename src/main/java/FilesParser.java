import java.io.*;
import java.util.Scanner;

public class FilesParser {
    public final static String RESOURCES_PATH = "src/main/resources/";
    public final static String RESOURCES_PATH_SISTEM_1 = "src/main/resources/";
    public final static String RESOURCES_PATH_SISTEM_2 = "src/main/java/ar/edu/itba/ss/system2/resources/";
    public final static String STATIC_FILE_NAME = "static.txt";

    public static int timeStep;

//    public static final Logger LOGGER = LoggerFactory.getLogger(FilesParser.class);

    public static void readInputFile() {
        File staticFile = new File(RESOURCES_PATH + STATIC_FILE_NAME);
        try (Scanner scanner = new Scanner(staticFile)) {
            timeStep = scanner.nextInt();
        } catch (FileNotFoundException e) {
//            LOGGER.error("File not found: {}", staticFile.getAbsolutePath());
            System.err.println("File not found: " + staticFile.getAbsolutePath());
        }
    }

    private static void writeLineOutputFile(BufferedWriter writer, double currentR) throws IOException {
          writer.append(String.valueOf(currentR))
                .append("\n");
    }

    public static void writeOutputFile(File filePath, double currentR) {
        try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(filePath, true))) {
            writeLineOutputFile(writer, currentR);
        } catch (Exception e) {
//            LOGGER.error("Error while writing output file");
            System.err.println("Error while writing output file");
        }
    }

    public static void deleteFileContent(File fileName) {
        try {
            RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
            raf.setLength(0);
            raf.close();
            System.out.println("File cleaned: " + fileName.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
