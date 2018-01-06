package org.usfirst.frc.team2832.robot.subsystems;

import org.usfirst.frc.team2832.robot.Robot;
import org.usfirst.frc.team2832.robot.commands.DriveForwardEncoder;
import org.usfirst.frc.team2832.robot.commands.DriveForwardTimed;
import org.usfirst.frc.team2832.robot.commands.TurnGyro;
import org.usfirst.frc.team2832.robot.commands.TurnTimed;
import org.usfirst.frc.team2832.robot.commands.VisionAimBlueRod;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SmartDashboardSub extends Subsystem{
	private SendableChooser<String> autoChooser;
	private String autoMode;
	
	public SmartDashboardSub() {
		//pack auto chooser
		autoChooser = new SendableChooser<String>();
		autoChooser.addDefault("Do nothing at all", "None");
		autoChooser.addObject("Drive Forward Time", "DriveForwardTime");
		autoChooser.addObject("2017 Game Timed (straight, turn, straight)", "2017Timed");
		autoChooser.addObject("2017 Game Sensors (straight, turn, straight)", "2017Sensors");
		autoChooser.addObject("Vision Aim", "VisionAim");
		SmartDashboard.putData("Autonomous Selection", autoChooser);
	}
	
	public void periodic() {
		autoMode = autoChooser.getSelected();
		if (autoMode == null) {
			autoMode = "None";
		}
		SmartDashboard.putString("Selected Auto", autoMode);
		
		//put sensor values so we can verify that the sensors work
		SmartDashboard.putNumber("Encoder Dist", Robot.DriveTrain.getEncoderDistance());
		SmartDashboard.putNumber("Gyro", Robot.DriveTrain.getGyroAngle());
		SmartDashboard.putNumber("Vision X", Robot.Vision.getTargetX());
		SmartDashboard.putNumber("Vision Y", Robot.Vision.getTargetY());
	}
	
	public Command getAutoCommand() {
		CommandGroup cmd = new CommandGroup();
		
		if(autoMode.equals("DriveForwardTime")) {
			cmd.addSequential(new DriveForwardTimed());
		} 
		else if(autoMode.equals("2017Timed")) {
			cmd.addSequential(new DriveForwardTimed());
			cmd.addSequential(new TurnTimed());
			cmd.addSequential(new DriveForwardTimed());
		}
		else if(autoMode.equals("2017Sensors")) {
			//distances in mm, angles in degrees
			cmd.addSequential(new DriveForwardEncoder(1906));
			cmd.addSequential(new TurnGyro(60));
			cmd.addSequential(new DriveForwardEncoder(1000));
		}
		else if(autoMode.equals("VisionAim")) {
			cmd.addSequential(new VisionAimBlueRod());
		} 
		else {
			//unknown command, do nothing
		}
		
		return cmd;
	}
	
	@Override
	protected void initDefaultCommand() {
		//no command to run
	}

}
