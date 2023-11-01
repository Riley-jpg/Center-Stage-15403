package org.firstinspires.ftc.teamcode;
import android.graphics.Bitmap;
import android.os.Handler;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.Camera;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraCaptureSession;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
//import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;


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

    public void runOpMode(){
        robot.init(hardwareMap);
        telemetry.addData("Status,", "Ready to run");
        telemetry.update();
        waitForStart();

        while(opModeIsActive()) {

            //Drive

            fwdBackPower = gamepad1.left_stick_y * slowamount;
            strafePower = gamepad1.left_stick_x * slowamount;
            turnPower = gamepad1.right_stick_x * slowamount;

            lfPower = (fwdBackPower - turnPower - strafePower);
            rfPower = (fwdBackPower + turnPower + strafePower);
            lbPower = (fwdBackPower - turnPower + strafePower);
            rbPower = (fwdBackPower + turnPower - strafePower);


            robot.leftfrontDrive.setPower(lfPower);
            robot.leftbackDrive.setPower(lbPower);
            robot.rightfrontDrive.setPower(rfPower);
            robot.rightbackDrive.setPower(rbPower);

            if (gamepad1.right_bumper){
                slowamount = 0.5;}
            else if (gamepad1.left_bumper) {
                slowamount = 0.1;}
            else{
                slowamount = 1;}
            }

            telemetry.addData("LFpwr", lfPower);
            telemetry.addData("gamepad left stick x", gamepad1.left_stick_x);
            telemetry.addData("gamepad left stick y", gamepad1.left_stick_y);

           while(gamepad1.a){
                robot.leftfrontDrive.setPower(1);
            }
            while(gamepad1.b){
                robot.rightfrontDrive.setPower(1);
            }
            while(gamepad1.x){
                robot.leftbackDrive.setPower(1);
            }
            while(gamepad1.y){
                robot.rightbackDrive.setPower(1);
            }

        }








    }
