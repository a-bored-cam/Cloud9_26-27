package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;

import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedro.Constants;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;
import com.pedropathing.math.Pose;

@TeleOp(name = "Test TeleOp")
public class ExTeleOp extends OpMode {

    private Follower follower;
    private DcMotor intake;
    private DcMotor flywheel;
    private DcMotor cycler;

    boolean prevFlywheelButtonState = false;
    boolean flywheelRunning = false;

    

    @Override
    public void init() {
        follower = Constants.create(hardwareMap);

        intake = hardwareMap.get(DcMotor.class, "intake");
        flywheel = hardwareMap.get(DcMotor.class, "flywheel");
        cycler = hardwareMap.get(DcMotor.class, "cycler");

        prevFlywheelButtonState = false;
        flywheelRunning = false;
    }

    @Override
    public void loop() {
        if (gamepad1.options) {
            follower.setHeading(0);
            follower.hold(follower.pose());
        }


        if (gamepad1.x && !prevFlywheelButtonState) {
            flywheelRunning = !flywheelRunning;
        }

        prevFlywheelButtonState = gamepad1.x;

        if (flywheelRunning) {
            flywheel.setPower(1.0);
        } else {
            flywheel.setPower(0.0);
        }

        if (gamepad1.left_bumper) {
            // This is running it backwards (which has priority)
            intake.setPower(1.0);
            
        } else if (gamepad1.left_trigger_pressed) {
            // This is running it forwards (trigger)
            intake.setPower(-1.0);

        } else {
            intake.setPower(0.0);
        }
        
        if (gamepad1.right_bumper) {
            // This is running it backwards (which has priority)
            cycler.setPower(1.0);
            
        } else if (gamepad1.right_trigger_pressed) {
            // This is running it forwards (trigger)
            cycler.setPower(-1.0);

        } else {
            cycler.setPower(0.0);
        }



        DrivePowers powers = ManualDrive.fieldCentric(
                -gamepad1.left_stick_y,
                -gamepad1.left_stick_x,
                -gamepad1.right_stick_x,
                follower.pose().heading()
        );

        ManualDrive.driveOrHold(follower, powers);
        follower.update();

        Pose robotPose = follower.pose(); // returns a Pose object

        telemetry.addData("Robot X", robotPose.x());
        telemetry.addData("Robot Y", robotPose.y());
        telemetry.addData("Robot Heading", Math.toDegrees(robotPose.heading())); 
    }
}