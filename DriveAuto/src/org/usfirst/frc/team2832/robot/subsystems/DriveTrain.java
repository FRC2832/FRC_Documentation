package org.usfirst.frc.team2832.robot.subsystems;

import org.usfirst.frc.team2832.robot.commands.TeleopDriveTrain;
import org.usfirst.frc.team2832.robot.dummy.DummyEncoder;
import org.usfirst.frc.team2832.robot.dummy.DummyGyro;
import org.usfirst.frc.team2832.robot.dummy.ServoSpeedController;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class DriveTrain extends Subsystem {
	private DifferentialDrive drive;
	private SpeedController leftDrive;
	private SpeedController rightDrive;
	private Encoder driveEncoder;
	private GyroBase gyro;
	
	public DriveTrain() {
		//initialize the speed controllers and drive train, numbers are PWM channels that the speed controllers are plugged in
		leftDrive = new ServoSpeedController(0);
		rightDrive = new ServoSpeedController(1);
		drive = new DifferentialDrive(leftDrive, rightDrive);
		
		//setup encoder
		driveEncoder = new DummyEncoder(0,1);  //numbers are DIO pins used for encoders (note, dummy ignores the pins)
		driveEncoder.setDistancePerPulse(0.1);	//set mm per pulse (NEED TO CALIBRATE)
		
		//setup gyro
		gyro = new DummyGyro();
	}
	
	public void arcadeDrive(double speed, double turn) {
		drive.arcadeDrive(speed, turn);
	}
	
	public void tankDrive(double left, double right) {
		drive.tankDrive(left, right);
	}
	
	public int getEncoderPulses() {
		return driveEncoder.get();
	}
	
	public double getEncoderDistance() {
		return driveEncoder.getDistance();
	}
	
	public double getGyroAngle() {
		return gyro.getAngle();
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new TeleopDriveTrain());
	}
	
	public void periodic() {
	}
}
