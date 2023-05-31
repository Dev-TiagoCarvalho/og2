package dev.online.monitor.method.component;

import dev.online.monitor.api.model.AuditEntity;
import dev.online.monitor.method.annot.IgnoreOnAudit;
import dev.online.monitor.method.auditor.Auditor;
import dev.online.monitor.method.model.AuditInfo;
import dev.online.monitor.method.model.AuditStatus;
import dev.online.utils.HashedIdGenerator;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Parameter;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AuditAspect {

    public static final String TRACE_ID_TEMPLATE = "MTHD:";
    private static final byte[] DEFAULT_ID_HOLDER = "[API]".getBytes(Charset.defaultCharset());

    private final Auditor auditor;

    public AuditAspect(Auditor auditor) {
        this.auditor = auditor;
    }

    @Around("@annotation(dev.online.monitor.method.annot.Audit)")
    public Object auditMethod(ProceedingJoinPoint p) throws Throwable {
        MethodSignature s = (MethodSignature) p.getSignature();
        String clazz = s.getMethod().getDeclaringClass().getName();
        StringBuilder builder = new StringBuilder(clazz).append('.').append(s.getName());
        String method = builder.toString();

        AuditInfo info = new AuditInfo(obtainTraceID(method), method, extractParams(s, p));
        info.Init(System.currentTimeMillis());
        auditor.audit(info);

        try { return finalizeAudit(info, AuditStatus.Finished, p.proceed()); }
        catch(Throwable e) { throw (Throwable) finalizeAudit(info, AuditStatus.Error, e); }
    }

    private String obtainTraceID(String method) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(requestAttributes == null) return null;

        HttpServletRequest request = requestAttributes.getRequest();
        if(request.getAttribute(AuditEntity.REQUEST_ID_TEMPLATE) != null) return (String) request.getAttribute(AuditEntity.REQUEST_ID_TEMPLATE);
        if(request.getAttribute(TRACE_ID_TEMPLATE) != null) return (String) request.getAttribute(TRACE_ID_TEMPLATE);
        String traceID = HashedIdGenerator.generateHashedId(TRACE_ID_TEMPLATE, method.getBytes(StandardCharsets.UTF_8));
        request.setAttribute(TRACE_ID_TEMPLATE, traceID);
        return traceID;
    }

    private Object finalizeAudit(AuditInfo info, AuditStatus status, Object result) {
        info.Timestamp(System.currentTimeMillis());
        info.Status(status);
        if(status == AuditStatus.Finished) info.Result(result);
        else if(status == AuditStatus.Error) info.Exception((Throwable) result);
        auditor.audit(info);
        return result;
    }

    private static Map<String, Object> extractParams(MethodSignature s, ProceedingJoinPoint p) {
        Parameter[] params = s.getMethod().getParameters();
        String[] names = s.getParameterNames();
        Object[] args = p.getArgs();
        HashMap<String, Object> map = new HashMap<>();
        for(int i = 0; i < names.length; ++i) {
            if(!params[i].isAnnotationPresent(IgnoreOnAudit.class)) map.put(names[i], args[i]);
        }
        return map;
    }
}
