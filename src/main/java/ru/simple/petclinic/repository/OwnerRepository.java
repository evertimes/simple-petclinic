package ru.simple.petclinic.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.simple.petclinic.domain.Owner;

import java.util.List;
import java.util.Set;

public interface OwnerRepository extends JpaRepository<Owner, Long> {


    @Query("SELECT o FROM Owner o LEFT JOIN FETCH o.pets")
    List<Owner> getOwnersWithPets();

    @Query("SELECT o FROM Owner o LEFT JOIN FETCH o.pets")
    List<Owner> getOwnersWithPets(Pageable pageable);

    @Query(value = "SELECT o.id as owner_id, o.name as owner_name, o.address as owner_address, " +
            "p.id as pet_id, p.name as pet_name, p.date_of_birth as pet_date_of_birth " +
            "from (SELECT id, name, address from owner offset :offset rows fetch first :limit rows only) o " +
            "left join pet p on p.owner_id = o.id", nativeQuery = true)
    List<Owner> getOwnersWithPetsNativePagination(int limit, int offset);

}
