package org.usfirst.frc.team2832.robot.commands;

import org.usfirst.frc.team2832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveForwardEncoder extends Command {
	double startDist;
	double targetDist;
	
	public DriveForwardEncoder(double targDist) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DriveTrain);
		targetDist = targDist;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		startDist = Robot.DriveTrain.getEncoderDistance();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.DriveTrain.arcadeDrive(0.6, 0);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double traveledDist = Robot.DriveTrain.getEncoderDistance() - startDist;
		if(traveledDist > targetDist) {
		  return true;  //have traveled far enough
		} else {
		  return false;
		}
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		//stop the motors when leaving
		Robot.DriveTrain.arcadeDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
