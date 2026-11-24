package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.mechanisms.AprilTagWebcam;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;

import java.util.ArrayList;
import java.util.Vector;

@Autonomous
public class AprilTagWebcamTest extends OpMode {
    AprilTagWebcam aprilTagWebcam = new AprilTagWebcam();

    @Override
    public void init()
    {
        aprilTagWebcam.init(hardwareMap, telemetry);
    }

    @Override
    public void loop()
    {
        aprilTagWebcam.update();
        AprilTagDetection id4 = aprilTagWebcam.getTagBySpecificId(4);
        aprilTagWebcam.displayDetectionTelemetry(id4);
    }

    public double getRotationFromAprilTags(Integer[][] coordinates)
    {
        //{{x,y},{x,y},{x,y},{x,y}}
        // {x, x, x, x}


        // get the median point
         Integer[] median = {(coordinates[0][0] + coordinates[1][0] + coordinates[2][0] +
                 coordinates[3][0]) / 4, (coordinates[0][1] + coordinates[1][1] + coordinates[2][1] +
                 coordinates[3][1]) / 4};

        if (median[0] - 960 / 2 <= 50 && median[0] - 960 / 2 >= -50)
        {
            return 0;
        }

        else if (median[0] - 960/2 < -50 && median[0] - 960/2 >= -70)
        {
            return -0.25;
        }

        else if (median[0] - 960/2 < -70 && median[0] - 960/2 >= -100)
        {
            return -0.5;
        }

        else if (median[0] - 960/2 < -100 && median[0] - 960/2 >= -125)
        {
            return -0.75;
        }

        else if (median[0] < 960/2)
        {
            return -1;
        }

        else if (median[0] - 960/2 <= 70 && median[0] - 960/2 > 50)
        {
            return 0.25;
        }

        else if (median[0] - 960/2 <= 100 && median[0] - 960/2 > 70)
        {
            return 0.5;
        }

        else if (median [0] - 960/2 <= 125 && median[0] - 960/2 > 100)
        {
            return 0.75;
        }

        else
        {
            return 1;
        }

    }
}
