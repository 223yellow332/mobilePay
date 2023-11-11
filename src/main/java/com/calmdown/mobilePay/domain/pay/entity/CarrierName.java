package com.calmdown.mobilePay.domain.pay.entity;

import java.util.Arrays;

public enum CarrierName {
    SK("SK"),
    KT("KT"),
    LGU("LGU");

    private final String str;

    CarrierName(String str) {
        this.str = str;
    }

    public String str() {
        return str;
    }

    public static CarrierName valueOfStr(String str) {
        return Arrays.stream(values())
                .filter(value -> value.str.equalsIgnoreCase(str))
                .findAny()
                .orElse(null);
    }
}
