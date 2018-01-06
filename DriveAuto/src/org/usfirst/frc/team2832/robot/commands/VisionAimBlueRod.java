package org.usfirst.frc.team2832.robot.commands;

import org.usfirst.frc.team2832.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class VisionAimBlueRod extends Command {
	double servoPos = 0.5;
	int lastArc;
	
	public VisionAimBlueRod() {
		requires(Robot.Vision);
	}
	
	@Override
	protected void execute() {
		
		
		if(lastArc != Robot.Vision.getVisionArc()) {
			//have to scale (-1 to 1) to (0 to 1)
			double pos = (Robot.Vision.getTargetY() + 1) / 2;
			
			
			if(pos < 0.5) {
				servoPos = Math.min(servoPos + 0.02, 1);
			} else if(pos > 0.5) {
				servoPos = Math.max(servoPos - 0.02, -1);
			}
			
			lastArc = Robot.Vision.getVisionArc();
		}
		Robot.Vision.setServo(servoPos);
	}
	
	@Override
	protected boolean isFinished() {
		//never stop
		return false;
	}

}
