package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.util.ElapsedTime;

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