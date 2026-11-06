
Thabitha, I checked your entire PDF. There are 8 sample programs, but the good news is that you do NOT need to memorise 8 completely different Spring Boot programs.

All 8 use almost the same structure:

Hospital Patient Registration System

Library Management System

Employee Management System

Product Inventory Management System

Online Banking System

Course Management System

Blood Donor Management System

Pharmacy Management System

The main differences are:

Class and variable names.

Database table and column names.

Search conditions.

A few special operations, such as deposit, withdrawal, and validation.

1. The one reference code to remember
Most important for your exam
Remember this flow:

JSP – Frontend

Form, buttons and table

Controller

Receives requests

Service

Contains business logic

Repository

Database operations using JPA

PostgreSQL

Stores records

Memory trick:

Entity = What information to store.

Repository = How to access the database.

Service = What work to perform.

Controller = Which request to handle.

JSP = What the user sees.

Remember: E → R → S → C → J

2. Common project structure
Use the same structure for all 8 questions.

ManagementSystem
│
├── src/main/java
│   └── com.example.management
│       ├── ManagementApplication.java
│       │
│       ├── controller
│       │   └── ManagementController.java
│       │
│       ├── entity
│       │   └── Management.java
│       │
│       ├── repository
│       │   └── ManagementRepository.java
│       │
│       └── service
│           └── ManagementService.java
│
├── src/main/resources
│   └── application.properties
│
└── src/main/webapp
    └── WEB-INF
        └── views
            ├── index.jsp
            └── form.jsp
For each question, change Management into Patient, Book, Employee, Product, Account, Course, Donor or Medicine.

3. Complete reference program – Hospital Patient Registration
We will learn the first question completely. This is your master program. The same CRUD logic will be used in the other questions.

Step 1: Create the project in Eclipse
Create a Spring Starter Project.

Setting

Value

Name

HospitalManagement

Type

Maven

Language

Java

Packaging

WAR

Java

17 or your lab-supported version

Dependencies

Spring Web, Spring Data JPA, PostgreSQL Driver

Add JSP support to your pom.xml:

<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.apache.tomcat.embed</groupId>
        <artifactId>tomcat-embed-jasper</artifactId>
    </dependency>

    <dependency>
        <groupId>jakarta.servlet.jsp.jstl</groupId>
        <artifactId>jakarta.servlet.jsp.jstl-api</artifactId>
    </dependency>

    <dependency>
        <groupId>org.glassfish.web</groupId>
        <artifactId>jakarta.servlet.jsp.jstl</artifactId>
    </dependency>

</dependencies>

Use WAR packaging because JSP is best supported with this deployment setup.

Step 2: application.properties
Location:

src/main/resources/application.properties

spring.datasource.url=jdbc:postgresql://localhost:5432/hospital
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
Change your_password to your PostgreSQL password.

Create the database in pgAdmin:

CREATE DATABASE hospital;

Hibernate will create the patient table automatically.

Step 3: Main application
ManagementApplication.java

package com.example.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ManagementApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            ManagementApplication.class, args
        );
    }
}

Memory trick: Every Spring Boot project starts here.

Step 4: Entity class
entity/Patient.java

package com.example.management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long patientId;

    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String bloodGroup;

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}

Remember these three annotations:

@Entity – makes the class a database entity.

@Id – identifies the primary key.

@GeneratedValue – automatically generates the ID.

Step 5: Repository
repository/PatientRepository.java

package com.example.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.management.entity.Patient;

public interface PatientRepository
        extends JpaRepository<Patient, Long> {

}

That's it!

You don't need to write SQL for basic CRUD operations.

JpaRepository already provides:

Method

Purpose

save()

Insert or update

findAll()

Display all

findById()

Search

deleteById()

Delete

existsById()

Check ID

Step 6: Service
service/PatientService.java

package com.example.management.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.management.entity.Patient;
import com.example.management.repository.PatientRepository;

