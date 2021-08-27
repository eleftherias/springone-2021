package com.example.springone2021;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class ConferenceControllerTests {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void getAboutReturnsConferenceInfo() throws Exception {
        this.mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(content().string("Join us online September 1–2!"));
    }

    @Test
    public void greetingReturnsHelloAndUsername() throws Exception {
        this.mockMvc.perform(get("/greeting").with(user("Ria")))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, Ria!"));
    }

    @Test
    public void greetingWhenUnauthenticatedUserReturns401() throws Exception {
        this.mockMvc.perform(get("/greeting"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void submissionsWhenUserIsSpeakerReturnsListOfSubmissions() throws Exception {
        ConferenceUser joe = new ConferenceUser();
        joe.setUsername("Joe");
        joe.setSubmissions(List.of("Getting Started with Spring Authorization Server"));
        joe.setSpeaker(true);
        this.mockMvc.perform(get("/submissions").with(user(new ConferenceUserService.ConferenceUserDetails(joe))))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0]", is("Getting Started with Spring Authorization Server")));
    }

    @Test
    public void submissionsWhenUserIsNotSpeakerReturns403() throws Exception {
        this.mockMvc.perform(get("/submissions").with(user("user").roles("ATTENDEE")))
                .andExpect(status().isForbidden());
    }

    @Test
    public void submissionsWhenUnauthenticatedUserReturn401() throws Exception {
        this.mockMvc.perform(get("/submissions"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postAboutWhenUserIsAdminThenUpdatesConferenceInfo() throws Exception {
        this.mockMvc.perform(post("/about")
                        .content("Join us online September 11-12!")
                        .with(csrf())
                        .with(user("admin").roles("ADMIN"))
                )
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/about"))
                .andExpect(status().isOk())
                .andExpect(content().string("Join us online September 11-12!"));
    }

    @Test
    public void postAboutWhenUserIsNotAdminThenReturns403() throws Exception {
        this.mockMvc.perform(post("/about")
                        .content("Join us online September 11-12!")
                        .with(csrf())
                        .with(user("speaker").roles("SPEAKER"))
                )
                .andExpect(status().isForbidden());
    }

    @Test
    public void postAboutWhenUnauthenticatedUserReturns401() throws Exception {
        this.mockMvc.perform(post("/about")
                        .content("Join us online September 11-12!")
                        .with(csrf())
                )
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void postAboutWithoutCsrfThenReturns403() throws Exception {
        this.mockMvc.perform(post("/about"))
                .andExpect(status().isForbidden());
    }

}