package org.usfirst.frc.team2832.robot.commands;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2832.robot.Robot;

/**
 *
 */
public class TeleopDriveTrain extends Command {
	public TeleopDriveTrain() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DriveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double speed = Robot.DriveController.getY(Hand.kLeft);
		double turn = Robot.DriveController.getX(Hand.kRight);
		
		Robot.DriveTrain.arcadeDrive(speed, turn);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
