package ar.edu.itba.ss.system2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Main {

    private static PriorityQueue<Event> events;
    private static double currentTime = 0.0;
    private static Ball[] holes = new Ball[6];
    private static final List<Ball> balls = new ArrayList<>();
    public static final List<Ball> ballsInHoles = new ArrayList<>();
    public static int iterationWithThatYPosOfWhiteBall = 24;
    private static final boolean flagOfReRun = true;
    public static void main(String[] args) {

    }

    private static void initializeHoles(Ball[] holes) {

        holes[0] = new Ball(0, Utils.tableHeight, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");
        holes[1] = new Ball(Utils.tableWidth / 2, Utils.tableHeight,
                0, 0, Utils.particleRadius * 2, 0,
                BallType.HOLE,169, 169, 169,"H");
        holes[2] = new Ball(Utils.tableWidth, Utils.tableHeight, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE,169, 169, 169, "H");
        holes[3] = new Ball(0, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE,169, 169, 169, "H");
        holes[4] = new Ball(Utils.tableWidth / 2, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");
        holes[5] = new Ball(Utils.tableWidth, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE, 169, 169, 169, "H");

    }
}
