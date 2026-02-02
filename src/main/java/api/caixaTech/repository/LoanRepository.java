package api.caixaTech.repository;

import api.caixaTech.model.LoanEntity;
import api.caixaTech.model.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<LoanEntity, Long> {
    List<LoanEntity> findByStatus(Status status);
}
