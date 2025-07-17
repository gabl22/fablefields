package me.gabl.fablefields.player;

public enum ActionLayer {
    BASE("base"), BOWLHAIR("bowlhair"), CURLYHAIR("curlyhair"), LONGHAIR("longhair"), MOPHAIR("mophair"), SHORTHAIR(
        "shorthair"), SPIKEYHAIR("spikeyhair"), TOOLS("tools"),
    ;

    public final String id;

    ActionLayer(String id) {
        this.id = id;
    }
}
