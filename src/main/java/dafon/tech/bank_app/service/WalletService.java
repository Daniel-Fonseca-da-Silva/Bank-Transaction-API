package dafon.tech.bank_app.service;

import dafon.tech.bank_app.controller.dto.CreateWalletDto;
import dafon.tech.bank_app.entity.Wallet;
import dafon.tech.bank_app.exception.WalletDataAlreadyExistsException;
import dafon.tech.bank_app.repository.WalletRepository;
import org.springframework.stereotype.Service;

@Service
public class WalletService {

    private final WalletRepository walletRepository;

    public WalletService(WalletRepository walletRepository) {
        this.walletRepository = walletRepository;
    }

    public Wallet createWallet(CreateWalletDto dto) {

        var walletDb = walletRepository.findByNifOrEmail(dto.nif(), dto.email());
        if(walletDb.isPresent()) {
            throw new WalletDataAlreadyExistsException("NIF or Email already exists");
        }

        return walletRepository.save(dto.toWallet());
    }
}
