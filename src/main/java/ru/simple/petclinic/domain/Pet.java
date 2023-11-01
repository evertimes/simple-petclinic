package ru.simple.petclinic.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Pet {
    @Id
    private long id;
    private String name;
    private LocalDateTime dateOfBirth;
    private String petType;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private Owner owner;

    public Pet(long id, String name, LocalDateTime dateOfBirth, String petType) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.petType = petType;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", petType='" + petType + '\'' +
                '}';
    }
}
