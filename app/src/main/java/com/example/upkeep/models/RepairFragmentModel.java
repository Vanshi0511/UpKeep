package com.example.upkeep.models;

public class RepairFragmentModel {
    private boolean expand;

    public RepairFragmentModel() {
        expand = false;
    }

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }
}
