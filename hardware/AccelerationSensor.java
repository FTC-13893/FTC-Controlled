/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.sources.com.qualcomm.robotcore.hardware;

/**
 * Acceleration Sensor
 */
public abstract class AccelerationSensor {

	/**
	 * Acceleration in the x, y, and z axis
	 */
	public static class Acceleration {

		public Acceleration() {
			this(0.0, 0.0, 0.0);
		}

		public Acceleration(double x, double y, double z) {
			this.x = x; 
			this.y = y;
			this.z = z;
		}

		public double x;
		public double y;
		public double z;

		@Override
		public String toString() {
			return String.format("Acceleration - x: %5.2f, y: %5.2f, z: %5.2f", x, y, z);
		}
	}

	/**
	 * Acceleration, measured in g's
	 * 
	 * @return acceleration in g's
	 */
	public abstract Acceleration getAcceleration();

	/**
	 * Status of this sensor, in string form
	 * 
	 * @return status
	 */
	public abstract String status();

	@Override
	public String toString() {
		return getAcceleration().toString();
	}

}
