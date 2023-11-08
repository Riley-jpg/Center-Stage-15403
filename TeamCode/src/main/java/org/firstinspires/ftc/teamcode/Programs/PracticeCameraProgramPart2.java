package org.firstinspires.ftc.teamcode.Programs;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.ExposureControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.FocusControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.GainControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.PtzControl;
import org.firstinspires.ftc.robotcore.external.hardware.camera.controls.WhiteBalanceControl;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagPoseFtc;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;
import org.firstinspires.ftc.vision.tfod.TfodProcessor;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Size;
import com.qualcomm.hardware.bosch.BNO055IMU;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.*;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.ReadWriteFile;
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
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.internal.collections.EvictingBlockingQueue;
import org.firstinspires.ftc.robotcore.internal.system.AppUtil;
import org.firstinspires.ftc.teamcode.odometry.OdometryGlobalCoordinatePosition;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PracticeCameraProgramPart2 extends LinearOpMode {
    /*
    ___________________________________________________________________________________________________________________________________
    -Basic Vision Variables
    ___________________________________________________________________________________________________________________________________
     */
    HardwareMap hardwareMap;
    WebcamName camera;
    /*
    ___________________________________________________________________________________________________________________________________
    -VisionPortal Variables
    ___________________________________________________________________________________________________________________________________
     */
    VisionPortal.Builder visionPortalBuilder;
    VisionPortal visionPortal;
    /*
    ___________________________________________________________________________________________________________________________________
    -TensorFlow Variables
    ___________________________________________________________________________________________________________________________________
     */
    /*
    ___________________________________________________________________________________________________________________________________
    -AprilTag Variables
    ___________________________________________________________________________________________________________________________________
     */
    /*
    ___________________________________________________________________________________________________________________________________
    -Exposure and Gain Variables
    ___________________________________________________________________________________________________________________________________
     */
    /*
    ___________________________________________________________________________________________________________________________________
    -White Balance Variables
    ___________________________________________________________________________________________________________________________________
     */
    /*
    ___________________________________________________________________________________________________________________________________
    -Focus Control Variables
    ___________________________________________________________________________________________________________________________________
     */
    /*
    ___________________________________________________________________________________________________________________________________
    -Pan-Tilt-Zoom Variables
    ___________________________________________________________________________________________________________________________________
     */
    public void PracticeCameraStuffSetUp(int gain, int temp, PtzControl.PanTiltHolder panTiltHolder,int zoom){
        /*
        Basic Set-up of Vision Variables
        */

        hardwareMap = null;
        camera = hardwareMap.get(WebcamName.class,"ToBeAdded");
        //Create vision portal builder
        visionPortalBuilder = new VisionPortal.Builder();

        //Configure camera for builder
        visionPortalBuilder.setCamera(hardwareMap.get(WebcamName.class,"Webcam 1"));
        visionPortalBuilder.setCameraResolution(new Size(640,480));
        visionPortalBuilder.setStreamFormat(VisionPortal.StreamFormat.YUY2);

        //Configure LiveView
        visionPortalBuilder.enableLiveView(true);
        visionPortalBuilder.setAutoStopLiveView(true);

        //Add processors to builder
        //visionPortalBuilder.addProcessor();

        //Build vision portal
        visionPortal = visionPortalBuilder.build();


    }
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("you shouldn't be here!", "This program isnt meant to be run, only for use with all of its methods");
        telemetry.update();
    }
}
