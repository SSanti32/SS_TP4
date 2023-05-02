package ar.edu.itba.ss.system2;

import java.util.List;
import java.util.Random;

// Copia del que teniamos el tp pasado
public class Utils {

    private static final Random random = new Random();
    public static Double tableWidth = 224.0;
    public static Double tableHeight = 112.0;
    public static Double particleMass = 165.0; // gramos
    public static Double particleRadius = 5.7 / 2;
    public static Double whiteBallInitialPosX = 56.0;
    public static Double whiteBallInitialPosY = 56.0;
    public static Double whiteBallInitialVelX = 200.0;
    public static Double whiteBallInitialVelY = 0.0;
    public static Double firstBallInitialPosX = 168.0;
    public static Double firstBallInitialPosY = 56.0;
    public static Double topEpsilon = 0.03;
    public static Double bottomEpsilon = 0.02;

    // Returns difference of velocities between two balls
    public static double[] getDeltaV(double vx1, double vy1, double vx2,
                                     double vy2) {
        double[] deltaV = new double[2];
        deltaV[0] = vx2 - vx1;
        deltaV[1] = vy2 - vy1;
        return deltaV;
    }

    // Returns difference of positions between two balls
    public static double[] getDeltaR(double x1, double y1, double x2,
                                     double y2) {
        double[] deltaR = new double[2];
        deltaR[0] = x2 - x1;
        deltaR[1] = y2 - y1;
        return deltaR;
    }

    public static double getScalarProduct(double[] v1, double[] v2) {
        return v1[0] * v2[0] + v1[1] * v2[1];
    }

    public static void initializeHoles(Ball[] holes) {

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

    public static void initializeTable(List<Ball> balls,
                                       double whiteBallInitialPosX,
                                       double whiteBallInitialPosY,
                                       double whiteBallInitialVelX,
                                       double whiteBallInitialVelY,
                                       double firstBallInitialPosX,
                                       double firstBallInitialPosY) {

        // white ball
        balls.add(new Ball(whiteBallInitialPosX, whiteBallInitialPosY,
                whiteBallInitialVelX, whiteBallInitialVelY,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, 255,
                255, 255, "He"));

        // triangle
        balls.add(new Ball(firstBallInitialPosX, firstBallInitialPosY, 0,
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

//        balls.forEach(Sistema_2.Utils::perturbBall);
//        for (Ball ball : balls) {
//            if (ball.getX() == whiteBallInitialPosX &&
//                    ball.getY() == whiteBallInitialPosY &&
//                    ball.getVx() == whiteBallInitialVelX &&
//                    ball.getVy() == whiteBallInitialVelY) {
//                continue;
//            }
//            perturbBall(ball);
//        }
    }

    public static void perturbateBalls(List<Ball> balls,
                                       double whiteBallInitialPosX,
                                       double whiteBallInitialPosY,
                                       double whiteBallInitialVelX,
                                       double whiteBallInitialVelY) {
        for (Ball ball : balls) {
            if (ball.getX() == whiteBallInitialPosX &&
                    ball.getY() == whiteBallInitialPosY &&
                    ball.getVx() == whiteBallInitialVelX &&
                    ball.getVy() == whiteBallInitialVelY) {
                continue;
            }
            perturbBall(ball);
        }
    }

    private static Ball createBall(double relativeBallX, double relativeBallY
            , double sign, int colorR, int colorG, int colorB, String symbol) {
        double hypothenus = 2 * (Utils.particleRadius + Utils.topEpsilon);
        double moveInX = hypothenus * Math.cos(Math.toRadians(30));
        double moveInY = hypothenus * Math.sin(Math.toRadians(30));
        return new Ball(relativeBallX + moveInX, relativeBallY + moveInY * sign, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, colorR, colorG, colorB, symbol);
    }

    private static void perturbBall(Ball ball) {
        double epsilon = bottomEpsilon +
                (topEpsilon - bottomEpsilon) * random.nextDouble();
        double moveInX = epsilon * (random.nextBoolean() ? 1 : -1);
        double moveInY = epsilon * (random.nextBoolean() ? 1 : -1);

        ball.setX(ball.getX() + moveInX);
        ball.setY(ball.getY() + moveInY);
    }
}