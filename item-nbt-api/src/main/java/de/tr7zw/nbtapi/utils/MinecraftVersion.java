package de.tr7zw.nbtapi.utils;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;

/**
 * This class acts as the "Brain" of the NBTApi. It contains the main logger for
 * other classes,registers bStats and checks rather Maven shading was done
 * correctly.
 * 
 * @author tr7zw
 *
 */
public enum MinecraftVersion {

    UNKNOWN(Integer.MAX_VALUE), // Use the newest known mappings
    MC1_7_R4(174), MC1_8_R3(183), MC1_9_R1(191), MC1_9_R2(192), MC1_10_R1(1101), MC1_11_R1(1111), MC1_12_R1(1121),
    MC1_13_R1(1131), MC1_13_R2(1132), MC1_14_R1(1141), MC1_15_R1(1151), MC1_16_R1(1161), MC1_16_R2(1162),
    MC1_16_R3(1163), MC1_17_R1(1171), MC1_18_R1(1181, true), MC1_18_R2(1182, true), MC1_19_R1(1191, true),
    MC1_19_R2(1192, true), MC1_19_R3(1193, true), MC1_20_R1(1201, true), MC1_20_R2(1202, true), MC1_20_R3(1203, true),
    MC1_20_R4(1204, true), MC1_21_R1(1211, true), MC1_21_R2(1212, true), MC1_21_R3(1213, true),
    MC1_21_R4(1214, true), MC1_21_R5(1215, true);

    private static MinecraftVersion version;
    private static Boolean hasGsonSupport;

    private final int versionId;
    private final boolean mojangMapping;

    // TODO: not nice
    @SuppressWarnings("serial")
    private static final Map<String, MinecraftVersion> VERSION_TO_REVISION = new HashMap<String, MinecraftVersion>() {
        {
            this.put("1.20",   MC1_20_R1);
            this.put("1.20.1", MC1_20_R1);
            this.put("1.20.2", MC1_20_R2);
            this.put("1.20.3", MC1_20_R3);
            this.put("1.20.4", MC1_20_R3);
            this.put("1.20.5", MC1_20_R4);
            this.put("1.20.6", MC1_20_R4);
            this.put("1.21",   MC1_21_R1);
            this.put("1.21.1", MC1_21_R1);
            this.put("1.21.2", MC1_21_R2);
            this.put("1.21.3", MC1_21_R2);
            this.put("1.21.4", MC1_21_R3);
            this.put("1.21.5", MC1_21_R4);
            this.put("1.21.6", MC1_21_R5);
            this.put("1.21.7", MC1_21_R5);
        }
    };

    MinecraftVersion(int versionId) {
        this(versionId, false);
    }

    MinecraftVersion(int versionId, boolean mojangMapping) {
        this.versionId = versionId;
        this.mojangMapping = mojangMapping;
    }

    /**
     * @return A simple comparable Integer, representing the version.
     */
    public int getVersionId() {
        return versionId;
    }

    /**
     * @return True if method names are in Mojang format and need to be remapped
     *         internally
     */
    public boolean isMojangMapping() {
        return mojangMapping;
    }

    /**
     * This method is required to hot-wire the plugin during mappings generation for
     * newer mc versions thanks to md_5 not used mojmap.
     *
     * @return
     */
    public String getPackageName() {
        if (this == UNKNOWN) {
            try {
                return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            } catch (Exception ex) {
                // ignore, paper without remap, will fail
            }
        }
        return this.name().replace("MC", "v");
    }

    /**
     * Returns true if the current versions is at least the given Version
     *
     * @param version The minimum version
     * @return
     */
    public static boolean isAtLeastVersion(MinecraftVersion version) {
        return getVersion().getVersionId() >= version.getVersionId();
    }

    /**
     * Returns true if the current versions newer (not equal) than the given version
     *
     * @param version The minimum version
     * @return
     */
    public static boolean isNewerThan(MinecraftVersion version) {
        return getVersion().getVersionId() > version.getVersionId();
    }

    public static MinecraftVersion getVersion() {
        if (version != null) {
            return version;
        }
        try {
            final String ver = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
            version = MinecraftVersion.valueOf(ver.replace("v", "MC"));
        } catch (Exception ex) {
            version = VERSION_TO_REVISION.getOrDefault(Bukkit.getServer().getBukkitVersion().split("-")[0],
                    MinecraftVersion.UNKNOWN);
        }

        return version;
    }

    public static String getNBTAPIVersion() {
        return "1.0.9";
    }

    /**
     * @return True, if Gson is usable
     */
    public static boolean hasGsonSupport() {
        if (hasGsonSupport != null) {
            return hasGsonSupport;
        }

        try {
            Class.forName("com.google.gson.Gson");
            hasGsonSupport = true;
        } catch (Exception ex) {
            hasGsonSupport = false;
        }

        return hasGsonSupport;
    }
}