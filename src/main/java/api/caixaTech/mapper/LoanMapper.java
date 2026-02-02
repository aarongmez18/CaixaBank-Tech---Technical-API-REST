package api.caixaTech.mapper;

import api.caixaTech.dto.LoanRequestDTO;
import api.caixaTech.dto.LoanResponseDTO;
import api.caixaTech.model.LoanEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = LocalDateTime.class)
public interface LoanMapper {

    LoanEntity toEntity(LoanRequestDTO dto);
    LoanResponseDTO toDTO(LoanEntity entity);
}
