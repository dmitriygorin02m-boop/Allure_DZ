package ru.netology.allure.pack2;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import ru.netology.allure.pack2.data.DataGeneratorDelivery;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

class DeliveryTest {

    @BeforeAll
    static void setUpAll() {
        // Добавляем слушатель в тестовый класс перед выполнением всех тестов
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        // Удаляем слушатель после выполнения всех тестов
        SelenideLogger.removeListener("allure");
    }

    @BeforeEach
    void setup() {
        open("http://localhost:9999");
    }

    @Test
    @DisplayName("Should successful plan and replan meeting")
    void shouldSuccessfulPlanAndReplanMeeting() {
        var validUser = DataGeneratorDelivery.Registration.generateUser("ru");
        var daysToAddForFirstMeeting = 4;
        var firstMeetingDate = DataGeneratorDelivery.generateDate(daysToAddForFirstMeeting);
        var daysToAddForSecondMeeting = 7;
        var secondMeetingDate = DataGeneratorDelivery.generateDate(daysToAddForSecondMeeting);

        // --- Первая отправка ---
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(firstMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());

        // Просто ставим галочку — после перезагрузки она всегда снята
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Запланировать")).click();

        // Проверка успешного планирования
        $("[data-test-id=success-notification]")
                .should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__title").should(Condition.text("Успешно!"));
        $(".notification__content").should(Condition.text(
                "Встреча успешно запланирована на " + firstMeetingDate));

        // Закрываем уведомление
        $("[data-test-id=success-notification] .notification__closer").click();

        // ВАЖНО: обновляем страницу — форма очищается, чекбокс сбрасывается,
        // а заявка остаётся на бэкенде (по телефону)
        open("http://localhost:9999");

        // --- Вторая отправка (перепланирование) ---
        $("[data-test-id=city] input").setValue(validUser.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.CONTROL + "a");
        $("[data-test-id=date] input").sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=date] input").setValue(secondMeetingDate);
        $("[data-test-id=name] input").setValue(validUser.getName());
        $("[data-test-id=phone] input").setValue(validUser.getPhone());

        // Снова просто ставим галочку (она точно снята после open)
        $("[data-test-id=agreement]").click();
        $$("button").find(Condition.exactText("Запланировать")).click();

        // Ждём уведомления о необходимости подтверждения перепланирования
        $("[data-test-id=replan-notification]")
                .should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__title")
                .should(Condition.text("Необходимо подтверждение"));

        // Подтверждаем перепланирование
        $("[data-test-id=replan-notification] .button").click();

        // Финальная проверка: встреча успешно запланирована на новую дату
        $("[data-test-id=success-notification]")
                .should(Condition.visible, Duration.ofSeconds(15));
        $(".notification__title").should(Condition.text("Успешно!"));
        $(".notification__content").should(Condition.text(
                "Встреча успешно запланирована на " + secondMeetingDate));
    }
}