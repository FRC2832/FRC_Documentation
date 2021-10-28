package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Drive using a xbox controller joystick
 */
public class DriveStick {
    private XboxController controller;
    private DifferentialDrive drive;

    /**
     * Inject what objects will be controlled
     * @param controller - Xbox Controller to use 
     * @param drive - Drivetrain to control
     */
    public DriveStick(XboxController controller, DifferentialDrive drive) {
        //copy the inputs to our local variables
        this.controller = controller;
        this.drive = drive;
    }

    /**
     * Initialize
     */
    public void Init() {

    }

    /**
     * Drive the robot
     */
    public void Periodic() {
        double speed = -controller.getRawAxis(1);           //get the speed based on left stick up/down
        double turn = -controller.getRawAxis(4) * 0.7;      //get the turn based on right stick left/right
        drive.arcadeDrive(speed, turn);                     //command the drivetrain based on our inputs
    }
}