@Service
public class PatientService {

    private final PatientRepository repository;

    public PatientService(PatientRepository repository) {
        this.repository = repository;
    }

    public Patient savePatient(Patient patient) {
        return repository.save(patient);
    }

    public List<Patient> getAllPatients() {
        return repository.findAll();
    }

    public Patient getPatientById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deletePatient(Long id) {
        repository.deleteById(id);
    }
}

Memory trick:

savePatient()
getAllPatients()
getPatientById()
deletePatient()

These four methods are the backbone of your CRUD program.

Step 7: Controller
controller/PatientController.java

package com.example.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.management.entity.Patient;
import com.example.management.service.PatientService;

@Controller
public class PatientController {

    private final PatientService service;

    public PatientController(PatientService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String home(Model model) {

        model.addAttribute(
            "patients", service.getAllPatients()
        );

        return "index";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("patient", new Patient());

        return "form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Patient patient) {

        service.savePatient(patient);

        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, Model model) {

        model.addAttribute(
            "patient", service.getPatientById(id)
        );

        return "form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        service.deletePatient(id);

        return "redirect:/";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam Long id, Model model) {

        model.addAttribute(
            "patients",
            java.util.Collections.singletonList(
                service.getPatientById(id)
            )
        );

        return "index";
    }
}

Controller memory trick:

Annotation

Meaning

@Controller

Controller class

@GetMapping

Get/display request

@PostMapping

Submit/save request

@ModelAttribute

Receives form data

@PathVariable

Gets ID from URL

@RequestParam

Gets value from request

Step 8: JSP pages
Create this folder:

src/main/webapp/WEB-INF/views/

index.jsp

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<html>
<head>
    <title>Patient Management</title>
</head>
<body>

<h2>Hospital Patient Management</h2>

<a href="add">Add Patient</a>

<h3>Search Patient</h3>

<form action="search" method="get">
    Patient ID:
    <input type="number" name="id" required>
    <button type="submit">Search</button>
</form>

<table border="1">

<tr>
    <th>ID</th>
    <th>Name</th>
    <th>Age</th>
    <th>Gender</th>
    <th>Contact</th>
    <th>Email</th>
    <th>Address</th>
    <th>Blood Group</th>
    <th>Actions</th>
</tr>

<c:forEach var="p" items="${patients}">

<tr>
    <td>${p.patientId}</td>
    <td>${p.name}</td>
    <td>${p.age}</td>
    <td>${p.gender}</td>
    <td>${p.contactNumber}</td>
    <td>${p.email}</td>
    <td>${p.address}</td>
    <td>${p.bloodGroup}</td>

    <td>
        <a href="edit/${p.patientId}">Edit</a>
        <a href="delete/${p.patientId}">Delete</a>
    </td>
</tr>

</c:forEach>

</table>

</body>
</html>

form.jsp

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Patient Form</title>
</head>
<body>

<h2>Patient Registration</h2>

<form:form action="/save" method="post"
           modelAttribute="patient">

    Name:
    <form:input path="name"/>
    <br><br>

    Age:
    <form:input path="age"/>
    <br><br>

    Gender:
    <form:input path="gender"/>
    <br><br>

    Contact Number:
    <form:input path="contactNumber"/>
    <br><br>

    Email:
    <form:input path="email"/>
    <br><br>

    Address:
    <form:input path="address"/>
    <br><br>

    Blood Group:
    <form:input path="bloodGroup"/>
    <br><br>

