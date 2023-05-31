package dev.online.utils;

public class CRC64 {

    private static final long POLY64REV = 0xd800000000000000L;
    private static final long[] LOOKUP_TABLE;

    static {
        LOOKUP_TABLE = new long[0x100];
        for(int i = 0; i < 0x100; i++) {
            long v = i;
            for (int j = 0; j < 8; j++) v = (v & 1) == 1 ? (v >>> 1) ^ POLY64REV : (v >>> 1);
            LOOKUP_TABLE[i] = v;
        }
    }

    private static long update(final long sum, final byte b) {
        final int lookup_idx = ((int) sum ^ b) & 0xff;
        return (sum >>> 8) ^ LOOKUP_TABLE[lookup_idx];
    }

    private static long update(long sum, final byte[] bytes, final int fromIndexInclusive, final int toIndexExclusive) {
        for (int i = fromIndexInclusive; i < toIndexExclusive; i++) sum = update(sum, bytes[i]);
        return sum;
    }

    public static long classId(final byte[] bytes) {
        return update(0, bytes, 0, bytes.length);
    }

    private CRC64() {}
}
