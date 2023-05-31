package dev.online.monitor.api.component;

import dev.online.monitor.api.model.AuditEntity;
import dev.online.monitor.api.model.RequestEntity;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class AuditFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuditFilter.class);

    private final AsyncAuditService service;

    public AuditFilter(AsyncAuditService service) {
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;

        RequestEntity requestEntity = new RequestEntity(servletRequest.getRequestURI(), servletRequest.getMethod());
        LOGGER.info("API AUDIT IS ENABLED - {} - {}: {}", requestEntity.getId(), servletRequest.getMethod(), servletRequest.getRequestURI());
        AuditEntity auditEntity = new AuditEntity(requestEntity);
        setRequestHeaders(requestEntity, servletRequest);
        setQueryStringParams(requestEntity, servletRequest);

        servletRequest.setAttribute(AuditEntity.REQUEST_ID_TEMPLATE, auditEntity.getId());
        servletRequest.setAttribute("dev.online.monitor.api.model.AuditEntity", auditEntity);

        chain.doFilter(request, response);

        auditEntity.response.setStatusCode(servletResponse.getStatus());
        auditEntity.response.setStatusMessage(HttpStatus.resolve(servletResponse.getStatus()).getReasonPhrase());
        setResponseHeaders(auditEntity, servletResponse);
        auditEntity.setExecutionTime(System.currentTimeMillis());

        service.audit(auditEntity);
    }

    private void setResponseHeaders(AuditEntity entity, HttpServletResponse response) {
        Collection<String> headerNames = response.getHeaderNames();
        if(headerNames != null) headerNames.forEach(name -> entity.response.addHeader(name, response.getHeader(name)));
    }

    private void setRequestHeaders(RequestEntity entity, HttpServletRequest request) {
        Enumeration<String> headerNames = request.getHeaderNames();
        if(headerNames != null) {
            while(headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                entity.addHeader(name, request.getHeader(name));
            }
        }
    }

    private void setQueryStringParams(RequestEntity entity, HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        if(parameterNames != null) {
            while(parameterNames.hasMoreElements()) {
                String name = parameterNames.nextElement();
                entity.addParameter(name, request.getParameter(name));
            }
        }
    }
}
