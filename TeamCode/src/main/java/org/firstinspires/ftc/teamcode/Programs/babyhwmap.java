package org.firstinspires.ftc.teamcode.Programs;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class babyhwmap extends HardwareMapUtil {
    HardwareMap hwmap = null;

    public DcMotor leftfrontDrive = null;

    public DcMotor rightfrontDrive = null;

    public DcMotor leftbackDrive = null;

    public DcMotor rightbackDrive = null;

    public DcMotor armMotorOne = null;
    public DcMotor armMotorTwo = null;


    public void init(HardwareMap ahwMap){
        hwMap=ahwMap;
        leftfrontDrive = HardwareInitMotor("lfD", true);
        rightfrontDrive = HardwareInitMotor("rfD", false);
        leftbackDrive = HardwareInitMotor("lbD", true);
        rightbackDrive = HardwareInitMotor("rbD", false);
        armMotorOne = HardwareInitMotor("arm_1", true);
        armMotorTwo = HardwareInitMotor("arm_2", true);


        leftfrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightfrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftbackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightbackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorOne.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotorTwo.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);















    }






















}
