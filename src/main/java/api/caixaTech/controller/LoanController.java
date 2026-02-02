package api.caixaTech.controller;

import java.net.URI;
import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.validation.Valid;
import api.caixaTech.dto.LoanRequestDTO;
import api.caixaTech.dto.UpdateStatusDTO;
import api.caixaTech.service.LoanService;
import api.caixaTech.dto.LoanResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
@Tag(name = "Loans", description = "Endpoints to manage loan requests")
public class LoanController {

    private final LoanService loanService;
    private static final Logger log = LoggerFactory.getLogger(LoanController.class);

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    // Devuelve un préstamo solicitado por su ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a loan request by id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loan found",
                    content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content)
    })
    public ResponseEntity<LoanResponseDTO> getLoanById(@PathVariable Long id) {
        log.info("LoanControler > getLoanById > INIT");
        return ResponseEntity.ok(loanService.findById(id));
    }

    // Devuelve el listado completo de todos los préstamos solicitados, además funciona como filtro según el estado del préstamo
    @GetMapping
    @Operation(summary = "List loan requests", description = "Optional filter by status")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Loans returned"),
            @ApiResponse(responseCode = "400", description = "Invalid status value", content = @Content)
    })
    public ResponseEntity<List<LoanResponseDTO>> getLoans(@RequestParam(value = "status", required = false) String status) {
        log.info("LoanControler > getLoans > INIT");
        return ResponseEntity.ok(loanService.findAll(status));
    }

    // Crea una nueva solicitud de préstamo
    @PostMapping
    @Operation(summary = "Create a new loan request")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Loan created"),
            @ApiResponse(responseCode = "400", description = "Validation error", content = @Content)
    })
    public ResponseEntity<String> createLoan(@Valid @RequestBody LoanRequestDTO request) {
        log.info("LoanControler > createLoan > INIT");
        LoanResponseDTO created = loanService.create(request);
        return ResponseEntity
                .created(URI.create("/caixabank/loans/" + created.getId()))
                .body("Loan request created successfully.");
    }

    // Modifica el estado del préstamo solicitado
    @PatchMapping("/status/{id}")
    @Operation(summary = "Update loan status",
            description = "Allowed transitions: PENDING -> APPROVED/REJECTED, APPROVED -> CANCELLED")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Status updated",
                    content = @Content(schema = @Schema(implementation = LoanResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Loan not found", content = @Content),
            @ApiResponse(responseCode = "409", description = "Invalid state transition", content = @Content)
    })
    public ResponseEntity<LoanResponseDTO> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusDTO request) {
        log.info("LoanControler > updateStatus > INIT");
        return ResponseEntity.ok(loanService.updateStatus(id, request));
    }



}
