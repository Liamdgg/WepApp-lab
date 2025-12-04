# Product Management System

## Student Information
- **Name:** Dang Thanh Lam
- **Student ID:** ITITDK23039
- **Class:** ITIT23UN41

## Technologies Used
- Spring Boot 4.0.0
- Spring Data JPA
- MySQL 8.0
- Thymeleaf
- Maven

## Setup Instructions
1. Import project into VS Code
2. Create database: `product_management`
3. Update `application.properties` with your MySQL credentials
4. Run: `mvn spring-boot:run`
5. Open browser: http://localhost:8080/products

## Completed Features
- [x] CRUD operations
- [x] Search functionality
- [x] Advanced search with filters
- [x] Validation
- [x] Sorting
- [x] Pagination
- [ ] REST API (Bonus)

## Project Structure
product-management.zip
├── src/
│   ├── main/
│   │   ├── java/com/example/productmanagement/
│   │   │   ├── ProductManagementApplication.java
│   │   │   ├── entity/Product.java
│   │   │   ├── repository/ProductRepository.java
│   │   │   ├── service/
│   │   │   │   ├── ProductService.java
│   │   │   │   └── ProductServiceImpl.java
│   │   │   └── controller/ProductController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── templates/
│   │           ├── dashboard.html		
│   │           ├── product-list.html
│   │           └── product-form.html
├── pom.xml
└── README.md


## Database Schema
See `schema.sql` for database structure.

## Known Issues


## Time Spent
Approximately 5 hours

## Screenshots
See `capture/` folder.

