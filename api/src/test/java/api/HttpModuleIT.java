package api;

import api.dto.CurrencyCourseFromPbDto;
import api.dto.CurrencyCourseToUser;
import api.dto.CurrencyCourseToUserWithTime;
import api.dto.UpdateCaseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.client.RestTemplate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "parameter.greeting=helloTest ",
        "parameter.url.exchange=http://course-url.com"
})
public class HttpModuleIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RestTemplate restTemplate;

    private static Stream<Arguments> getPostRequestTestParams() {
        return Stream.of(
                Arguments.arguments(new UpdateCaseDto("aaa", "bbb"),
                        new UpdateCaseDto("AAA", "bbb")),
                Arguments.arguments(new UpdateCaseDto("CCC", "DDD"),
                        new UpdateCaseDto("CCC", "ddd")),
                Arguments.arguments(new UpdateCaseDto("eee", "FFF"),
                        new UpdateCaseDto("EEE", "fff")),
                Arguments.arguments(new UpdateCaseDto("GGG", "hhh"),
                        new UpdateCaseDto("GGG", "hhh"))
        );
    }

    @Test
    void helloWorldTest() throws Exception {
        String mapping = "/hello-world";

        mockMvc.perform(get(mapping))
                .andExpect(status().isOk())
                .andExpect(header().string("MyHeaderKey", "MyHeaderValue"))
                .andExpect(content().contentType(MediaType.TEXT_PLAIN))
                .andExpect(content().string("Hello World"));
    }

    @Test
    void redirectTest() throws Exception {
        String mapping = "/redirect";

        mockMvc.perform(get(mapping))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/hello-world"));
    }

    @ParameterizedTest(name = "helloWorld1 test with {0}")
    @ValueSource(strings = {"name1", "name2", "name3"})
    void helloWorld1Test(String name) throws Exception {
        String mapping = "/hello-world-1/" + name;

        mockMvc.perform(get(mapping))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello " + name));
    }

    @ParameterizedTest(name = "helloWorld2 test with {0}")
    @ValueSource(strings = {"name1", "name2", "name3", ""})
    void helloWorld2Test(String name) throws Exception {
        String mapping = "/hello-world-2?name=" + name;

        mockMvc.perform(get(mapping))
                .andExpect(status().isOk())
                .andExpect(content().string("helloTest " + name));
    }

    @ParameterizedTest(name = "testing of post request")
    @MethodSource("getPostRequestTestParams")
    void postMethodTest(UpdateCaseDto request,
                        UpdateCaseDto exp) throws Exception {
        String mapping = "/update-case";
        String requestBody = objectMapper.writeValueAsString(request);
        String expResponseBody = objectMapper.writeValueAsString(exp);

        mockMvc.perform(
                post(mapping)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(content().json(expResponseBody));
    }

    @Test
    void requestToExternalModule() throws Exception {
        String mapping = "/course";
        CurrencyCourseFromPbDto cc1 = new CurrencyCourseFromPbDto("USD", "UAH", "27.60000", "28.02000");
        CurrencyCourseFromPbDto cc2 = new CurrencyCourseFromPbDto("BTC", "USD", "53285.7771", "58894.8063");
        CurrencyCourseFromPbDto[] responseFromApi = new CurrencyCourseFromPbDto[] { cc1, cc2 };
        Set<CurrencyCourseToUser> exp = new HashSet<>();
        exp.add(new CurrencyCourseToUser(cc1));
        exp.add(new CurrencyCourseToUser(cc2));

        Mockito.when(restTemplate.getForObject("http://course-url.com", CurrencyCourseFromPbDto[].class))
                .thenReturn(responseFromApi);
        long timeBeforeRequest = System.currentTimeMillis();

        MvcResult result = mockMvc.perform(get(mapping))
                .andExpect(status().isOk())
                .andReturn();

        long timeAfterRequest = System.currentTimeMillis();
        String responseBody = result.getResponse().getContentAsString();
        CurrencyCourseToUserWithTime responseObj = objectMapper.readValue(responseBody, CurrencyCourseToUserWithTime.class);

        assertEquals(exp, responseObj.getCourse());
        assertTrue(timeBeforeRequest <= responseObj.getTimestamp() && timeAfterRequest >= responseObj.getTimestamp());
    }
}
