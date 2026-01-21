package frc.robot.subsystems;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.DutyCycleOut;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.InvertedValue;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    TalonFX motor;

    public Shooter(int motorID) {
        motor = new TalonFX(motorID);
        
        TalonFXConfiguration motorConfig = new TalonFXConfiguration();
        motorConfig.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        motorConfig.CurrentLimits.StatorCurrentLimit = frc.robot.Constants.MOTOR_CURRENT_LIMIT;
        motorConfig.CurrentLimits.StatorCurrentLimitEnable = true;

        motor.getConfigurator().apply(motorConfig);
    }

    public Command setOutCmd(double out) {
        return Commands.runOnce(
            () -> motor.setControl(new DutyCycleOut(out)),
            this
        );
    }

    public Command stopCmd() {
        return setOutCmd(0);
    }

    @Override
    public void periodic() {}
}
