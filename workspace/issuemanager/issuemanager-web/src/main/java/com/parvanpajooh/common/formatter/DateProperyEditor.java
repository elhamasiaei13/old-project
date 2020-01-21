package com.parvanpajooh.common.formatter;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;

import org.springframework.util.StringUtils;

import com.parvanpajooh.commons.constants.StringPool;
import com.parvanpajooh.commons.util.DateUtil;
import com.parvanpajooh.commons.util.LocaleUtil;
import com.parvanpajooh.commons.util.ZoneIdUtil;

public class DateProperyEditor extends PropertyEditorSupport {

	private final boolean allowTime;
	private final boolean allowEmpty;

	public DateProperyEditor(boolean allowTime, boolean allowEmpty) {
		this.allowTime = allowTime;
		this.allowEmpty = allowEmpty;
	}

	public void setAsText(String text) throws IllegalArgumentException {
		if (this.allowEmpty && !StringUtils.hasText(text)) {
			// Treat empty String as null value.
			setValue(null);
		} else {
			Locale locale = LocaleUtil.fromLanguageId("fa");

			// Locale locale =
			// LocaleUtil.fromLanguageId(userInfo.getLocale());//
			// LocaleContextHolder.getLocale();

			try {
				Object utc = null;

				boolean isDate = false;
				boolean isDateTime = false;

				String[] splited = text.split(StringPool.DASH);
				if (splited.length == 3) {

					isDate = true;
					if (splited[2].indexOf(StringPool.COLON) > -1) {
						isDateTime = true;
					}
				}

				if (isDateTime) {

					if (!allowTime) {
						String[] dateAndTime = splited[2].split(StringPool.SPACE);
						text = splited[0] + StringPool.DASH + splited[1] + StringPool.DASH + dateAndTime[0];
						utc = DateUtil.parseLocalDate(text, locale, ZoneIdUtil.getDefault(), "gregorian");

					} else {						
						utc = DateUtil.parseLocalDateTime(text, locale, ZoneIdUtil.getDefault(), "gregorian");						
					}

				} else if (isDate) {
					if (allowTime) {
						text += StringPool.SPACE + "00:00";

						/*
						 * Instant instant = Instant.now();
						 * 
						 * ZoneOffset zoneOffset =
						 * ZoneIdUtil.getZoneId(userInfo.getZoneId()).getRules()
						 * .getOffset(instant); int seconds =
						 * zoneOffset.get(ChronoField.OFFSET_SECONDS);
						 * 
						 * LocalDateTime localDateTime =
						 * DateUtil.parseLocalDateTime(text, locale,
						 * ZoneIdUtil.getZoneId(userInfo.getZoneId()),
						 * userInfo.getCalendar()); localDateTime =
						 * localDateTime.plusSeconds(seconds);
						 * 
						 * utc = localDateTime;
						 */

						utc = DateUtil.parseLocalDateTime(text, locale, ZoneIdUtil.getDefault(), "gregorian");

					} else {

						utc = DateUtil.parseLocalDate(text, locale, ZoneIdUtil.getDefault(), "gregorian");
					}

				}

				setValue(utc);

			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}

	/**
	 * Format the Date as String, using the specified DateFormat.
	 */
	@Override
	public String getAsText() {
		Object value = (Object) getValue();

		if (value == null)
			return StringPool.BLANK;

		Locale locale = LocaleUtil.fromLanguageId("fa");

		// Locale locale = LocaleUtil.fromLanguageId(userInfo.getLocale());//
		// LocaleContextHolder.getLocale();

		String localValue = StringPool.BLANK;

		if (value instanceof LocalDate) {
			localValue = DateUtil.getDate((LocalDate) value, locale, "gregorian", ZoneIdUtil.getDefault());

		} else if (value instanceof LocalDateTime) {
			localValue = DateUtil.getDateTime((LocalDateTime) value, locale, "gregorian", ZoneIdUtil.getDefault());

		}

		return localValue;
	}
}
