/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.RobotDriveBase;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */
  private SpeedController frontRightDrive; 
  private SpeedController frontLeftDrive; 
  private SpeedController rearRightDrive; 
  private SpeedController rearLeftDrive; 

  private SpeedControllerGroup rightDrive; 
  private SpeedControllerGroup leftDrive; 

  private RobotDriveBase rDrive; 

  public DriveTrain(Class<? extends SpeedController> typeC, Class<? extends RobotDriveBase> typeB) throws InvocationTargetException, InstantiationException, IllegalAccessException {
    Constructor<?> c = typeC.getDeclaredConstructors()[0]; 
    Constructor<?> b[] = typeB.getDeclaredConstructors(); 

    frontRightDrive = (SpeedController)c.newInstance(Constants.frd); 
    frontLeftDrive = (SpeedController)c.newInstance(Constants.fld); 
    rearRightDrive = (SpeedController)c.newInstance(Constants.rrd); 
    rearLeftDrive = (SpeedController)c.newInstance(Constants.rld); 
    
    rightDrive = new SpeedControllerGroup(frontRightDrive, rearRightDrive); 
    leftDrive = new SpeedControllerGroup(frontLeftDrive, frontRightDrive); 

    if (b.length == 4)
      rDrive = (MecanumDrive)c.newInstance(frontLeftDrive, rearLeftDrive, frontRightDrive, rearRightDrive); 
    else if (b.length == 2)
      rDrive = (DifferentialDrive)c.newInstance(leftDrive, rightDrive); 
    else
      System.out.println("Please input a SpeedController class and a RobotDriveBase class."); 
      throw new IllegalArgumentException(); 

  }
  
  public void stop() {
    frontRightDrive.set(0); 
    frontLeftDrive.set(0); 
    rearRightDrive.set(0);
    rearLeftDrive.set(0); 
  }
  

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
