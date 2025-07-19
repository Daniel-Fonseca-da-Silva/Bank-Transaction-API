package dafon.tech.bank_app.service;

import dafon.tech.bank_app.client.AuthorizationClient;
import dafon.tech.bank_app.controller.dto.TransferDto;
import dafon.tech.bank_app.exception.BankException;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private final AuthorizationClient authorizationClient;

    public AuthorizationService(AuthorizationClient authorizationClient) {
        this.authorizationClient = authorizationClient;
    }

    public boolean isAuthorized(@Valid TransferDto transfer) {
        var resp = authorizationClient.isAuthorized();

        if(resp.getStatusCode().isError()) {
            throw new BankException();
        }

        return resp.getBody().authorized();
    }
}
