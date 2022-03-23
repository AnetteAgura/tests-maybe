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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import javax.transaction.Transactional;

import static ee.bitweb.testingsample.domain.datapoint.DataPointHelper.create;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class GetByExternalIdIntegrationTests {

    private static final String URI = "/data-points/external-id";

    private static final String REQUEST_ID = "ThisIsARequestId";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataPointRepository repository;

    @Test
    @Transactional
    void onValidIdShouldReturnSuccessResponse() throws Exception {
        DataPoint point = repository.save(create(1L));
        mockMvc.perform(createDefaultRequest("external-id-1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", aMapWithSize(5)))
                .andExpect(jsonPath("$.id", is(point.getId().intValue())))
                .andExpect(jsonPath("$.externalId", is("external-id-1")))
                .andExpect(jsonPath("$.value", is("some-value-1")))
                .andExpect(jsonPath("$.comment", is("some-comment-1")))
                .andExpect(jsonPath("$.significance", is(1)));
    }



    @Test
    @javax.transaction.Transactional
    void onInvalidIdShouldReturnNotFound() throws Exception{

        mockMvc.perform(createDefaultRequest("external-id-none"))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", aMapWithSize(4)))
                .andExpect(jsonPath("$.id", is(startsWith(REQUEST_ID))))
                .andExpect(jsonPath("$.message", is("Entity DataPoint not found")))
                .andExpect(jsonPath("$.entity", is("DataPoint")))
                .andExpect(jsonPath("$.criteria[0].field", is("externalId")))
                .andExpect(jsonPath("$.criteria[0].value", is("external-id-none")));

    }



    private MockHttpServletRequestBuilder createDefaultRequest(String param) {
        return get(URI + "/" + param)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(TraceIdCustomizerImpl.DEFAULT_HEADER_NAME, REQUEST_ID);
    }
}
