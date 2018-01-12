
package org.usfirst.frc.team2832.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team2832.robot.subsystems.DriveTrain;
import org.usfirst.frc.team2832.robot.subsystems.GyroSub;
import org.usfirst.frc.team2832.robot.subsystems.SmartDashboardSub;
import org.usfirst.frc.team2832.robot.subsystems.Vision;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	//do not initialize classes here, if they use NetworkTables, it will crash the code.
	public static DriveTrain DriveTrain;
	public static SmartDashboardSub SmartDashboard;
	public static Vision Vision;
	public static GyroSub Gyro;
	
	public static XboxController DriveController;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		DriveController = new XboxController(0);
		
		//initialize our subsystems
		DriveTrain = new DriveTrain();
		Vision = new Vision();
		Gyro = new GyroSub();
		SmartDashboard = new SmartDashboardSub();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
     * This function runs before autonomous and should setup auto mode
	 */
	@Override
	public void autonomousInit() {
		Scheduler.getInstance().removeAll();  //safety check, clear out all running commands
		Scheduler.getInstance().add(Robot.SmartDashboard.getAutoCommand());
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		//reset the schedule back to subsystem defaults to prep for teleop mode
		Scheduler.getInstance().removeAll();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
	
	@Override
	public void robotPeriodic() {

	}
}
