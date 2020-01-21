package com.parvanpajooh.common.formatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

import com.parvanpajooh.commons.util.DateFields;
import com.parvanpajooh.commons.util.SimplePersianCalendar;

public class DateFormatter implements Formatter<Date> {

    private String pattern;

    public DateFormatter(String pattern) {
        this.pattern = pattern;
    }

    public String print(Date date, Locale locale) {
        if (date == null) {
            return "";
        }
        
        if("fa".equals(locale.getLanguage())){
    		Calendar cal = Calendar.getInstance();
    		cal.setTime(date);
    		
    		SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
    		persianCalendar.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
    		DateFields dfs = persianCalendar.getDateFields();
    		
    		return dfs.getYear() + "/" + (dfs.getMonth() + 1) + "/" + dfs.getDay();
    	}
    	else {
    		return getDateFormat(locale).format(date);
    	}
    }

    public Date parse(String formatted, Locale locale) throws ParseException {
        if (formatted.length() == 0) {
            return null;
        }
        
        if("fa".equals(locale.getLanguage())){
        	String[] splited = formatted.split("/");
        	String year = splited[0];
        	String month = splited[1];
        	String day = splited[2];
        	
        	SimplePersianCalendar persianCalendar = new SimplePersianCalendar();
        	persianCalendar.setDateFields(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day));
        	
        	Calendar cal = Calendar.getInstance();
        	cal.setTime(persianCalendar.getTime());

        	return cal.getTime();
        }
        else {
        	return getDateFormat(locale).parse(formatted);
        }
    }

    protected DateFormat getDateFormat(Locale locale) {
        DateFormat dateFormat = new SimpleDateFormat(this.pattern, locale);
        dateFormat.setLenient(false);
        return dateFormat;
    }

}