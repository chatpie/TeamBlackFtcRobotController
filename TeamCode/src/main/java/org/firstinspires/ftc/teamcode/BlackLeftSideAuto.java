package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "BlackLeftSideAuto")
public class BlackLeftSideAuto extends LinearOpMode
{

    private DcMotor backLeftDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor frontLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private ElapsedTime runtime = new ElapsedTime();
    static final double DRIVE_SPEED = 0.6;
    static final double TURN_SPEED  = 0.5;
    static final double COUNTS_PER_MOTOR_REV  = 537.7 ;   // check your motor vendor's page
    static final double DRIVE_GEAR_REDUCTION  = 1.0 ;    // 2.0 for a 12 tooth gear driving a 24 tooth gear
    static final double WHEEL_DIAMETER_INCHES = 104/25.4;
    static final double COUNTS_PER_INCH       = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    @Override
    public void runOpMode() throws InterruptedException
    {
        backLeftDrive = hardwareMap.get(DcMotor.class, "motorBL");
        backRightDrive = hardwareMap.get(DcMotor.class, "motorBR");
        frontLeftDrive = hardwareMap.get(DcMotor.class, "motorFL");
        frontRightDrive = hardwareMap.get(DcMotor.class, "motorFR");

        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        //frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);

        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);

        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData("Starting at",  "%7d :%7d :%7d :%7d",
                backLeftDrive.getCurrentPosition(),
                backRightDrive.getCurrentPosition(),
                frontRightDrive.getCurrentPosition(),
                frontLeftDrive.getCurrentPosition());
        telemetry.update();

        waitForStart();
        encoderDrive(DRIVE_SPEED, 5, 5, 0.22);
        

        telemetry.addData("Path", "Complete");
        telemetry.update();
        sleep(500);
    }

    private void encoderDrive(double speed, double leftInches, double rightInches, double timeoutS)
    {
        int newFrontLeftTarget;
        int newFrontRightTarget;
        int newBackLeftTarget;
        int newBackRightTarget;

        if (opModeIsActive())
        {
            newBackLeftTarget = backLeftDrive.getCurrentPosition() + (int)(leftInches*COUNTS_PER_INCH);
            newFrontLeftTarget = frontLeftDrive.getCurrentPosition() + (int)(leftInches*COUNTS_PER_INCH);
            newBackRightTarget = backRightDrive.getCurrentPosition() + (int)(rightInches* COUNTS_PER_INCH);
            newFrontRightTarget = frontRightDrive.getCurrentPosition() + (int)(rightInches*COUNTS_PER_INCH);
            backLeftDrive.setTargetPosition(newBackLeftTarget);
            frontLeftDrive.setTargetPosition(newFrontLeftTarget);
            backRightDrive.setTargetPosition(newBackRightTarget);
            frontRightDrive.setTargetPosition(newFrontRightTarget);

            backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            runtime.reset();
            backLeftDrive.setPower(Math.abs(speed));
            frontLeftDrive.setPower(Math.abs(speed));
            backRightDrive.setPower(Math.abs(speed));
            frontRightDrive.setPower(Math.abs(speed));

            while (opModeIsActive() &&
                    (runtime.seconds() < timeoutS) &&
                    (backLeftDrive.isBusy() && frontLeftDrive.isBusy() && backRightDrive.isBusy()
                    && frontRightDrive.isBusy())) {

                telemetry.addData("Running to",  " %7d :%7d :%7d :%7d", newBackLeftTarget,  newFrontLeftTarget,
                        newBackRightTarget, newFrontRightTarget);
                telemetry.addData("Currently at",  " at %7d :%7d :%7d :%7d",
                        backLeftDrive.getCurrentPosition(), frontLeftDrive.getCurrentPosition(), backRightDrive.getCurrentPosition(),
                        frontRightDrive.getCurrentPosition());
                telemetry.update();
            }

            backLeftDrive.setPower(0);
            frontLeftDrive.setPower(0);
            backRightDrive.setPower(0);
            frontRightDrive.setPower(0);

            backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            backLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            sleep(250);
        }
    }
}
