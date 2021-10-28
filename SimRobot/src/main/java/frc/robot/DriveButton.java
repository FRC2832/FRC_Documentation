package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Drive using a xbox controller buttons
 */
public class DriveButton {
    private XboxController controller;
    private DifferentialDrive drive;

    /**
     * Inject what objects will be controlled
     * 
     * @param controller - Xbox Controller to use
     * @param drive      - Drivetrain to control
     */
    public DriveButton(XboxController controller, DifferentialDrive drive) {
        // copy the inputs to our local variables
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
        double turn = 0;        //make a variable to store how much we want to turn
        double speed = 0;       //make a variable to store how fast we want to go

        if (controller.getAButton() == true) {
            //go 50% speed forward
            speed = 0.5;
        } else if (controller.getBButton() == true) {
            //go 50% backwards
            speed = -0.5;
        } else {
            //no button pressed, don't go anywhere
            speed = 0;
        }
        //no matter what path we take, we want only 1 command to the drivetrain,
        //and we want to make sure it happens every loop
        drive.arcadeDrive(speed, turn);

        //output our results on the Dashboard
        SmartDashboard.putNumber("Drive Speed", speed);
        SmartDashboard.putNumber("Drive Turn", turn);
    }
}
