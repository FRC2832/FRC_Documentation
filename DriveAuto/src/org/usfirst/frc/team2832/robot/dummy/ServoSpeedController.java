package org.usfirst.frc.team2832.robot.dummy;

import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.livewindow.LiveWindowSendable;
import edu.wpi.first.wpilibj.tables.ITable;

public class ServoSpeedController implements SpeedController, LiveWindowSendable {
	private Servo servo;
	private boolean inverted;
	private double speed;
	
	public ServoSpeedController(int channel) {
		servo = new Servo(channel);
	}

	  @Override
	  public void set(double speed) {
		  this.speed = speed;
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

	private ITable table;
	@Override
	public void initTable(ITable subtable) {
		table = subtable;
	}

	@Override
	public ITable getTable() {
		return table;
	}

	@Override
	public String getSmartDashboardType() {
		return "Servo";
	}

	@Override
	public void updateTable() {
		table.putNumber("Speed", speed);
		table.putBoolean("Inverted", inverted);
	}

	@Override
	public void startLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stopLiveWindowMode() {
		// TODO Auto-generated method stub
		
	}
}
