package org.firstinspires.ftc.teamcode.Programs;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.vision.VisionPortal;
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

public class PracticeCameraStuff {/*
    WebcamName camera = hardwareMap.get(WebcamName.class,"ToBeAdded");
    HardwareMap hardwareMap = null;
    //Create TensorFlow processor
    TfodProcessor tfodProcessor = TfodProcessor.easyCreateWithDefaults();
    //Create VisionPortal with TensorFlow processor
    VisionPortal visionPortal = VisionPortal.easyCreateWithDefaults(camera, tfodProcessor);
    //Get recognized objects from TensorFlow
    List<Recognition> recognitions = null;
    //Iterate through each recognized object in the list
    for(Recognition recognition : recognitions)
    {
        //Get label of this recognized object
        String label = recognition.getLabel();
        //Get confidence of this recognized object
        float confidence = recognition.getConfidence();
        //Add this label and confidence to the telemetry
        //telemetry.addLine("Recognized object"+label);
        //telemetry.addLine("Recognized confidence"+confidence);

    }*/

}
