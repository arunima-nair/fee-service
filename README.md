 Fee Receipt Service API

This repository contains the implementation of a **Fee Receipt Service** API built with **Spring Boot**. The API provides functionality to handle fee payment processing and receipt generation for students. 

## Table of Contents
- [Project Overview](#project-overview)
- [Technologies Used](#technologies-used)
- [Setup Instructions](#setup-instructions)
- [API Documentation](#api-documentation)
  - [POST /fees/collect](#1-collect-fee-and-generate-receipt)
- [Kafka Integration](#kafka-integration)
- [Swagger Integration](#swagger-integration)
- [Running the Application](#running-the-application)
- [Testing the API](#testing-the-api)
  - [Unit Testing](#unit-testing)
  - [Integration Testing](#integration-testing)
- [Contributing](#contributing)
- [License](#license)

## Project Overview

The **Fee Receipt Service** API allows administrators to process fee payments and generate receipts for students. It provides a single endpoint to collect fee payments and generate receipts.

## Technologies Used

- **Java** - Programming language
- **Spring Boot** - Framework for building the API
- **Spring Data JPA** - For database interaction
- **H2 Database** - In-memory database for storing fee receipts
- **Maven** - Dependency management and build tool
- **Spring Kafka** - For integrating Kafka messaging for fee receipt notifications
- **Swagger** - For API documentation and testing
- **JUnit** - For unit testing

## Setup Instructions

### Prerequisites

- Java 17
- Maven
- Kafka 
- IDE (IntelliJ IDEA, Eclipse, etc.)

### Clone the Repository

```bash
git clone gh repo clone arunima-nair/fee-service
cd fee-receipt-service
