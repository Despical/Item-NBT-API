package de.tr7zw.changeme.nbtapi.iface;

public interface NBTHandler<T> {

    default boolean fuzzyMatch(Object obj) {
        return false;
    }

    void set(ReadWriteNBT nbt, String key, T value);

    T get(ReadableNBT nbt, String key);
}