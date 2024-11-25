# Project Overview

This project serves as a base for building IoT-based solutions.
It is designed to be highly extensible, allowing developers to integrate additional
functionality with ease.

## Key Features:

[1] Message Broker Flexibility:
Initially supports RabbitMQ but can be extended to work with other message brokers.

[2] Comprehensive Notification Services:
Easily add support for various messaging channels such as email, push notifications,
SMS, in-app notifications, and more.

[3] Scalability:
The modular architecture enables seamless expansion to accommodate new features and services.

This project provides a robust starting point for creating IoT based applications, empowering
developers to adapt it to their unique requirements.

## Used Technologies

- **Java 21** - The primary programming language.
- **Spring Boot** - For dependency management, configuration, and application setup.
- **Spring Security** - For authentication and authorization.
- **Maven** - For project build and dependency management.
- **Data Jpa** - For DB interaction.
- **RabbitMq** - As message broker.
- **Mail Sender** - For email notifications.
- **Twilio** - For sms notifications.
- **Firebase** - For push notifications.
- **Postgres** - As relational database management system.
- **JUnit 5** - For testing design pattern implementations.
- **Lombok** - For reducing boilerplate code.

## Used design patterns

The project is divided into two independent, self-contained microservices, each with
a specific responsibility`
API Service: Handles messaging and data sharing,
OAuth Service: Manages user authentication and authorization.
I have followed the traditional layered
architectural design pattern, with a clear separation of concerns.
Additionally, I used ############ for notification system.

## Getting Started

You need to follow up some steps before you can run the application`

1. Clone the repository:`
   git clone [git@github.com:TigranMelkonyan/IoT-Messaging-Platform.git]()

2. Set up JDK 21

3. Change application.properties with your own credentials

   #### Data source config
   [spring.datasource.url=jdbc:your_database_url
   spring.datasource.username=your_database_username
   spring.datasource.password=your_database_password]

   #### Rabbitmq_config
   [spring.rabbitmq.host
   spring.rabbitmq.port=
   spring.rabbitmq.stomp.port=
   spring.rabbitmq.username=
   spring.rabbitmq.password=]

4. Build the project using maven
   [./mvn clean install]

5. Run application
   [./mvn spring-boot:run]

6. Run tests
   [./mvn test]
   or run separately
   [./mvn test -Dtest"TestClassName"]

* The application will not run without setting up proper application properties
  for RabbitMq, Firebase, Twilio and Datasource, for simple demonstration you
  can run unit tests without using real beans.
  Additionally, you need to have AWS credentials and use the file located in
  resources/publisher RabbitMqPublisher.go, which is already compiled GoLang file
  and put it in AWS Lambda to be able to publish test data.