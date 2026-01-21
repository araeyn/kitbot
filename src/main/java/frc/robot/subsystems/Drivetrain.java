package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;
import com.ctre.phoenix6.signals.NeutralModeValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

import frc.robot.Utils;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
    TalonFX leftMotor, rightMotor;

    public Drivetrain(int leftMotorID, int rightMotorID) {
        leftMotor = new TalonFX(leftMotorID);
        rightMotor = new TalonFX(rightMotorID);

        TalonFXConfiguration baseConfig = new TalonFXConfiguration();
        baseConfig.CurrentLimits.StatorCurrentLimit = Constants.MOTOR_CURRENT_LIMIT;
        baseConfig.CurrentLimits.StatorCurrentLimitEnable = true;
        baseConfig.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        leftMotor.getConfigurator().apply(baseConfig);

        baseConfig.MotorOutput.Inverted = InvertedValue.Clockwise_Positive;
        rightMotor.getConfigurator().apply(baseConfig);
    }

    public void drive(double power, double steer) {
        double leftOut = power + steer, rightOut = power - steer;

        leftMotor.setControl(new DutyCycleOut(leftOut));
        rightMotor.setControl(new DutyCycleOut(rightOut));
    }

    public Command joyDriveCmd(java.util.function.DoubleSupplier power, java.util.function.DoubleSupplier steer, double deadband) {
        return run(
            () -> {
                double p = power.getAsDouble() * 0.8; // Dampened speed
                double s = steer.getAsDouble() * 0.8; // Dampened turn
                double powerDB = Utils.inTolerance(0, p, deadband) ? 0 : p;
                double steerDB = Utils.inTolerance(0, s, deadband) ? 0 : s;

                drive(powerDB, steerDB);
            }
        );
    }

    public Command aimAndDriveCmd(java.util.function.DoubleSupplier power, double deadband) {
        return run(
            () -> {
                double p = power.getAsDouble();
                double powerDB = Utils.inTolerance(0, p, deadband) ? 0 : p;

                var table = NetworkTableInstance.getDefault().getTable("limelight-center");
                double tv = table.getEntry("tv").getDouble(0);
                double tx = table.getEntry("tx").getDouble(0);

                double steer = 0;
                if (tv > 0) {
                    steer = tx * -0.015; // Inverted steering, dampened
                    System.out.println("Aiming: tx=" + tx + ", steer=" + steer);
                }

                SmartDashboard.putNumber("Limelight/tv", tv);
                SmartDashboard.putNumber("Limelight/tx", tx);
                SmartDashboard.putNumber("Limelight/steer", steer);

                drive(powerDB, steer);
            }
        );
    }
    
    public Command stopCmd() {
        return Commands.runOnce(
            () -> drive(0, 0),
            this
        );
    }

    @Override
    public void periodic() {}
}