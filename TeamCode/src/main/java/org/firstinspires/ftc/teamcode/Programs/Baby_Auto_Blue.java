package org.firstinspires.ftc.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

/*
Basic Drive Test Left Side of the Field for Autonomous
 */
@Autonomous(name="Baby Auto Blue",group="Left")
public class Baby_Auto_Blue extends Auto_Util {
    public void runOpMode() throws InterruptedException {
        initAuto();
        waitForStart();
        setAllDriveMotors(2);
        strafeLeft(7);
    }
}