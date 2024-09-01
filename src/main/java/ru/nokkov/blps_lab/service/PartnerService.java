package ru.nokkov.blps_lab.service;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;
import ru.nokkov.blps_lab.partner.model.PartnerUnit;
import ru.nokkov.blps_lab.partner.model.PaymentType;
import ru.nokkov.blps_lab.partner.repository.PartnerRepository;


@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public void registerArticle(Long articleId, PaymentType paymentType) {
        PartnerUnit partnerUnit = new PartnerUnit();
        partnerUnit.setRelatedArticleId(articleId);

        partnerUnit.setPaymentType(Objects.requireNonNullElse(paymentType, PaymentType.CommercialView));

        partnerRepository.save(partnerUnit);
    }
}