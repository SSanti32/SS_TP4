package ar.edu.itba.ss.system2;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FilesGenerator {

    public final static String RESOURCES_PATH = "src/main/resources/";

    public static void writeAnimationFile(String fileFullPath, int time,
                                          List<Ball> ballsList,
                                          List<Ball> holesList) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(fileFullPath, true))) {
            writeAnimationFileLines(time, writer, ballsList, holesList);
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    private static void writeCollectionToFileLines(BufferedWriter writer,
                                                   List<Ball> list)
            throws IOException {
        for (Ball ball : list) {
            // TODO: Check correct format for Ovito (moving balls)
            writer.append(String.valueOf(ball.getX()))
                    .append("\t")
                    .append(String.valueOf(ball.getY()))
                    .append("\t")
                    .append(String.valueOf(ball.getVx()))
                    .append("\t")
                    .append(String.valueOf(ball.getVy()))
                    .append("\t")
                    .append(String.valueOf(ball.getRadius()))
                    .append("\t")
                    .append(String.valueOf(ball.getMass()))
                    .append("\t")
                    .append(String.valueOf(ball.getColorR())).append("\t")
                    .append(String.valueOf(ball.getColorG())).append("\t")
                    .append(String.valueOf(ball.getColorB()))
//                    .append(String.valueOf(ball.getColorG())).append("\t")
                    .append(String.valueOf(ball.getSymbol()))
                    .append("\n");
        }
    }

    private static void writeAnimationFileLines(int time, BufferedWriter writer,
                                                List<Ball> ballsList,
                                                List<Ball> holesList)
            throws IOException {
        int totalBalls = ballsList.size() + holesList.size();
        writer.append(String.valueOf(totalBalls))
                .append("\n")
                .append("Generation: ")
                .append(String.valueOf(time))
                .append("\n");
        writeCollectionToFileLines(writer, holesList);
        writeCollectionToFileLines(writer, ballsList);
//        writer.append("\n");
    }

    public static void writePositionsFile(String filename, Map<Long, List<double[]>> ballsPositions) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(filename, true))) {
            for (Map.Entry<Long, List<double[]>> entry : ballsPositions.entrySet()) {
                writer.append(String.valueOf(entry.getKey()))
                        .append("\t");
                for (double[] position : entry.getValue()) {
                    writer.append(String.valueOf(position[0]))
                            .append("\t")
                            .append(String.valueOf(position[1]))
                            .append("\t");
                }
                writer.append("\n");
            }
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
        }
    }

    public static void writeTimeFile(double time, String fileName) throws IOException {
        File timeFile = new File(fileName);
        FileWriter fileWriter = new FileWriter(timeFile, true);
        fileWriter.write(time + "\n");
        fileWriter.close();
    }
}
