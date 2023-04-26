# PA Preparation Project

## Description

This is a solo project from my preparation phase for the final personal assessment to ensure that all required topics are covered and practiced. The main purpose of this project is not to add many features, but rather to put focus on the main topics that help to learn the tech stack.

It is a simple task tracker, similar to a To-Do list, where you can add and remove tasks, that have to be done. Furthermore, users can sign up and log into the application.

It is a web application containing a frontend- and a backend server. The frontend is the UI built with React and Node.js, and the backend is a REST API built with the Spring Framework in Java.

## Goals

The project is divided into milestones to let the project grow iteratively. After finishing these milestones, it has the following tech stack:

- Java (eg. Data- & Service classes, Repositories, Streams)
- JUnit (tests, parameterized tests)
- Mockito
- Spring Framework
- Spring Web (CRUD functionality in a `@RestController`)
- Spring Data JPA (at least one `@Entity` with a repository and an H2 database)
- Spring Security (with basic authentication and Oauth2 Resource Server for JWT)
- Spring tests (`@SpringBootTest`, `@WebMvcTest`, `@DataJpaTest`)
- Spring security tests (`@WithMockUser`)
- JavaScript (eg. arrow functions, spread operator, fetch/ axios)
- React (several components, use of several hooks and custom hooks)

## Milestones

- Milestone 1: Spring Boot with Spring Web & JavaScript with React
- Milestone 2: Milestone 1 & Spring Data JPA and H2
- Milestone 3: Milestone 2 & Spring Security
- Milestone 4: Milestone 3 & OAuth2 Resource Server
- Milestone 5: milestone 4 & increased complexity

## Run locally

In order to start both, the frontend and the backend server we need two terminals. This commands are tested in a Linux (Debian) environment.

Open a Terminal and clone the project:
```ssh
git clone git@github.com:Philippovitsch/pa-preparation-project.git
```

Navigate to the backend directory to start the backend:
```ssh
mvn spring-boot:run
```

In a second terminal navigate to the frontend folder to install the dependencies and to start the frontend:
```ssh
npm install && npm run dev
```

When both servers are running, open the following link in your browser to open the application: http://localhost:5173/.

There are two default users provided for the login:
- Username: `user`, Password: `user`
- Username: `admin`, Password: `admin`

## Screenshots

Index page with a logged in user:
![image](https://user-images.githubusercontent.com/16825493/234654865-5c9e8546-3ecf-4778-8a18-58e1fd5d6831.png)

Form to save a new task:
![image](https://user-images.githubusercontent.com/16825493/234655088-362511f5-cd16-45cf-be3d-22cbf998b0a2.png)
