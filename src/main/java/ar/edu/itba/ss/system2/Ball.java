package ar.edu.itba.ss.system2;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;

public class Ball {
    private static final AtomicLong ID_GENERATOR = new AtomicLong(0);
    private final long id;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double[][] actualR;
    private double[][] predictedR;
    private double[] forces;

    private final double radius;
    private final double mass;

    private final BallType type;

    private int colorR;
    private int colorG;
    private int colorB;

    private String symbol;

    public Ball(double x, double y, double vx, double vy, double radius,
                double mass, BallType type, int colorR, int colorG,
                int colorB, String symbol) {
        this.id = ID_GENERATOR.getAndIncrement();

        this.vx = vx;
        this.vy = vy;

        this.actualR = new double[][] {
                {x, vx, 0, 0, 0, 0},
                {y, vy, 0, 0, 0, 0}
        };
        this.predictedR = new double[][] {
                {0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0}
        };
        this.forces = new double[] {0, 0};
        this.radius = radius;
        this.mass = mass;
        this.type = type;
        this.colorR = colorR;
        this.colorG = colorG;
        this.colorB = colorB;
        this.symbol = symbol;
    }

    public int getColorR() {
        return colorR;
    }

    public int getColorG() {
        return colorG;
    }

    public int getColorB() {
        return colorB;
    }

    public String getSymbol() {
        return symbol;
    }


    public void setX(double x) {
//        this.x = x;
        this.actualR[0][0] = x;
    }

    public void setY(double y) {
//        this.y = y;
        this.actualR[1][0] = y;
    }

    public long getId() {
        return id;
    }

    public double getX() {
        return actualR[0][0];
    }

    public double getY() {
        return actualR[1][0];
    }

    public double getVx() {
        return actualR[0][1];
    }

    public double getVy() {
        return actualR[1][1];
    }

    public double[][] getActualR() {
        return actualR;
    }

    public double[][] getPredictedR() {
        return predictedR;
    }

    public double[] getForces() {
        return forces;
    }

//    public double[] getDeltaR2() {
//        return deltaR2;
//    }

    public double getRadius() {
        return radius;
    }

    public double getMass() {
        return mass;
    }

    public BallType getType() {
        return type;
    }

    public void gearPredict(double step) {
        double[] rxPredict = gearPredictComponent(this.actualR[0], step);
        double[] ryPredict = gearPredictComponent(this.actualR[1], step);

        this.predictedR = new double[][] {rxPredict, ryPredict};
    }

    private double[] gearPredictComponent(double r[], double step) {
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

    // TODO: Take into account walls
    public void calculateNetForce(List<Ball> balls) {
        double[] netForce = new double[] {0, 0};

        for (Ball otherBall : balls) {
            if (this.equals(otherBall)) {
                continue;
            }
            double[] force = getForce(this.getX(), this.getY(), otherBall.getX(), otherBall.getY(),
                    this.getRadius(), otherBall.getRadius());

            netForce[0] += force[0];
            netForce[1] += force[1];
        }

        this.forces = netForce;
    }

    private double[] getForce(double x1, double y1, double x2,
                                     double y2, double radius1, double radius2) {
        double[] deltaR = Utils.getDeltaR(x1, y1, x2, y2);
        double distance = Math.sqrt(Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2));
        double[] force = new double[2];

        force[0] = Utils.k * (distance - (radius1 + radius2)) * (deltaR[0] / distance);
        force[1] = Utils.k * (distance - (radius1 + radius2)) * (deltaR[1] / distance);

        return force;
    }

    public void gearCorrect(double integrationStep) {
        double[] deltaR2 = calculateDeltaR2(integrationStep);
        this.actualR[0] = gearCorrectComponent(this.predictedR[0], deltaR2[0], integrationStep);
        this.actualR[1] = gearCorrectComponent(this.predictedR[1], deltaR2[1], integrationStep);
    }

    private static double[] gearCorrectComponent(double[] predictedComponentRs, double deltaComponentR2, double step) {
        double[] correctedR = new double[6];

        correctedR[0] = predictedComponentRs[0] + Utils.alphas[0] * deltaComponentR2;
        correctedR[1] = predictedComponentRs[1] + Utils.alphas[1] * deltaComponentR2 * (1 / Math.pow(step, 1));
        correctedR[2] = predictedComponentRs[2] + Utils.alphas[2] * deltaComponentR2 * (2 / Math.pow(step, 2));
        correctedR[3] = predictedComponentRs[3] + Utils.alphas[3] * deltaComponentR2 * (6 / Math.pow(step, 3));
        correctedR[4] = predictedComponentRs[4] + Utils.alphas[4] * deltaComponentR2 * (24 / Math.pow(step, 4));
        correctedR[5] = predictedComponentRs[5] + Utils.alphas[5] * deltaComponentR2 * (120 / Math.pow(step, 5));

        return correctedR;
    }

    private double[] calculateDeltaR2(double step) {
        double[] deltaR2 = new double[2];

        double factor = Math.pow(step, 2) / 2;

        deltaR2[0] = (this.forces[0] - predictedR[0][2]) * factor;
        deltaR2[1] = (this.forces[1] - predictedR[1][2]) * factor;

        return deltaR2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ball ball = (Ball) o;
        return id == ball.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
