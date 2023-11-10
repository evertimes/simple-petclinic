package ru.simple.petclinic.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Builder
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Owner {
    @Id
    private long id;
    private String name;
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Pet> pets = new HashSet<>();

    public Owner(long id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setOwner(this);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pets=" + pets +
                '}';
    }
}