    <button type="submit">Save</button>

</form:form>

</body>
</html>

For an application deployed with a context path, use context-aware URLs in the JSP rather than hardcoding /save and other paths.

4. How to use the same reference code for all 8 questions
This is the most important exam preparation section.

You will copy the same Entity → Repository → Service → Controller → JSP structure.

Only change the class name, fields and database name.

Question 1 – Hospital Patient Registration
Reference program already completed
Item

Value

Entity

Patient

Repository

PatientRepository

Service

PatientService

Controller

PatientController

Database

hospital

Primary key

patientId

Fields:

private Long patientId;
private String name;
private int age;
private String gender;
private String contactNumber;
private String email;
private String address;
private String bloodGroup;

Question 2 – Library Management System
GL Bajaj Institute of Management
Change the Patient names to Book.

Book.java

@Entity
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String edition;
    private int publicationYear;
    private String category;
    private int availableCopies;

    // Generate getters and setters
}

Change the other classes:

PatientRepository  → BookRepository
PatientService     → BookService
PatientController  → BookController
Repository:

public interface BookRepository
        extends JpaRepository<Book, Long> {
}

Service:

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    public Book saveBook(Book b) {
        return repository.save(b);
    }

    public List<Book> getAllBooks() {
        return repository.findAll();
    }

    public Book getBookById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteBook(Long id) {
        repository.deleteById(id);
    }
}

Use the same controller logic as PatientController, replacing:

Patient with Book.

patients with books.

patient with book.

Patient ID with Book ID.

Database:

CREATE DATABASE library;

Question 3 – Employee Management System
Employee Management Software Systems: Workforce Solutions
 – Gallery HR
Employee.java

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String name;
    private int age;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String department;
    private String designation;
    private String dateOfJoining;
    private double salary;

    // Generate getters and setters
}

Repository:

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
}

Service:

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository repository;

    public Employee saveEmployee(Employee e) {
        return repository.save(e);
    }

    public List<Employee> getAllEmployees() {
        return repository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteEmployee(Long id) {
        repository.deleteById(id);
    }
}

Use the same PatientController logic, replacing Patient with Employee.

Database:

CREATE DATABASE employee;

Question 4 – Product Inventory Management
Inventory Control: Methods, Best Practices & Systems That Work
Product.java

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    private String productName;
    private String category;
    private String brand;
    private String description;
    private double price;
    private int quantity;
    private String supplierName;
    private String manufacturingDate;
    private String expiryDate;

    // Generate getters and setters
}

Repository:

public interface ProductRepository
        extends JpaRepository<Product, Long> {
}

Service:

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Product saveProduct(Product p) {
        return repository.save(p);
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }
}

Database:

CREATE DATABASE inventory;

Use the same controller and JSP structure, replacing Patient with Product.

Question 5 – Online Banking System
Bank Operations Manager Carefully Monitors Real-Time Payment Processing
This question is slightly different because it has Deposit, Withdraw and Balance operations.

Account.java

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    private String accountNumber;
    private String customerName;
    private int age;
    private String gender;
    private String contactNumber;
    private String email;
    private String address;
    private String accountType;
    private double accountBalance;

    // Generate getters and setters
}

Repository:

public interface AccountRepository
        extends JpaRepository<Account, Long> {

    Account findByAccountNumber(String accountNumber);
}

Service:

@Service
public class AccountService {

    @Autowired
    private AccountRepository repository;

    public Account saveAccount(Account a) {
        return repository.save(a);
    }

    public List<Account> getAllAccounts() {
        return repository.findAll();
    }

    public Account getAccount(String number) {
        return repository.findByAccountNumber(number);
    }

    public Account deposit(String number, double amount) {

        Account a = getAccount(number);

        if (a == null || amount <= 0) {
            throw new IllegalArgumentException("Invalid account or amount");
        }

        a.setAccountBalance(
            a.getAccountBalance() + amount
        );

        return repository.save(a);
    }

    public Account withdraw(String number, double amount) {

        Account a = getAccount(number);

        if (a == null || amount <= 0 ||
            amount > a.getAccountBalance()) {

            throw new IllegalArgumentException("Invalid withdrawal");
        }

        a.setAccountBalance(
            a.getAccountBalance() - amount
        );

        return repository.save(a);
    }

