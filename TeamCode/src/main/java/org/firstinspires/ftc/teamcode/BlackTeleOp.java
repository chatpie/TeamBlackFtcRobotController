package org.firstinspires.ftc.teamcode;

import org.firstinspires.ftc.teamcode.mechanisms.mecanumDrive;

/*
TO BE IMPLEMENTED:
Mecanum drive, left stick Y moves forwards, left stick X strafes, and right stick X rotates.

*/



@TeleOp(name = "BlackTeleOp")
public class BlackTeleOp extends OpMode {
    // Code to run ONCE when the driver hits INIT
    @Override
    public void init()
    {

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
    }

    //Code to run REPEATEDLY after the driver hits START but before they hit STOP
    @Override
    public void loop()
    {

    }

    // Code to run ONCE after the driver hits STOP
    @Override
    public void stop()
    {

    }
}
