package dev.linwood.api.server;

import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public class Version {
    private final int major;
    private final int minor;
    private final int revision;

    public Version(String version) {
        version = version.toLowerCase();
        version = version.replace("v", "");
        version = version.replace("_", ".");
        version = version.replace("R", "");
        version = version.replace("-", ".");
        String[] versionSplit = version.split("\\.");
        major = Integer.parseInt(versionSplit[0]);
        minor = Integer.parseInt(versionSplit[1]);
        revision = versionSplit.length > 2 ? Integer.parseInt(versionSplit[2]) : 1;
    }

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

    public static @NotNull String getBukkitVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().substring(23);
    }

    public static @NotNull Version getCurrentVersion() {
        return new Version(getBukkitVersion());
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