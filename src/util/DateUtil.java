package util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
//	把Date转换为String
	public static String formatDate(Date date,String format){
		String result="";
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		if(date!=null){
			result=sdf.format(date);
		}
		return result;
	}
	
//	把String转换为Date
	public static Date formatString(String str,String format) throws Exception{
		if(StringUtil.isEmpty(str)){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat(format);
		return sdf.parse(str);
	}
	
	//生成文件名(使用当前时间生成文件名)
	public static String getCurrentDateStr(){
		Date date=new Date();
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}
	
	
}
