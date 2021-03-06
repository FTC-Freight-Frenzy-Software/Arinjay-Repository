//regular tankdrivetrain
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


@Autonomous(name = "ArinjayAuton", group = "LinearOpMode")

public class ArinjayAuton extends LinearOpMode {
    //declare motors
    static RobotHardware robot = new RobotHardware();
    public DcMotor backLeft = null;
    public DcMotor frontLeft = null;
    public DcMotor backRight = null;
    public DcMotor frontRight = null;
    public double turn = 0;
    public double horizontal = 0;
    public double vertical = 0;
    static final double wheelDiameter = 3.7796;
    static final double ticksPerRevolution = 537.6;
    double circumference = Math.PI * wheelDiameter;
    public int direction[] = {0,90,180,270,45,315,225,135};
    //test


    public void setAllPower(double speed){
        frontLeft.setPower(speed);
        backLeft.setPower(speed);
        frontRight.setPower(speed);
        backRight.setPower(speed);
    }
    public void resetEncoders(){
        robot.frontLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backLeft.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.frontRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.backRight.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void dirF(int ticks) {//0
        resetEncoders();
        frontLeft.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
        frontRight.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
    }
    public void dirB(int ticks) {//1
        resetEncoders();
        ticks*=-1;
        frontLeft.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
        frontRight.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
    }
    public void dirR(int ticks) {//2
        resetEncoders();
        frontLeft.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks * -1);
        frontRight.setTargetPosition(ticks * -1);
        backRight.setTargetPosition(ticks);
    }
    public void dirL(int ticks) {//3
        resetEncoders();
        frontLeft.setTargetPosition(ticks * -1);
        backLeft.setTargetPosition(ticks);
        frontRight.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks * -1);
    }
    public void dirFR(int ticks) {//4
        resetEncoders();
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
    }
    public void dirFL(int ticks) {//5
        resetEncoders();
        frontRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
    }
    public void dirBL(int ticks) {//6
        resetEncoders();
        ticks*=-1;
        frontLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
    }
    public void dirBR(int ticks) {//7
        resetEncoders();
        ticks*=-1;
        frontRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
    }

    public double strafe(int dir, double dist) { //dir{for,back,right,left}
        int ticks = (int)(dist*ticksPerRevolution/circumference);
        if((dir+360)%360 == direction[0]) {
            dirF(ticks);
        }
        else if((dir+360)%360 == direction[1]) {
            dirB(ticks);
        }
        else if((dir+360)%360 == direction[2]) {
            dirR(ticks);
        }
        else if((dir+360)%360 == direction[3]) {
            dirL(ticks);
        }
        else if((dir+360)%360 == direction[4]) {
            dirFR(ticks);
        }
        else if((dir+360)%360 == direction[5]) {
            dirFL(ticks);
        }
        else if((dir+360)%360 == direction[6]) {
            dirBL(ticks);
        }
        else if((dir+360)%360 == direction[7]) {
            dirBR(ticks);
        }

        while (true) {
            if(!frontLeft.isBusy()&&!frontRight.isBusy()&&!backLeft.isBusy()&&!backRight.isBusy()){
                //isbusy is self explanatory boolean function
                setAllPower(0);
                return dist;
            }
        }
    }
    public void rotate(int dir,int magnitude) { //rotates on center point because the other points seem niche and I'm lazy
        resetEncoders();
        int ticks = (int)(magnitude*ticksPerRevolution/circumference);//multiply this by a constant
        //constant dependent on how far wheels are apart:further apart wheels rotate slower
        //either do math or do trial and error
        if(dir == -1) {
            ticks*=-1; //clockwise being positive is weird, but the whole thing is like that so...
        }
        frontLeft.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
        frontRight.setTargetPosition(ticks*-1);
        backRight.setTargetPosition(ticks*-1);
    }

    public void runOpMode() {
        //initialize motors
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");

        waitForStart();

        //set motors to controller input
        /*
        something like strafe(<dir>,<dist>)
        dir{0,90,180,270,45,135,225,315} OR use negative angles for left
        */
        strafe(0, 5);

        /*
        something like rotate(<dir>,<magnitude>)
        dir = 1 implies right, dir = -1 implies left
        rotation magnitude to go backwards the long way if necessary
         */
        rotate(-1, 90);
    }
}
