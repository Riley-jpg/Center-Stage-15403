package org.firstinspires.ftc.teamcode.Programs;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Programs.Auto_Util;
import org.firstinspires.ftc.teamcode.Programs.babyhwmap;


@TeleOp(name="baby teleop", group="Pushbot")
public class babyteleop extends Auto_Util {
    babyhwmap robot=new babyhwmap();

    private ElapsedTime runtime = new ElapsedTime();

    static double turnPower;
    static double fwdBackPower;
    static double strafePower;
    static double lbPower;
    static double lfPower;
    static double rbPower;
    static double rfPower;
    static double slowamount = 1;

    public void runOpMode(){
        robot.init(hardwareMap);
        initAuto();
        telemetry.addData("Status,", "Ready to run");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {

            //Drive

            fwdBackPower = gamepad1.left_stick_y;
            strafePower = gamepad1.left_stick_x;
            turnPower= gamepad1.right_stick_x;

            lfPower = (fwdBackPower - turnPower - strafePower);
            rfPower = (fwdBackPower + turnPower + strafePower);
            lbPower = (fwdBackPower - turnPower + strafePower);
            rbPower = (fwdBackPower + turnPower - strafePower);

            robot.leftfrontDrive.setPower(lfPower*slowamount);
            robot.leftbackDrive.setPower(lbPower*slowamount);
            robot.rightfrontDrive.setPower(rfPower*slowamount);
            robot.rightbackDrive.setPower(rbPower*slowamount);

            if(gamepad1.right_bumper){
                slowamount=0.5;
            }  else{
                slowamount=1;
            }

            if(gamepad1.dpad_up){
                robot.leftfrontDrive.setPower(1);
            }
            if(gamepad1.dpad_right){
                robot.rightfrontDrive.setPower(1);
            }
            if(gamepad1.dpad_down){
                robot.leftbackDrive.setPower(1);
            }
            if(gamepad1.dpad_left){
                robot.rightbackDrive.setPower(1);
            }

        }








    }











}
