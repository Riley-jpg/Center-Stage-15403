package org.firstinspires.ftc.teamcode.Programs;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.Programs.Auto_Util;
import org.firstinspires.ftc.teamcode.Programs.practicehwwmap;

public class projectSERVO  extends Auto_Util {


    CRServo crServo;
    practicehwwmap robot = new practicehwwmap();
    double slowamount = 1;

    @Override

    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);
        initAuto();
        crServo = robot.getTheServo() ;
        waitForStart();
        while (opModeIsActive()) {
            crServo.setPower(gamepad1.left_stick_y);

        }
    }
}