package ar.edu.itba.ss.system2;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import static ar.edu.itba.ss.system2.Utils.createBall;

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

    private static void perturbBall(Ball ball) {
        double epsilon = Utils.bottomEpsilon +
                (Utils.topEpsilon - Utils.bottomEpsilon) * Utils.random.nextDouble();
        double moveInX = epsilon * (Utils.random.nextBoolean() ? 1 : -1);
        double moveInY = epsilon * (Utils.random.nextBoolean() ? 1 : -1);

        ball.setX(ball.getX() + moveInX);
        ball.setY(ball.getY() + moveInY);
    }
}
