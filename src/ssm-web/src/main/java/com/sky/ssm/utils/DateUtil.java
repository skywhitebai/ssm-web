package com.sky.ssm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

	public static String getFormatSSS(Date date){
		if(date==null){
			return null;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
		String formatStr =formatter.format(date);
		return formatStr;
	}
}
