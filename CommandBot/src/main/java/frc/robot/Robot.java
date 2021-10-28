// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

    // robot parts
    private VictorSP leftDrive;
    private VictorSP rightDrive;
    private DifferentialDrive drive;
    private XboxController driverCont;
    private ADXRS450_Gyro gyro;
    private Encoder leftEncoder;
    private Encoder rightEncoder;

    // robot features
    private Simulate sim;

    /**
     * This function is run when the robot is first started up and should be used
     * for any initialization code.
     */
    @Override
    public void robotInit() {
        // initialize robot parts and locations where they are
        leftDrive = new VictorSP(1);                //left drive motor is connected to VictorSP on PWM channel 1
        rightDrive = new VictorSP(2);               //right drive motor is connected to VictorSP on PWM channel 2
        drive = new DifferentialDrive(leftDrive, rightDrive);   //combine the 2 motors into a drivetrain
        driverCont = new XboxController(0);         //XboxController plugged into Joystick port 0 on the driver station
        gyro = new ADXRS450_Gyro();                 //Kit Gyro plugged into the SPI port on the top right (traditionally we use a Pigeon IMU on a TalonSRX)
        leftEncoder = new Encoder(1, 2);            //Encoder for the left axle plugged into DIO 1 and 2 (traditionally we use the TalonSRX IO port)
        rightEncoder = new Encoder(3, 4);           //Encoder for the right axle plugged into DIO 1 and 2 (traditionally we use the TalonSRX IO port)
        leftEncoder.setDistancePerPulse(0.01);      //set to 1 meter / x pulses, need to measure on robot
        rightEncoder.setDistancePerPulse(0.01);     //set to 1 meter / x pulses, need to measure on robot

        // initialize robot features
        sim = new Simulate(this, gyro, leftEncoder, rightEncoder, driverCont);
    }

    /**
     * This function is called every robot packet, no matter the mode. Use this for
     * items like diagnostics that you want ran during disabled, autonomous,
     * teleoperated and test.
     */
    @Override
    public void robotPeriodic() {
    }

    /* Where to initialize simulation objects */
    @Override
    public void simulationInit() {
        sim.Init();
    }

    /* where to map simulation physics, like drive commands to encoder counts */
    @Override
    public void simulationPeriodic() {
        sim.Periodic();
    }

    /** This function is called once when autonomous is enabled. */
    @Override
    public void autonomousInit() {
    }

    /** This function is called periodically during autonomous. */
    @Override
    public void autonomousPeriodic() {
    }

    /** This function is called once when teleop is enabled. */
    @Override
    public void teleopInit() {
    }

    /** This function is called periodically during operator control. */
    @Override
    public void teleopPeriodic() {
    }

    /** This function is called once when the robot is disabled. */
    @Override
    public void disabledInit() {
    }

    /** This function is called periodically when disabled. */
    @Override
    public void disabledPeriodic() {
    }

    /** This function is called once when test mode is enabled. */
    @Override
    public void testInit() {
    }

    /** This function is called periodically during test mode. */
    @Override
    public void testPeriodic() {
    }
}