    public double getBalance(String number) {

        Account a = getAccount(number);

        if (a == null) {
            throw new IllegalArgumentException("Account not found");
        }

        return a.getAccountBalance();
    }
}

Additional controller methods:

@PostMapping("/deposit")
public String deposit(
        @RequestParam String accountNumber,
        @RequestParam double amount) {

    service.deposit(accountNumber, amount);

    return "redirect:/";
}

@PostMapping("/withdraw")
public String withdraw(
        @RequestParam String accountNumber,
        @RequestParam double amount) {

    service.withdraw(accountNumber, amount);

    return "redirect:/";
}

@GetMapping("/balance")
public String balance(
        @RequestParam String accountNumber,
        Model model) {

    model.addAttribute(
        "balance", service.getBalance(accountNumber)
    );

    return "balance";
}

Database:

CREATE DATABASE banking;

Remember the banking logic:

Deposit = Balance + Amount.

Withdraw = Balance - Amount.

Balance = Current account balance.

Never allow a negative deposit or withdrawal.

Never allow withdrawal greater than the available balance.

For an actual banking application, monetary values should use BigDecimal, and balance updates should be transactional.

Question 6 – Course Management System
Teaching Resources | Center for Teaching and Learning | UTD
Course.java

@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    private String courseCode;
    private String courseName;
    private String department;
    private String facultyName;
    private int credits;
    private int semester;
    private int courseCapacity;

    // Generate getters and setters
}

Repository:

public interface CourseRepository
        extends JpaRepository<Course, Long> {
}

Service:

@Service
public class CourseService {

    @Autowired
    private CourseRepository repository;

    public Course saveCourse(Course c) {
        return repository.save(c);
    }

    public List<Course> getAllCourses() {
        return repository.findAll();
    }

    public Course getCourseById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteCourse(Long id) {
        repository.deleteById(id);
    }
}

Special requirement:

Your PDF also asks for:

Prevent registration when course capacity is full.

Avoid duplicate student registrations for the same course.

For this, you need a separate StudentCourse registration entity containing student ID and course ID, plus a registration count. The eight fields in Course alone are not enough to implement duplicate student registration checking.

Database:

CREATE DATABASE course;

Question 7 – Blood Donor Management System
Ik ga doneren en ik neem mee... | Sanquin
Donor.java

@Entity
public class Donor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long donorId;

    private String name;
    private int age;
    private String gender;
    private String bloodGroup;
    private String contactNumber;
    private String email;
    private String address;
    private String lastDonationDate;
    private String healthStatus;

    // Generate getters and setters
}

Repository:

public interface DonorRepository
        extends JpaRepository<Donor, Long> {

    List<Donor> findByBloodGroup(String bloodGroup);
}

Service:

@Service
public class DonorService {

    @Autowired
    private DonorRepository repository;

    public Donor saveDonor(Donor d) {
        return repository.save(d);
    }

    public List<Donor> getAllDonors() {
        return repository.findAll();
    }

    public List<Donor> searchByBloodGroup(String group) {
        return repository.findByBloodGroup(group);
    }

    public Donor getDonorById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void deleteDonor(Long id) {
        repository.deleteById(id);
    }
}

Special search controller:

@GetMapping("/search")
public String search(
        @RequestParam String bloodGroup,
        Model model) {

    model.addAttribute(
        "donors",
        service.searchByBloodGroup(bloodGroup)
    );

    return "index";
}

Database:

CREATE DATABASE donor;

Remember: Search is based on Blood Group, not Donor ID.

Age, health status and donation eligibility need validation. Your question does not specify the exact eligibility criteria, so don't invent a medical rule in the exam.

Question 8 – Pharmacy Management System
Día Mundial del Farmacéutico: historia, origen y el rol vital de quienes garantizan salud y bienestar en cada rincón del planeta - Infobae
Medicine.java

