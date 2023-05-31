package dev.online.controller.admin;

import dev.online.monitor.method.annot.Audit;
import org.springframework.stereotype.Service;

@Service
public class MainService {

    @Audit
    public int test() {
        return (int) (Math.random() + 0.5);
    }
}
