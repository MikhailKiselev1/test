package superkassa.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import superkassa.model.dto.request.ModifyRq;
import superkassa.model.dto.response.ModifyRs;
import superkassa.services.EntityService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/modify")
public class ModifyController {

    private final EntityService modifyService;

    @PostMapping
    public ResponseEntity<ModifyRs> postModify(@RequestBody ModifyRq request) {
        return modifyService.postModify(request);
    }
}
