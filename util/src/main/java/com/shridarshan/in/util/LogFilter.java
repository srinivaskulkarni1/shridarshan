package com.shridarshan.in.util;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

public class LogFilter extends Filter<ILoggingEvent> {

	public static final String APPLICATION_LOG = "SHRIDARSHAN";
	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getLoggerName().contains("com.shridarshan.in")) {
			return FilterReply.ACCEPT;
		} else {
			return FilterReply.DENY;
		}
	}
}