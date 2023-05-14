package ar.edu.itba.ss.system2;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static ar.edu.itba.ss.system2.Utils.createBall;

public class PoolGameSimulation {
    private static final Random random = new Random();
    public static List<Ball> balls = new ArrayList<>();
    public static Ball[] holes = new Ball[6];
    public static Set<Ball> holesSet = new HashSet<>();

    // To save all positions through time
    public static final Map<Long, List<double[]>> ballsPositions = new HashMap<>();

    public static final double k = 3;
    public static final double INTEGRATION_STEP = Math.pow(10, -k);
    public static final String FILENAME = "animation-2-step" + k + ".txt";
    public static final String TIME_FILENAME = "time-2-pos" + Utils.whiteBallInitialPosY + ".txt";
    public final static String RESOURCES_PATH_SISTEM = "src/main/java/resources/";


    public static void main(String[] args) {
        initializeBalls();

        // add holes to holesSet
        holesSet.addAll(Arrays.asList(holes));

        for (Ball ball : balls) {
            ballsPositions.put(ball.getId(), new ArrayList<>());
            ballsPositions.get(ball.getId()).add(new double[] {ball.getX(), ball.getY()});
        }

        simulate();

    }

    public static void simulate() {
        int i = 0;
        double time = 0;
        FilesGenerator.writeAnimationFile(FILENAME, i, balls, List.of(holes));

        while(balls.size() > 8) {
            // predict
            for (Ball ball : balls) {
                ball.gearPredict(INTEGRATION_STEP);
            }

            // calculate forces
            for (Ball ball : balls) {
                ball.calculateNetForce(balls);
            }

            // correct
            for (Ball ball : balls) {
                ball.gearCorrect(INTEGRATION_STEP);
            }

            // Save the current state of the system
            for (Ball ball : balls) {
                ballsPositions.get(ball.getId()).add(new double[] {ball.getX(), ball.getY()});
            }

            // Delete balls that are in holes
            balls = getBallsInTable();

            FilesGenerator.writeAnimationFile(FILENAME, ++i, balls, List.of(holes));
            time += INTEGRATION_STEP;
        }

        try {
            FilesGenerator.writeTimeFile(time, TIME_FILENAME);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private static void initializeBalls() {
        // white ball
        balls.add(new Ball(Utils.whiteBallInitialPosX, Utils.whiteBallInitialPosY,
                Utils.whiteBallInitialVelX, Utils.whiteBallInitialVelY,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, 255,
                255, 255, "He"));

        // triangle
        balls.add(new Ball(Utils.firstBallInitialPosX, Utils.firstBallInitialPosY, 0,
                0, Utils.particleRadius, Utils.particleMass, BallType.BALL,
                255, 255, 0, "Li"));

        balls.add(createBall(balls.get(1).getX(), balls.get(1).getY(), 1.0, 0
                , 0, 255, "Be"));
        balls.add(createBall(balls.get(1).getX(), balls.get(1).getY(), -1.0,
                255, 0, 0, "B"));
        balls.add(createBall(balls.get(2).getX(), balls.get(2).getY(), 1.0, 128,
                0, 128, "C"));
        balls.add(createBall(balls.get(2).getX(), balls.get(2).getY(), -1.0, 0
                , 0, 0, "N"));
        balls.add(
                createBall(balls.get(3).getX(), balls.get(3).getY(), -1.0, 255,
                        165, 0, "O"));
        balls.add(createBall(balls.get(4).getX(), balls.get(4).getY(), 1.0, 0,
                128, 0, "F"));
        balls.add(
                createBall(balls.get(4).getX(), balls.get(4).getY(), -1.0, 165,
                        42, 42, "Ne"));
        balls.add(
                createBall(balls.get(5).getX(), balls.get(5).getY(), -1.0, 139,
                        69, 19, "Na"));
        balls.add(
                createBall(balls.get(6).getX(), balls.get(6).getY(), -1.0, 173,
                        255, 47, "Fe"));
        balls.add(createBall(balls.get(7).getX(), balls.get(7).getY(), 1.0, 173,
                216, 230, "Co"));
        balls.add(
                createBall(balls.get(7).getX(), balls.get(7).getY(), -1.0, 199,
                        21, 133, "Ni"));
        balls.add(
                createBall(balls.get(8).getX(), balls.get(8).getY(), -1.0, 139,
                        0, 0, "Cu"));
        balls.add(createBall(balls.get(9).getX(), balls.get(9).getY(), -1.0, 34,
                139, 34, "Zn"));
        balls.add(createBall(balls.get(10).getX(), balls.get(10).getY(), -1.0
                , 128, 128, 0, "Na"));

        balls.forEach(PoolGameSimulation::perturbBall);

        // holes
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
                Utils.particleRadius * 2, 0, BallType.HOLE,169, 169, 169, "H");
        holes[5] = new Ball(Utils.tableWidth, 0, 0, 0,
                Utils.particleRadius * 2, 0, BallType.HOLE,169, 169, 169, "H");
    }

    private static void perturbBall(Ball ball) {
        //Avoid perturbing the white ball
        if (ball.getX() != 56.0) {
            double epsilon = Utils.bottomEpsilon + (Utils.topEpsilon - Utils.bottomEpsilon) * random.nextDouble();
            double moveInX = epsilon * (random.nextBoolean() ? 1 : -1);
            double moveInY = epsilon * (random.nextBoolean() ? 1 : -1);

            ball.setX(ball.getX() + moveInX);
            ball.setY(ball.getY() + moveInY);
        }
    }

    private static List<Ball> getBallsInTable() {
        return balls.stream().filter(ball -> {
            for (Ball hole : holes) {
                if (ball.isInHole(hole)) {
                    return false;
                }
            }
            return true;
        }).collect(Collectors.toList());
    }
}
