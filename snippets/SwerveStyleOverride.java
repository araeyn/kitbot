// --- 1. Inside Drivetrain.java ---

private DoubleSupplier rotationOverride = null;

public void setRotationOverride(DoubleSupplier override) {
    this.rotationOverride = override;
}

public Command joyDriveCmd(DoubleSupplier power, DoubleSupplier turn, double deadband) {
    return run(() -> {
        double p = power.getAsDouble();
        
        // CHOOSE: Override or Manual?
        double s;
        if (rotationOverride != null) {
            s = rotationOverride.getAsDouble();
        } else {
            s = turn.getAsDouble();
        }

        drive(p, s);
    });
}


// --- 2. Inside RobotContainer.java (How to use it) ---

// Normal driving
drivetrain.setDefaultCommand(
    drivetrain.joyDriveCmd(
        () -> -controller.getLeftY(),
        () -> controller.getRightX(),
        0.1
    )
);

// Aiming Button
controller.a()
    .whileTrue(runOnce(() -> drivetrain.setRotationOverride(() -> limelight.tx * 0.02)))
    .onFalse(runOnce(() -> drivetrain.setRotationOverride(null)));
