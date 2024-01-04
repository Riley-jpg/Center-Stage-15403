package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Close Park Red",group="Encoder")
public class Red_Park_Close extends Auto_Util {
    public void runOpMode() throws InterruptedException {
        initAuto();

        waitForStart();
        encoderStrafe(STRAFE_SPEED,-37,-37,10,0);
        encoderStrafe(1,7,7,10,0);

    }
}
