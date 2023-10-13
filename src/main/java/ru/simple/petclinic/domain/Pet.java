package ru.simple.petclinic.domain;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
public class Pet {
    @Id
    private long id;
    private String name;
    private LocalDateTime dateOfBirth;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pet")
    private Set<Visit> visits = new HashSet<>();
    @ManyToOne
    private PetType petType;
    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Owner owner;

    public Pet(long id, String name, LocalDateTime dateOfBirth, PetType petType) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.petType = petType;
    }

    public void addVisit(Visit visit){
        visits.add(visit);
        visit.setPet(this);
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }
}
