package dev.online.monitor.api.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestEntityTests {

    @Test
    public void validateInitEntityTest() {
        String url1 = "http://localhost:8080/api/v1/admin/test";
        RequestEntity entity1 = new RequestEntity(url1, "Get");
        assertEquals(59, entity1.getId().length());
        assertEquals("admin", entity1.getApi());
        assertEquals("GET", entity1.getMethod());
        assertEquals("v1", entity1.getVersion());
        assertEquals("/test", entity1.getResource());
        assertEquals(url1, entity1.getUri());

        String url2 = "http://localhost:8080/api/v1/admin/test/sample";
        RequestEntity entity2 = new RequestEntity(url2, "post");
        assertEquals(59, entity2.getId().length());
        assertEquals("admin", entity2.getApi());
        assertEquals("POST", entity2.getMethod());
        assertEquals("v1", entity2.getVersion());
        assertEquals("/test/sample", entity2.getResource());
        assertEquals(url2, entity2.getUri());
    }

    @Test
    public void invalidInitEntityTest() {
        String url1 = "http://localhost:8080/";
        RequestEntity entity1 = new RequestEntity(url1, "get");
        assertEquals(59, entity1.getId().length());
        assertEquals("[API NOT FOUND]", entity1.getApi());
        assertEquals("GET", entity1.getMethod());
        assertEquals("[VERSION NOT FOUND]", entity1.getVersion());
        assertEquals("[RESOURCE NOT FOUND]", entity1.getResource());
        assertEquals(url1, entity1.getUri());

        String url2 = "http://localhost:8080/api//admin/test";
        RequestEntity entity42 = new RequestEntity(url2, "GeT");
        assertEquals(59, entity42.getId().length());
        assertEquals("admin", entity42.getApi());
        assertEquals("GET", entity42.getMethod());
        assertEquals("[VERSION NOT FOUND]", entity42.getVersion());
        assertEquals("/test", entity42.getResource());
        assertEquals(url2, entity42.getUri());

        String url3 = "http://localhost:8080/api/v1//test";
        RequestEntity entity3 = new RequestEntity(url3, "gET");
        assertEquals(59, entity3.getId().length());
        assertEquals("[API NOT FOUND]", entity3.getApi());
        assertEquals("GET", entity3.getMethod());
        assertEquals("v1", entity3.getVersion());
        assertEquals("/test", entity3.getResource());
        assertEquals(url3, entity3.getUri());

        String url4 = "http://localhost:8080/api/v1/admin";
        RequestEntity entity4 = new RequestEntity(url4, "pOSt");
        assertEquals(59, entity4.getId().length());
        assertEquals("admin", entity4.getApi());
        assertEquals("POST", entity4.getMethod());
        assertEquals("v1", entity4.getVersion());
        assertEquals("/", entity4.getResource());
        assertEquals(url4, entity4.getUri());
    }

    @Test
    public void invalidURLInitEntityTest() {
        String url = "/api/v1/admin/test";
        RequestEntity entity = new RequestEntity(url, "GET");
        assertEquals(59, entity.getId().length());
        assertEquals("admin", entity.getApi());
        assertEquals("GET", entity.getMethod());
        assertEquals("v1", entity.getVersion());
        assertEquals("/test", entity.getResource());
        assertEquals(url, entity.getUri());
    }

    @Test
    public void blankOrNullURLInitEntityTest() {
        String url1 = "";
        RequestEntity entity1 = new RequestEntity(url1, "Get");
        assertEquals(59, entity1.getId().length());
        assertEquals("[API NOT FOUND]", entity1.getApi());
        assertEquals("GET", entity1.getMethod());
        assertEquals("[VERSION NOT FOUND]", entity1.getVersion());
        assertEquals("[RESOURCE NOT FOUND]", entity1.getResource());
        assertEquals("/", entity1.getUri());

        RequestEntity entity2 = new RequestEntity(null, "Get");
        assertEquals(59, entity2.getId().length());
        assertEquals("[API NOT FOUND]", entity2.getApi());
        assertEquals("GET", entity2.getMethod());
        assertEquals("[VERSION NOT FOUND]", entity2.getVersion());
        assertEquals("[RESOURCE NOT FOUND]", entity2.getResource());
        assertEquals("/", entity2.getUri());
    }
}
