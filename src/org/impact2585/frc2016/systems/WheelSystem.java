package org.impact2585.frc2016.systems;

import org.impact2585.frc2016.Environment;
import org.impact2585.frc2016.input.InputMethod;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.Victor;


/**
 * Drivetrain for the robot
 */
public class WheelSystem implements RobotSystem, Runnable{
	
	private RobotDrive drivetrain;
	private double currentRampForward;
	private double rotationValue;
	public static final double DEADZONE = 0.15;
	private InputMethod input;

	/* (non-Javadoc)
	 * @see org.impact2585.frc2016.Initializable#init(org.impact2585.frc2016.Environment)
	 */
	@Override
	public void init(Environment environ) {
		drivetrain = new RobotDrive(new Victor(0), new Victor(1), new Victor(2), new Victor(3));
		input = environ.getInput();
	}
	
	/**Sets new input method
	 * @param controller the type of input method
	 */
	protected synchronized void setInput(InputMethod controller) {
		input = controller;
	}
	
	/**
	 * @returns the input method that this system is using
	 */
	public InputMethod getInput() {
		return input;
	}
	
	
	/** Drives forward and turns
	 * @param driveForward distance to drive forward
	 * @param rotate how much the robot will turn
	 */
	public void drive(double driveForward, double rotate) {
		drivetrain.arcadeDrive(driveForward, -rotate);
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		currentRampForward = input.forwardMovement();
		rotationValue = input.rotationValue();
		if(currentRampForward < DEADZONE && currentRampForward > -DEADZONE)
			currentRampForward = 0;
		if(rotationValue < DEADZONE && rotationValue > -DEADZONE)
			rotationValue = 0;
		drive(currentRampForward, rotationValue);
	}
	
	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		drivetrain.free();
	}

}
