package org.example.user;

import org.apache.commons.lang3.RandomStringUtils;

public class UserGenerator {

    public User getDefault() {
        return new User("kate_3189@mail.ru", "qwerty", "Kate");
    }

    public User randomData() {
        return new User(RandomStringUtils.randomAlphanumeric(8) + "@mail.ru", "qwerty", "Star123");
    }

    public User loginData() {
        return new User("kate_310101@mail.ru", "qwerty");
    }

    public User notFullData() {
        return new User("kate_3199@mail.ru", "");
    }
}
