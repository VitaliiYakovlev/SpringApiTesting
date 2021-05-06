package api.service;

import api.dto.CurrencyCourseFromPbDto;
import api.dto.CurrencyCourseToUser;
import api.dto.CurrencyCourseToUserWithTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CurrencyCourseService {

    private final RestTemplate restTemplate;

    @Value("${parameter.url.exchange}")
    private String exchangeUrl;

    public CurrencyCourseService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public CurrencyCourseToUserWithTime getCourse() {
        CurrencyCourseFromPbDto[] response = restTemplate.getForObject(exchangeUrl, CurrencyCourseFromPbDto[].class);
        long time = System.currentTimeMillis();
        Set<CurrencyCourseToUser> course = Arrays.stream(response)
                .map(CurrencyCourseToUser::new)
                .collect(Collectors.toUnmodifiableSet());
        return new CurrencyCourseToUserWithTime(course, time);
    }
}
