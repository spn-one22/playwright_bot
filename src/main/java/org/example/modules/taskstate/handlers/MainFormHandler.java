package org.example.modules.taskstate.handlers;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.WaitForSelectorState;
import org.example.modules.taskstate.TaskHandler;
import org.example.utils.FormFiller;
import org.example.utils.generators.*;

import java.util.Random;

public class MainFormHandler implements TaskHandler {

    @Override
    public void handle(Page page) {
        System.out.println("🧩 Обработка MAIN_FORM");

        // FILL TARGET LINK
        for (int i = 0; i < 3; i++) {
            String link = Extractor.extractUrl(page);
            FormFiller.fillIfExists(page, FormFiller.urlField(page), link + i);

            if (!FormFiller.hasError(FormFiller.urlField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }

        // FILL USERNAME
        String approvedUsername = null;
        String baseUsername = Extractor.extractUsername(page);

        Locator usernameField = FormFiller.usernameField(page);
        Random random = new Random();

        for (int i = 0; i < 3; i++) {
            String candidate;

            if (i == 0) {
                // первая попытка — чистый username
                candidate = baseUsername;
            } else {
                // последующие — с рандомным числом
                int randomNum = 1 + random.nextInt(99);
                candidate = baseUsername + randomNum;
            }

            System.out.println("Trying username: " + candidate);

            FormFiller.fillIfExists(page, usernameField, candidate);

            if (!FormFiller.hasError(usernameField)) {
                System.out.println("✅ Поле заполнено успешно");
                approvedUsername = candidate; // сохраняем именно успешный вариант
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                page.waitForTimeout(3000);
            }
        }

        //FILL ACCOUNT LINK
        if (approvedUsername == null) {
            approvedUsername = Extractor.extractUsername(page);
        }

        String accountLink = Extractor.extractBaseUrl(Extractor.extractUrl(page)) + approvedUsername;
        for (int i = 0; i < 3; i++) {

            FormFiller.fillIfExists(page, FormFiller.messageField(page), accountLink);

            if (!FormFiller.hasError(FormFiller.messageField(page))) {
                System.out.println("✅ Поле заполнено успешно");
                break;
            } else {
                System.out.println("❌ Ошибка валидации, пробуем ещё раз");
                accountLink = accountLink + i;
                page.waitForTimeout(3000);

            }
        }

        Locator dopInfoBtn = FormFiller.dopInfoButton(page);
        if (dopInfoBtn.isVisible()) {
            dopInfoBtn.click();
            FormFiller.fillIfExists(page, FormFiller.dopInfoField(page), "Доп инфа");
        }

        Locator submitBtn = FormFiller.submitButton(page);
        submitBtn.waitFor();
        page.waitForFunction(
                "!document.querySelector('#model-form input[type=submit]').disabled"
        );
        submitBtn.click();
        System.out.println("📤 Форма отправлена");
        submitBtn.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.DETACHED));
    }
}