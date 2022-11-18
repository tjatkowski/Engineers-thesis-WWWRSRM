package pl.edu.agh.wwwrsrm.utils;

public class Interpolation {
    public static double lerp(double start, double end, double progress) {
        return start + progress * (end - start);
    }

    public static double parabola(double start, double end, double t, double k) {
        return lerp(start, end, Math.pow(4.0 * t * (1.0 - t), k));
    }
}
