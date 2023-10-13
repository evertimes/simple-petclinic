package ru.simple.petclinic;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.simple.petclinic.domain.Owner;
import ru.simple.petclinic.domain.Pet;
import ru.simple.petclinic.domain.PetType;
import ru.simple.petclinic.domain.Visit;
import ru.simple.petclinic.repository.OwnerRepository;
import ru.simple.petclinic.repository.PetRepository;
import ru.simple.petclinic.repository.PetTypeRepository;
import ru.simple.petclinic.repository.VisitRepository;

import java.time.LocalDateTime;

@SpringBootTest
public class PetClinicTest {
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private PetTypeRepository petTypeRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @Test
    @Disabled
    //Пусть будет тут, но юзать будем data.sql
    void init() {
        var visit1 = visitRepository.save(new Visit(1, LocalDateTime.now(), "Боль в лапе"));
        var visit2 = visitRepository.save(new Visit(2, LocalDateTime.now(), "Несварение"));
        var visit3 = visitRepository.save(new Visit(3, LocalDateTime.now(), "Стрижка когтей"));
        var visit4 = visitRepository.save(new Visit(4, LocalDateTime.now(), "Груминг"));
        var visit5 = visitRepository.save(new Visit(5, LocalDateTime.now(), "Взятие крови на анализы"));
        var visit6 = visitRepository.save(new Visit(6, LocalDateTime.now(), "Кастрация"));
        var visit7 = visitRepository.save(new Visit(7, LocalDateTime.now(), "Плановый осмотр кошки"));
        var visit8 = visitRepository.save(new Visit(8, LocalDateTime.now(), "Плановый осмотр собаки"));
        var visit9 = visitRepository.save(new Visit(9, LocalDateTime.now(), "Плановый осмотр хомяка"));
        var visit10 = visitRepository.save(new Visit(10, LocalDateTime.now(), "Плановый осмотр кролика"));
        var visit11 = visitRepository.save(new Visit(11, LocalDateTime.now(), "Пропал аппетит"));
        var visit12 = visitRepository.save(new Visit(12, LocalDateTime.now(), "Анализы"));

        var sobaka = petTypeRepository.save(new PetType("Собака"));
        var koshka = petTypeRepository.save(new PetType("Кошка"));
        var homyak = petTypeRepository.save(new PetType("Хомяк"));
        var krolik = petTypeRepository.save(new PetType("Кролик"));

        var barsik = new Pet(1, "Барсик", LocalDateTime.now(), koshka);
        barsik.addVisit(visit3);
        barsik.addVisit(visit5);
        barsik.addVisit(visit6);
        barsik.addVisit(visit7);
        barsik.addVisit(visit2);

        var veniamin = new Pet(2, "Вениамин", LocalDateTime.now(), homyak);
        veniamin.addVisit(visit12);
        veniamin.addVisit(visit9);

        var sharik = new Pet(3, "Шарик", LocalDateTime.now(), sobaka);
        sharik.addVisit(visit4);
        sharik.addVisit(visit8);
        sharik.addVisit(visit1);

        var viktor = new Pet(4, "Виктор", LocalDateTime.now(), krolik);
        viktor.addVisit(visit10);
        viktor.addVisit(visit11);

        barsik = petRepository.save(barsik);
        veniamin = petRepository.save(veniamin);
        sharik = petRepository.save(sharik);
        viktor = petRepository.save(viktor);

        var andrey = new Owner(1, "Андрей", "г. Рязань");
        andrey.addPet(barsik);
        andrey.addPet(veniamin);
        ownerRepository.save(andrey);

        var kostya = new Owner(2, "Костя", "г. Рязань");
        kostya.addPet(sharik);
        kostya.addPet(viktor);
        ownerRepository.save(kostya);
    }

}
