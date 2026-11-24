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
        // TODO: добавить логику для объявления переменной date и задания её значения, для генерации строки с датой
        // Вы можете использовать класс LocalDate и его методы для получения и форматирования даты
        return date;
    }

    public static String generateCity(Faker faker) {
        String[] validCities = {
                "Москва", "Санкт-Петербург", "Екатеринбург",
                "Казань", "Нижний Новгород"
        };
        String city = validCities[new Random().nextInt(validCities.length)];
        return city;
        // TODO: добавить логику для объявления переменной city и задания её значения, генерацию можно выполнить
        // с помощью Faker, либо используя массив валидных городов и класс Random

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
        // TODO: добавить логику для объявления переменной name и задания её значения, для генерации можно
        // использовать Faker

    }

    public static String generatePhone(Faker faker) {
        String phone = faker.numerify("+7##########");
        return phone;
        // TODO: добавить логику для объявления переменной phone и задания её значения, для генерации можно
        // использовать Faker

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
            // TODO: добавить логику для создания пользователя user с использованием методов generateCity(faker),
            // generateName(faker), generatePhone(faker)

        }
    }

    @Value
    public static class UserInfo {
        String city;
        String name;
        String phone;
    }
}