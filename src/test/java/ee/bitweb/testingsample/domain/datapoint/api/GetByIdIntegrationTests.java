package ee.bitweb.testingsample.domain.datapoint.api;

import ee.bitweb.testingsample.common.trace.TraceIdCustomizerImpl;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static ee.bitweb.testingsample.domain.datapoint.DataPointHelper.create;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetByIdIntegrationTests {

    private static final String URI = "/data-points";

    private static final String REQUEST_ID = "ThisIsARequestId";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataPointRepository repository;

    @Test
    @Transactional
    void onValidIdShouldReturnSuccessResponse() throws Exception {
        mockMvc.perform(createDefaultRequest("1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)))
                .andExpect(jsonPath("$.id", notNullValue()))
                .andExpect(jsonPath("$.externalId", notNullValue()))
                .andExpect(jsonPath("$.value", notNullValue()))
                .andExpect(jsonPath("$.comment", notNullValue()))
                .andExpect(jsonPath("$.significance", notNullValue()));
    }

    @Test
    @Transactional
    void OnInvalidShouldReturnNotFound() throws  Exception {
        mockMvc.perform(createDefaultRequest("2"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", startsWith(("ThisIsARequestId_"))))
                .andExpect(jsonPath("$.message", is("Entity DataPoint not found")))
                .andExpect(jsonPath("$.entity", is("DataPoint")))
                .andExpect(jsonPath("$.criteria[0].field", is("id")))
                .andExpect(jsonPath("$.criteria[0].value", is("2")));


    }

    @Test
    @Transactional
    void onInvalidNegativeIdShouldReturnNotFound() throws Exception {
        mockMvc.perform(createDefaultRequest("-1"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", is(startsWith("ThisIsARequestId"))))
                .andExpect(jsonPath("$.message", is("Entity DataPoint not found")))
                .andExpect(jsonPath("$.entity", is("DataPoint")))
                .andExpect(jsonPath("$.criteria[0].field", is("id")))
                .andExpect(jsonPath("$.criteria[0].value", is("-1")));


    }

    @Test
    @Transactional
    void onMalformedIdShouldReturnBadRequest() throws Exception {
        MockHttpServletRequestBuilder mockMvcBuilder = post(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(TraceIdCustomizerImpl.DEFAULT_HEADER_NAME, "-1")
                .content("");

        mockMvc.perform(mockMvcBuilder)
                .andDo((print()))
                .andExpect(jsonPath("$", aMapWithSize(2)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.id", is(startsWith("-1"))))
                .andExpect(jsonPath("$.message", is("MESSAGE_NOT_READABLE")));

        Assertions.assertEquals(0, repository.count());

    }
    private MockHttpServletRequestBuilder createDefaultRequest(String param) {
        return get(URI + "/" + param)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(TraceIdCustomizerImpl.DEFAULT_HEADER_NAME, REQUEST_ID);
    }
}