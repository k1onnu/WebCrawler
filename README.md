# Web Crawler

![Java](https://img.shields.io/badge/Java-17+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.8-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![H2 Database](https://img.shields.io/badge/H2_Database-2.x-4479A1?style=for-the-badge&logo=databricks&logoColor=white)
![JUnit 5](https://img.shields.io/badge/JUnit_5-Tested-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-blue?style=for-the-badge)

> **Многопоточный сервис для сбора контактных данных (email, телефоны, адреса) с веб-сайтов.**
> Демонстрирует современные практики работы с потоками, блокирующими очередями, параллельными стримами и Spring Data JPA.

---

## Как запустить

1. Убедитесь, что у вас установлена **Java 17** (или выше).
2. В корне проекта выполните:
   ```bash
   ./mvnw spring-boot:run
   ```
   *(Или запустите класс `WebCrawlerApplication` из вашей IDE).*
3. Приложение запустится на порту `8080`. База данных H2 создается в памяти автоматически (дополнительная настройка не требуется).

---

## Как проверить

1. Откройте в браузере: **`http://localhost:8080/`**
2. В поле «Стартовый URL» вставьте ссылку.
3. Нажмите кнопку **«Запустить краулер»**.
4. Подождите 15–30 секунд (запрос синхронный, бэкенд обходит страницы в несколько потоков).
5. На странице появится зеленая плашка об успехе и **таблица с найденными контактами**.

### Проверка через REST API (Опционально)

**1. Запуск краулера (POST):**
```bash
curl -X POST http://localhost:8080/api/crawler \
  -H "Content-Type: application/json" \
  -d '{"startUrls":["https://skillbox.ru/"],"maxPages":10}'
```

**2. Получение результатов (GET):**
Откройте в браузере: `http://localhost:8080/api/answer?page=0&size=5&sortBy=email`

### Проверка базы данных H2
Веб-интерфейс БД доступен по адресу: `http://localhost:8080/db`
*   **JDBC URL:** `jdbc:h2:mem:crawlerdb`
*   **User Name:** `sa`
*   **Password:** (оставьте пустым)

---

## Архитектура и реализованные требования

Проект спроектирован с упором на **многопоточность** и **отказоустойчивость**.

| Компонент | Реализация |
| :--- | :--- |
| **Spring Boot** | REST-контроллеры (`CrawlerController`, `AnswerController`) |
| **Frontend** | Статическая страница `index.html` (Vanilla JS + Fetch API) |
| **Парсинг** | `HtmlParser` (Jsoup для HTML + Regex для телефонов/email/адресов) |
| **Многопоточность** | Кастомный `ExecutorService` и `ScheduledExecutorService` (`ExecutorConfig`) |
| **Задачи** | `CrawlerWorker` (`implements Runnable`) |
| **Синхронизация** | `BlockingQueue`, `ConcurrentHashMap`, `AtomicInteger`, `CountDownLatch` |
| **Логирование** | Демон-поток `FileLogger` (`extends Thread`), пишет в `crawler.log` |
| **База данных** | Встроенная H2, Spring Data JPA (`ContactRepository`) |
| **Обработка данных** | Сортировка и пагинация в `AnswerService` через **`parallelStream()`** |
| **Расписание** | Периодический фоновый запуск через `@Scheduled` |
| **Тестирование** | Unit-тесты (JUnit 5) для парсера (`HtmlParserTest`) |

---

## Структура проекта

```text
src/main/java/com/webcrawler/
├── config/          # Настройки пулов потоков (ExecutorConfig)
├── concurrency/     # Многопоточные классы (CrawlerWorker, FileLogger)
├── controller/      # REST API (CrawlerController, AnswerController)
├── dto/             # Data Transfer Objects (Records)
├── entity/          # JPA Сущности (Contact)
├── repository/      # Spring Data JPA (ContactRepository)
├── scheduler/       # Задачи по расписанию (@Scheduled)
├── service/         # Бизнес-логика (CrawlerService, HtmlParser, AnswerService)
└── util/            # Утилиты (RegexPatterns)
```