package org.firstinspires.ftc.teamcode.Programs;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.Programs.babyhwmap;


@TeleOp(name="baby teleop", group="Pushbot")
public class babyteleop extends LinearOpMode {
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
    static double open = .77;
    static double closed = .4;

    private boolean changed1 = false;
    private boolean changed3 = false;
    private boolean changed7 = false;

    private boolean changed8 = false;

    private boolean changed9 = false;
    public double intakePower = 0.5;


    public void runOpMode(){
        robot.init(hardwareMap);

        telemetry.addData("Status,", "Ready to run");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {

            //Drive

            fwdBackPower = -gamepad1.left_stick_y * slowamount;
            strafePower = -gamepad1.left_stick_x * slowamount;
            turnPower = -gamepad1.right_stick_x * slowamount;

            lfPower = (fwdBackPower - turnPower - strafePower);
            rfPower = (fwdBackPower + turnPower + strafePower);
            lbPower = (fwdBackPower - turnPower + strafePower);
            rbPower = (fwdBackPower + turnPower - strafePower);


            robot.leftfrontDrive.setPower(lfPower);
            robot.leftbackDrive.setPower(lbPower);
            robot.rightfrontDrive.setPower(rfPower);
            robot.rightbackDrive.setPower(rbPower);

            //intake drive
            robot.Intake.setPower(intakePower);

            //slow mode
            if (gamepad1.right_bumper){
                slowamount = 0.5;}
            else if (gamepad1.left_bumper) {
                slowamount = 0.1;}
            else{
                slowamount = 1;}

            if(gamepad2.right_bumper){
                robot.armMotorOne.setPower(-gamepad2.right_stick_y*.4 - .15);
            } else{
                robot.armMotorOne.setPower(-gamepad2.right_stick_y*.4);
            }
            if(gamepad2.left_bumper) {
                robot.armMotorTwo.setPower(gamepad2.left_stick_y*.7 - .08);
            }else{
                robot.armMotorTwo.setPower(gamepad2.left_stick_y*.7);
            }



            if(gamepad2.a){

                robot.Intake.setPower(1);
            } else if(gamepad2.b){
                robot.Intake.setPower(-1);
            } else{
                robot.Intake.setPower(0);
            }
            if (gamepad1.a && gamepad1.b){
                robot.intakeServo.setPosition(open);
            }else{
                robot.intakeServo.setPosition(closed);
            }

            //FIXXXXXX or check 
            if(gamepad1.x && !changed9){
                resetRuntime();
                if(runtime.seconds() > 0.8) {
                    robot.armServo.setPower(1);
                }
                changed9 = true;
            } else if(!gamepad1.x){
                changed9 = false;
                robot.armServo.setPower(0);
            }



            if(gamepad1.dpad_up && !changed3) {//speed limiter toggle
                if(intakePower == 0.5){
                    intakePower = -1;
                }
                else{
                    intakePower = 0.5;
                }
                changed3 = true;
            } else if(!gamepad1.dpad_up){changed3 = false;}

            if(gamepad1.dpad_right && !changed7) { //CHEETAH toggle
                if(intakePower == -1){
                    intakePower = 0.5;
                }
                else{
                    intakePower = -1;
                }
                changed7 = true;
            } else if(!gamepad1.dpad_right){changed7 = false;}

            if(gamepad1.dpad_down && !changed8) { //CHEETAH toggle
                double save = intakePower;
                if(intakePower == -1 || intakePower == 0.5 ){
                    intakePower = 0;
                }
                else{
                    intakePower = save;
                }
                changed8 = true;
            } else if(!gamepad1.dpad_down){changed8 = false;}


        }
           /* if(gamepad1.a){
                robot.armMotorOne.setPower(1);
            } else if(gamepad1.b){
                robot.armMotorOne.setPower(-1);
            } else{
                robot.armMotorOne.setPower(0);
            }
          if(gamepad1.x){
                robot.armMotorTwo.setPower(1);
            } else if(gamepad1.y){
                robot.armMotorTwo.setPower(-1);

            } else{
              robot.armMotorTwo.setPower(0);
          }*/

           /*while(gamepad1.dpad_up){
                robot.leftfrontDrive.setPower(1);
            }
            while(gamepad1.dpad_left){
                robot.rightfrontDrive.setPower(1);
            }
            while(gamepad1.dpad_down){
                robot.leftbackDrive.setPower(1);
            }
            while(gamepad1.dpad_right){
                robot.rightbackDrive.setPower(1);
            }*/

    }








}