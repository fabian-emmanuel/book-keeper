package com.findar.bookkeeper.utils;

import org.apache.commons.lang3.RandomStringUtils;

public interface UniqueRefUtil {
    static String generateUniqueRef(String prefix, int count) {
        String ref = RandomStringUtils.randomAlphanumeric(count);
        return String.format("%s_%s",prefix, ref.toUpperCase());
    }
}
