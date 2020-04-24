package com.jmchugh.demo.config;

import com.github.javafaker.Faker;
import com.jmchugh.demo.user.entity.AddressEntity;
import com.jmchugh.demo.user.entity.UserEntity;
import com.jmchugh.demo.user.entity.UserStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class SeedDataConfig {

    private final EntityManager entityManager;

    @EventListener(ApplicationStartedEvent.class)
    public void seedDataBase() {

        log.info("Seeding the database...");

        for(int i = 0; i < 225; i++) {

            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(Faker.instance().name().username());
            userEntity.setFirstName(Faker.instance().name().firstName());
            userEntity.setLastName(Faker.instance().name().lastName());
            userEntity.setEmail(Faker.instance().internet().emailAddress(String.format("%s.%s", userEntity.getFirstName(), userEntity.getLastName())));
            userEntity.setStatus(UserStatus.values()[Faker.instance().random().nextInt(1, 3) - 1]);

            AddressEntity addressEntity = new AddressEntity();
            addressEntity.setUser(userEntity);
            addressEntity.setCountry(Faker.instance().address().country());
            addressEntity.setState(Faker.instance().address().state());
            addressEntity.setPostalCode(Faker.instance().address().zipCode());
            addressEntity.setCity(Faker.instance().address().city());
            addressEntity.setStreetAddress(Faker.instance().address().streetAddress());

            boolean hasSecondaryAddress = Faker.instance().random().nextBoolean();
            if(hasSecondaryAddress) {

                addressEntity.setSecondaryAddress(Faker.instance().address().secondaryAddress());
                addressEntity.setBuildingNumber(Faker.instance().address().buildingNumber());
            }

            userEntity.setAddress(addressEntity);

            entityManager.persist(userEntity);
        }

        log.info("Seeding data complete...");
    }
}
