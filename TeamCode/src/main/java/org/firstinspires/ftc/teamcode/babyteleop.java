package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;



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
    static double slowamount ;

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
            turnPower = gamepad1.right_stick_x;

            lfPower = (fwdBackPower - turnPower - strafePower) * slowamount;
            rfPower = (fwdBackPower + turnPower + strafePower) * slowamount;
            lbPower = (fwdBackPower - turnPower + strafePower) * slowamount;
            rbPower = (fwdBackPower + turnPower - strafePower) * slowamount;

            robot.leftfrontDrive.setPower(lfPower);
            robot.leftbackDrive.setPower(lbPower);
            robot.rightfrontDrive.setPower(rfPower);
            robot.rightbackDrive.setPower(rbPower);

            if (gamepad1.right_bumper){
                slowamount = 0.5;
            } else if(gamepad1.left_bumper) {
                slowamount = 0.1;
            }else{
                slowamount = 1;
            }
            telemetry.addData("LFpwr", lfPower);
            telemetry.addData("gamepad left stick x", gamepad1.left_stick_x);
            telemetry.addData("gamepad left stick y", gamepad1.left_stick_y);

         /*   if(gamepad1.dpad_up){
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
            }*/

        }








    }











}
