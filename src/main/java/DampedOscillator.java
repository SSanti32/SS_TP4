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

    public double analyticalSolution(double t) {
        return amplitude * Math.exp(-(gamma/2 * mass)*t) * Math.cos(Math.pow(k/mass - Math.pow(gamma,2)/4*Math.pow(mass,2),0.5)*t);
    }

    public double f(double r, double v) {
        return -k * r - gamma * v;
    }

    private double[] euler(double r, double v, double mass, double step) {
        double updatedR = r + step * v + (step * step * f(r,v))/(2 * mass);
        double updatedV = v + (step * f(r, v))/mass;
        return new double[]{updatedR, updatedV};
    }

    private double[] verlet(double r, double prevR, double v, double mass, double step) {
        double updatedR = 2 * r - prevR + (step * step * f(r,v))/mass;
        double updatedV = (updatedR - prevR)/(2 * step);
        return new double[]{updatedR, updatedV};
    }

    private double[] beeman(double r, double v, double amplitude, double prevAmplitude, double nextAmplitude, double step) {
        double updatedR = r + v * step + 2.0/3.0 * amplitude * step * step - 1.0/6.0 * prevAmplitude * step * step;
        double updatedV = v + 1.0/3.0 * nextAmplitude + 5.0/6.0 * amplitude - 1.0/6.0 * prevAmplitude * step;
        return new double[]{updatedR, updatedV};
    }

    private double[] gearPredict(double[] r, double step) {
        double updatedR0 = r[0] + r[1] * step + r[2] * Math.pow(step,2)/2 + r[3] * Math.pow(step,3)/6 + r[4] * Math.pow(step,4)/24 + r[5] * Math.pow(step,5)/120;
        double updatedR1 = r[1] + r[2] * step + r[3] * Math.pow(step,2)/2 + r[4] * Math.pow(step,3)/6 + r[5] * Math.pow(step,4)/24;
        double updatedR2 = r[2] + r[3] * step + r[4] * Math.pow(step,2)/2 + r[5] * Math.pow(step,3)/6;
        double updatedR3 = r[3] + r[4] * step + r[5] * Math.pow(step,2)/2;
        double updatedR4 = r[4] + r[5] * step;
        double updatedR5 = r[5];
        return new double[]{updatedR0, updatedR1, updatedR2, updatedR3, updatedR4, updatedR5};
    }

    private void gearCorrect(double[] r, double deltaR2, double[] alphas, double step) {
        r[0] += alphas[0] * deltaR2 * 1 / Math.pow(step, 0);
        r[1] += alphas[1] * deltaR2 * 1 / Math.pow(step, 1);
        r[2] += alphas[2] * deltaR2 * 2 / Math.pow(step, 2);
        r[3] += alphas[3] * deltaR2 * 6 / Math.pow(step, 3);
        r[4] += alphas[4] * deltaR2 * 24 / Math.pow(step, 4);
        r[5] += alphas[5] * deltaR2 * 120 / Math.pow(step, 5);
    }

    private double[] initializeGear() {
        double[] r = new double[6];
        r[0] = r0;
        r[1] = v0;
        r[2] = f(r[0],r[1]) / mass;
        r[3] = f(r[1],r[2]) / mass;
        r[4] = f(r[2],r[3]) / mass;
        r[5] = f(r[3],r[4]) / mass;
        return r;
    }

    public void verletAlgorithm() {
        double prevR = euler(r0, v0, mass, -step)[0];
        double currentR = r0;
        double currentV = v0;
        double nextR;
        System.out.println(currentR);
        for(double t = 0; t < t_f; t += step) {
            double[] nextValues = verlet(currentR, prevR, currentV, mass, step);
            nextR = nextValues[0];
            currentV = nextValues[1];
            prevR = currentR;
            currentR = nextR;
            System.out.println(currentR);
        }
    }

    public void gearAlgorithm() {
        double[] alphas = {3.0/16.0, 251.0/360.0, 1.0, 11.0/18.0, 1.0/6.0, 1.0/60.0};
        double currentR = r0;
        double[] r = initializeGear();
        double currentAmp;
        System.out.println(currentR);
        for(double t = 0; t < t_f; t += step) {
            //Predict, evaluate and correct
            r = gearPredict(r,step);
            currentAmp = f(r[0],r[1])/mass;
            double deltaAmp = currentAmp - r[2];
            double deltaR2 = deltaAmp * (step * step) / 2;
            gearCorrect(r, deltaR2, alphas, step);
            System.out.println(r[0]);
        }
    }



    public static void main(String[] args) {
        DampedOscillator oscillator = new DampedOscillator(70, 10e4, 100, 1.0, 5);
        oscillator.verletAlgorithm();
        System.out.println();
        oscillator.gearAlgorithm();
    }
}
