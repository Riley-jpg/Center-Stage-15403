package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Far Park Blue",group="Encoder")
public class Blue_Park_Far extends Auto_Util {
    public void runOpMode() throws InterruptedException {
        initAuto();

        waitForStart();
        encoderDrive(DRIVE_SPEED,-2.5,-2.5,10,0);
        encoderStrafe(STRAFE_SPEED,18,18,10,0);
        encoderDrive(DRIVE_SPEED,-32.5,-32.5,10,0);
        encoderStrafe(STRAFE_SPEED,56,56,10,0);
        encoderDrive(DRIVE_SPEED,1, 1,10,0);

    }
}
