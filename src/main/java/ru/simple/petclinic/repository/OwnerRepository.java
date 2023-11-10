package ru.simple.petclinic.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.simple.petclinic.domain.Owner;

import java.util.List;
import java.util.Set;

public interface OwnerRepository extends JpaRepository<Owner, Long> {

    @Query("SELECT o FROM Owner o LEFT JOIN FETCH o.pets")
    List<Owner> findOwnersWithPets();

    @Query("SELECT o FROM Owner o LEFT JOIN FETCH o.pets")
    List<Owner> findOwnersWithPets(Pageable page);
}
