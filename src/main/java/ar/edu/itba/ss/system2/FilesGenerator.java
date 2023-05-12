package ar.edu.itba.ss.system2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FilesGenerator {


    public static void writeAnimationFile(String file, int time,
                                          List<Ball> ballsList) {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter(file, true))) {
            writeAnimationFileLines(time, writer, ballsList);
        } catch (Exception e) {
            System.err.println("Error while writing animation file");
            System.out.println(e);
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
                    .append(String.valueOf(ball.getColorG())).append("\t")
                    .append(String.valueOf(ball.getSymbol()))
                    .append("\n");
        }
    }

    private static void writeAnimationFileLines(int time, BufferedWriter writer,
                                                List<Ball> ballsList)
            throws IOException {
        int totalBalls = ballsList.size();
        writer.append(String.valueOf(totalBalls))
                .append("\n")
                .append("Generation: ")
                .append(String.valueOf(time))
                .append("\n");
        writeCollectionToFileLines(writer, ballsList);
//        writer.append("\n");
    }
}
