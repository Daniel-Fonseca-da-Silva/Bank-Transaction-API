package dafon.tech.bank_app.service;

import dafon.tech.bank_app.controller.dto.TransferDto;
import dafon.tech.bank_app.entity.Transfer;
import dafon.tech.bank_app.entity.Wallet;
import dafon.tech.bank_app.exception.InsufficientBalanceException;
import dafon.tech.bank_app.exception.TransferNotAllowedForWalletTypeException;
import dafon.tech.bank_app.exception.TransferNotAuthorizedException;
import dafon.tech.bank_app.exception.WalletNotFoundException;
import dafon.tech.bank_app.repository.TransferRepository;
import dafon.tech.bank_app.repository.WalletRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TransferService {

    private final TransferRepository transferRepository;
    private final AuthorizationService authorizationService;
    private final NotificationService notificationService;
    private final WalletRepository walletRepository;

    public TransferService(TransferRepository transferRepository, AuthorizationService authorizationService, NotificationService notificationService, WalletRepository walletRepository) {
        this.transferRepository = transferRepository;
        this.authorizationService = authorizationService;
        this.notificationService = notificationService;
        this.walletRepository = walletRepository;
    }

    @Transactional
    public Transfer transfer(@Valid TransferDto transferDto) {
       var sender = walletRepository.findById(transferDto.payer())
               .orElseThrow(() -> new WalletNotFoundException(transferDto.payer()));

        var receiver = walletRepository.findById(transferDto.payee())
                .orElseThrow(() -> new WalletNotFoundException(transferDto.payee()));

       validateTransfer(transferDto, sender);
       sender.debit(transferDto.value());
       receiver.credit(transferDto.value());

       var transfer = new Transfer(sender, receiver, transferDto.value());

       walletRepository.save(sender);
       walletRepository.save(receiver);
       var transferResult = transferRepository.save(transfer);

        CompletableFuture.runAsync(() -> notificationService.sendNotification(transferResult));

       return transferResult;
    }

    private void validateTransfer(@Valid TransferDto transferDto, Wallet sender) {
        if (!sender.isTransferAllowedForWalletType()) {
            throw new TransferNotAllowedForWalletTypeException();
        }

        if (!sender.isBalanceEqualOrGreaterThan(transferDto.value())) {
           throw new InsufficientBalanceException();
        }

        if (!authorizationService.isAuthorized(transferDto)) {
            throw new TransferNotAuthorizedException();
        }
    }

}
