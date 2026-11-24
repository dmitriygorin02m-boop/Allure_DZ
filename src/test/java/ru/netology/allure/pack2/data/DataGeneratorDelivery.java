package ru.netology.allure.pack2.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGeneratorDelivery {
    private DataGeneratorDelivery() {
    }

    public static String generateDate(int shift) {
        String date = LocalDate.now().plusDays(shift)
                .format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return date;
    }

    public static String generateCity(Faker faker) {
        String[] validCities = {
                "Москва", "Санкт-Петербург", "Екатеринбург",
                "Казань", "Нижний Новгород"
        };
        String city = validCities[new Random().nextInt(validCities.length)];
        return city;
    }

    public static String generateName(Faker faker) {
        String[] firstNames = {
                "Александр", "Мария", "Дмитрий", "Елена", "Андрей",
                "Ольга", "Сергей", "Наталья", "Владимир", "Ирина"
        };
        String[] lastNames = {
                "Иванов", "Петрова", "Смирнов", "Васильева", "Кузнецов",
                "Михайлова", "Попов", "Федорова", "Соколов", "Алексеева"
        };
        Random random = new Random();
        String name = lastNames[random.nextInt(lastNames.length)] + " "
                + firstNames[random.nextInt(firstNames.length)];
        return name;
    }

    public static String generatePhone(Faker faker) {
        String phone = faker.numerify("+7##########");
        return phone;
    }

    public static class Registration {
        private static Faker faker;

        private Registration() {
        }

        public static UserInfo generateUser(String locale) {
            faker = new Faker(new Locale(locale));
            UserInfo user = new UserInfo(
                    generateCity(faker),
                    generateName(faker),
                    generatePhone(faker)
            );
            return user;
        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}