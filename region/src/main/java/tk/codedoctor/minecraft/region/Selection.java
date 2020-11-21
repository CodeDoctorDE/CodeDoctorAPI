package tk.codedoctor.minecraft.region;


import org.bukkit.Location;

/**
 * @author CodeDoctorDE
 */
public class Selection {
    private Location pos1;
    private Location pos2;

    public Selection(final Location pos1, final Location pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public Location getPos1() {
        return pos1;
    }

    public void setPos1(Location pos1) {
        this.pos1 = pos1;
    }

    public Location getPos2() {
        return pos2;
    }

    public void setPos2(Location pos2) {
        this.pos2 = pos2;
    }

    public boolean inSelection(Location location) {
        double minX = Math.min(pos1.getX(), pos2.getX());
        double minY = Math.min(pos1.getY(), pos2.getY());
        double minZ = Math.min(pos1.getZ(), pos2.getZ());

        double maxX = Math.max(pos1.getX(), pos2.getX());
        double maxY = Math.max(pos1.getY(), pos2.getY());
        double maxZ = Math.max(pos1.getZ(), pos2.getZ());

        return (minX <= location.getX() && maxX >= location.getX() &&
                minY <= location.getY() && maxY >= location.getY() &&
                minZ <= location.getZ() && maxZ >= location.getZ());
    }
}
