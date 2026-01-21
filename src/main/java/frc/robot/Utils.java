package frc.robot;

public class Utils {
    public static boolean inTolerance(double target, double current, double tolerance) {
        return Math.abs(target - current) < tolerance;
    }
}
