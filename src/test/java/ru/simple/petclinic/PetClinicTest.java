package ru.simple.petclinic;

import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import ru.simple.petclinic.domain.Owner;
import ru.simple.petclinic.domain.Pet;
import ru.simple.petclinic.domain.PetType;
import ru.simple.petclinic.domain.Visit;
import ru.simple.petclinic.repository.OwnerRepository;
import ru.simple.petclinic.repository.PetRepository;
import ru.simple.petclinic.repository.PetTypeRepository;
import ru.simple.petclinic.repository.VisitRepository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class PetClinicTest {
    @Autowired
    private VisitRepository visitRepository;
    @Autowired
    private PetTypeRepository petTypeRepository;
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private OwnerRepository ownerRepository;
    @Autowired
    private DataSource dataSource;

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

    //FetchType=eager на pet в owner
    //Представим, что мы хотим получить получить всех владельцев.
    //Получили n+1 - один запрос на владельца и n на его питомцев. Кажется не ок. И питомцы нам вообще не нужны.
    @Test
    void fetchTypeEagerNotOk() {
        var owner = ownerRepository.findAll().stream().findAny().get();
        Assertions.assertThat(true).isTrue();
    }

    //Попробуем поставить fetchType = Lazy, что бы питомцы не тащились, только когда нужно.
    //Получили один запрос на владельца. Все ок.
    @Test
    void fetchTypeLazyIsOk() {
        var owner = ownerRepository.findAll().stream().findAny().get();
        Assertions.assertThat(true).isTrue();
    }


    //Допустим теперь мы захотели посмотреть питомцев. Смотрим сколько у каждого владельца есть питомцев.
    //Тест падает - LazyInitException, т.к. у нас уже закрыта транзакция.
    @Test
    void fetchTypeLazyGetPetNotOk() {
        ownerRepository.findAll().forEach(e -> e.getPets().size());
        Assertions.assertThat(true).isTrue();
    }


    //Для таких целей можно использовать аннотацию transcational, которая позволяет выполнить все манипуляции
    //в бд внутри одной транзакции
    //Теперь метод работает, но мы снова получили n+1.
    //Как быть, когда в одном случае нам нужны питомцы, а в другом нет?
    @Test
    @Transactional
    void fetchTypeLazyGetPetTransactionalNotOk() {
        ownerRepository.findAll().forEach(e -> e.getPets().size());
        Assertions.assertThat(true).isTrue();
    }


    //Наиболее лучшим решением будет добавить еще один метод для поиска, с joinFetch.
    @Test
    void joinFetchIsOk() {
        ownerRepository.getOwnersWithPets().forEach(e -> e.getPets().size());
        Assertions.assertThat(true).isTrue();
    }

    //На проектах с таблицами, в которых содержится очень много данных вряд ли будут запросы на поиск всех сущностей
    //Причины у этого две. Допустим в таблице один миллион строк:
    //1.Такой запрос будет выполняться очень долго и сильно нагрузит базу. К тому же, вряд ли кому действительно нужно посмотреть 1 миллион записей разом.
    //2.Все эти сущности будут выгружены в оперативную память вашего приложения, а она не бесконечная. Можно ожидать что память закончится и приложение просто упадет.
    //Для этого используется пагинация.
    //Тут мы видим что выполнилось 2 запроса. Один с offset + fetch first rows only
    //Другой с count. Зачем? Такие запросы нужны что бы посчитать количество записей в таблице, а на основе их на фронте
    //можно будет показать какое количество страниц всего. В интернет магазинах и тд все это видели.
    //Этот запрос тоже может нагрузить бд и увеличить время выполнения метода на больших объемах данных
    //Все когда-нибудь листали ленту в vk/instagram и тд. Там действительно огромные объемы данных. Бесконечная лента, которая динамически подгружается
    // позволяет не считать тут count.
    // Тут эта проблема решаема, хоть и немного коряво, но давайте пока закроем на неё глаза.
    @Test
    void paginationIsOk() {
        ownerRepository.findAll(PageRequest.of(1, 1));
    }

    //Давайте сделаем тоже самое, но попробуем получить вместе с владельцами их питомцев
    //В запросе мы почему-то не увидим никаких offset/limit, зато увидим warn
    //firstResult/maxResults specified with collection fetch; applying in memory.
    //Получилось так, пагинации нет. Из базы вытащили большие объемы данных, еще и загрузили их все в память.
    //Я лично один раз так ронял прод))))))
    //Что делать? Писать SQL.
    @Test
    void paginationWithPetsIsNotOk() {
        ownerRepository.getOwnersWithPets(PageRequest.of(0, 1));
        Assertions.assertThat(true).isTrue();
    }


    //Тут страшное.
    @Test
    void paginationWithNativeIsOK() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Map<Long, Owner> result = new HashMap<>();
        jdbcTemplate.query("SELECT o.id as owner_id, o.name as owner_name, o.address as owner_address, " +
                        "p.id as pet_id, p.name as pet_name, p.date_of_birth as pet_date_of_birth " +
                        "from (SELECT id, name, address from owner offset ? rows fetch first ? rows only) o " +
                        "left join pet p on p.owner_id = o.id",
                (RowMapper<Owner>) (rs, rowNum) -> {
                    Long ownerId = rs.getLong("owner_id");
                    if (result.containsKey(ownerId)) {
                        mapPet(rs, result, ownerId);
                        return null;
                    }
                    String name = rs.getString("owner_name");
                    String address = rs.getString("owner_address");
                    Owner owner = new Owner(ownerId, name, address);
                    result.put(ownerId, owner);
                    mapPet(rs, result, ownerId);
                    return null;
                }, 0, 2);;
        result.values().forEach(System.out::println);
    }

    public void mapPet(ResultSet rs, Map<Long, Owner> result, Long ownerId) throws SQLException {
        Long petId = rs.getLong("pet_id");
        if (petId != null) {
            String petName = rs.getString("pet_name");
            LocalDateTime petDateOfBirth = rs.getTimestamp("pet_date_of_birth").toLocalDateTime();
            Pet pet = new Pet(petId, petName, petDateOfBirth, null);
            result.get(ownerId).addPet(pet);

        }
    }

}
