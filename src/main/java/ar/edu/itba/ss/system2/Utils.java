package ar.edu.itba.ss.system2;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class Utils {

    public static final Random random = new Random();
    public static Double tableWidth = 224.0/100;
    public static Double tableHeight = 112.0/100;
    public static Double particleMass = 165.0; // gramos
    public static Double particleRadius = (5.7 / 2)/100;
    public static Double whiteBallInitialPosX = 56.0/100;
    public static Double whiteBallInitialPosY = 56.0/100;
    public static Double whiteBallInitialVelX = 2.0;
    public static Double whiteBallInitialVelY = 0.0;
    public static Double firstBallInitialPosX = 168.0/100;
    public static Double firstBallInitialPosY = 56.0/100;
    public static Double topEpsilon = 0.03/100;
    public static Double bottomEpsilon = 0.02/100;
    // TODO: Check different units: [k]=N/m
    //  -> for now, changed to N/cm => k = 10^4 N/m = 10^2 N/cm
    public static double k = Math.pow(10, 4);
    public static double[] alphas = {3.0/20.0, 251.0/360.0, 1.0, 11.0/18.0, 1.0/6.0, 1.0/60.0};

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

    public static Ball createBall(double relativeBallX, double relativeBallY
            , double sign, int colorR, int colorG, int colorB, String symbol) {
        double hypothenus = 2 * (Utils.particleRadius + Utils.topEpsilon);
        double moveInX = hypothenus * Math.cos(Math.toRadians(30));
        double moveInY = hypothenus * Math.sin(Math.toRadians(30));
        return new Ball(relativeBallX + moveInX, relativeBallY + moveInY * sign, 0, 0,
                Utils.particleRadius, Utils.particleMass, BallType.BALL, colorR, colorG, colorB, symbol);
    }

}
