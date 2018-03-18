package com.telnetar.desktop;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.apache.commons.lang3.StringUtils;

public class Util {
	public static final String DEFAULT_DATE_FORMAT_PATTERN = "dd/MM/yyyy";

	public static String formatDate(Date date) {
		return formatDate(date, DEFAULT_DATE_FORMAT_PATTERN);
	}

	public static String formatDate(Date date, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(date);
	}

	public static Date parseDate(String date) throws ParseException {
		return parseDate(date, DEFAULT_DATE_FORMAT_PATTERN);
	}

	public static Date parseDate(String date, String pattern) throws ParseException {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.parse(date);
	}

	/*
	 * Pasado un valor de 0 - 100, retorna un valor de 0 a 250
	 */
	public static Integer getVirtualIntensity(int value) {
		return (value * 250) / 100;
	}

	/*
	 * Pasado un valor de 0 - 250, retorna un valor de 0 a 100
	 */
	public static Integer getRealIntensity(int value) {
		return (value * 100) / 250;
	}

	public static int getTemperature(Integer hightTemp, Integer lowTemp) {
		if (hightTemp != null && lowTemp != null) {
			String sum = new String(
					Integer.toHexString(hightTemp) + StringUtils.leftPad(Integer.toHexString(lowTemp), 2, "0"));
			Integer temp = new Integer(Integer.parseInt(sum, 16));

			if (temp > 937 && temp <= 941)
				return -3;
			else if (temp > 933 && temp <= 937)
				return -2;
			else if (temp > 927 && temp <= 933)
				return -1;
			else if (temp > 924 && temp <= 927)
				return 0;
			else if (temp > 921 && temp <= 924)
				return 1;
			else if (temp > 916 && temp <= 921)
				return 2;
			else if (temp > 911 && temp <= 916)
				return 3;
			else if (temp > 905 && temp <= 911)
				return 4;
			else if (temp > 899 && temp <= 905)
				return 5;
			else if (temp > 893 && temp <= 899)
				return 6;
			else if (temp > 887 && temp <= 893)
				return 7;
			else if (temp > 881 && temp <= 887)
				return 8;
			else if (temp > 875 && temp <= 881)
				return 9;
			else if (temp > 870 && temp <= 875)
				return 10;
			else if (temp > 863 && temp <= 870)
				return 11;
			else if (temp > 857 && temp <= 863)
				return 12;
			else if (temp > 851 && temp <= 857)
				return 13;
			else if (temp > 844 && temp <= 851)
				return 14;
			else if (temp > 836 && temp <= 844)
				return 15;
			else if (temp > 829 && temp <= 836)
				return 16;
			else if (temp > 823 && temp <= 829)
				return 17;
			else if (temp > 817 && temp <= 823)
				return 18;
			else if (temp > 806 && temp <= 817)
				return 19;
			else if (temp > 798 && temp <= 806)
				return 20;
			else if (temp > 789 && temp <= 798)
				return 21;
			else if (temp > 783 && temp <= 789)
				return 22;
			else if (temp > 774 && temp <= 783)
				return 23;
			else if (temp > 766 && temp <= 774)
				return 24;
			else if (temp > 760 && temp <= 766)
				return 25;
			else if (temp > 749 && temp <= 760)
				return 26;
			else if (temp > 740 && temp <= 749)
				return 27;
			else if (temp > 730 && temp <= 740)
				return 28;
			else if (temp > 722 && temp <= 730)
				return 29;
			else if (temp > 714 && temp <= 722)
				return 30;
			else if (temp > 705 && temp <= 714)
				return 31;
			else if (temp > 691 && temp <= 705)
				return 32;
			else if (temp > 683 && temp <= 691)
				return 33;
			else if (temp > 679 && temp <= 683)
				return 34;
			else if (temp > 666 && temp <= 679)
				return 35;
			else if (temp > 659 && temp <= 666)
				return 36;
			else if (temp > 649 && temp <= 659)
				return 37;
			else if (temp > 640 && temp <= 649)
				return 38;
			else if (temp > 626 && temp <= 640)
				return 39;
			else if (temp > 617 && temp <= 626)
				return 40;
			else if (temp > 608 && temp <= 617)
				return 41;
			else if (temp > 599 && temp <= 608)
				return 42;
			else if (temp > 591 && temp <= 599)
				return 43;
			else if (temp > 579 && temp <= 591)
				return 44;
			else if (temp > 567 && temp <= 579)
				return 45;
			else if (temp > 558 && temp <= 567)
				return 46;
			else if (temp > 545 && temp <= 558)
				return 47;
			else if (temp > 534 && temp <= 545)
				return 48;
			else if (temp > 526 && temp <= 534)
				return 49;
			else if (temp > 516 && temp <= 526)
				return 50;
			else if (temp > 508 && temp <= 516)
				return 51;
			else if (temp > 496 && temp <= 508)
				return 52;
			else if (temp > 491 && temp <= 496)
				return 53;
			else if (temp > 483 && temp <= 491)
				return 54;
			else if (temp > 481 && temp <= 483)
				return 55;
			else if (temp > 472 && temp <= 481)
				return 56;
			else if (temp > 463 && temp <= 472)
				return 57;
			else if (temp > 454 && temp <= 463)
				return 58;
			else if (temp > 440 && temp <= 454)
				return 59;
			else if (temp > 429 && temp <= 440)
				return 60;
			else if (temp > 424 && temp <= 429)
				return 61;
			else if (temp > 417 && temp <= 424)
				return 62;
			else if (temp > 405 && temp <= 417)
				return 63;
			else if (temp > 401 && temp <= 405)
				return 64;
			else if (temp > 384 && temp <= 401)
				return 65;
			else if (temp > 374 && temp <= 384)
				return 66;
			else if (temp > 367 && temp <= 374)
				return 67;
			else if (temp > 359 && temp <= 367)
				return 68;
			else if (temp > 351 && temp <= 359)
				return 69;
			else if (temp > 348 && temp <= 351)
				return 70;
			else if (temp > 336 && temp <= 348)
				return 71;
			else if (temp > 328 && temp <= 336)
				return 72;
			else if (temp > 320 && temp <= 328)
				return 73;
			else if (temp > 314 && temp <= 320)
				return 74;
			else if (temp > 305 && temp <= 314)
				return 75;
			else if (temp > 297 && temp <= 305)
				return 76;
			else if (temp > 289 && temp <= 297)
				return 77;
			else if (temp > 287 && temp <= 289)
				return 78;
			else if (temp > 279 && temp <= 287)
				return 79;
			else if (temp > 274 && temp <= 279)
				return 80;
			else if (temp > 268 && temp <= 274)
				return 81;
			else if (temp > 262 && temp <= 268)
				return 82;
			else if (temp > 256 && temp <= 262)
				return 83;
			else if (temp > 250 && temp <= 256)
				return 84;
			else if (temp > 244 && temp <= 250)
				return 85;
			else if (temp > 238 && temp <= 244)
				return 86;
			else if (temp > 232 && temp <= 238)
				return 87;
			else if (temp > 225 && temp <= 232)
				return 88;
			else if (temp > 219 && temp <= 225)
				return 89;
			else if (temp > 214 && temp <= 219)
				return 90;
			else if (temp > 209 && temp <= 214)
				return 91;
			else if (temp > 205 && temp <= 209)
				return 92;
			else if (temp > 200 && temp <= 205)
				return 93;
			else if (temp > 195 && temp <= 200)
				return 94;
			else if (temp > 189 && temp <= 195)
				return 95;
			else if (temp > 184 && temp <= 189)
				return 96;
			else if (temp > 180 && temp <= 184)
				return 97;
			else if (temp > 176 && temp <= 180)
				return 98;
			else if (temp > 172 && temp <= 176)
				return 99;
			else if (temp <= 172)
				return 100;
			else
				return 0;
		} else {
			return 0;
		}
	}

	public static Date addToDate(Date pDate, int pQtDias, int pQtHoras, int pQtMinutos, int pQtSegundos) {
		if (pDate != null) {
			Calendar xCalendar = new GregorianCalendar();
			xCalendar.setTime(pDate);
			xCalendar.add(Calendar.DATE, pQtDias);
			xCalendar.add(Calendar.HOUR, pQtHoras);
			xCalendar.add(Calendar.MINUTE, pQtMinutos);
			xCalendar.add(Calendar.SECOND, pQtSegundos);
			return xCalendar.getTime();
		} else
			return pDate;
	}
	
	public static Integer fromByteToInt(Byte xByte){
		return xByte & 0xFF;
	}
}
