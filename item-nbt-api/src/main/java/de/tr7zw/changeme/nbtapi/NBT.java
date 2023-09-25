package de.tr7zw.changeme.nbtapi;

import de.tr7zw.changeme.nbtapi.iface.ReadWriteNBT;

/**
 * General utility class for a clean and simple nbt access.
 * 
 * @author tr7zw
 *
 */
public class NBT {

    private NBT() {
    }

    /**
     * Create a new NBTContainer object and return it.
     * 
     * @return A new instance of the NBTContainer class.
     */
    public static ReadWriteNBT createNBTObject() {
        return new NBTContainer();
    }
}
