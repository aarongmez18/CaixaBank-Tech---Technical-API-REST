package api.caixaTech.service;

import api.caixaTech.dto.LoanRequestDTO;
import api.caixaTech.dto.LoanResponseDTO;
import api.caixaTech.dto.UpdateStatusDTO;

import java.util.List;

public interface LoanService {
    LoanResponseDTO findById(Long id);
    List<LoanResponseDTO> findAll(String status);
    LoanResponseDTO create(LoanRequestDTO request);
    LoanResponseDTO updateStatus(Long id, UpdateStatusDTO request);
}
