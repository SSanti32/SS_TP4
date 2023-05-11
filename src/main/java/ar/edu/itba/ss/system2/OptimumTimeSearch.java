package ar.edu.itba.ss.system2;

import java.io.File;
import java.util.*;

import static ar.edu.itba.ss.system2.Utils.createBall;

public class OptimumTimeSearch {

    private static final Random random = new Random();
    public static final List<Ball> balls = new ArrayList<>();

    // To save all positions through time
    public static final Map<Long, List<double[]>> ballsPositions = new HashMap<>();

    public static final double MAX_TIME = 100;
    public static final double k = 2;
    public static final double INTEGRATION_STEP = Math.pow(10, -k);
    public static final String FILENAME = "animation-1-step" + k + ".txt";
    public final static String RESOURCES_PATH_SISTEM = "src/main/java/resources/";


    public static void main(String[] args) {
        initializeBallsWithEqualConditions();
        for (Ball ball : balls) {
            ballsPositions.put(ball.getId(), new ArrayList<>());
        }

        simulate();
    }

    public static void simulate() {
        Map<Long, double[][]> actualRs = new HashMap<>();
        Map<Long, double[][]> predictedRs = new HashMap<>();
        Map<Long, double[]> forces = new HashMap<>();
        Map<Long, double[]> deltaR2s = new HashMap<>();
        int i = 0;

        File animationFile = new File(RESOURCES_PATH_SISTEM + FILENAME);

        // Save initial positions state
        for (Ball ball : balls) {
            double[][] r = Utils.gearInit(ball.getX(), ball.getY(), ball.getVx(), ball.getVy());
            ballsPositions.get(ball.getId()).add(new double[] {r[0][0], r[1][0]});
            actualRs.put(ball.getId(), r);
            FilesGenerator.writeAnimationFile(FILENAME, i, balls);
        }

        for (double actualTime = 0; actualTime < MAX_TIME; actualTime += INTEGRATION_STEP) {
            // predict
            for (Ball ball : balls) {
                double[][] r = Utils.gearPredict(actualRs.get(ball.getId()), INTEGRATION_STEP);

                predictedRs.put(ball.getId(), r);
            }

            // calculate forces
            for (Ball ball : balls) {
                forces.put(ball.getId(), Utils.getNetForce(ball, balls, predictedRs));
                deltaR2s.put(ball.getId(),
                        Utils.getDeltaR2(forces.get(
                                ball.getId()),
                                new double[] {predictedRs.get(ball.getId())[0][2], predictedRs.get(ball.getId())[1][2]}));
            }

            // correct
            for (Ball ball : balls) {
                Utils.gearCorrect(ball, actualRs, predictedRs.get(ball.getId()), deltaR2s.get(ball.getId()),INTEGRATION_STEP);
            }

            // Save the current state of the system
            for (Ball ball : balls) {
                ballsPositions.get(ball.getId()).add(new double[] {actualRs.get(ball.getId())[0][0], actualRs.get(ball.getId())[1][0]});
            }

            // empty predictedRs
            predictedRs.clear();
            forces.clear();
            deltaR2s.clear();
            FilesGenerator.writeAnimationFile(FILENAME, ++i, balls);
        }
    }

    private static void initializeBallsWithEqualConditions() {
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

        perturbBallsWithFixedEpsilon(balls);
    }

    public static void perturbBallsWithFixedEpsilon(List<Ball> balls) {
        for (int i = 2; i < balls.size(); i++) {
            double moveInX = Utils.ballsPerturbance[i - 2][0] * (random.nextBoolean() ? 1 : -1);
            double moveInY = Utils.ballsPerturbance[i - 2][1] * (random.nextBoolean() ? 1 : -1);

            balls.get(i).setX(balls.get(i).getX() + moveInX);
            balls.get(i).setY(balls.get(i).getY() + moveInY);
        }
    }
}
