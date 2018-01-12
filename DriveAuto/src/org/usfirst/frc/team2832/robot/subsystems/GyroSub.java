package org.usfirst.frc.team2832.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;

public class GyroSub extends Subsystem {
	PigeonIMU pigeon;
	double pigeon_ypr_deg[];
	
	public GyroSub() {
		pigeon_ypr_deg = new double[3];
		TalonSRX talon = new TalonSRX(1);
		pigeon = new PigeonIMU(talon);
	}
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void periodic() {
		pigeon.getYawPitchRoll(pigeon_ypr_deg);
	}
	
	public double getYaw() {
		return pigeon_ypr_deg[0];
	}
	
	public double getPitch() {
		return pigeon_ypr_deg[1];
	}
	
	public double getRoll() {
		return pigeon_ypr_deg[2];
	}
}
