package com.findar.bookkeeper.constants;

public interface Regex {
    String VALID_PASSWORD = "^(?=[^a-z]*[a-z])(?=[^A-Z]*[A-Z])(?=\\D*\\d)(?=.*?[!#@$%&/()=?*\\-+_.:;,\\]\\[{}^])[A-Za-z0-9!#@$%&/()=?*\\-+_.:;,\\]\\[{}^]{8,30}$";
    String SQUARE_BRACKETS = "[\\[\\]]";
}
