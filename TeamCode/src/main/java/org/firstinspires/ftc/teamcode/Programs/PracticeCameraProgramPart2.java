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
    float fps;
    /*
    ___________________________________________________________________________________________________________________________________
    -VisionPortal Variables
    ___________________________________________________________________________________________________________________________________
     */
    VisionPortal.Builder visionPortalBuilder;
    VisionPortal visionPortal;
    VisionPortal.CameraState cameraState;
    /*
    ___________________________________________________________________________________________________________________________________
    -TensorFlow Variables
    ___________________________________________________________________________________________________________________________________
     */
    TfodProcessor tfodProcessor;
    VisionPortal tfodVisionPortal;
    /*
    ___________________________________________________________________________________________________________________________________
    -AprilTag Variables
    ___________________________________________________________________________________________________________________________________
     */
    AprilTagProcessor aprilTagProcessor;
    VisionPortal aprilTagVisionPortal;

    /*NAVIGATION WITH APRILTAGS*/

    //Tag pose relative to camera
    double range = 0d;
    double bearing = 0d;
    double yaw = 0d;
    /*
    ___________________________________________________________________________________________________________________________________
    -Exposure & Gain Control Variables
    ___________________________________________________________________________________________________________________________________
     */
    ExposureControl exposureControl;
    GainControl gainControl;
    boolean exposureSupported;
    boolean manualSupported;
    long minExposure;
    long maxExposure;
    int minGain;
    int maxGain;

    /*
    ___________________________________________________________________________________________________________________________________
    -White Balance Variables
    ___________________________________________________________________________________________________________________________________
     */
    WhiteBalanceControl whiteBalanceControl;
    int minTemperature;
    int maxTemperature;
    /*
    ___________________________________________________________________________________________________________________________________
    -Focus Control Variables
    ___________________________________________________________________________________________________________________________________
     */
    FocusControl focusControl;
    boolean focusSupported;
    boolean fixedSupported;
    double minFocusLength;
    double maxFocusLength;

    /*
    ___________________________________________________________________________________________________________________________________
    -Pan-Tilt-Zoom(PTZ) Variables
    ___________________________________________________________________________________________________________________________________
     */
    PtzControl ptzControl;
    PtzControl.PanTiltHolder minPanTilt;
    PtzControl.PanTiltHolder maxPanTilt;
    int minZoom;
    int maxZoom;
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

        //Get camera state (eg. ready, streaming, closed, etc)
        cameraState = visionPortal.getCameraState();//Could be in both set-up and run methods!!!

        /*
        Get Camera Controls
        */
        exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        gainControl = visionPortal.getCameraControl(GainControl.class);
        whiteBalanceControl = visionPortal.getCameraControl(WhiteBalanceControl.class);
        focusControl = visionPortal.getCameraControl(FocusControl.class);
        ptzControl = visionPortal.getCameraControl(PtzControl.class);

        //Get current frame rate to estimate CPU load
        fps = visionPortal.getFps();

        /*
        TensorFlow Processor Builder
        */

    }
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("you shouldn't be here!", "This program isnt meant to be run, only for use with all of its methods");

        //Save next frame captured by camera to "/sdcard/VisionPortal-myImage.png"
        visionPortal.saveNextFrameRaw("myImage");


        telemetry.update();
    }
    /*
    ___________________________________________________________________________________________________________________________________
    -Extra Code that might be useful
    ___________________________________________________________________________________________________________________________________
     */
    /*

    //Change to different camera
    visionPortal.setActiveCamera(hardwareMap.get(WebcamName.class,"Webcam 2"));

    //Disable features to manage CPU load
        visionPortal.stopLiveView();
        visionPortal.setProcessorEnabled(tfodProcessor,false);//set to separate visionProcessor!!!
        visionPortal.stopStreaming();

    //Close VisionPortal to stop everything
        visionPortal.close();

    */
}
