package ee.bitweb.testingsample.domain.datapoint.api;

import ee.bitweb.testingsample.common.trace.TraceIdCustomizerImpl;
import ee.bitweb.testingsample.domain.datapoint.DataPointHelper;
import ee.bitweb.testingsample.domain.datapoint.common.DataPoint;
import ee.bitweb.testingsample.domain.datapoint.common.DataPointRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GetListIntegrationTests {

    private static final String URI = "/data-points";

    private static final String REQUEST_ID = "ThisIsARequestId";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private DataPointRepository repository;

    @Test
    @Transactional
    void onValidRequestAndEmptyDatabaseShouldReturnSuccessfulEmptyResponse() throws Exception {

        mockMvc.perform(createDefaultRequest())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));


    }

    @Test
    @Transactional
    void onValidRequestShouldReturnSuccessfulResponse() throws Exception {
        DataPoint point1 = repository.save(DataPointHelper.create(1L));
        DataPoint point2 = repository.save(DataPointHelper.create(2L));

        mockMvc.perform(createDefaultRequest())
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]", aMapWithSize(5)))
                .andExpect(jsonPath("$[0].id", is(point1.getId().intValue())))
                .andExpect(jsonPath("$[0].externalId", is("external-id-1")))
                .andExpect(jsonPath("$[0].value", is("some-value-1")))
                .andExpect(jsonPath("$[0].comment", is("some-comment-1")))
                .andExpect(jsonPath("$[0].significance", is(1)))
                .andExpect(jsonPath("$[1]", aMapWithSize(5)))
                .andExpect(jsonPath("$[1].id", is(point2.getId().intValue())))
                .andExpect(jsonPath("$[1].externalId", is("external-id-2")))
                .andExpect(jsonPath("$[1].value", is("some-value-2")))
                .andExpect(jsonPath("$[1].comment", is("some-comment-2")))
                .andExpect(jsonPath("$[1].significance", is(0)));
    }


    private MockHttpServletRequestBuilder createDefaultRequest() {
        return get(URI)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .header(TraceIdCustomizerImpl.DEFAULT_HEADER_NAME, REQUEST_ID);
    }
}
