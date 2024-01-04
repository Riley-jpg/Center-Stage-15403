package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Far Park Red",group="Encoder")
public class Red_Park_Far extends Auto_Util {
    public void runOpMode() throws InterruptedException {
        initAuto();

        waitForStart();
        encoderDrive(DRIVE_SPEED,8,8,10,0);
        encoderStrafe(STRAFE_SPEED,16,16,10,0);

    }
}
