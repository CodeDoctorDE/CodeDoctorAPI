package tk.codedoctor.minecraft.region;

import java.util.ArrayList;
import java.util.List;

/**
 * @author CodeDoctorDE
 */
public class Region {
    private final List<Selection> selections = new ArrayList<>();

    public List<Selection> getSelections() {
        return selections;
    }
}
