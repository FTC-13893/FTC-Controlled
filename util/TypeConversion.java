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

package com.sources.com.qualcomm.robotcore.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

/**
 * Utility class for performing type conversions
 */
public class TypeConversion {

	private static final Charset UTF8_CHARSET = Charset.forName("UTF-8");

	/*
	 * Only contains static utility methods
	 */
	private TypeConversion() {}

	/**
	 * convert a short into a byte array; big endian is assumed
	 * @param shortInt
	 * @return byte array
	 */
	public static byte[] shortToByteArray(short shortInt) {
		return shortToByteArray(shortInt, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * convert a short into a byte array
	 * @param shortInt
	 * @param byteOrder
	 * @return byte array
	 */
	public static byte[] shortToByteArray(short shortInt, ByteOrder byteOrder) {
		return ByteBuffer.allocate(2).order(byteOrder).putShort(shortInt).array();
	}

	/**
	 * convert an int into a byte array; big endian is assumed
	 * @param integer
	 * @return byte array
	 */
	public static byte[] intToByteArray(int integer) {
		return intToByteArray(integer, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * convert an int into a byte array
	 * @param integer
	 * @param byteOrder
	 * @return byte array
	 */
	public static byte[] intToByteArray(int integer, ByteOrder byteOrder) {
		return ByteBuffer.allocate(4).order(byteOrder).putInt(integer).array();
	}

	/**
	 * convert a long into a byte array; big endian is assumed
	 * @param longInt
	 * @return byte array
	 */
	public static byte[] longToByteArray(long longInt) {
		return longToByteArray(longInt, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * convert a long into a byte array
	 * @param longInt
	 * @param byteOrder
	 * @return byte array
	 */
	public static byte[] longToByteArray(long longInt, ByteOrder byteOrder) {
		return ByteBuffer.allocate(8).order(byteOrder).putLong(longInt).array();
	}

	/**
	 * convert a short into a byte array; big endian is assumed
	 * @param byteArray
	 * @return byte array
	 */
	public static short byteArrayToShort(byte[] byteArray) {
		return byteArrayToShort(byteArray, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * convert a short into a byte array
	 * @param byteArray
	 * @param byteOrder
	 * @return byte array
	 */
	public static short byteArrayToShort(byte[] byteArray, ByteOrder byteOrder) {
		return ByteBuffer.wrap(byteArray).order(byteOrder).getShort();
	}

	/**
	 * convert a byte array into an int; big endian is assumed
	 * @param byteArray
	 * @return an integer
	 */
	public static int byteArrayToInt(byte[] byteArray) {
		return byteArrayToInt(byteArray, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * convert a byte array into an int
	 * @param byteArray
	 * @param byteOrder
	 * @return an integer
	 */
	public static int byteArrayToInt(byte[] byteArray, ByteOrder byteOrder) {
		return ByteBuffer.wrap(byteArray).order(byteOrder).getInt();
	}

	/**
	 * convert a byte array into a long; big endian is assumed
	 * @param byteArray
	 * @return an integer
	 */
	public static long byteArrayToLong(byte[] byteArray) {
		return byteArrayToLong(byteArray, ByteOrder.BIG_ENDIAN);
	}

	/**
	 * convert a byte array into a long
	 * @param byteArray
	 * @param byteOrder
	 * @return a long
	 */
	public static long byteArrayToLong(byte[] byteArray, ByteOrder byteOrder) {
		return ByteBuffer.wrap(byteArray).order(byteOrder).getLong();
	}

	/**
	 * Accept a byte, treat that byte as an unsigned byte, then covert it to the return type
	 * @param b byte to treat as unsigned byte
	 * @return a positive between 0 and 255
	 */
	public static int unsignedByteToInt(byte b) {
		return ((int)(b) & 0xff);
	}

	/**
	 * Accept a byte, treat that byte as an unsigned byte, then covert it to the return type
	 * @param b byte to treat as unsigned byte
	 * @return a positive between 0 and 255
	 */
	public static double unsignedByteToDouble(byte b) {
		return ((int)(b) & 0xff);
	}

	/**
	 * Accept an int, treat that int as an unsigned int, then covert it to the return type
	 * @param i int to treat as unsigned int
	 * @return a positive between 0 and 2^32
	 */
	public static long unsignedIntToLong(int i) {
		return ((long)(i) & 0xffffffffL);
	}

	/**
	 * Convert a Java String into a UTF-8 byte array
	 * @param javaString Java String to convert
	 * @throws java.lang.IllegalArgumentException if unable to cleanly convert the Java String
	 * @return UTF-8 byte array
	 */
	public static byte[] stringToUtf8(String javaString) {
		byte[] utf8String = javaString.getBytes(UTF8_CHARSET);

		if (!javaString.equals(new String(utf8String, UTF8_CHARSET))) {
			String msg = String.format("string cannot be cleanly encoded into %s - '%s' -> '%s'",
					UTF8_CHARSET.name(), javaString, new String(utf8String, UTF8_CHARSET));
			throw new IllegalArgumentException(msg);
		}

		return utf8String;
	}

	public static String utf8ToString(byte[] utf8String) {
		return new String(utf8String, UTF8_CHARSET);
	}

}
