package dev.online.monitor.api.component;

import dev.online.monitor.api.model.AuditEntity;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@ControllerAdvice
public class AuditBodyInterceptor implements ResponseBodyAdvice<Object> {

    private final Logger LOGGER = LoggerFactory.getLogger(AuditBodyInterceptor.class);

    private final HttpServletRequest request;

    public AuditBodyInterceptor(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        LOGGER.debug("START INTERCEPT BODY");
        AuditEntity entity = (AuditEntity) this.request.getAttribute("dev.online.monitor.api.model.AuditEntity");
        if(entity == null) return body;
        entity.response.setPayload(body);
        LOGGER.info("SUCCESSFULLY INTERCEPTED BODY - {}", entity.request.getId());
        return body;
    }
}
