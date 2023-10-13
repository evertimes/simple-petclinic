package ru.simple.petclinic.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
public class Visit {
    @Id
    private long id;
    private LocalDateTime dateOfVisit;
    private String description;
    @ManyToOne
    @JoinColumn(name = "pet", referencedColumnName = "id")
    private Pet pet;

    public Visit(long id, LocalDateTime dateOfVisit, String description) {
        this.id = id;
        this.dateOfVisit = dateOfVisit;
        this.description = description;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }
}
