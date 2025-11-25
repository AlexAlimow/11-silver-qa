# TaskTrackerQA

**TaskTrackerQA** is a test automation project for the Task Tracker application. It covers UI, API, and database testing using Java, TestNG, Selenium WebDriver, Rest Assured, JDBC, and Gradle. The architecture follows the Page Object Model and supports modular and reusable components.

---

## 🚀 Technologies

- Java 17+
- Gradle
- TestNG
- Selenium WebDriver (UI Testing)
- Rest Assured (API Testing)
- JDBC + HikariCP (Database Testing)
- Lombok
- Logback
- MySQL

---

## 📂 Project Structure
```
src/
main/java/de/ait/taskTracker/
├─ api/
│ ├─ endpoints/ # API request methods
│ └─ objects/ # Entities with Lombok
├─ config/ # Logger, SoftAssert, PropertiesLoader
├─ dataBase/ # DB connection and config variables
├─ dto/ # DTO objects (fields)
├─ gui/
│ ├─ core/ # BasePage and core UI classes
│ └─ pages/ # UI page methods
└─ utils/ # DataProvider, RetryAnalyzer, Listeners, PropertiesLoader

test/java/de/ait/taskTracker/
├─ api/
│ ├─ config/ # Base API test class
│ └─ tests/ # API tests
├─ gui/
│ ├─ core/ # Base UI test class
│ └─ tests/ # UI tests

test/resources/
├─ suites/ # TestNG XML suite files
├─ data/ # CSV files for DataProvider
├─ data.properties # Configuration properties
└─ logback.xml # Logging configuration
build.gradle
```
---

## ⚙️ Setup Instructions

1. Install **JDK 17+**
2. Setup a **MySQL** database with the required schema
3. Configure DB connection and properties in:
   - `src/main/java/de/ait/taskTracker/config/PropertiesLoader.java`
   - `src/main/java/de/ait/taskTracker/dataBase/`
   - `test/resources/data.properties`
---

## 📦 Build and Test Execution

The project uses **Gradle** as a build tool.

### Running Tests

Run all tests:

gradle clean test

Run a specific suite (e.g., positiveAuth):

gradle positiveAuth

Run with a specific browser:

gradle positiveAuth -Dbrowser=edge

## 🧪 Test Coverage
- **API tests**: test/java/de/ait/taskTracker/api/tests/, use Rest Assured and extend base API TestBase.

- **UI tests**: test/java/de/ait/taskTracker/gui/tests/, use Selenium WebDriver and Page Object Model.

- **Database tests**: use JDBC repositories and DB utilities.

## ➕ Adding New Entities or Tests
1. Add API endpoints and DTOs in src/main/java/de/ait/taskTracker/api/.

2. Add UI page objects in gui/pages/ and tests in gui/tests/.

3. Add DTOs in dto/ and DB logic in dataBase/.

4. Write tests in corresponding test/java/de/ait/taskTracker/{api|gui}/tests/.

5. Add test data in test/resources/data/.

6. Update TestNG suite XML files in test/resources/suites/.

