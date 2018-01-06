package org.usfirst.frc.team2832.robot.dummy;

import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.tables.ITable;

public class DummyEncoder extends Encoder {
	private double counts = 0;
	private double distPerPulse = 1;
	
	private class RunThread extends Thread {  
		BuiltInAccelerometer accel = new BuiltInAccelerometer();
		public void run() { 
			while(true) {
				double axis = -accel.getY();
				counts += axis * 80;  //1g = vertical
				
				//sleep every 10ms
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					//don't care
				}
			}
		}
	}
		
	public DummyEncoder(int sourceA, int sourceB) {
		//set the encoder to some high unused pins since it isn't commanding actual pins
		super(29, 30);
		
		//thread to read the controller for pulses
		Thread t = new RunThread();
		t.start();
	}

	@Override
	public double getDistance() {
		return counts * distPerPulse;
	}

	@Override
	public int get() {
		return (int)counts;
	}
	
	@Override
	public int getRaw() {
		return (int)counts;
	}
	
	@Override
	public void setDistancePerPulse(double distancePerPulse) {
		distPerPulse = distancePerPulse;
	}
	
	@Override
	public void reset() {
		counts = 0;
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
	        m_table.putNumber("Distance", getDistance());
	        m_table.putNumber("Pulses", counts);
	        //m_table.putNumber("Distance per Tick", EncoderJNI.getEncoderDistancePerPulse(m_encoder));
	    }
	}
}
