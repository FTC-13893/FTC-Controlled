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

package com.sources.com.qualcomm.robotcore.hardware.logitech;

import android.os.Build;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Support for Logitech Gamepad F310. This class is only needed if the OS does
 * not properly recognize the F310. Use of this class is the same as
 * {@link com.qualcomm.robotcore.hardware.Gamepad}.
 */
public class LogitechGamepadF310 extends Gamepad {

	public LogitechGamepadF310() {
		this(null);
	}

	public LogitechGamepadF310(GamepadCallback callback) {
		super(callback);

		// calibrate the device
		joystickDeadzone = 0.06f;
	}

	/*
	 * Need to have custom android MotionEvent processor for this gamepad
	 *
	 * @see com.qualcomm.robotcore.hardware.Gamepad#update(android.view.MotionEvent)
	 */
	@Override
	public void update(MotionEvent event) {

		id = event.getDeviceId();
		timestamp = event.getEventTime();

		String androidOS = Build.VERSION.RELEASE;
		if (androidOS.startsWith("5")){
			update_5_dot_oh(event);
		} else {
			left_stick_x = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_X));
			left_stick_y = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_Y));
			right_stick_x = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_RX));
			right_stick_y = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_RY));
			left_trigger = (event.getAxisValue(MotionEvent.AXIS_Z) + 1f) / 2f;
			right_trigger = (event.getAxisValue(MotionEvent.AXIS_RZ) + 1f) / 2f;
			dpad_down = event.getAxisValue(MotionEvent.AXIS_HAT_Y) > dpadThreshold;
			dpad_up = event.getAxisValue(MotionEvent.AXIS_HAT_Y) < -dpadThreshold;
			dpad_right = event.getAxisValue(MotionEvent.AXIS_HAT_X) > dpadThreshold;
			dpad_left = event.getAxisValue(MotionEvent.AXIS_HAT_X) < -dpadThreshold;

			callCallback();
		}
	}

	private void update_5_dot_oh(MotionEvent event){

		left_stick_x = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_X));
		left_stick_y = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_Y));

		//** right stick x-values are mapped to AXIS_Z on 5.0 **//
		right_stick_x = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_Z));

		//** right stick y-values are mapped to AXIS_RZ on 5.0 **//
		right_stick_y = cleanMotionValues(event.getAxisValue(MotionEvent.AXIS_RZ));

		//** left trigger mapped to AXIS_BRAKE on 5.0 **//
		left_trigger = event.getAxisValue(MotionEvent.AXIS_BRAKE);

		//** right trigger mapped to AXIS_GAS on 5.0 **//
		right_trigger = event.getAxisValue(MotionEvent.AXIS_GAS);

		dpad_down = event.getAxisValue(MotionEvent.AXIS_HAT_Y) > dpadThreshold;
		dpad_up = event.getAxisValue(MotionEvent.AXIS_HAT_Y) < -dpadThreshold;
		dpad_right = event.getAxisValue(MotionEvent.AXIS_HAT_X) > dpadThreshold;
		dpad_left = event.getAxisValue(MotionEvent.AXIS_HAT_X) < -dpadThreshold;

		callCallback();
	}

	@Override
	public void update(KeyEvent event) {

		id = event.getDeviceId();
		timestamp = event.getEventTime();

		int key = event.getKeyCode();
		if      (key == KeyEvent.KEYCODE_DPAD_UP)
			dpad_up = pressed(event);
		else if (key == KeyEvent.KEYCODE_DPAD_DOWN)
			dpad_down = pressed(event);
		else if (key == KeyEvent.KEYCODE_DPAD_RIGHT)
			dpad_right = pressed(event);
		else if (key == KeyEvent.KEYCODE_DPAD_LEFT)
			dpad_left = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_A)
			a = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_B)
			b = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_X)
			x = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_Y)
			y = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_MODE)
			guide = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_START)
			start = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_SELECT)
			back = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_R1)
			right_bumper = pressed(event);
		else if (key == KeyEvent.KEYCODE_BUTTON_L1)
			left_bumper = pressed(event);

		callCallback();
	}

	@Override
	public String type() {
		return "F310";
	}

}
