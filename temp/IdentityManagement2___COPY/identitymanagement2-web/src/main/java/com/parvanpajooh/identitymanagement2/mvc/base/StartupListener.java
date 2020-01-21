package com.parvanpajooh.identitymanagement2.mvc.base;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.parvanpajooh.common.util.LocaleThreadLocal;
import com.parvanpajooh.common.util.LocaleUtil;
import com.parvanpajooh.common.util.TimeZoneThreadLocal;
import com.parvanpajooh.common.util.TimeZoneUtil;

@Component
public class StartupListener implements ApplicationListener<ContextRefreshedEvent> {

	@Override
	public void onApplicationEvent(final ContextRefreshedEvent event) {
		LocaleThreadLocal.setDefaultLocale(LocaleUtil.IRAN);
		TimeZoneThreadLocal.setDefaultTimeZone(TimeZoneUtil.getTimeZone("Asia/Tehran"));
	}

}
