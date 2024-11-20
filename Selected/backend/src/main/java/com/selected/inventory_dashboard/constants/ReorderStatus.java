package com.selected.inventory_dashboard.constants;

public enum ReorderStatus {
    REORDERED,FAILED,FULFILLED;

    public static ReorderStatus getReorderStatusFromNumber(final Integer number) {
        return switch (number) {
            case 0 -> FAILED;
            case 1 -> REORDERED;
            case 2 -> FULFILLED;
            default -> throw new IllegalStateException("Unexpected value: " + number);
        };
    }
}
