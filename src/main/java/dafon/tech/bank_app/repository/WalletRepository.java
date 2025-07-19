package dafon.tech.bank_app.repository;

import dafon.tech.bank_app.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByNifOrEmail(String nif, String email);
}
