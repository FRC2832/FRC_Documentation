package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Sequence to drive the robot in an S-Turn for autonomous. Steps are: Drive
 * forward 2m, 90* right, forward 2m, 90* left, and forward 1m
 */
public class AutoSTurn {
    //constants that say what state the auto is currently in
    final int DRIVE_FORWARD = 1;
    final int TURN = 2;
    final int DRIVE_FORWARD2 = 3;
    final int TURN2 = 4;
    final int DRIVE_FORWARD3 = 5;
    final int END = 6;

    private Timer timer;
    private int autoState;
    private DifferentialDrive drive;

    /**
     * Inject the drivetain for us to control
     * @param drive Drivetrain to control
     */
    public AutoSTurn(DifferentialDrive drive) {
        this.drive = drive;
    }
    /**
     * Initialize the autonomous sequence
     */
    public void Init() {
        //create a timer and start it
        timer = new Timer();
        timer.reset();
        timer.start();

        //initialize the state machine state
        autoState = DRIVE_FORWARD;
    }

    public void Periodic() {
        //default the outputs
        double speed = 0.0;
        double turn = 0.0;

        //check which state we are in to know what to command
        if (autoState == DRIVE_FORWARD) {
            // drive forward 2 seconds
            if (timer.hasElapsed(2) == false) {
                speed = 0.5;
            } else {
                autoState = TURN;
                timer.reset();
                timer.start();
            }
        } else if (autoState == TURN) {
            // turn 90* right
            if (timer.hasElapsed(1) == false) {
                turn = -0.322;
            } else {
                autoState = DRIVE_FORWARD2;
                timer.reset();
                timer.start();
            }
        } else if (autoState == DRIVE_FORWARD2) {
            // drive forward 2 seconds
            if (timer.hasElapsed(2) == false) {
                speed = 0.5;
            } else {
                autoState = TURN2;
                timer.reset();
                timer.start();
            }
        } else if (autoState == TURN2) {
            // turn 90* left
            if (timer.hasElapsed(1) == false) {
                turn = 0.322;
            } else {
                autoState = DRIVE_FORWARD3;
                timer.reset();
                timer.start();
            }
        } else if (autoState == DRIVE_FORWARD3) {
            // drive forward 1 seconds
            if (timer.hasElapsed(1) == false) {
                speed = 0.5;
            } else {
                autoState = END;
                timer.reset();
                timer.start();
            }
        } else {
            //at END or unknown state, do nothing
        }

        //command the drivetrain
        drive.arcadeDrive(speed, turn);

        //output debug variables
        SmartDashboard.putNumber("AutoSTurn/State", autoState);
        SmartDashboard.putNumber("AutoSTurn/Timer", timer.get());
    }
}
