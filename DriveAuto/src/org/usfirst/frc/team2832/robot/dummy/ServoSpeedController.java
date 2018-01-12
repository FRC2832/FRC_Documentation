package org.usfirst.frc.team2832.robot.dummy;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;

public class ServoSpeedController implements SpeedController {
	private Servo servo;
	private boolean inverted;
	
	public ServoSpeedController(int channel) {
		servo = new Servo(channel);
	}

	  @Override
	  public void set(double speed) {
		  //move from (-1 to 1) to (0 to 1)
		  servo.set((speed + 1) / 2);
	  }
	  
	  @Override
	  public double get() {
		  return (servo.get() * 2) - 1;
	  }

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInverted(boolean isInverted) {
		inverted = isInverted;
		
	}

	@Override
	public boolean getInverted() {
		return inverted;
	}

	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopMotor() {
		// TODO Auto-generated method stub
		
	}
}
