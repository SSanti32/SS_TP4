import java.util.Locale;

public class DampedOscillator {
    private double mass;
    private double k;
    private double gamma;
    private double t_f;
    private double r0;
    private double v0;

    private double t = 5;
    private final double amplitude = 1.0;

    public DampedOscillator(double mass, double k, double gamma, double t_f) {
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        this.t_f = t_f;
        this.r0 = 1;
        this.v0 = -amplitude * gamma / (2 * mass);
    }

    public double analyticalSolution() {
        return amplitude * Math.exp(-(gamma/2 * mass)*t) * Math.cos(Math.pow(k/mass - Math.pow(gamma,2)/4*Math.pow(mass,2),0.5)*t);
    }

    public double f(double r, double v) {
        return -k * r - gamma * v;
    }

    public double[] euler(double r, double v, double mass, double step) {
        double updatedR = r + step * v + (step * step)/(2 * mass) * f(r, v);
        double updatedV = v + step/mass * f(r, v);
        return new double[]{updatedR, updatedV};
    }

    public double[] verlet(double r, double prevR, double v, double mass, double step) {
        double updatedR = 2 * r - prevR + (step * step)/mass * f(r,v);
        double updatedV = (updatedR - prevR)/(2 * step);
        return new double[]{updatedR, updatedV};
    }

    public double[]



    public static void main(String[] args) {
        DampedOscillator oscillator = new DampedOscillator(70, 10e4, 100, 5);
    }
}
