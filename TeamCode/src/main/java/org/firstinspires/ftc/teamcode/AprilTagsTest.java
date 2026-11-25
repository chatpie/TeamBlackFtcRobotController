package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;



@Autonomous(name = "AprilTagsTest")
public class AprilTagsTest extends OpMode {
    AprilTags aprilTags;

    @Override
    public void init()
    {
        aprilTags.init(hardwareMap, telemetry);
    }

    @Override
    public void loop()
    {
        aprilTags.update();
    }

}
