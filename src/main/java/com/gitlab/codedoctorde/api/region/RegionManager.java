package com.gitlab.codedoctorde.api.region;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class RegionManager {
    private List<Region> regions = new ArrayList<>();
    private List<Flag> flags = new ArrayList<>();

    public List<Flag> getFlags() {
        return flags;
    }

    public List<Region> getRegions() {
        return regions;
    }
}
