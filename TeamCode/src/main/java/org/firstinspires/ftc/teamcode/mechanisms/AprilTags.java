package org.firstinspires.ftc.teamcode.mechanisms;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes.FiducialResult;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;

import java.util.ArrayList;
import java.util.List;

public class AprilTags {
    private int[][] coordinates = new int[4][2];
    private LimeLight3A limelight;
    private Telemetry telemetry;
    private int[] redTags = {30, 31, 32, 33, 34, 35, 36, 37};
    private int[] blueTags = {38, 39, 40, 41, 42, 43, 44, 45};
    private String allianceColor = "Blue";

    public void init(HardwareMap hwMap, Telemetry telemetry)
    {
        this.telemetry = telemetry;

        limelight = hardwareMap.get(LimeLight3A.class, "limelight");
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public void update()
    {
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            List<FiducialResult> fiducials = result.getFiducialResults();

            for (FiducialResult tag : fiducials)
            {
                telemetry.addData("a detected tag: ", tag.getFiducialId());
            }
        }
    }


    public void setAllianceColor(String color)
    {
        allianceColor = color;
    }

    public boolean isRed(int tag)
    {
        for (num : redTags)
        {
            if (num == tag)
            {
                return true;
            }
        }

        return false;
    }

    public boolean isBlue(int tag)
    {
        for (num : blueTags)
        {
            if (num == tag)
            {
                return true;
            }
        }

        return false;
    }

    public double getRotationFromAprilTags(Integer[][] coordinates)
    {
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
