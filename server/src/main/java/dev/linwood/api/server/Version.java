package dev.linwood.api.server;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class Version {
    private final int major;
    private final int minor;
    private final int revision;

    public Version(int major, int minor, int revision) {
        this.major = major;
        this.minor = minor;
        this.revision = revision;
    }

    public int getMajor() {
        return major;
    }

    public int getMinor() {
        return minor;
    }

    public static @NotNull Version getVersion() {
        // Get bukkit major.minor version
        String version = getBukkitVersion().substring(1);
        String[] versionSplit = version.split("_");
        int major = Integer.parseInt(versionSplit[0]);
        int minor = Integer.parseInt(versionSplit[1]);
        int revision = versionSplit.length > 2 ? Integer.parseInt(versionSplit[2].substring(1)) : 1;
        return new Version(major, minor, revision);

    }

    public static @NotNull String getBukkitVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    @Override
    public String toString() {
        return "v" + major + "_" + minor + "_R" + revision;
    }

    public boolean isBiggerThan(@NotNull Version version) {
        if (major > version.getMajor()) return true;
        if (major == version.getMajor()) return minor > version.getMinor();
        return false;
    }

    public boolean isLowerThan(@NotNull Version version) {
        if (major < version.getMajor()) return true;
        if (major == version.getMajor()) return minor < version.getMinor();
        return false;
    }
}