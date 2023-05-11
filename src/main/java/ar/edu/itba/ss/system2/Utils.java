package ar.edu.itba.ss.system2;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {

    public static final Random random = new Random();
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
    // TODO: Check different units: [k]=N/m
    //  -> for now, changed to N/cm => k = 10^4 N/m = 10^2 N/cm
    public static double k = Math.pow(10, 2);
    public static double[] alphas = {3.0/16.0, 251.0/360.0, 1.0, 11.0/18.0, 1.0/6.0, 1.0/60.0};

    public static double[][] ballsPerturbance = new double[][] {
            {0.021235598922957385, 0.022694037540199326},
            {0.023897233284257816, 0.024566358057843015},
            {0.026337147938454455, 0.02067130411936912},
            {0.02147620716290274, 0.026869462194055796},
            {0.023941629628297018, 0.02621201662594255},
            {0.02976091954892348, 0.021961774142220728},
            {0.02901048824612499, 0.02047350062876492},
            {0.0203275406781949, 0.021808879241172623},
            {0.022556135250976377, 0.024058000120774126},
            {0.02985562179742978, 0.025997207818308568},
            {0.024901696307081042, 0.02646666246029257},
            {0.027475320510322143, 0.022300688806439425},
            {0.027409308396072314, 0.024363052061283873},
            {0.022508866493280422, 0.022964725921354075},
            {0.02595795102960729, 0.020288199160830182}
    };

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

    public static double[] getNetForce(Ball ball, List<Ball> balls, Map<Long, double[][]> predictedRs) {
        double[] netForceX = new double[2];
        double[] netForceY = new double[2];
        double x1 = predictedRs.get(ball.getId())[0][0];
        double y1 = predictedRs.get(ball.getId())[0][1];
        double x2, y2;
        for (Ball b : balls) {
            if (b.getId() != ball.getId()) {
                x2 = predictedRs.get(b.getId())[0][0];
                y2 = predictedRs.get(b.getId())[0][1];
                double[] force = getForce(x1, y1, x2, y2, ball.getRadius(), b.getRadius());
                netForceX[0] += force[0];
                netForceY[0] += force[1];
            }
        }
        return new double[]{netForceX[0], netForceY[0]};
    }

    private static double[] getForce(double x1, double y1, double x2,
                                    double y2, double radius1, double radius2) {
        double[] deltaR = getDeltaR(x1, y1, x2, y2);
        double distance = Math.sqrt(Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2));
        double[] force = new double[2];

        force[0] = k * (distance - (radius1 + radius2)) * (deltaR[0] / distance);
        force[1] = k * (distance - (radius1 + radius2)) * (deltaR[1] / distance);

        return force;
    }

    public static double[] getDeltaR2(double[] acceleration, double[] predictedAcceleration) {
        double[] deltaR2 = new double[2];
        deltaR2[0] = acceleration[0] - predictedAcceleration[0];
        deltaR2[1] = acceleration[1] - predictedAcceleration[1];
        return deltaR2;
    }

    public static double[][] gearInit(double x, double y, double vx, double vy) {
        double[] rx = new double[6];
        double[] ry = new double[6];

        rx[0] = x;
        ry[0] = y;

        rx[1] = vx;
        ry[1] = vy;

        // TODO: Check if it needs to be calculated
        rx[2] = 0;
        ry[2] = 0;

        rx[3] = 0;
        ry[3] = 0;

        rx[4] = 0;
        ry[4] = 0;

        rx[5] = 0;
        ry[5] = 0;

        double[][] r = new double[2][6];
        r[0] = rx;
        r[1] = ry;

        return r;
    }
    public static double[][] gearPredict(double[][] r, double step) {
        double[] rxPredict = gearPredictComponent(r[0], step);
        double[] ryPredict = gearPredictComponent(r[1], step);

        return new double[][] {rxPredict, ryPredict};
    }

    private static double[] gearPredictComponent(double r[], double step) {
        double[] rPredict = new double[6];

        rPredict[0] = r[0] + r[1] * step
                + r[2] * Math.pow(step, 2) / 2
                + r[3] * Math.pow(step, 3) / 6
                + r[4] * Math.pow(step, 4) / 24
                + r[5] * Math.pow(step, 5) / 120;

        rPredict[1] = r[1] + r[2] * step
                + r[3] * Math.pow(step, 2) / 2
                + r[4] * Math.pow(step, 3) / 6
                + r[5] * Math.pow(step, 4) / 24;

        rPredict[2] = r[2] + r[3] * step
                + r[4] * Math.pow(step, 2) / 2
                + r[5] * Math.pow(step, 3) / 6;

        rPredict[3] = r[3] + r[4] * step
                + r[5] * Math.pow(step, 2) / 2;

        rPredict[4] = r[4] + r[5] * step;

        rPredict[5] = r[5];

        return rPredict;
    }

    public static void gearCorrect(Ball ball, Map<Long, double[][]> actualRs, double[][] predictedRs, double[] deltaR2, double step) {
        double[][] correctedR = new double[2][6];
        correctedR[0] = gearCorrectComponent(predictedRs[0], deltaR2[0], step);
        correctedR[1] = gearCorrectComponent(predictedRs[1], deltaR2[1], step);

        actualRs.put(ball.getId(), correctedR);

        ball.setX(correctedR[0][0]);
        ball.setY(correctedR[1][0]);

        ball.setVx(correctedR[0][1]);
        ball.setVy(correctedR[1][1]);
    }

    private static double[] gearCorrectComponent(double[] predictedComponentRs, double deltaComponentR2, double step) {
        double[] correctedR = new double[6];

        correctedR[0] = predictedComponentRs[0] + alphas[0] * deltaComponentR2;
        correctedR[1] = predictedComponentRs[1] + alphas[1] * deltaComponentR2 * (1 / Math.pow(step, 1));
        correctedR[2] = predictedComponentRs[2] + alphas[2] * deltaComponentR2 * (1 / Math.pow(step, 2));
        correctedR[3] = predictedComponentRs[3] + alphas[3] * deltaComponentR2 * (1 / Math.pow(step, 3));
        correctedR[4] = predictedComponentRs[4] + alphas[4] * deltaComponentR2 * (1 / Math.pow(step, 4));
        correctedR[5] = predictedComponentRs[5] + alphas[5] * deltaComponentR2 * (1 / Math.pow(step, 5));

        return correctedR;
    }

    public static Ball createBall(double relativeBallX, double relativeBallY
            , double sign, int colorR, int colorG, int colorB, String symbol) {
        double hypothenus = 2 * (Utils.particleRadius + Utils.topEpsilon);
        double moveInX = hypothenus * Math.cos(Math.toRadians(30));
        double moveInY = hypothenus * Math.sin(Math.toRadians(30));
        return new Ball(relativeBallX + moveInX, relativeBallY + moveInY * sign, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, colorR, colorG, colorB, symbol);
    }

}
