# Patient Management Application

A Java-based console application designed to manage patient information, user accounts, and medical appointments. Developed as an academic software engineering project using object-oriented programming principles and a layered architecture.

## Features

- **User Management:** Create, view, update, and delete user records.
- **Role-Based Models:** Separate classes for patients, doctors, and administrators.
- **Appointment Management:** Create, view, reschedule, and cancel appointments.
- **Layered Architecture:** Organized into models, services, and controllers for better maintainability.
- **Unit Testing:** JUnit 5 tests for user and appointment service operations.

## Technologies Used

- Java 22
- Apache Maven
- JUnit 5
- Object-Oriented Programming (OOP)
- Git and GitHub

## Project Structure

```text
src/
├── main/java/edu/secourse/patientportal/
│   ├── controllers/
│   ├── models/
│   ├── services/
│   └── Main.java
└── test/java/edu/secourse/patientportal/
    └── services/
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 22
- Apache Maven

### Installation

Clone the repository:

```bash
git clone https://github.com/nischaltimalsina7/PatientManagementApp.git
cd PatientManagementApp
```

Compile the application:

```bash
mvn clean compile
```

Run the application:

```bash
java -cp target/classes edu.secourse.patientportal.Main
```

### Running Tests

```bash
mvn test
```

## Current Limitations

- Application data is stored in memory and is not retained after the program exits.
- The application uses a command-line interface rather than a graphical interface.
- Database integration and persistent storage are potential future improvements.

## Future Improvements

- Add database persistence.
- Improve input validation and error handling.
- Expand unit test coverage.
- Add a graphical or web-based interface.

## Project Background

This application was originally developed as an academic project to practice Java programming, object-oriented design, and software engineering concepts. Ongoing improvements focus on code quality, documentation, testing, and maintainability.