import java.io.IOException;
import java.util.Locale;

public class DampedOscillator {
    private double mass;
    private double k;
    private double gamma;
    private double step = 0.5;
    private double r0;
    private double v0;

    private double t_f = 5;
    private final double amplitude = 1.0;

    public DampedOscillator(double mass, double k, double gamma, double step, double t_f) {
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        this.step = step;
        this.r0 = 1;
        this.v0 = -amplitude * gamma / (2 * mass);
        this.t_f = t_f;
    }

    public double analyticalSolution() {
        return amplitude * Math.exp(-(gamma/2 * mass)*t_f) * Math.cos(Math.pow(k/mass - Math.pow(gamma,2)/4*Math.pow(mass,2),0.5)*t_f);
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

    public double[] beeman(double r, double v, double amplitude, double prevAmplitude, double nextAmplitude, double step) {
        double updatedR = r + v * step + 2.0/3.0 * amplitude * step * step - 1.0/6.0 * prevAmplitude * step * step;
        double updatedV = v + 1.0/3.0 * nextAmplitude + 5.0/6.0 * amplitude - 1.0/6.0 * prevAmplitude * step;
        return new double[]{updatedR, updatedV};
    }

    public double[] gearPC(double[] r, double step) {
        double updatedR0 = r[0] + r[1] * step + r[2] * Math.pow(step,2)/2 + r[3] * Math.pow(step,3)/6 + r[4] * Math.pow(step,4)/24 + r[5] * Math.pow(step,5)/120;
        double updatedR1 = r[1] + r[2] * step + r[3] * Math.pow(step,2)/2 + r[4] * Math.pow(step,3)/6 + r[5] * Math.pow(step,4)/24;
        double updatedR2 = r[2] + r[3] * step + r[4] * Math.pow(step,2)/2 + r[5] * Math.pow(step,3)/6;
        double updatedR3 = r[3] + r[4] * step + r[5] * Math.pow(step,2)/2;
        double updatedR4 = r[4] + r[5] * step;
        double updatedR5 = r[5];
        return new double[]{updatedR0, updatedR1, updatedR2, updatedR3, updatedR4, updatedR5};
    }

    public void verletAlgorithm() {
        double prevR = euler(r0, v0, mass, -step)[0];
        double currentR = r0;
        double currentV = v0;
        double nextR;

        System.out.println(currentR);
        for(int t = 0; t < t_f; t += step) {
            double[] nextValues = verlet(currentR, prevR, currentV, mass, step);
            nextR = nextValues[0];
            currentV = nextValues[1];
            prevR = currentR;
            currentR = nextR;
            System.out.println(currentR);
        }
    }

    public static void main(String[] args) throws IOException {
        DampedOscillator oscillator = new DampedOscillator(70, 10e4, 100, 1, 5);
        oscillator.verletAlgorithm();
    }
}
