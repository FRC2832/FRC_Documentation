package org.usfirst.frc.team2832.robot.subsystems;

import java.util.ArrayList;

import org.opencv.core.MatOfPoint;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.usfirst.frc.team2832.robot.BlueRod;

import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.vision.VisionThread;

public class Vision extends Subsystem{
	private UsbCamera camera;
	private double targetX, targetY;
	private Object visionLock = new Object();	//vision thread lock object
	private Servo servo;
	private int rollCount;
	
	public Vision() {
		servo = new Servo(3);
		rollCount = 0;
		
		//get first camera and send it on the server
		camera = CameraServer.getInstance().startAutomaticCapture();
		//set resolution
		camera.setResolution(640, 480);
		
		//handle outputs of the camera thread
		VisionThread thread = new VisionThread(camera, new BlueRod(), pipeline -> {
			//get list of objects found by the vision code
			ArrayList<MatOfPoint> contours = pipeline.filterContoursOutput();
			
			//for each object found
			for(MatOfPoint contour : contours) {
				//get rectangle around points
				Rect box = Imgproc.boundingRect(contour);
				double centerX = (box.x) + (box.width/2);
				double centerY = (box.y) + (box.height/2);
				
				//convert to -1 to 1 of the frame, 0 is center
				centerX = (320 - centerX) / 320;
				centerY = (240 - centerY) / 240;
				
				//since vision is on a different thread as our robot code, it could get updated whenever, so use thread safety to save the value
				//note, probably should share either a data result class or an ArrayList instead
				synchronized(visionLock) {
					this.targetX = centerX;
					this.targetY = centerY;
				}
			}
			
			//increment rolling count after each frame processed
			synchronized(visionLock) {
				rollCount++;
			}
		});
		thread.start();
	}
	
	public double getTargetX() {
		synchronized(visionLock) {
			return targetX;
		}
	}
	
	public double getTargetY() {
		synchronized(visionLock) {
			return targetY;
		}
	}
	
	public int getVisionArc() {
		synchronized(visionLock) {
			return rollCount;
		}
	}
	
	public void setServo(double position) {
		servo.set(position);	
	}
	
	@Override
	protected void initDefaultCommand() {
		// No default command
	}

}
