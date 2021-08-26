package com.example.springone2021;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ConferenceUserTests {

    @Test
    public void constructorCreatesCopy() {
        ConferenceUser user = new ConferenceUser();
        user.setUsername("user");
        user.setPassword("password");
        user.setSubmissions(List.of("Talk 1"));
        user.setSpeaker(true);
        user.setAdmin(true);
        ConferenceUser copy = new ConferenceUser(user);
        assertThat(user).usingRecursiveComparison().isEqualTo(copy);
    }
}
