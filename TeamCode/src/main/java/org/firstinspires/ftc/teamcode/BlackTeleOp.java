package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.mecanumDrive;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.mechanisms.mecanumDrive;
/*
TO BE IMPLEMENTEDk,:
Mecanum drive, left stick Y moves forwards, left stick X strafes, and right stick X rotates.

*/

@TeleOp(name = "BlackTeleOp")
public class BlackTeleOp extends OpMode {
    // Code to run ONCE when the driver hits INIT
    private DcMotorEx topBarrel;
    private DcMotorEx bottomBarrel;
    private DcMotor intake;
    private CRServo leftIntakeServo;
    private CRServo rightIntakeServo;
    public int LAUNCHER_TARGET_VELOCITY = 437; //2678 RPM
    public int LAUNCHER_MIN_VELOCITY = 437 - 50; //2571 RPM
    double intakePower;
    double forward,strafe, rotate;
    private mecanumDrive drive;


    @Override
    public void init()
    {
        drive = new mecanumDrive();
        drive.init(hardwareMap);

        leftIntakeServo = hardwareMap.get(CRServo.class, "left_intake_servo");
        rightIntakeServo = hardwareMap.get(CRServo.class, "right_intake_servo");
        topBarrel = hardwareMap.get(DcMotorEx.class, "TopBarrel");
        bottomBarrel = hardwareMap.get(DcMotorEx.class, "BottomBarrel");

        topBarrel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        bottomBarrel.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        topBarrel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));
        bottomBarrel.setPIDFCoefficients(DcMotor.RunMode.RUN_USING_ENCODER, new PIDFCoefficients(40, 0, 0, 12.5));

        rightIntakeServo.setDirection(DcMotorSimple.Direction.REVERSE);

        telemetry.addData("========", "INITIALIZED========");
    }

    // Code to run REPEATEDLY after the driver hits INIT, but before they hit START
    @Override
    public void init_loop()
    {

    }

    //Code to run ONCE when the driver hits START
    @Override
    public void start()
    {
        drive.resetYaw();
        drive.setZeroPowerBehaviors();

        telemetry.addData("========", "RUNNING========");

    }

    //Code to run REPEATEDLY after the driver hits START but before they hit STOP
    @Override
    public void loop()
    {
        forward = -gamepad1.left_stick_y;
        strafe = gamepad1.left_stick_x;
        rotate = gamepad1.right_stick_x;
        drive.driveField(forward,strafe,rotate);

        updateTargetVelocity();
        launch();

        if (gamepad1.left_bumper)
        {
            intakePower = 1 - gamepad1.left_trigger;
        }
        else
        {
            intakePower = -gamepad1.left_trigger;
        }

        leftIntakeServo.setPower(-intakePower);
        rightIntakeServo.setPower(intakePower);

        if (gamepad1.b)
        {
            drive.resetIMUYaw();
        }

        telemetry.addData("Top Barrel Velocity: ", topBarrel.getVelocity());
        telemetry.addData("Bottom Barrel Velocity: ", bottomBarrel.getVelocity());

    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop()
    {

    }
    void launch() {
        if (gamepad1.right_bumper) {
            topBarrel.setVelocity(LAUNCHER_TARGET_VELOCITY);
        }
        else if (gamepad1.right_trigger_pressed){
            bottomBarrel.setVelocity(LAUNCHER_TARGET_VELOCITY);
        } else {
            bottomBarrel.setVelocity(0);
            topBarrel.setVelocity(0);
        }
    }

    public void updateTargetVelocity()
    {
        if (gamepad1.x)
        {
            LAUNCHER_MIN_VELOCITY -= 10;
            LAUNCHER_TARGET_VELOCITY -= 10;

            if (LAUNCHER_MIN_VELOCITY < 0)
            {
                LAUNCHER_MIN_VELOCITY = 0;
                LAUNCHER_TARGET_VELOCITY = 50;
            }
        }

        if (gamepad1.y)
        {
            LAUNCHER_MIN_VELOCITY += 10;
            LAUNCHER_TARGET_VELOCITY += 10;

            if (LAUNCHER_TARGET_VELOCITY > 1250)
            {
                LAUNCHER_MIN_VELOCITY = 1250;
                LAUNCHER_TARGET_VELOCITY = 1200;
            }
        }
    }
}
