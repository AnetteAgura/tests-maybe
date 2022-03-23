package ee.bitweb.testingsample.domain.datapoint.api;


import ee.bitweb.testingsample.common.trace.TraceIdCustomizerImpl;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class CreateIntegrationTests {

    private static final String URI = "/data-points";

    private static final String REQUEST_ID = "ThisIsARequestId";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataPointRepository repository;

    @Test
    @Transactional
    void onMinimalValidPayloadShouldCreatePointAndReturnSuccess() throws Exception {
        JSONObject payload = createValidMinimalPayload(123L);

        mockMvc.perform(createDefaultRequest(payload))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.externalId", is("external-id-123")))
                .andExpect(jsonPath("$.value", is("some-value-123")))
                .andExpect(jsonPath("$.comment",nullValue()))
                .andExpect(jsonPath("$.significance", is(1)));

        DataPoint point = repository.findAll().get(0);

        assertAll(
                () -> Assertions.assertEquals("external-id-123", point.getExternalId()),
                () -> Assertions.assertEquals("some-value-123", point.getValue()),
                () -> Assertions.assertNull(point.getComment()),
                () -> Assertions.assertEquals(1, point.getSignificance())
        );
    }

    @Test
    @Transactional
    void onFullValidPayloadShouldCreatePointAndReturnSuccess() throws Exception {
        JSONObject payload = createValidFullPayload(123L);

        mockMvc.perform(createDefaultRequest(payload))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.externalId", is("external-id-123")))
                .andExpect(jsonPath("$.value", is("some-value-123")))
                .andExpect(jsonPath("$.comment", is("some-comment-123")))
                .andExpect(jsonPath("$.significance", is(1)));

        DataPoint point = repository.findAll().get(0);
        assertAll(
                () -> Assertions.assertEquals("external-id-123", point.getExternalId()),
                () -> Assertions.assertEquals("some-value-123", point.getValue()),
                () -> Assertions.assertEquals("some-comment-123", point.getComment()),
                () -> Assertions.assertEquals(1, point.getSignificance())
        );
    }

    @Test
    @Transactional
    void onMalformedPayLoadShouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(TraceIdCustomizerImpl.DEFAULT_HEADER_NAME, REQUEST_ID)
                .content("5645gdf");

        mockMvc.perform(mockMvcBuilder)
                .andDo((print()))
                .andExpect(jsonPath("$", aMapWithSize(2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", is(startsWith(REQUEST_ID))))
                .andExpect(jsonPath("$.message", is("MESSAGE_NOT_READABLE")));

        Assertions.assertEquals(0, repository.count());

    }

    @Test
    @Transactional
    void onEmptyRequestShouldReturnBadRequest() throws Exception {
        mockMvc.perform(createDefaultRequest(new JSONObject()))
                .andDo(print())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", is(startsWith(REQUEST_ID))))
                .andExpect(jsonPath("$.message", is("INVALID_ARGUMENT")))
                .andExpect(jsonPath("$.errors", hasSize(3)))
                .andExpect(jsonPath("$.errors[0]", aMapWithSize(3)))
                .andExpect(jsonPath("$.errors[0].field", is("externalId")))
                .andExpect(jsonPath("$.errors[0].reason", is("NotBlank")))
                .andExpect(jsonPath("$.errors[0].message", is("must not be blank")))
                .andExpect(jsonPath("$.errors[1]", aMapWithSize(3)))
                .andExpect(jsonPath("$.errors[1].field", is("significance")))
                .andExpect(jsonPath("$.errors[1].reason", is("NotNull")))
                .andExpect(jsonPath("$.errors[1].message", is("must not be null")))
                .andExpect(jsonPath("$.errors[2]", aMapWithSize(3)))
                .andExpect(jsonPath("$.errors[2].reason", is("NotBlank")))
                .andExpect(jsonPath("$.errors[2].field", is("value")))
                .andExpect(jsonPath("$.errors[2].message", is("must not be blank")));
    }

    @Test
    @Transactional
    void onNegativeSignificanceShouldReturnBadRequest() throws Exception {
        JSONObject payload = createValidFullPayload(-1L);

        mockMvc.perform(createDefaultRequest(payload))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(startsWith("ThisIsARequestId_"))))
                .andExpect(jsonPath("$.message", is("INVALID_ARGUMENT")))
                .andExpect(jsonPath("$.errors[0]", aMapWithSize(3)))
                .andExpect(jsonPath("$.errors[0].field", is("significance")))
                .andExpect(jsonPath("$.errors[0].reason", is("Positive")))
                .andExpect(jsonPath("$.errors[0].message", is("must be greater than 0")));

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    @Transactional
    void onFloatingPointShouldReturnBadRequest() throws Exception {
        JSONObject payload = createValidFullPayload(123L);

        payload.put("significance", 1.5);
        mockMvc.perform(createDefaultRequest(payload))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$", aMapWithSize(3)))
                .andExpect(jsonPath("$.id", is(startsWith("ThisIsARequestId_"))))
                .andExpect(jsonPath("$.message", is("INVALID_ARGUMENT")))
                .andExpect(jsonPath("$.errors[0]", aMapWithSize(3)))
                .andExpect(jsonPath("$.errors[0].field", is("significance")))
                .andExpect(jsonPath("$.errors[0].reason", is("InvalidFormat")))
                .andExpect(jsonPath("$.errors[0].message", is(startsWith("Value not recognized as integer"))));

        Assertions.assertEquals(0, repository.count());
    }

    @Test
    @Transactional
    void onDuplicateExternalIdShouldReturnConflict() throws Exception {
        repository.save(createDataPoint(123L));
        JSONObject payload = createValidFullPayload(123L);
        mockMvc.perform(createDefaultRequest(payload))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", is(startsWith("ThisIsARequestId_"))))
                .andExpect(jsonPath("$.message", is("Cannot persist data point as external id already exists")))
                .andExpect(jsonPath("$.entity", is("DataPoint")))
                .andExpect(jsonPath("$.criteria[0]", aMapWithSize(2)))
                .andExpect(jsonPath("$.criteria[0].field", is("externalId")))
                .andExpect(jsonPath("$.criteria[0].value", is("external-id-123")));
    }

    private MockHttpServletRequestBuilder createDefaultRequest(JSONObject payload) {
        return post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(TraceIdCustomizerImpl.DEFAULT_HEADER_NAME, REQUEST_ID)
                .content(payload.toString());
    }

    private JSONObject createValidFullPayload(Long id) {
        JSONObject payload = createValidMinimalPayload(id);

        payload.put("comment", "some-comment-" + id);

        return payload;
    }

    private JSONObject createValidMinimalPayload(Long id) {
        JSONObject payload = new JSONObject();

        payload.put("externalId", "external-id-" + id);
        payload.put("value", "some-value-" + id);
        payload.put("significance", (id % 2));

        return payload;
    }

    private DataPoint createDataPoint(Long id) {
        DataPoint p = new DataPoint();
        p.setExternalId("external-id-" + id);
        p.setValue("initial-some-value-" + id);
        p.setComment("initial-some-commend-" + id);
        p.setSignificance(id.intValue());

        return p;
    }
}