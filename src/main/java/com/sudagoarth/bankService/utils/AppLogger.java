package com.sudagoarth.bankService.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Map;

@Component
public class AppLogger {

    public void info(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).info(format(message, args));
    }

    public void debug(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).debug(format(message, args));
    }

    public void warn(Class<?> clazz, String message, Object... args) {
        getLogger(clazz).warn(format(message, args));
    }

    public void error(Class<?> clazz, String message, Throwable ex, Object... args) {
        getLogger(clazz).error(format(message, args), ex);
    }

    public void info(Class<?> clazz, String message, Map<String, Object> data) {
        getLogger(clazz).info(format(message, data));
    }

    public void debug(Class<?> clazz, String message, Map<String, Object> data) {
        getLogger(clazz).debug(format(message, data));
    }

    public void warn(Class<?> clazz, String message, Map<String, Object> data) {
        getLogger(clazz).warn(format(message, data));
    }

    public void error(Class<?> clazz, String message, Throwable ex, Map<String, Object> data) {
        getLogger(clazz).error(format(message, data), ex);
    }

    private Logger getLogger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    private String format(String message, Object... args) {
        String formatted = message;
        if (args != null) {
            for (Object arg : args) {
                formatted = formatted.replaceFirst("\\{\\}", arg != null ? arg.toString() : "null");
            }
        }
        return String.format("[%s] %s %s", Instant.now(), formatted, appendCorrelationId());
    }

    private String format(String message, Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        sb.append("[").append(Instant.now()).append("] ").append(message);
        if (data != null && !data.isEmpty()) {
            data.forEach((k, v) -> sb.append(" ").append(k).append("=").append(v));
        }
        sb.append(" ").append(appendCorrelationId());
        return sb.toString().trim();
    }

    private String appendCorrelationId() {
        String correlationId = MDC.get("correlationId");
        return correlationId != null ? "correlationId=" + correlationId : "";
    }
}
