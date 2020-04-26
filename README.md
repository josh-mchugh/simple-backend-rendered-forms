### Simple Thymeleaf Backend Rendering Application

This is a simple self contained Spring Boot app to demonstrate backend rendering and validation with Thymeleaf. 
Thymeleaf can make user input field validation easy with the use of jQuery as you can submit forms and re-render the form 
after it has been validated on the backend. Pageable tables can also be handle simply with thymeleaf rendering as it's just HTML and CSS
and no a lot of need for extensive javascript or javascript frameworks.

The use of jQuery's delegation and backend render of partial HTML reduces the complexity of app development as the view portion of the application 
is just HTML and CSS and th validation logic is handled on the server.

Thymeleaf Extensions:
  - Layout templates: https://github.com/ultraq/thymeleaf-layout-dialect
  - Pagination: https://github.com/jpenren/thymeleaf-spring-data-dialect

Front End Components:
- gulp
- webpack
- bootstrap
- fontawesome

## Running the App
````
mvn spring-boot:run
````

## Building Frontend
````
cd frontend
gulp build
````
or
````
cd frontend
npm run build
````

## Building the App
````
mvn clean install
````

## Demostration
![alt text](./demo.gif)