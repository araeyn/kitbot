// Example: Driving while Aiming (Simultaneously)
// This logic allows the driver to control the Speed (throttle) 
// while the Limelight controls the Steering.

// Implementation Strategy:
// Instead of scheduling an "Align" command that stops the "Drive" command,
// we create a single drive command that chooses its turning source dynamically.

/**
 * PRO TIP: 
 * This code would go into RobotContainer.java inside the constructor.
 */

/*
drivetrain.setDefaultCommand(
    drivetrain.joyDriveCmd(
        () -> controller.getLeftY(), 

        () -> {
            if (controller.getHID().getAButton()) {
                double tx = NetworkTableInstance.getDefault()
                    .getTable("limelight-center")
                    .getEntry("tx").getDouble(0);
                
                double kP = 0.02;
                return tx * kP;
            } else {
                return controller.getRightX();
            }
        },
        0.1 // Deadband
    )
);
*/
