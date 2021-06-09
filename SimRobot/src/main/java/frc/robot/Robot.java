// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.simulation.ADXRS450_GyroSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.wpilibj.simulation.EncoderSim;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotGearing;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotMotor;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim.KitbotWheelSize;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //robot parts
  public VictorSP leftDrive;
  public VictorSP rightDrive;
  public DifferentialDrive drive;
  public XboxController driverCont;
  public ADXRS450_Gyro gyro;
  public Encoder leftEncoder;
  public Encoder rightEncoder;

  //kinetic parts
  public Field2d field;
  public DifferentialDriveOdometry odometry;

  //simulation parts
  public XboxControllerSim driverContSim;
  public DifferentialDrivetrainSim driveSim;
  public ADXRS450_GyroSim gyroSim;
  public EncoderSim leftEncoderSim;
  public EncoderSim rightEncoderSim;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    leftDrive = new VictorSP(1);
    rightDrive = new VictorSP(2);
    drive = new DifferentialDrive(leftDrive, rightDrive);
    driverCont = new XboxController(0);
    gyro = new ADXRS450_Gyro();
    leftEncoder = new Encoder(1,2);
    rightEncoder = new Encoder(3,4);
    leftEncoder.setDistancePerPulse(0.01); //set to 1 meter / x pulses, need to measure on robot
    rightEncoder.setDistancePerPulse(0.01);

    field = new Field2d();
    SmartDashboard.putData("Field", field);
    SmartDashboard.putBoolean("Reset Position", false);

    odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), new Pose2d(0.0, 0, new Rotation2d()));
    resetRobotPosition();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Get my gyro angle
    var gyroAngle = Rotation2d.fromDegrees(-gyro.getAngle());

    // Update the pose
    odometry.update(gyroAngle, leftEncoder.getDistance(), rightEncoder.getDistance());
    field.setRobotPose(odometry.getPoseMeters());

    //if the driver requests to reset position, do it
    if(SmartDashboard.getBoolean("Reset Position", false) == true) {
      resetRobotPosition();
      SmartDashboard.putBoolean("Reset Position", false);
    }
  }

  //in case the simulation goes crazy, allow the driver to reset the position of the robot back to start
  private void resetRobotPosition() {
    leftEncoder.reset();
    rightEncoder.reset();
    odometry.resetPosition(new Pose2d(0.4, 6.1, gyro.getRotation2d()), gyro.getRotation2d());
    if(isSimulation()) {
      //reset the drivetrain
      if(driveSim!= null) {
        driveSim.setPose(new Pose2d(0, 6, new Rotation2d()));
      }
    }
  }

  /* Where to initialize simulation objects */
  @Override
  public void simulationInit() {
    driverContSim = new XboxControllerSim(driverCont);
    driveSim = DifferentialDrivetrainSim.createKitbotSim(KitbotMotor.kDualCIMPerSide,KitbotGearing.k10p71,KitbotWheelSize.EightInch,null);
    gyroSim = new ADXRS450_GyroSim(gyro);
    leftEncoderSim = new EncoderSim(leftEncoder);
    rightEncoderSim = new EncoderSim(rightEncoder);
  }

  /* where to map simulation physics, like drive commands to encoder counts */
  @Override
  public void simulationPeriodic() {
    final double RATE = 0.02;  //rate we run at, 20ms loop time
    var volt = RobotController.getBatteryVoltage();
    double last;

    //run the drivetrain
    driveSim.setInputs(leftDrive.get()*volt, -rightDrive.get()*volt);
    driveSim.update(RATE);

    //set the gyro based on drivetrain
    last = gyro.getAngle();
    gyroSim.setAngle(driveSim.getHeading().getDegrees());
    gyroSim.setRate((gyro.getAngle() - last)/RATE);

    //set the left encoder based on drivetrain
    leftEncoderSim.setDistance(driveSim.getLeftPositionMeters());
    rightEncoderSim.setDistance(driveSim.getRightPositionMeters());
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    drive.arcadeDrive(0, 0);
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {}

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    var speed = -driverCont.getRawAxis(1);
    var turn = -driverCont.getRawAxis(4) * 0.7;
    drive.arcadeDrive(speed, turn);
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {
  }

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}
}
