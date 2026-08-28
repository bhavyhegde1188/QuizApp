import org.junit.jupiter.api.Test;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizAppE2EIT {

    @Test
    void quizAppFlow() {
        String frontendUrl = System.getProperty(
            "frontend.url",
            System.getenv().getOrDefault(
            "FRONTEND_URL",
            "http://localhost:8084/quizapp/"
            )
        );

        try (Playwright playwright = Playwright.create();
             Browser browser = playwright.chromium().launch(
                 new BrowserType.LaunchOptions().setHeadless(true))) {
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            Response response = page.navigate(frontendUrl);

            assertTrue(response != null && response.status() >= 200 && response.status() < 400);
            page.locator("#QuizAp__FirstPage__el_btn_2_0").click();
            page.locator("#QuizAp__SubmitAnswer__i__answers__selectedAnswer_0").fill("B");
            page.locator("#QuizAp__SubmitAnswer__i__answers__selectedAnswer_1").fill("A");
            page.locator("#QuizAp__SubmitAnswer__i__answers__selectedAnswer_2").fill("C");
            page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Submit Answer")
            ).click();
            page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Ok")
            ).click();
            page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("View Result")
            ).click();

            assertTrue(page.locator("body").innerText().contains("Result"));
        }
    }
}