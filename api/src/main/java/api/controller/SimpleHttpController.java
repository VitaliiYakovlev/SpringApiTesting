package api.controller;

import api.dto.CurrencyCourseToUserWithTime;
import api.dto.UpdateCaseDto;
import api.service.CaseUpdatorService;
import api.service.CurrencyCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import javax.validation.Valid;

@RestController
public class SimpleHttpController {

    private final CaseUpdatorService caseUpdatorService;
    private final CurrencyCourseService currencyCourseService;

    @Value("${parameter.greeting}")
    private String greeting;

    @Autowired
    public SimpleHttpController(CaseUpdatorService caseUpdatorService, CurrencyCourseService currencyCourseService) {
        this.caseUpdatorService = caseUpdatorService;
        this.currencyCourseService = currencyCourseService;
    }

    @GetMapping("/hello-world")
    public ResponseEntity<String> helloWorld() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("MyHeaderKey", "MyHeaderValue")
                .contentType(MediaType.TEXT_PLAIN)
                .body("Hello World");
    }

    @GetMapping("/redirect")
    public RedirectView redirectToHelloWorld() {
        return new RedirectView("/hello-world");
    }

    @GetMapping("/hello-world-1/{name}")
    public String helloWorld1(@PathVariable String name) {
        return "Hello " + name;
    }

    @GetMapping("/hello-world-2")
    public String helloWorld2(@RequestParam(name = "name") String name) {
        return greeting + name;
    }

    @PostMapping(value = "/update-case",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UpdateCaseDto updateCase(@RequestBody @Valid UpdateCaseDto updateCaseDto) {
        return caseUpdatorService.updateCase(updateCaseDto);
    }

    @GetMapping(value = "/course",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public CurrencyCourseToUserWithTime getCourse() {
        return currencyCourseService.getCourse();
    }
}
