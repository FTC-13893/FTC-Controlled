/* Created 2023 @TheRealByteBandit, Lead Programmer and Member of Team Fibonacci.
 *
 * Introductory Information:
 *
 * This file contains the programming for the teleop of an FTC Comp.
 * This is a 'program' that runs in the teleop period of an FTC match.
 * The names of OpModes (Specifically 'teleop') appear on the menu of the FTC Driver Station.
 * When a selection is made from the menu, the corresponding OpMode is executed.
 *
 * This particular OpMode illustrates driving a 4-motor Omni-Directional (or Holonomic) robot.
 * This code will work with either a Mecanum-Drive or an X-Drive train.
 * Both of these drives are illustrated at https://gm0.org/en/latest/docs/robot-design/drivetrains/holonomic.html
 * Note that a Mecanum drive must display an X roller-pattern when viewed from above.
 *
 * Critical Information:
 *
 * Holonomic drives provide the ability for the robot to move in three axes (directions) simultaneously.
 * Each motion axis is controlled by one Joystick axis.
 *
 * 1) Axial:    Driving forward and backward               Left-joystick Forward/Backward
 * 2) Lateral:  Strafing right and left                     Left-joystick Right and Left
 * 3) Yaw:      Rotating Clockwise and counter clockwise    Right-joystick Right and Left
 *
 * This code is written assuming that the right-side motors need to be reversed for the robot to drive forward.
 * When you first test your robot, if it moves backward when you push the left stick forward, then you must flip
 * the direction of all 4 motors (see code below).
 *
 * Use Android Studio to Copy this Class, and Paste it into your team's code folder with a new name.
 * Remove or comment out the @Disabled line to add this OpMode to the Driver Station OpMode list
 */

package com;

import static android.icu.text.Transliterator.FORWARD;
import static android.icu.text.Transliterator.REVERSE;

import com.example.teamcode.annotations.TeleOp;
import com.qualcomm.robotcore.eventloop.FtcRobotController.src.main.java.com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.FtcRobotController.src.main.java.com.qualcomm.robotcore.robocol.Telemetry;
import com.qualcomm.robotcore.eventloop.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "Basic: Omni Linear OpMode", group = "Linear OpMode")
public class OmniOpMode extends LinearOpMode {

    public final ElapsedTime runtime = new ElapsedTime();
    public Servo MainArmServo;
    public Servo ClawServo;
    public Servo DroneLaunchServo;
    public Servo DroneLauncherServo;
    Telemetry telemetry = null;






    public void runOpMode() throws Throwable {
        // Controlling Classes for the Wheel Drives.
        DcMotor leftFrontDrive = hardwareMap.get(DcMotor.class, "Left-Front-Drive");
        DcMotor leftBackDrive = hardwareMap.get(DcMotor.class, "Left-Back-Drive");
        DcMotor rightFrontDrive = hardwareMap.get(DcMotor.class, "Right-Front-Drive");
        DcMotor rightBackDrive = hardwareMap.get(DcMotor.class, "Right-Back-Drive");

        // Controlling Classes for the Arm Servos.
        com.qualcomm.robotcore.eventloop.robotcore.hardware.DcMotor mainArmServoUp = hardwareMap.get(Servo.class, "Main-Arm-Servo-Up");
        com.qualcomm.robotcore.eventloop.robotcore.hardware.DcMotor MainArmServoDown = hardwareMap.get(Servo.class, "Main-Arm-Servo-Down"); // This line is commented out, as it seems incorrect
        com.qualcomm.robotcore.eventloop.robotcore.hardware.DcMotor clawServoLeft = hardwareMap.get(Servo.class, "Claw-Servo-Left");
        com.qualcomm.robotcore.eventloop.robotcore.hardware.DcMotor clawServoRight = hardwareMap.get(Servo.class, "Claw-Servo-Right");

        // Motor Directions
        leftFrontDrive.setPower(REVERSE);
        leftBackDrive.setPower(REVERSE);
        rightFrontDrive.setPower(FORWARD);
        rightBackDrive.setPower(FORWARD);

        mainArmServoUp.setPower(Servo.Direction.FORWARD.ordinal());
        clawServoLeft.setPower(Servo.Direction.REVERSE.ordinal());
        MainArmServoDown.setPower(Servo.Direction.REVERSE.ordinal()); // This line is commented out, as it seems incorrect
        clawServoRight.setPower(Servo.Direction.FORWARD.ordinal());

        // Wait for the game to start (driver presses PLAY)
        double LaunchDroneAction = 0;
        double ClawServoRightMove = 0;
        telemetry.addData("Status", "Initialized", LaunchDroneAction, ClawServoRightMove);
        telemetry.update();

        wait();
        runtime.wait();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()){
        double max;

        // POV Mode uses left joystick to go forward & strafe, and right joystick to rotate.
        double axial = -gamepad1.left_stick_y;  // Note: pushing stick forward gives negative value
        double lateral = gamepad1.left_stick_x;
        double yaw = gamepad1.right_stick_x;

        // Combine the joystick requests for each axis-motion to determine each wheel's power.
        // Set up a variable for each drive wheel to save the power level for telemetry.
        double leftFrontPower = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower = axial - lateral + yaw;
        double rightBackPower = axial + lateral - yaw;

        // Normalize the values so no wheel power exceeds 1.0
        max = (int) Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = (int) Math.max(max, Math.abs(leftBackPower));
        max = (int) Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower /= max;
            rightFrontPower /= max;
            leftBackPower /= max;
            rightBackPower /= max;
        }

