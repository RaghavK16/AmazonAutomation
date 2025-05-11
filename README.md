# AmazonAutomation

This project is a simple UI automation framework built using **Java**, **Selenium WebDriver**, **TestNG**, and **Page Object Model (POM)** design pattern. It runs on the **LambdaTest Selenium Grid** to execute tests on macOS environments with Chrome browser.

## 🔧 Features

- Test Amazon.com search and add-to-cart functionality
- Execute on remote browser (Chrome/macOS Sequoia) using LambdaTest
- Modular Page Object Model structure
- Handles dynamic waits using WebDriverWait
- Robust error handling and locator separation

---

## 🗂️ Project Structure

amazon-search-automation/
│
├── src/
│ └── main/
│ └── java/
│ └── amazon/
│ ├── AmazonSearchTest.java # Test class
│ └── pages/
│ └── AmazonPageLocators.java # All locators used
│
├── testng.xml # TestNG suite
├── pom.xml # Maven dependencies
└── README.md

---

## ✅ Prerequisites

- Java JDK 8+
- Maven
- Internet connection
- LambdaTest credentials

---

## 🧪 Test Scenarios

- Search for a product (e.g., iPhone or Galaxy)
- Click the first product from the search results
- Add the product to cart
- Capture and display the product price (if available)

---

## 🔑 LambdaTest Configuration

Make sure to replace these in `AmazonSearchTest.java`:

```java
String username = "YOUR_LT_USERNAME";
String accessKey = "YOUR_LT_ACCESS_KEY";
```

You can find these in your LambdaTest profile.

## 🚀 How to Run

1. Clone the repo

git clone https://github.com/yourusername/amazon-search-automation.git
cd amazon-search-automation

2. Update LambdaTest credentials

3. Run with Maven

```bash
mvn clean test
```

## 🧱 Dependencies

Defined in pom.xml:

Selenium Java

TestNG

WebDriverManager (optional for local runs)

## 📌 Notes

This test runs entirely on LambdaTest's cloud platform.

Elements like cookie banners and price locators are handled using fallback strategies.

Make sure the browser version/platform specified in capabilities is available on LambdaTest.

