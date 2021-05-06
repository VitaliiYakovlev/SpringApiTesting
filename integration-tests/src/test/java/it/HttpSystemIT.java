package it;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import dto.UpdateCaseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.stream.Stream;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HttpSystemIT {

    private final TestRestTemplate testRestTemplate = new TestRestTemplate();

    private static Stream<Arguments> getPostRequestTestParams() {
        return Stream.of(
                Arguments.arguments(new UpdateCaseModel("aaa", "bbb"),
                        new UpdateCaseModel("AAA", "bbb")),
                Arguments.arguments(new UpdateCaseModel("CCC", "DDD"),
                        new UpdateCaseModel("CCC", "ddd")),
                Arguments.arguments(new UpdateCaseModel("eee", "FFF"),
                        new UpdateCaseModel("EEE", "fff")),
                Arguments.arguments(new UpdateCaseModel("GGG", "hhh"),
                        new UpdateCaseModel("GGG", "hhh"))
        );
    }

    @Test
    void helloWorldTest() {
        String url = "http://localhost:8081/hello-world";

        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.TEXT_PLAIN, response.getHeaders().getContentType());
        assertEquals(Collections.singletonList("MyHeaderValue"), response.getHeaders().get("MyHeaderKey"));
        assertEquals("Hello World", response.getBody());
    }

    @Test
    void redirectTest() {
        String url = "http://localhost:8081/redirect";

        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
        assertEquals(Collections.singletonList("http://localhost:8081/hello-world"), response.getHeaders().get("Location"));
    }

    @ParameterizedTest(name = "helloWorld1 test with {0}")
    @ValueSource(strings = {"name1", "name2", "name3"})
    void helloWorld1Test(String name) {
        String url = "http://localhost:8081/hello-world-1/" + name;

        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Hello " + name, response.getBody());
    }

    @ParameterizedTest(name = "helloWorld2 test with {0}")
    @ValueSource(strings = {"name1", "name2", "name3"})
    void helloWorld2Test(String name) {
        String url = "http://localhost:8081/hello-world-2?name=" + name;

        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("helloTest " + name, response.getBody());
    }

    @ParameterizedTest(name = "testing of post request")
    @MethodSource("getPostRequestTestParams")
    void postMethodTest(UpdateCaseModel request, UpdateCaseModel exp) {
        String url = "http://localhost:8081/update-case";

        ResponseEntity<UpdateCaseModel> response = testRestTemplate.postForEntity(url, request, UpdateCaseModel.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(exp, response.getBody());
    }

    @Test
    void requestToInternalModuleTest() {
        String url = "http://localhost:8081/course";
        String responseBody = "[{\"ccy\":\"USD\",\"base_ccy\":\"UAH\",\"buy\":\"27.55000\",\"sale\":\"27.95000\"}," +
                "{\"ccy\":\"EUR\",\"base_ccy\":\"UAH\",\"buy\":\"32.40000\",\"sale\":\"33.01000\"}," +
                "{\"ccy\":\"RUR\",\"base_ccy\":\"UAH\",\"buy\":\"0.35500\",\"sale\":\"0.38500\"}," +
                "{\"ccy\":\"BTC\",\"base_ccy\":\"USD\",\"buy\":\"55245.2534\",\"sale\":\"61060.5432\"}]";

        WireMockServer wireMockServer = new WireMockServer(options().port(9081));
        wireMockServer.stubFor(WireMock.get(
                WireMock.urlEqualTo("/exchange"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json; charset=UTF-8")
                        .withBody(responseBody)
                ));
        wireMockServer.start();
        long beforeRequest = System.currentTimeMillis();

        ResponseEntity<String> response = testRestTemplate.getForEntity(url, String.class);

        long afterRequest = System.currentTimeMillis();
        wireMockServer.stop();
        ///Parse response and make assertions
    }
}
