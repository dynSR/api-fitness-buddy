package com.dyns.api_fitness_buddy.controllers;

import com.dyns.api_fitness_buddy.domain.entities.Exercise;
import com.dyns.api_fitness_buddy.fixtures.impl.ExerciseFixture;
import com.dyns.api_fitness_buddy.services.ExerciseService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ExerciseControllerIntegrationTests {
    private final String apiPath = "/exercises";

    private final ExerciseService service;
    private final MockMvc mockMvc;
    private final ExerciseFixture fixture;
    private final ObjectMapper objectMapper;

    @Autowired
    public ExerciseControllerIntegrationTests(ExerciseService service, MockMvc mockMvc, ExerciseFixture fixture) {
        this.service = service;
        this.mockMvc = mockMvc;
        this.fixture = fixture;
        this.objectMapper = new ObjectMapper();
    }

    @Nested
    @DisplayName("Create exercise")
    class Create {
        private final Exercise exercise = fixture.getOne();
        private String exerciseAsJSON = "";

        @BeforeEach
        public void configureCreateTests() throws JsonProcessingException {
            exerciseAsJSON = objectMapper.writeValueAsString(exercise);
        }


        @Test
        @DisplayName("Should create an exercise and return HTTP status CREATED (201)")
        public void givenExercise_whenPostRequestIsSuccessful_thenReturnsHttpStatus201() throws Exception {
            mockMvc.perform(
                    post(apiPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exerciseAsJSON)
            ).andExpect(status().isCreated());
        }

        @Test
        @DisplayName("Should create an exercise and return an exercise as JSON")
        public void givenExercise_whenPostRequestIsSuccessful_thenReturnsExerciseAsJSON() throws Exception {
            mockMvc.perform(
                    post(apiPath)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exerciseAsJSON)
            ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should create an exercise and return its information")
        public void givenExercise_whenPostRequestIsSuccessful_thenReturnsPersistedExercise() throws Exception {
            mockMvc.perform(
                            post(apiPath)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(exerciseAsJSON)
                    ).andExpect(jsonPath("$.id").isNumber())
                    .andExpect(jsonPath("$.name").value("Exercise"));
        }
    }

    @Nested
    @DisplayName("Find all exercises")
    class GetAll {
        private final List<Exercise> exercises = fixture.getMany();

        @BeforeEach
        public void configureGetAllTests() {
            exercises.forEach(service::save);
        }

        @Test
        @DisplayName("Should return HTTP status OK (200)")
        public void givenExercises_whenGetRequestSucceeds_thenReturnsHttpStatus200() throws Exception {
            mockMvc.perform(
                    get(apiPath).contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should return the exercises found as JSON")
        public void givenExercises_whenGetRequestSucceeds_thenReturnsAsJson() throws Exception {
            mockMvc.perform(
                    get(apiPath).contentType(MediaType.APPLICATION_JSON)
            ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return a list of exercises with valid information")
        public void givenExercises_whenGetRequestSucceeds_thenReturnsExercises() throws Exception {
            mockMvc.perform(
                            get(apiPath).contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.length()").value(6))
                    .andExpect(jsonPath("$[0].id").value(1))
                    .andExpect(jsonPath("$[0].name").value("Exercise 0"));
        }
    }

    @Nested
    @DisplayName("Find one exercise")
    class GetOne {
        private String uriTemplate = "";

        @BeforeEach
        public void configureGetOneTests() {
            Exercise exercise = fixture.getOne();
            service.save(exercise);
            uriTemplate = apiPath + '/' + exercise.getId();
        }

        @Test
        @DisplayName("Should return HTTP status FOUND (302)")
        public void givenExercise_whenGetRequestSucceeds_thenReturnsHttpStatus302() throws Exception {
            mockMvc.perform(
                    get(uriTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isFound());
        }

        @Test
        @DisplayName("Should return HTTP status NOT_FOUND (404)")
        public void givenInvalidId_whenGetRequestFails_thenReturnsHttpStatus404() throws Exception {
            // Note that the valid id provided before each test is 1.
            mockMvc.perform(
                    get(apiPath + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should return one exercise as JSON")
        public void givenValidId_whenGetRequestSucceeds_thenReturnsAsJson() throws Exception {
            mockMvc.perform(
                    get(uriTemplate).contentType(MediaType.APPLICATION_JSON)
            ).andExpect(content().contentType(MediaType.APPLICATION_JSON));
        }

        @Test
        @DisplayName("Should return one exercise with valid information")
        public void givenValidId_whenGetRequestSucceeds_thenReturnsExercise() throws Exception {
            mockMvc.perform(
                            get(uriTemplate)
                                    .contentType(MediaType.APPLICATION_JSON)
                    ).andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Exercise"));
        }
    }

    @Nested
    @DisplayName("Full update exercise")
    class FullUpdate {
        protected String exerciseAsJSON = "";
        protected final String newName = "Updated";
        protected String uriTemplate = "";

        @BeforeEach
        public void configureUpdateTests() throws JsonProcessingException {
            Exercise updatedExercise = fixture.getOne();
            updatedExercise.setName(newName);
            service.save(updatedExercise);

            exerciseAsJSON = objectMapper.writeValueAsString(updatedExercise);

            uriTemplate = apiPath + '/' + updatedExercise.getId();
        }

        @Test
        @DisplayName("Should update an exercise and return HTTP status OK (200)")
        public void givenExercise_whenPutRequestSucceeds_thenReturnsHttpStatus200() throws Exception {
            mockMvc.perform(
                    put(uriTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exerciseAsJSON)
            ).andExpect(status().isOk());
        }

        @Test
        @DisplayName("Should update an exercise and return it as JSON")
        public void givenExercise_whenPutRequestSucceeds_thenReturnsTheExerciseAsJSON() throws Exception {
            mockMvc.perform(
                    put(uriTemplate)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exerciseAsJSON)
            ).andExpect(
                    MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
            );
        }

        @Test
        @DisplayName("Should be impossible to update and return HTTP status NOT_FOUND (404)")
        public void givenInvalidId_whenPutRequestFails_thenReturnsHttpStatus404() throws Exception {
            // Note that the valid id provided before each test is 1.
            mockMvc.perform(
                    put(apiPath + "/2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(exerciseAsJSON)
            ).andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("Should update an exercise and persist its new properties value")
        public void givenExercise_whenPutRequestSucceeds_thenReturnsTheExerciseWithValidInformation() throws Exception {
            mockMvc.perform(
                            put(uriTemplate)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(exerciseAsJSON)
                    ).andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value(newName));
        }
    }

    @Nested
    @DisplayName("Partial update exercise")
    class PartialUpdate extends FullUpdate {
//        private String exerciseAsJSON = "";
//        private final String newName = "Partial update";
//        private String uriTemplate = "";
//
//        @BeforeEach
//        public void configureUpdateTests() throws JsonProcessingException {
//            Exercise updatedExercise = fixture.getOne();
//            updatedExercise.setName(newName);
//            service.save(updatedExercise);
//
//            exerciseAsJSON = objectMapper.writeValueAsString(updatedExercise);
//
//            uriTemplate = apiPath + '/' + updatedExercise.getId();
//        }
//
//        @Test
//        @DisplayName("Should update an exercise and return HTTP status OK (200)")
//        public void givenExercise_whenUpdated_thenReturnsHttpStatus200() throws Exception {
//            mockMvc.perform(
//                    put(uriTemplate)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(exerciseAsJSON)
//            ).andExpect(status().isOk());
//        }
//
//        @Test
//        @DisplayName("Should update an exercise and return it as JSON")
//        public void givenExercise_whenUpdated_thenReturnsTheExerciseAsJSON() throws Exception {
//            mockMvc.perform(
//                    put(uriTemplate)
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(exerciseAsJSON)
//            ).andExpect(
//                    MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON)
//            );
//        }
//
//        @Test
//        @DisplayName("Should be impossible to update and return HTTP status NOT_FOUND (404)")
//        public void givenInvalidId_whenUpdated_thenReturnsHttpStatus404() throws Exception {
//            // Note that the valid id provided before each test is 1.
//            mockMvc.perform(
//                    put(apiPath + "/2")
//                            .contentType(MediaType.APPLICATION_JSON)
//                            .content(exerciseAsJSON)
//            ).andExpect(status().isNotFound());
//        }
//
//        @Test
//        @DisplayName("Should update an exercise and persist its new properties value")
//        public void givenExercise_whenUpdated_thenReturnsTheExerciseWithValidInformation() throws Exception {
//            mockMvc.perform(
//                            put(uriTemplate)
//                                    .contentType(MediaType.APPLICATION_JSON)
//                                    .content(exerciseAsJSON)
//                    ).andExpect(jsonPath("$.id").value(1))
//                    .andExpect(jsonPath("$.name").value(newName));
//        }
    }

    @Nested
    @DisplayName("Delete exercise")
    class Delete {
        private String uriTemplate = "";

        @BeforeEach
        public void configureUpdateTests() {
            Exercise deletedExercise = fixture.getOne();
            service.save(deletedExercise);

            uriTemplate = apiPath + '/' + deletedExercise.getId();
        }

        @Test
        @DisplayName("Should delete an exercise and return HTTP status NO_CONTENT (204)")
        public void givenValidId_whenDeleteRequestSucceeds_thenReturnsHttpStatus204() throws Exception {
            mockMvc.perform(
                    delete(uriTemplate)
            ).andExpect(status().isNoContent());
        }

        @Test
        @DisplayName("Should be impossible to delete exercise and return HTTP status NOT_FOUND (404)")
        public void givenInvalidId_whenDeleteRequestFails_thenReturnsHttpStatus404() throws Exception {
            // Note that the valid id provided before each test is 1.
            mockMvc.perform(
                    delete(apiPath + "/2")
            ).andExpect(status().isNotFound());

        }
    }
}
