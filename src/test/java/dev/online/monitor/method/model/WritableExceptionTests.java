package dev.online.monitor.method.model;

import org.junit.jupiter.api.Test;

public class WritableExceptionTests {

    @Test
    public void test() {
        try { throw new Exception("message"); }
        catch(Exception e) {
            try { throw new Exception(e); }
            catch(Exception ex) {
                WritableException we = new WritableException(ex);
                System.out.println(we);
            }
        }
    }
}
