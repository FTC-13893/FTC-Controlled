/*
 * Copyright (c) 2014 Qualcomm Technologies Inc
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted
 * (subject to the limitations in the disclaimer below) provided that the following conditions are
 * met:
 *
 * Redistributions of source code must retain the above copyright notice, this list of conditions
 * and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions
 * and the following disclaimer in the documentation and/or other materials provided with the
 * distribution.
 *
 * Neither the name of Qualcomm Technologies Inc nor the names of its contributors may be used to
 * endorse or promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS LICENSE. THIS
 * SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF
 * THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.sources.com.qualcomm.robotcore.hardware;

import com.qualcomm.robotcore.exception.RobotCoreException;
import com.qualcomm.robotcore.hardware.AccelerationSensor;
import com.qualcomm.robotcore.hardware.CompassSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.LegacyModule;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;
import com.qualcomm.robotcore.hardware.UltrasonicSensor;
import com.qualcomm.robotcore.util.SerialNumber;

import java.util.Map;

public abstract class DeviceManager {

	/**
	 * Enum of Device Types
	 */
	public enum DeviceType {
		FTDI_USB_UNKNOWN_DEVICE,
		MODERN_ROBOTICS_USB_UNKNOWN_DEVICE,
		MODERN_ROBOTICS_USB_DC_MOTOR_CONTROLLER,
		MODERN_ROBOTICS_USB_SERVO_CONTROLLER,
		MODERN_ROBOTICS_USB_LEGACY_MODULE,
		MODERN_ROBOTICS_USB_SENSOR_MUX
	}

	/**
	 * Get a listing of all Modern Robotics devices connected.
	 * <p>
	 * This method will attempt to open all USB devices that are using an FTDI USB chipset. It will
	 * then probe the device to determine if it is a Modern Robotics device. Finally, it will close the
	 * device.
	 * <p>
	 * Because of the opening and closing of devices, it is recommended that this method is not called
	 * while any FTDI devices are in use.
	 *
	 * @return a map of serial numbers to Modern Robotics device types
	 * @throws RobotCoreException if unable to open a device
	 */
	public abstract Map<SerialNumber, DeviceType> scanForUsbDevices() throws RobotCoreException;

	/**
	 * Create an instance of a DcMotorController
	 *
	 * @param serialNumber serial number of controller
	 * @return an instance of a DcMotorController
	 * @throws RobotCoreException if unable to create instance
	 * @throws InterruptedException if the thread is interrupted
	 */
	public abstract com.qualcomm.robotcore.hardware.DcMotorController createUsbDcMotorController(SerialNumber serialNumber)
			throws RobotCoreException, InterruptedException;

	/**
	 * Create an instance of a DcMotor
	 *
	 * @param controller DC Motor controller this motor is attached to
	 * @param portNumber physical port number on the controller
	 * @return an instance of a DcMotor
	 */
	public com.qualcomm.robotcore.hardware.DcMotor createDcMotor(com.qualcomm.robotcore.hardware.DcMotorController controller, int portNumber) {
		return new com.qualcomm.robotcore.hardware.DcMotor(controller, portNumber, DcMotor.Direction.FORWARD);
	}

	/**
	 * Create an instance of a ServoController
	 *
	 * @param serialNumber serial number of controller
	 * @return an instance of a ServoController
	 * @throws RobotCoreException if unable to create instance
	 * @throws InterruptedException if the thread is interrupted
	 */
	public abstract com.qualcomm.robotcore.hardware.ServoController createUsbServoController(SerialNumber serialNumber)
			throws RobotCoreException, InterruptedException;

	/**
	 * Create an instance of a Servo
	 *
	 * @param controller Servo controller this servo is attached to
	 * @param portNumber physical port number on the controller
	 * @return an instance of a Servo
	 */
	public com.qualcomm.robotcore.hardware.Servo createServo(com.qualcomm.robotcore.hardware.ServoController controller, int portNumber) {
		return new com.qualcomm.robotcore.hardware.Servo(controller, portNumber, Servo.Direction.FORWARD);
	}

	/**
	 * Create an instance of a LegacyModule
	 *
	 * @param serialNumber serial number of legacy module
	 * @return an instance of a LegacyModule
	 * @throws RobotCoreException if unable to create instance
	 * @throws InterruptedException if the thread is interrupted
	 */
	public abstract com.qualcomm.robotcore.hardware.LegacyModule createUsbLegacyModule(SerialNumber serialNumber)
			throws RobotCoreException, InterruptedException;

	/**
	 * Create an instance of an NXT DcMotorController
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a DcMotorController
	 */
	public abstract DcMotorController createNxtDcMotorController(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of an NXT ServoController
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a ServoController
	 */
	public abstract ServoController createNxtServoController(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of a NxtCompassSensor
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a CompassSensor
	 */
	public abstract CompassSensor createNxtCompassSensor(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of a AccelerationSensor
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return an AccelerationSensor
	 */
	public abstract AccelerationSensor createNxtAccelerationSensor(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of a LightSensor
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a LightSensor
	 */
	public abstract LightSensor createNxtLightSensor(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of a IrSeekerSensor
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a IrSeekerSensor
	 */
	public abstract IrSeekerSensor createNxtIrSeekerSensor(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of an UltrasonicSensor
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a UltrasonicSensor
	 */
	public abstract UltrasonicSensor createNxtUltrasonicSensor(com.qualcomm.robotcore.hardware.LegacyModule legacyModule, int physicalPort);

	/**
	 * Create an instance of a GyroSensor
	 * 
	 * @param legacyModule Legacy Module this device is connected to
	 * @param physicalPort port number on the Legacy Module this device is connected to
	 * @return a GyroSensor
	 */
	public abstract GyroSensor createNxtGyroSensor(LegacyModule legacyModule, int physicalPort);

}
