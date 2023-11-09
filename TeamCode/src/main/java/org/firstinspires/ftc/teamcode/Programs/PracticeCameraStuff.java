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

public class PracticeCameraStuff extends LinearOpMode{
    /*
    ___________________________________________________________________________________________________________________________________
    -Basic Vision Variables
    ___________________________________________________________________________________________________________________________________
     */
    HardwareMap hardwareMap;
    WebcamName camera;

    /*
    ___________________________________________________________________________________________________________________________________
    -TensorFlow Variables
    ___________________________________________________________________________________________________________________________________
     */
    TfodProcessor tfodProcessor;
    VisionPortal visionPortal;
    /*
    ___________________________________________________________________________________________________________________________________
    -AprilTag Variables
    ___________________________________________________________________________________________________________________________________
     */
    AprilTagProcessor aprilTagProcessor;
    VisionPortal visionPortal2;

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

        /*
        TFOD
        */

        //Create TensorFlow processor
        tfodProcessor = TfodProcessor.easyCreateWithDefaults();
        //Create VisionPortal with TensorFlow processor
        visionPortal = VisionPortal.easyCreateWithDefaults(camera, tfodProcessor,aprilTagProcessor);

        /*
        AprilTags
        */

        //Create AprilTag processor
        aprilTagProcessor = AprilTagProcessor.easyCreateWithDefaults();
        //Create VisionPortal with AprilTag processor
        visionPortal2 = VisionPortal.easyCreateWithDefaults(camera,aprilTagProcessor);

        /*
        Exposure and Gain
        */

        //Get exposure and gain control
        exposureControl = visionPortal.getCameraControl(ExposureControl.class);
        gainControl = visionPortal.getCameraControl(GainControl.class);

        //Check if control and manual mode are supported by this camera
        exposureSupported = exposureControl.isExposureSupported();
        manualSupported = exposureControl.isModeSupported(ExposureControl.Mode.Manual);

        //Check minimum and maximum exposure and gain
        minExposure = exposureControl.getMinExposure(TimeUnit.MILLISECONDS);
        maxExposure = exposureControl.getMaxExposure(TimeUnit.MILLISECONDS);
        minGain = gainControl.getMinGain();
        maxGain = gainControl.getMaxGain();

        //Set exposure mode to manual (also sets manual gain)
        exposureControl.setMode(ExposureControl.Mode.Manual);

        //Set exposure and gain values
        exposureControl.setExposure(0l,TimeUnit.MILLISECONDS);
        gainControl.setGain(1);//needs an int gain!!!

        /*
        White Balance
        */

        //Get white balance control
        whiteBalanceControl = visionPortal.getCameraControl(WhiteBalanceControl.class);

        //White balance control methods
        minTemperature = whiteBalanceControl.getMinWhiteBalanceTemperature();
        maxTemperature = whiteBalanceControl.getMaxWhiteBalanceTemperature();

        //Set white balance mod to manual
        whiteBalanceControl.setMode(WhiteBalanceControl.Mode.MANUAL);

        //Set Temperature
        whiteBalanceControl.setWhiteBalanceTemperature(1);//needs and int temp!!!

        /*
        Focus Control
        */

        //Get focus control
        focusControl = visionPortal.getCameraControl(FocusControl.class);

        //Check if control and fixed mode are supported by this camera
        focusSupported = focusControl.isFocusLengthSupported();
        fixedSupported = focusControl.isModeSupported(FocusControl.Mode.Fixed);
        //Check minimum and maximum focus length
        minFocusLength = focusControl.getMinFocusLength();
        maxFocusLength = focusControl.getMaxFocusLength();

        //Set focus mode to fixed
        focusControl.setMode(FocusControl.Mode.Fixed);

        //Set focus length
        //focusControl.setFocuslength(focusLength);//double foucuslength!!!

        /*
        PTZ
        */

        //Get PTZ control
        ptzControl  = visionPortal.getCameraControl(PtzControl.class);
        //Check minimum and maximum pan, tilt, and zoom
        minPanTilt = ptzControl.getMinPanTilt();
        maxPanTilt = ptzControl.getMaxPanTilt();
        minZoom = ptzControl.getMinZoom();
        maxZoom = ptzControl.getMaxZoom();

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
