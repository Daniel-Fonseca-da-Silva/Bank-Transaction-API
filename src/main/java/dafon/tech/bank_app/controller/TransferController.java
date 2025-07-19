package dafon.tech.bank_app.controller;

import dafon.tech.bank_app.controller.dto.TransferDto;
import dafon.tech.bank_app.entity.Transfer;
import dafon.tech.bank_app.service.TransferService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransferController {

    private final TransferService transferService;

    public TransferController(TransferService transferService) {
        this.transferService = transferService;
    }

    @PostMapping("/transfer")
    public ResponseEntity<Transfer> transfer(@RequestBody @Valid TransferDto dto) {

        var resp = transferService.transfer(dto);
        return ResponseEntity.ok(resp);
    }

}
