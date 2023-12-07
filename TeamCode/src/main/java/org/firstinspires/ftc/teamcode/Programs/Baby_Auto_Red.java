package org.firstinspires.ftc.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

public class Baby_Auto_Red extends Auto_Util {
    public void runOpMode() throws InterruptedException {
        initAuto();
        waitForStart();
        setAllDriveMotors(2);
        strafeRight(7);
    }
}
