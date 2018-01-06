package org.usfirst.frc.team2832.robot.dummy;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.tables.ITable;

public class DummyGyro extends GyroBase {
	private double angle = 0;
	
	private class RunThread extends Thread {  
		BuiltInAccelerometer accel = new BuiltInAccelerometer();
		public void run() { 
			while(true) {
				double axis = -accel.getX();
				angle += axis * 1;  //1g = vertical
				
				//sleep every 10ms
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					//don't care
				}
			}
		}
	}
		
	public DummyGyro() {
		//thread to read the controller for gyro angle
		Thread t = new RunThread();
		t.start();
	}
	
	@Override
	public void calibrate() {
		// nothing to calibrate
	}

	@Override
	public double getAngle() {
		return angle;
	}

	@Override
	public double getRate() {
		return 0;
	}

	@Override
	public void reset() {
		angle = 0;
	}
	
	private ITable m_table;

	@Override
	public void initTable(ITable subtable) {
	    m_table = subtable;
	    updateTable();
	}

	@Override
	public ITable getTable() {
	    return m_table;
	}

	@Override
	public void updateTable() {
	    if (m_table != null) {
	        m_table.putNumber("Speed", getRate());
	        m_table.putNumber("Angle", getAngle());
	    }
	}
}
