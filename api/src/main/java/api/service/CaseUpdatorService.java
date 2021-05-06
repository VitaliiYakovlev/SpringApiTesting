package api.service;

import api.dto.UpdateCaseDto;
import org.springframework.stereotype.Service;

@Service
public class CaseUpdatorService {

    public UpdateCaseDto updateCase(UpdateCaseDto input) {
        return new UpdateCaseDto(
                input.getToUpperCase().toUpperCase(),
                input.getToLowerCase().toLowerCase()
        );
    }
}
