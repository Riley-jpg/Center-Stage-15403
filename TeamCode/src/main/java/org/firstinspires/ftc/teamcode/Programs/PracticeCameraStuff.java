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

public class PracticeCameraStuff extends LinearOpMode{
    /*
    ___________________________________________________________________________________________________________________________________
    -Basic Set-Up of Vision Variables
    ___________________________________________________________________________________________________________________________________
     */
    HardwareMap hardwareMap = null;
    WebcamName camera = hardwareMap.get(WebcamName.class,"ToBeAdded");
    /*
    ___________________________________________________________________________________________________________________________________
    -TensorFlow Variables
    ___________________________________________________________________________________________________________________________________
     */
    //Create TensorFlow processor
    TfodProcessor tfodProcessor = TfodProcessor.easyCreateWithDefaults();
    //Create VisionPortal with TensorFlow processor
    VisionPortal visionPortal = VisionPortal.easyCreateWithDefaults(camera, tfodProcessor);
    /*
    ___________________________________________________________________________________________________________________________________
    -AprilTag Variables
    ___________________________________________________________________________________________________________________________________
     */
    //Create AprilTag processor
    AprilTagProcessor aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();

    //Create VisionPortal with AprilTag processor
    VisionPortal visionPortal2 = VisionPortal.easyCreateWithDefaults(camera,aprilTagProcessor);

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
    //Get exposure and gain control
    ExposureControl exposureControl = visionPortal.getCameraControl(ExposureControl.class);
    GainControl gainControl = visionPortal.getCameraControl(GainControl.class);

    //Check if control and manual mode are supported by this camera
    boolean exposureSupported = exposureControl.isExposureSupported();
    boolean manualSupported = exposureControl.isModeSupported(ExposureControl.Mode.Manual);

    //Check minimum and maximum exposure and gain
    long minExposure = exposureControl.getMinExposure(TimeUnit.MILLISECONDS);
    long maxExposure = exposureControl.getMaxExposure(TimeUnit.MILLISECONDS);
    int minGain = gainControl.getMinGain();
    int maxGain = gainControl.getMaxGain();

    /*
    ___________________________________________________________________________________________________________________________________
    -White Balance Variables
    ___________________________________________________________________________________________________________________________________
     */
    //Get white balance control
    WhiteBalanceControl whiteBalanceControl = visionPortal.getCameraControl(WhiteBalanceControl.class);

    //White balance control methods
    int minTemperature = whiteBalanceControl.getMinWhiteBalanceTemperature();
    int maxTemperature = whiteBalanceControl.getMaxWhiteBalanceTemperature();
    /*
    ___________________________________________________________________________________________________________________________________
    -Focus Control Variables
    ___________________________________________________________________________________________________________________________________
     */
    //Get focus control
    FocusControl focusControl = visionPortal.getCameraControl(FocusControl.class);

    //Check if control and fixed mode are supported by this camera
    boolean focusSupported = focusControl.isFocusLengthSupported();
    boolean fixedSupported = focusControl.isModeSupported(FocusControl.Mode.Fixed);

    //CHeck minimum and maximum focus length
    double minFocusLength = focusControl.getMinFocusLength();
    double maxFocusLength = focusControl.getMaxFocusLength();

    /*
    ___________________________________________________________________________________________________________________________________
    -Pan-Tilt-Zoom(PTZ) Variables
    ___________________________________________________________________________________________________________________________________
     */
    //Get PTZ control
    PtzControl ptzControl  = visionPortal.getCameraControl(PtzControl.class);

    //Check minimum and maximum pan, tilt, and zoom
    PtzControl.PanTiltHolder minPanTilt = ptzControl.getMinPanTilt();
    PtzControl.PanTiltHolder maxPanTilt = ptzControl.getMaxPanTilt();
    int minZoom = ptzControl.getMinZoom();
    int maxZoom = ptzControl.getMaxZoom();
    public void PracticeCameraStuffSetUp(int gain, int temp, PtzControl.PanTiltHolder panTiltHolder,int zoom){
        //Set exposure mode to manual (also sets manual gain)
        exposureControl.setMode(ExposureControl.Mode.Manual);

        //Set exposure and gain values
        exposureControl.setExposure(0l,TimeUnit.MILLISECONDS);
        gainControl.setGain(1);//needs an int gain!!!

        //Set white balance mod to manual
        whiteBalanceControl.setMode(WhiteBalanceControl.Mode.MANUAL);

        //Set Temperature
        whiteBalanceControl.setWhiteBalanceTemperature(1);//needs and int temp!!!

        //Set focus mode to fixed
        focusControl.setMode(FocusControl.Mode.Fixed);

        //Set focus length
        //focusControl.setFocuslength(focusLength);

        //Set pan, tilt, and zoom
        ptzControl.setPanTilt(panTiltHolder);
        ptzControl.setZoom(zoom);
    }
    @Override
    public void runOpMode() throws InterruptedException {
        telemetry.addData("you shouldn't be here!", "This program isnt meant to be run, only for use with all of its methods");
    /*
    ___________________________________________________________________________________________________________________________________
    -VisionPortal OpMode Management
    ___________________________________________________________________________________________________________________________________
     */
        //Get current frame rate to estimate CPU load
        float fps = visionPortal.getFps();

        //Disable features to manage CPU load
        //visionPortal.setProcessorEnabled(visionProcessor,false);
        visionPortal.stopLiveView();
        visionPortal.stopStreaming();

        //Close VisionPortal to stop everything
        visionPortal.close();
    /*
    ___________________________________________________________________________________________________________________________________
    -TensorFlow Management
    ___________________________________________________________________________________________________________________________________
     */
        //Get recognized objects from TensorFlow
        List<Recognition> recognitions = tfodProcessor.getRecognitions();

        //Iterate through each recognized object in the list
        for (Recognition recognition : recognitions) {
            //Get label of this recognized object
            String label = recognition.getLabel();

            //Get confidence of this recognized object
            float confidence = recognition.getConfidence();

            //Add this label and confidence to the telemetry
            telemetry.addLine("Recognized object" + label);
            telemetry.addLine("Recognized confidence" + confidence);

        }
    /*
    ___________________________________________________________________________________________________________________________________
    -AprilTag Management
    ___________________________________________________________________________________________________________________________________
     */
        //Get dectected tags from AprilTag processor
        List<AprilTagDetection> detections = aprilTagProcessor.getDetections();
        //Iterate through each detected tag in the list
        for (AprilTagDetection detection : detections) {

            //Get tag ID number
            int id = detection.id;

            //Get pose information of this tag
            AprilTagPoseFtc aprilTagPoseFtc = detection.ftcPose;
            telemetry.addLine("Detected tag ID: " + id);
            telemetry.addLine("Distance to tag: " + aprilTagPoseFtc.range);
            telemetry.addLine("Bearing to tag: " + aprilTagPoseFtc.bearing);
            telemetry.addLine("Angle of tag: " + aprilTagPoseFtc.yaw);

            //Tag pose relative to camera
            range = aprilTagPoseFtc.range;
            bearing = aprilTagPoseFtc.bearing;
            yaw = aprilTagPoseFtc.yaw;
            /*
            * STRATEGY
            * Reduce Bearing to Zero by Turning
            * Reduce Yaw to Zero by Driving Sideways
            * */
        }
        telemetry.update();
    }


}
