package tests;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

public class StudentRegistrationFormTests extends TestBase {

    Faker faker = new Faker();

     String
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            userEmail = faker.internet().emailAddress(),
            gender = "Female",
            mobile_Number = faker.phoneNumber().subscriberNumber(10),
            month = "July",
            year = "1991",
            subjects = "Physics",
            hobbies = "Sports",
            address = faker.address().cityName(),
            state = "Uttar Pradesh",
            city = "Lucknow";
    @Test
    void successfulFillFormTest() {
        step("Open students registration form", () -> {
            open("https://demoqa.com/automation-practice-form");
            $(".practice-form-wrapper").shouldHave(text("Student Registration Form"));
        });

        step("Fill students registration form", () -> {
            step("Fill common data", () -> {
                $("#firstName").setValue(firstName);
                $("#lastName").setValue(lastName);
                $("#userEmail").setValue(userEmail);
                $(byText(gender)).click();
                $("[placeholder='Mobile Number']").setValue(mobile_Number);
            });
            step("Set date", () -> {
                $(".react-datepicker-wrapper").click();
                $(".react-datepicker__month-select").selectOption(month);
                $(".react-datepicker__year-select").selectOption(year);
                $(".react-datepicker__day--013").click();
            });
            step("Set subjects", () -> {
                $("#subjectsInput").setValue(subjects).pressEnter();
                $(byText(hobbies)).click();
                $("input#uploadPicture").uploadFromClasspath("audi.jpg");
                $("#currentAddress").setValue(address);
                $("#state").scrollTo().click();
                $("#react-select-3-input").setValue(state).pressEnter();
                $("#city").click();
                $("#react-select-4-input").setValue(city).pressEnter();
            });
            step("Submit form", () ->
                    $("#submit").click());
        });

        step("Verify successful form submit", () -> {
            $("#example-modal-sizes-title-lg").shouldHave(text("Thanks for submitting the form"));

            $(".table-responsive").shouldHave(
                    text(firstName + " " + lastName),
                    text(userEmail),
                    text(gender),
                    text(mobile_Number),
                    text("13 " + month + "," + year),
                    text(subjects),
                    text(hobbies),
                    text("audi.jpg"),
                    text(address),
                    text(state + " " + city));
        });
    }
}
