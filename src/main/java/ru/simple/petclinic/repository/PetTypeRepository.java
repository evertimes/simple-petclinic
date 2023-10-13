package ru.simple.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simple.petclinic.domain.PetType;

public interface PetTypeRepository extends JpaRepository<PetType, String> {
}
