package com.gitlab.codedoctorde.api.region;


/**
 * @author CodeDoctorDE
 */
public class Selection {
    private Position pos1;
    private Position pos2;

    public Selection(final Position POS1, final Position POS2) {
        pos1 = POS1;
        pos2 = POS2;
    }

    public Position getPos1() {
        return pos1;
    }

    public Position getPos2() {
        return pos2;
    }
}
