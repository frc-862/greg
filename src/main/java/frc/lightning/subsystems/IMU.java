/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lightning.subsystems;

import java.util.function.Supplier;

import com.ctre.phoenix.sensors.PigeonIMU;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IMU extends SubsystemBase {

	public enum IMUType {
		PIGEON, 
		NAVX, 
		NONE,
	}

	public interface IMUFunction {
		void exec();
	}

	public static IMU pigeon(int id) {
		return new IMU(IMUType.PIGEON, id);
	}

	public static IMU navX() {
		return new IMU(IMUType.NAVX);
	}

	public static IMU none() {
		return new IMU(IMUType.NONE);
	}

	private IMUType type;

	private AHRS navx = null;

	private PigeonIMU pigeon = null;

	private double[] ypr = null;

	private IMU(IMUType type, int id) {
		this.type = type;
		switch (type) {
			case PIGEON:
				pigeon = new PigeonIMU(id);
				ypr = new double[3];
				break;
			case NAVX:
				navx = new AHRS(SPI.Port.kMXP);
				break;
			case NONE:
			default:
				break;
			}
		
	}

	private IMU(IMUType type) {
		this(type, -1);
	}

	public IMUType getType() {
		return type;
	}

	public Rotation2d getHeading() {
		if(type == IMUType.NAVX && navx != null) {
			return Rotation2d.fromDegrees(-navx.getAngle());
		}
		if(type == IMUType.PIGEON && ypr != null) {
			Rotation2d.fromDegrees((((ypr[0]+180)%360)-180));
		}
		return Rotation2d.fromDegrees(0d);
	}

	public Supplier<Rotation2d> heading() {
		return this::getHeading;
	}

	public void reset() {
		if(type == IMUType.NAVX && navx != null) {
			navx.reset();
		}
		if(type == IMUType.PIGEON && pigeon != null) {
			pigeon.setYaw(0d);
		}
	}

	public IMUFunction zero() {
		return this::reset;
	}

	@Override
	public void periodic() {
		if(type == IMUType.PIGEON && pigeon != null && ypr != null) {
			pigeon.getYawPitchRoll(ypr);
		}
	}

}
