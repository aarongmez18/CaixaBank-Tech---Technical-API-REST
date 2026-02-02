package api.caixaTech.dto;

import api.caixaTech.model.enums.Status;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusDTO {

    @NotNull
    private Status status;

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }
}
