package com.github.codedoctorde.api.region;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Region {
    private final List<Selection> selections = new ArrayList<>();

    public @NotNull List<Selection> getSelections() {
        return selections;
    }
}
