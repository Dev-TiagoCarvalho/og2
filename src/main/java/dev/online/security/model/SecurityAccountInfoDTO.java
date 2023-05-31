package dev.online.security.model;

public record SecurityAccountInfoDTO(int id, String email, String username, String hashedPassword) {}
