package ru.simple.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simple.petclinic.domain.Visit;

public interface VisitRepository extends JpaRepository<Visit, Long> {
}
