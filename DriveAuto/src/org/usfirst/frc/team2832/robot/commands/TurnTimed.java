package org.usfirst.frc.team2832.robot.commands;

import org.usfirst.frc.team2832.robot.Robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class TurnTimed extends Command {
	double startTime;
	
	public TurnTimed() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DriveTrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		startTime = Timer.getFPGATimestamp();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.DriveTrain.arcadeDrive(0, 0.4);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double autoTime = Timer.getFPGATimestamp() - startTime;
		if(autoTime > 5) { //drive forward for 5 seconds
		  return true;
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
