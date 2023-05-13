package ar.edu.itba.ss.system2;

import java.util.Arrays;
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
    private double ax = 0;
    private double ay = 0;
    private double r3x = 0;
    private double r3y = 0;
    private double r4x = 0;
    private double r4y = 0;
    private double r5x = 0;
    private double r5y = 0;
    private double[] forces;

    private final double radius;
    private final double mass;

    private final BallType type;

    private final int colorR;
    private final int colorG;
    private final int colorB;

    private final String symbol;

    public Ball(double x, double y, double vx, double vy, double radius,
                double mass, BallType type, int colorR, int colorG,
                int colorB, String symbol) {
        this.id = ID_GENERATOR.getAndIncrement();

        this.x = x;
        this.y = y;

        this.vx = vx;
        this.vy = vy;

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
        this.x = x;
    }

    public void setY(double y) {
//        this.y = y;
        this.y = y;
    }

    public long getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double[] getForces() {
        return forces;
    }

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
        this.x = this.x + this.vx * step + this.ax * Math.pow(step, 2) / 2 + this.r3x * Math.pow(step, 3) / 6 + this.r4x * Math.pow(step, 4) / 24 + this.r5x * Math.pow(step, 5) / 120;
        this.y = this.y + this.vy * step + this.ay * Math.pow(step, 2) / 2 + this.r3y * Math.pow(step, 3) / 6 + this.r4y * Math.pow(step, 4) / 24 + this.r5y * Math.pow(step, 5) / 120;

        this.vx = this.vx + this.ax * step + this.r3x * Math.pow(step, 2) / 2 + this.r4x * Math.pow(step, 3) / 6 + this.r5x * Math.pow(step, 4) / 24;
        this.vy = this.vy + this.ay * step + this.r3y * Math.pow(step, 2) / 2 + this.r4y * Math.pow(step, 3) / 6 + this.r5y * Math.pow(step, 4) / 24;

        this.ax = this.ax + this.r3x * step + this.r4x * Math.pow(step, 2) / 2 + this.r5x * Math.pow(step, 3) / 6;
        this.ay = this.ay + this.r3y * step + this.r4y * Math.pow(step, 2) / 2 + this.r5y * Math.pow(step, 3) / 6;

        this.r3x = this.r3x + this.r4x * step + this.r5x * Math.pow(step, 2) / 2;
        this.r3y = this.r3y + this.r4y * step + this.r5y * Math.pow(step, 2) / 2;

        this.r4x = this.r4x + this.r5x * step;
        this.r4y = this.r4y + this.r5y * step;

        // Skip last step of r5x and r5y as it is assigning self
    }

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

        // Calculate forces with vertical walls
        if (this.x - this.radius < 0) {
            netForce[0] += Utils.k * Math.abs(this.x - this.radius);
        }

        if (this.x + this.radius > Utils.tableWidth) {
            netForce[0] -= Utils.k * Math.abs(this.x + this.radius - Utils.tableWidth);
        }

        // Calculate forces with horizontal walls
        if (this.y - this.radius < 0) {
            netForce[1] += Utils.k * Math.abs(this.y - this.radius);
        }

        if (this.y + this.radius > Utils.tableHeight) {
            netForce[1] -= Utils.k * Math.abs(this.y + this.radius - Utils.tableHeight);
        }

        this.forces = netForce;
    }

    private double[] getForce(double x1, double y1, double x2,
                                     double y2, double radius1, double radius2) {
        double[] deltaR = new double[] {x2 - x1, y2 - y1};
        double distance = Math.sqrt(Math.pow(deltaR[0], 2) + Math.pow(deltaR[1], 2));

        // return 0 if balls do not collide
        if (distance > radius1 + radius2) {
            return new double[] {0, 0};
        }

        double forceX = Utils.k * (distance - (radius1 + radius2)) * (deltaR[0] / distance);
        double forceY = Utils.k * (distance - (radius1 + radius2)) * (deltaR[1] / distance);

        return new double[] {forceX, forceY};
    }

    public void gearCorrect(double integrationStep) {
        double[] deltaR2 = calculateDeltaR2(integrationStep);

        this.x = this.x + Utils.alphas[0] * deltaR2[0];
        this.y = this.y + Utils.alphas[0] * deltaR2[1];

        this.vx = this.vx + Utils.alphas[1] * deltaR2[0] * (1 / Math.pow(integrationStep, 1));
        this.vy = this.vy + Utils.alphas[1] * deltaR2[1] * (1 / Math.pow(integrationStep, 1));

        this.ax = this.ax + Utils.alphas[2] * deltaR2[0] * (2 / Math.pow(integrationStep, 2));
        this.ay = this.ay + Utils.alphas[2] * deltaR2[1] * (2 / Math.pow(integrationStep, 2));

        this.r3x = this.r3x + Utils.alphas[3] * deltaR2[0] * (6 / Math.pow(integrationStep, 3));
        this.r3y = this.r3y + Utils.alphas[3] * deltaR2[1] * (6 / Math.pow(integrationStep, 3));

        this.r4x = this.r4x + Utils.alphas[4] * deltaR2[0] * (24 / Math.pow(integrationStep, 4));
        this.r4y = this.r4y + Utils.alphas[4] * deltaR2[1] * (24 / Math.pow(integrationStep, 4));

        this.r5x = this.r5x + Utils.alphas[5] * deltaR2[0] * (120 / Math.pow(integrationStep, 5));
        this.r5y = this.r5y + Utils.alphas[5] * deltaR2[1] * (120 / Math.pow(integrationStep, 5));
    }

    private double[] calculateDeltaR2(double step) {
        double[] deltaR2 = new double[2];

        double factor = Math.pow(step, 2) / 2;

        deltaR2[0] = (this.forces[0] / this.mass - this.ax) * factor;
        deltaR2[1] = (this.forces[1] / this.mass - this.ay) * factor;

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
