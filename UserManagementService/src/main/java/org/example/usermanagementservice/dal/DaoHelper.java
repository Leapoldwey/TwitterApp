package org.example.usermanagementservice.dal;

import java.nio.ByteBuffer;
import java.util.UUID;

public class DaoHelper {
    public static UUID bytesArraytoUUID(byte[] array) {
        ByteBuffer bb = ByteBuffer.wrap(array);
        long firstLong = bb.getLong();
        long secondLong = bb.getLong();
        return new UUID(firstLong, secondLong);
    }

    public static byte[] UUIDtoByteArray(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
}
