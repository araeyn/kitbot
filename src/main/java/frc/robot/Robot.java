package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

public class Robot extends TimedRobot {
    CommandScheduler scheduler;
    RobotContainer container;

    public Robot() {
        scheduler = CommandScheduler.getInstance();
        container = new RobotContainer();
    }

    @Override
    public void robotPeriodic() {
        scheduler.run();
    }

    @Override
    public void autonomousInit() {}

    @Override
    public void autonomousPeriodic() {}

    @Override
    public void teleopInit() {}

    @Override
    public void teleopPeriodic() {}
}