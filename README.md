# Работа 1: Разработка защищенного REST API с интеграцией в CI/CD

## Эндпоинты

### Методы для аутентификации

#### Регистрация пользователя

```http
POST /auth/signup
Content-Type: application/json

{
  "email" : "xax@gmail.com",
  "password" : "password",
  "name" : "name"
}
```
##### Ответ:

"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4YXhAZ21haWwuY29tIiwiaWF0IjoxNzY0Njk5MTEyLCJleHAiOjE3NjQ3MDI3MTJ9.1K-e-CruzciEbK79koacBMj4yceZdn60INznj0fjFzU"

#### Аутентификация пользователя
```http
POST auth/signin
Content-Type: application/json

{
  "email" : "xax@gmail.com",
  "password" : "password"
}
````
##### Ответ:

"token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4YXhAZ21haWwuY29tIiwiaWF0IjoxNzY0Njk5MTEyLCJleHAiOjE3NjQ3MDI3MTJ9.1K-e-CruzciEbK79koacBMj4yceZdn60INznj0fjFzU"
### Методы доступные только авторизованным пользователям

#### Получение защищенных данных

```http
GET /api/data
Content-Type: application/json
Authorization: Bearer {jwt-token}


```
##### Ответ:
```http
[
    {
        "title": "Welcome message",
        "preview": "Hello &lt;b&gt;world&lt;/b&gt; &amp; welcome!"
    },
    {
        "title": "System info",
        "preview": "Server status: OK &lt;tag&gt;"
    }
]
```
#### Создание новых данных с автоматической санитизацией

```http
POST /api/data
Content-Type: application/json
Authorization: Bearer {jwt-token}

Sample data with {<b></b> &} for testing
````
#### Ответ:

```http
Received & processed: {&lt;b&gt;&lt;/b&gt; &amp;}
```

#### Получение информации о пользователе

```http
GET /api/user-info
Content-Type: application/json
Authorization: Bearer {jwt-token}

```
##### Ответ:
```http
{
    "email": "authenticated-user@example.com",
    "note": "Your profile is active"
}
```

## Меры защиты


### 1. Защита от SQL-инъекций
- Все запросы к базе данных через **Spring Data JPA / Hibernate**.
- Параметризованные выражения (Prepared Statements) используются автоматически.

```Java
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
```

### 2. Защита от XSS

- **HtmlUtils.htmlEscape()** Автоматическое экранирование HTML через HtmlUtils.htmlEscape()
```Java
@PostMapping("/data")
    public ResponseEntity<String> submitDemoData(@RequestBody String raw) {

        String safe = HtmlUtils.htmlEscape(raw);

        return ResponseEntity.ok(
                "Received & processed: " + safe
        );
    }
```

### 3. Аутентификация по JWT

- **JWT токены**: HS256 с 512-битным секретным ключом для безопасной передачи данных аутентификации
- **Stateless подход**: не требуется хранение сессий на сервере, уменьшая нагрузку и упрощая масштабирование
- **Валидация**: Проверка подписи и срока действия токенов при каждом запросе к защищенным эндпоинтам
- **Middleware**: JwtAuthenticationFilter проверяет наличие и валидность токена в заголовке Authorization
- **Хеширование паролей**: Алгоритм: BCrypt

### 4. Статический анализ и проверка зависимостей
- SpotBugs для выявления потенциальных уязвимостей / багов коде
- OWASP Dependency Check для аналиа зависимостей на известные CVE

## Скриншоты отчетов

### SAST
![img.png](img/dc.png)


### SCA
![img.png](img/spotbug.png)

