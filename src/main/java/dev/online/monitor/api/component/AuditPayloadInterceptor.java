package dev.online.monitor.api.component;

import dev.online.monitor.api.model.AuditEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.lang.reflect.Type;

@ControllerAdvice
public class AuditPayloadInterceptor extends RequestBodyAdviceAdapter {

    private final Logger LOGGER = LoggerFactory.getLogger(AuditPayloadInterceptor.class);
    private final HttpServletRequest request;

    public AuditPayloadInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        LOGGER.debug("START INTERCEPT PAYLOAD");
        AuditEntity entity = (AuditEntity) request.getAttribute("dev.online.monitor.api.model.AuditEntity");
        if(entity == null) return body;
        entity.request.setPayload(body);
        LOGGER.info("SUCCESSFULLY INTERCEPTED PAYLOAD - {}", entity.request.getId());
        return body;
    }

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }
}