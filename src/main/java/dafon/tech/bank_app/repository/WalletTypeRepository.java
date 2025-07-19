package dafon.tech.bank_app.repository;

import dafon.tech.bank_app.entity.WalletType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletTypeRepository extends JpaRepository<WalletType, Long> {
}
