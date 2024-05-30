package ru.nokkov.blps_lab.partner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.nokkov.blps_lab.partner.model.PartnerUnit;


@Repository
public interface PartnerRepository extends JpaRepository<PartnerUnit, Long> {

}

