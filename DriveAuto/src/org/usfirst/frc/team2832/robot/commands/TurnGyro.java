package org.usfirst.frc.team2832.robot.commands;

import org.usfirst.frc.team2832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TurnGyro extends Command {
	double startAngle;
	double targetAngle;
	int finishedCounts;
	
	final double TARGET_ANGLE = 2.0;
	final int GOAL_COUNTS = 12;  //each count is 20ms
	
	public TurnGyro(double targAngle) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.DriveTrain);
		
		targetAngle = targAngle;
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		startAngle = Robot.DriveTrain.getGyroAngle();
		finishedCounts = 0;
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		double turn;
		//start with amount of error
		turn = (Robot.DriveTrain.getGyroAngle() - startAngle) - targetAngle;
		//compensate (P = 0.01 here, so 1 degree of error = 0.01% motor command (40* off = 40% motor command))
		turn = turn * 0.01;
		//range check the value to make it between -0.4 to 0.4
		turn = Math.min(Math.max(-0.4, turn), 0.4);
		Robot.DriveTrain.arcadeDrive(0, turn);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		double error = Math.abs((Robot.DriveTrain.getGyroAngle() - startAngle) - targetAngle);
		
		if(error < TARGET_ANGLE) { 
			if (finishedCounts > GOAL_COUNTS) {
				//met our goal, exit
				return true;
			} else {
				//not at target counts yet, increment to get there
				finishedCounts++;
				return false;
			} 
		} else {
			//not in range, reset exit counts
			finishedCounts = 0;
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
