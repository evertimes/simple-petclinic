package ru.simple.petclinic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.simple.petclinic.domain.Pet;

public interface PetRepository extends JpaRepository<Pet, Long> {
}
