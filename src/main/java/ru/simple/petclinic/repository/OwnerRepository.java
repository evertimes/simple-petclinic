package ru.simple.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simple.petclinic.domain.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
