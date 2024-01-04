package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Auto_Util;

@Autonomous(name = "Close Park Blue", group = "Encoder")
public class Blue_Park_Close extends Auto_Util {
    public void runOpMode() throws InterruptedException {
        initAuto();

        waitForStart();
        encoderStrafe(STRAFE_SPEED, 37, 37, 10, 0);
        encoderStrafe(1,-7,-7,10,0);

    }
}
