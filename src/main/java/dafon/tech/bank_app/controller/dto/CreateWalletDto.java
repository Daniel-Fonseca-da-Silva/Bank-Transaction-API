package dafon.tech.bank_app.controller.dto;

import dafon.tech.bank_app.entity.Wallet;
import dafon.tech.bank_app.entity.WalletType;
import jakarta.validation.constraints.*;

public record CreateWalletDto(@NotBlank  String fullName, @NotBlank String nif, @NotBlank String email, @NotBlank String password, @NotNull WalletType.Enum walletType) {
    public Wallet toWallet() {
        Wallet wallet = new Wallet(
                fullName,
                nif,
                email,
                null, // password will be set securely
                walletType.get()
        );
        wallet.setPassword(password);
        return wallet;
    }
}
