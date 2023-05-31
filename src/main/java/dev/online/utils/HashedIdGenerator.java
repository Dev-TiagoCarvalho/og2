package dev.online.utils;

import java.util.UUID;

public class HashedIdGenerator {

    public static String generateHashedId(String prefix, byte[] bytes) {
        StringBuilder builder = new StringBuilder(prefix);
        builder.append(UUID.randomUUID()).append(':').append(Long.toHexString(CRC64.classId(bytes)));
        return builder.toString();
    }
}
