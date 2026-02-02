package api.caixaTech.model;

import api.caixaTech.model.enums.Status;
import jakarta.persistence.*;

import java.time.LocalDateTime;

public class LoanEntity {

    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false, length = 120)
    private String applicantName;

    @Column(nullable = false, precision = 19, scale = 2)
    private Long amount;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(nullable = false, length = 20)
    private String identityDocument;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public LoanEntity() {}

    public LoanEntity(LocalDateTime createdAt, Status status, String identityDocument, String currency, Long amount, String applicantName, Long id) {
        this.createdAt = createdAt;
        this.status = status;
        this.identityDocument = identityDocument;
        this.currency = currency;
        this.amount = amount;
        this.applicantName = applicantName;
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public void setApplicantName(String applicantName) {
        this.applicantName = applicantName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getIdentityDocument() {
        return identityDocument;
    }

    public void setIdentityDocument(String identityDocument) {
        this.identityDocument = identityDocument;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