        // Send calculated power to wheels
        leftFrontDrive.setPower(leftFrontPower);
        rightFrontDrive.setPower(rightFrontPower);
        leftBackDrive.setPower(leftBackPower);
        rightBackDrive.setPower(rightBackPower);

        // Show the elapsed game time and wheel power.
        LaunchDroneAction = 0;
        ClawServoRightMove = 0;
        telemetry.addData("Status", "Run Time: " + runtime, LaunchDroneAction, ClawServoRightMove);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
        telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
        telemetry.update();

        // ARM & CLAW SECTION
        // Assuming you have these declared somewhere
        axial = 0.0;
        lateral = 0.0;
        yaw = 0.0;

        boolean MainArmServoUpButton = gamepad1.dpad_up;
        boolean MainArmServoDownButton = gamepad1.dpad_down;
        boolean ClawServoRightButton = gamepad1.dpad_right;
        boolean ClawServoLeftButton = gamepad1.dpad_left;

        double MainArmServoUpMove = axial + lateral + yaw;
        double MainArmServoDownMove = axial - lateral - yaw;
        ClawServoRightMove = axial - lateral + yaw;
        double ClawServoLeftMove = axial + lateral - yaw;

        // Normalize the values so no Claw power exceeds 1.0
        max = Math.max(Math.abs(ClawServoLeftMove), Math.abs(ClawServoRightMove));

        double max1 = Math.max(Math.abs(MainArmServoDownMove), Math.abs(MainArmServoUpMove));
        if (max > 1.0) {
            ClawServoLeftMove /= max;
            ClawServoRightMove /= max;
        }
        max = max1;

        // Normalize the values so no Arm power exceeds 1.0
        if (max > 1.0) {
            MainArmServoUpMove /= max;
            MainArmServoDownMove /= max;
        }

        // Update servo positions based on button presses
        if (MainArmServoUpButton) {
            MainArmServo.setPosition(MainArmServo.getPosition() + MainArmServoUpMove);
        } else {
            MainArmServo.setPosition(MainArmServo.getPosition() - MainArmServoDownMove);
        }

        if (MainArmServoDownButton) {
            MainArmServoDown.setPosition((Double) MainArmServoDown.getPosition());
        } else {
            MainArmServoDown.setPosition((Double) MainArmServoDown.getPosition());
        }

        if (ClawServoRightButton) {
            ClawServo.setPosition(ClawServo.getPosition() + ClawServoRightMove);
        } else {
            ClawServo.setPosition(ClawServo.getPosition() - ClawServoRightMove);
        }

        if (ClawServoLeftButton) {
            DroneLaunchServo.setPosition(DroneLaunchServo.getPosition() + ClawServoLeftMove);
        } else {
            DroneLaunchServo.setPosition(DroneLaunchServo.getPosition() - ClawServoLeftMove);
        }

        LaunchDroneAction = 0;
        telemetry.addData("Status", "Run Time: " + runtime, LaunchDroneAction, ClawServoRightMove);
        telemetry.addData("Front left/Right", "%4.2f, %4.2f", MainArmServoUpMove, MainArmServoDownMove);
        telemetry.addData("Back left/Right", "%4.2f, %4.2f", ClawServoLeftMove, ClawServoRightMove);
        telemetry.update();


        // Drone Launcher Button

        boolean LaunchDroneButtonState = gamepad1.right_trigger;

        LaunchDroneAction = axial + lateral + yaw;

        // Normalize the values so no power exceeds 1.0
        double maxLaunchDroneAction = Math.max(Math.abs(LaunchDroneAction), 1.0);

        if (maxLaunchDroneAction > 1) {
            LaunchDroneAction /= maxLaunchDroneAction;
        }

        // Perform some action based on the button press
        if (LaunchDroneButtonState)
            DroneLauncherServo.setPosition(DroneLauncherServo.getPosition());
        else {
            DroneLauncherServo.setPosition(DroneLauncherServo.getPosition());
        }

        telemetry.addData("Status", "Run Time: " + runtime, LaunchDroneAction, ClawServoRightMove);
        telemetry.addData("Launch Drone Action", "%4.2f", LaunchDroneAction, ClawServoRightMove);
        telemetry.update();

        // Perform some action based on the button press
        // Perform the action you want when the button is pressed
        // For example, you might set a motor power, activate a mechanism, etc.

        telemetry.addData("Status", "Run Time: " + runtime, LaunchDroneAction, ClawServoRightMove);
        telemetry.addData("Launch Drone Action", "%4.2f", LaunchDroneAction, ClawServoRightMove);
        telemetry.update();
    }
}}