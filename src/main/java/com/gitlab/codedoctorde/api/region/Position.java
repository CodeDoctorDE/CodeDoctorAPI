package com.gitlab.codedoctorde.api.region;

/**
 * @author CodeDoctorDE
 */
public class Position {
    private int posX, posY, posZ;

    public Position(final int POSX, final int POSY, final int POSZ) {
        posX = POSX;
        posY = POSY;
        posZ = POSZ;
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public int getPosZ() {
        return posZ;
    }
}
