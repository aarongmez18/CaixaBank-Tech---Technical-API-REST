package api.caixaTech.service.impl;

import api.caixaTech.controller.HealthController;
import api.caixaTech.dto.LoanRequestDTO;
import api.caixaTech.dto.LoanResponseDTO;
import api.caixaTech.dto.UpdateStatusDTO;
import api.caixaTech.exception.BadRequestException;
import api.caixaTech.exception.InvalidStateTransitionException;
import api.caixaTech.exception.NotFoundException;
import api.caixaTech.mapper.LoanMapper;
import api.caixaTech.model.LoanEntity;
import api.caixaTech.model.enums.Status;
import api.caixaTech.repository.LoanRepository;
import api.caixaTech.service.LoanService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class LoanServiceImpl implements LoanService {

    private final LoanMapper loanMapper;
    private final LoanRepository loanRepository;
    private static final Logger log = LoggerFactory.getLogger(LoanServiceImpl.class);

    public LoanServiceImpl(LoanRepository loanRepository, LoanMapper loanMapper) {
        this.loanRepository = loanRepository;
        this.loanMapper = loanMapper;
    }

    @Override
    public LoanResponseDTO create(LoanRequestDTO request) {
        log.info("LoanServiceImpl > create > INIT");
        LoanEntity entity = loanMapper.toEntity(request);

        entity.setStatus(Status.PENDING);
        entity.setCreatedAt(LocalDateTime.now());

        LoanEntity saved = loanRepository.save(entity);
        log.info("LoanServiceImpl > create > END");
        return loanMapper.toDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public LoanResponseDTO findById(Long id) {
        log.info("LoanServiceImpl > findById > INIT");
        LoanEntity entity = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan with id " + id + " not found"));
        log.info("LoanServiceImpl > findById > END");
        return loanMapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LoanResponseDTO> findAll(String status) {
        log.info("LoanServiceImpl > findAll > INIT");
        List<LoanEntity> entities;

        if (status == null || status.isBlank()) {
            entities = loanRepository.findAll();
        } else {
            Status parsed = parseStatus(status);
            entities = loanRepository.findByStatus(parsed);
        }

        log.info("LoanServiceImpl > findAll > END");
        return entities.stream().map(loanMapper::toDTO).toList();
    }

    @Override
    public LoanResponseDTO updateStatus(Long id, UpdateStatusDTO request) {
        log.info("LoanServiceImpl > updateStatus > INIT");
        LoanEntity entity = loanRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Loan with id " + id + " not found"));

        Status from = entity.getStatus();
        Status to = request.getStatus();

        validateTransition(from, to);

        entity.setStatus(to);
        log.info("LoanServiceImpl > updateStatus > END");
        return loanMapper.toDTO(loanRepository.save(entity));
    }

    // Parsea el estado rebcido por si viene en otro formato
    private Status parseStatus(String status) {
        try {
            return Status.valueOf(status.trim().toUpperCase());
        } catch (Exception ex) {
            throw new BadRequestException("Invalid status value: " + status);
        }
    }

    // Valida las posibles transacciones
    private void validateTransition(Status from, Status to) {
        if (from == Status.PENDING && (to == Status.APPROVED || to == Status.REJECTED)) return;
        if (from == Status.APPROVED && to == Status.CANCELLED) return;
        throw new InvalidStateTransitionException("Cannot change status from " + from + " to " + to);
    }
}
