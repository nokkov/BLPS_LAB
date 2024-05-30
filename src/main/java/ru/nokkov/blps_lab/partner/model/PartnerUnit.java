package ru.nokkov.blps_lab.partner.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class PartnerUnit {
    @Id
    @GeneratedValue
    private Long id;

    private Long relatedArticleId;

    private PaymentType paymentType;
}