@Entity
public class Medicine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    private String medicineName;
    private String category;
    private String manufacturer;
    private String batchNumber;
    private double price;
    private int quantity;
    private String manufacturingDate;
    private String expiryDate;
    private String supplierName;

    // Generate getters and setters
}

Repository:

public interface MedicineRepository
        extends JpaRepository<Medicine, Long> {

    List<Medicine> findByMedicineNameContainingIgnoreCase(
        String name
    );
}

Service:

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository repository;

    public Medicine saveMedicine(Medicine m) {
        if (m.getQuantity() < 0) {
            throw new IllegalArgumentException(
                "Quantity cannot be negative"
            );
        }

        return repository.save(m);
    }

    public List<Medicine> getAllMedicines() {
        return repository.findAll();
    }

    public Medicine getMedicineById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Medicine> searchMedicine(String name) {
        return repository
            .findByMedicineNameContainingIgnoreCase(name);
    }

    public void deleteMedicine(Long id) {
        repository.deleteById(id);
    }
}

Special requirements:

Do not allow negative stock quantities.

Identify expired medicines using the expiry date.

Search using Medicine ID or Medicine Name.

Database:

CREATE DATABASE pharmacy;

For proper expiry checking, use LocalDate instead of String for manufacturing and expiry dates, and compare the expiry date with today's date.

5. The JSP code is reusable too!
You don't have to write a completely new JSP page for every question.

Copy index.jsp and form.jsp from the Patient program.

Then replace the names.

Question

List variable

Form object

ID

Hospital

patients

patient

patientId

Library

books

book

bookId

Employee

employees

employee

employeeId

Product

products

product

productId

Banking

accounts

account

accountId

Course

courses

course

courseId

Blood Donor

donors

donor

donorId

Pharmacy

medicines

medicine

medicineId

One important correction to the reference form.jsp: add this hidden field inside the form so editing an existing record preserves its ID.

<form:hidden path="patientId"/>

For another project, change patientId to its respective ID.

6. One-page revision sheet
Memorise this table before entering the lab.

Operation

Code to remember

Insert

repository.save(object)

Display

repository.findAll()

Search by ID

repository.findById(id)

Update

repository.save(object)

Delete

repository.deleteById(id)

Search by name

findByNameContainingIgnoreCase(name)

Search by blood group

findByBloodGroup(group)

The most important thing: Save is used for both inserting and updating. If the object has an existing ID, JPA updates that record.

7. How to remember everything for your practical
Don't memorise 8 programs line by line. Practise this order:

Your memory checklist

Entity

Write @Entity, @Id, @GeneratedValue, and variables.

Repository

Extend JpaRepository<Entity, Long>.

Service

Write save, getAll, getById and delete.

Controller

Write home, add, save, edit, delete and search.

JSP

Write form, table, edit and delete links.

Database

Configure PostgreSQL in application.properties.

8. Let's practise like your actual lab exam
Spring Boot practical mini-test

0/5 answered
1. Which annotation is used to make a Java class a database entity?


@Controller

@Entity

@Service
2. Which interface do we extend for CRUD operations?


JpaRepository

PatientController

SpringApplication
3. Which method is used to insert or update a record?


findAll()

save()

deleteById()
4. Which annotation receives form data?


@Entity

@ModelAttribute

@Id
5. Which layer contains business logic?


JSP

Repository

Service
My recommendation for your exam: First practise the complete Hospital Patient program in Eclipse. Then practise Library and Employee by changing only the entity fields and class names. Finally, learn Banking, Course, Donor and Pharmacy's additional requirements.

The PDF's eight questions are covered above. 
Sample Questions(1).pdf

One important distinction: the snippets for the other seven systems are reusable code sections, not eight independently complete projects. You still need their matching controller, JSP fields and generated getters/setters. Also, Course registration needs an additional registration entity.

I can next teach you the actual Eclipse execution from creating the project to running the JSP page and checking the PostgreSQL table, step by step, using this reference program.

