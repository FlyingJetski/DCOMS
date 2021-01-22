package com.company.models;

public class Sort {
    private boolean asc;
    private SortOption option;

    public Sort(boolean order, SortOption option) {
        this.asc = asc;
        this.option = option;
    }

    public boolean isAsc() {
        return asc;
    }

    public void setAsc(boolean asc) {
        this.asc = asc;
    }

    public SortOption getOption() {
        return option;
    }

    public void setOption(SortOption option) {
        this.option = option;
    }
}
