# MyFin Bank – Microservices Banking Application 💳🏦

## 1. Project Overview 📝

MyFin Bank is a microservices-based online banking platform that enables customers to perform day-to-day banking operations without visiting a physical branch. The system supports two primary roles:

- 👤 Customer – performs banking transactions and manages their own account.
- 🧑‍💼 Admin – manages customers, accounts, and loan approvals.

The system is built using Spring Boot microservices with service discovery, an API gateway, JWT-based security, H2 databases per service, a dedicated notification microservice, and JSP-based web UI.

This README provides a complete overview of the project, its architecture, the individual services, implemented user stories, setup instructions, and usage guidance.

---

## 2. Architecture 🏗️

### 2.1 High-Level Architecture

The application follows a microservices architecture, where each core capability is implemented as an independent Spring Boot service:

- 📘 eureka-server – Service registry (Netflix Eureka).
- 🚪 gateway – API gateway/edge service responsible for routing and security.
- 👨‍💻 customer-service – Customer-facing banking operations.
- 🧑‍💼 admin-service – Administrative operations for customers, accounts, and loans.
- 📧 notification-service – Email notification microservice.

Key characteristics:

- 🗄️ Each business service has its own H2 database instance and owns its data.
- 🔗 Services communicate primarily via REST APIs.
- 🌐 All services (except eureka-server) register with Eureka.
- 🌍 External clients (browser/JSP/JS, Postman) access the system only through the Gateway.
- 🔐 JWT-based security is applied at the gateway to protect internal services.

### 2.2 Logical Architecture Diagram (Textual)

Conceptually, the system looks like this:

[ Browser / Postman ]
        |
        v
   [ API Gateway ]  -- JWT auth, routing, CORS
        |
        +--> [ customer-service ]  -- Customer operations, transactions, loans, chat
        |
        +--> [ admin-service ]     -- Admin operations, approvals, customer mgmt
        |
        +--> [ notification-service ] -- Email notifications

All of the above register with:

    [ eureka-server ]  -- Service registry


Each business service has its own H2 DB:

    customer-service  --> H2 (customers, accounts, transactions, loans, chat, ...)
    admin-service     --> H2 (admins, customer views, loan approvals, ...)
    notification-serv --> H2 (notification logs, if applicable)

### 2.3 Microservice Responsibilities

- 👨‍💻 customer-service
  - Customer registration, login, and logout.
  - Account operations: deposit, withdraw, fund transfer.
  - Transaction logging with unique transaction IDs.
  - Investment transfers: Loan, Recurring Deposits (RD), Fixed Deposits (FD).
  - Loan EMI calculation and loan application.
  - Customer side of chat functionality.

- 🧑‍💼 admin-service
  - Admin registration, login, and logout.
  - CRUD-like operations on customers and customer accounts (activate/deactivate instead of delete).
  - View inactive (pending) customers and activate them, generating an AccountNo on approval.
  - Loan approval/denial based on customer balance and other rules.
  - Admin side of chat functionality.

- 📧 notification-service
  - Dedicated microservice to send email notifications.
  - Used when, for example, a customer balance turns zero.

- 📘 eureka-server
  - Service registry where all client microservices register and discover each other.

- 🚪 gateway
  - Single entry point for all client requests.
  - Routes requests to the appropriate microservice using Eureka service IDs.
  - Enforces JWT-based authentication and authorization.
  - Exposes public (unauthenticated) and protected (authenticated) endpoints.

---

## 3. Implemented User Stories ✅

### 3.1 Customer Stories 👤

1. 📥 Registration / Login / Logout
   - Customers can register with personal and account details.
   - Login authenticates credentials and issues a JWT token.
   - Logout invalidates the current session/token on the client side.

2. 💰 Balance Operations
   - Deposit – Add funds to the customer account.
   - Withdraw – Withdraw funds, validating minimum balance constraints.
   - Fund Transfer – Transfer money to:
     - Another account of the same customer.
     - Another customer’s account within the bank.

3. 🧾 Transaction ID Generation
   - Every transaction (deposit, withdraw, transfer, investment transfer, loan EMI payment) generates a unique Transaction ID.
   - Transaction metadata (amount, type, timestamp, account, status) is stored and visible in transaction history.

4. 📊 Investment Transfers
   - Transfer funds from the main account to internal investment products:
     - Loan – Pay loan EMIs.
     - Recurring Deposit (RD) – Create and manage RD investments.
     - Fixed Deposit (FD) – Create and manage FD investments.

5. 📉 Loan EMI Calculation
   - Customers can calculate EMI by providing:
     - Loan amount
     - Interest rate
     - Tenure (months)
   - System returns EMI amount and related details.

6. 📝 Loan Application
   - Customers can apply for various loan types.
   - Applications are stored and later reviewed by Admin.

7. 💬 Chat with Bank Authority
   - Customers can open chat sessions/rooms and send messages to Admin.
   - Admins can view and reply, enabling a helpdesk-style interaction.

### 3.2 Admin Stories 🧑‍💼

1. 📥 Admin Registration / Login / Logout
   - Admins can register and authenticate separately from customers.
   - Admin login yields an admin-specific session (and JWT claims/roles).

2. 👥 Customer Management (CRUD-ish)
   - View all customers.
   - View all inactive/pending customers.
   - Activate/deactivate customers (no hard deletes).
   - On activation, the system generates an AccountNo and sets status to Active.

3. 🧾 Customer Account Management
   - View and manage customer accounts.
   - Update certain account attributes depending on business rules.

4. 📧 Zero-Balance Notifications
   - When any customer’s bank balance hits zero, Admin receives an email notification.
   - Implemented via notification-service.

5. 🏦 Loan Approval/Deny
   - Admin reviews pending loan applications.
   - Approves or denies loans based on:
     - Customer’s current balance.
     - Other risk or eligibility criteria.

6. 💬 Admin-Customer Chat
   - Admin can see customer chat rooms and respond to messages.
   - Enables customer service within the application.

---

## 4. Module-Wise Details 🧩

### 4.1 eureka-server 📘

- Spring Boot service running the Eureka Discovery Server.
- Other microservices register as Eureka clients.
- Provides a dashboard to see registered instances.

### 4.2 gateway 🚪

- Spring Cloud Gateway configured to route incoming requests.
- Uses service discovery (Eureka) for resolving backend service URLs.
- Applies JWT security filters:
  - Public endpoints for login/registration.
  - Protected endpoints for account/transaction/loan APIs.
- Can be extended with rate limiting, CORS, or logging filters.

### 4.3 customer-service 👨‍💻

- Owns the Customer domain and related entities:
  - Customer, Account, Transaction, Loan, RD, FD, ChatMessage, etc.
- Repositories for each entity using Spring Data JPA.
- Service layer encapsulating business rules:
  - Balance validations, transaction rules, EMI calculations, etc.
- REST Controllers and/or JSP controllers for:
  - Auth, Accounts, Transactions, Loans, Investments, Chat.
- Exposes REST APIs (documented via Swagger/OpenAPI if configured).
- Uses its own H2 database.

### 4.4 admin-service 🧑‍💼

- Owns Admin domain and admin-side workflows:
  - Admin entity (AdminId, AdminName, AdminUserName, AdminPassword, AdminEmail, AdminPhone, AdminStatus).
  - Views over customers and loan applications (using DTOs and REST calls to customer-service).
- Uses RestTemplate/WebClient to call customer-service to get customer data (no direct shared entities).
- Implements admin-side controllers for:
  - Admin auth.
  - Viewing/activating/deactivating customers.
  - Managing accounts.
  - Reviewing and deciding on loan applications.
  - Admin chat UI.
- Uses its own H2 database.

### 4.5 notification-service 📧

- Standalone microservice responsible for sending emails.
- Exposes REST endpoints to accept notification requests from other services.
- Configurable SMTP/email provider.

---

## 5. Tech Stack 🛠️

- Language: Java
- Frameworks:
  - Spring Boot (Web, Data JPA, Security)
  - Spring Cloud (Eureka, Config, Gateway)
- Databases:
  - H2 (one instance per microservice)
- View Layer:
  - JSP / HTML / CSS, integrated with Spring MVC
  - JavaScript (Fetch API) for calling backend endpoints
- Security:
  - Spring Security with JWT-based authentication & authorization
- Build Tool:
  - Maven (with wrapper mvnw provided)

---

## 6. Setup & Prerequisites 🚀

### 6.1 Requirements

- Java JDK (version compatible with Spring Boot in this project)
- Maven 3.x (or use included mvnw scripts)
- IDE of your choice (IntelliJ IDEA, Eclipse, VS Code, etc.)

### 6.2 Project Structure (Top Level)

- admin-service/
- customer-service/
- notification-service/
- eureka-server/
- gateway/
---

## 7. Configuration ⚙️

Note: Adjust actual ports and configuration keys based on your concrete application.yml / application.properties and Config Server setup.

### 7.1 Ports & URLs (Typical)

- eureka-server: http://localhost:8751/
- gateway: http://localhost:8080/
- customer-service: runs on its own port and registers with Eureka.
- admin-service: runs on its own port and registers with Eureka.
- notification-service: runs on its own port and registers with Eureka.

### 7.2 Eureka Client Registration

Each client service (customer-service, admin-service, notification-service, gateway) configures:

- spring.application.name – service ID used by Eureka & gateway routing.
- eureka.client.serviceUrl.defaultZone – URL of the Eureka server.

### 7.3 H2 Database 🗄️

- Each microservice has its own H2 configuration (URL, username/password if any).
- H2 Console can be exposed for development:
  - spring.h2.console.enabled=true
  - spring.h2.console.path=/h2-console

### 7.4 Mail/Notification Settings 📧

- notification-service uses SMTP or a mock implementation.
- Configure host, port, username, password in properties or via Config Server.

---

## 8. Build & Run ▶️

### 8.1 Build the Project

From the project root:

./mvnw clean install

(or on Windows PowerShell):

./mvnw.cmd clean install

### 8.2 Recommended Startup Order

1. eureka-server
2. notification-service
3. customer-service
4. admin-service
5. gateway

### 8.3 Running Each Service

From each module directory (example for customer-service):

# Unix-like
./mvnw spring-boot:run

# Windows PowerShell
./mvnw.cmd spring-boot:run

Or run the generated WAR/JAR from target/ using java -jar (adjust filename and module as needed).

---

## 9. Routing via Gateway 🚪

### 9.1 Example Route Patterns

Actual paths depend on your gateway configuration.

- /api/customers/** → customer-service
- /api/admin/** → admin-service
- /api/notifications/** → notification-service

### 9.2 Public vs Protected Endpoints 🔐

- Public:
  - Customer registration and login endpoints.
  - Admin registration and login endpoints.
- Protected:
  - All other customer and admin operations.
  - Require a valid JWT token passed (e.g., in Authorization: Bearer <token> header).

---

## 10. Security (JWT) 🔑

### 10.1 Authentication Flow

1. User (Customer or Admin) submits credentials to the appropriate login endpoint.
2. Credentials are validated.
3. On success, a JWT token is generated with:
   - Subject (username or ID).
   - Role (CUSTOMER or ADMIN).
   - Expiration time.
4. Client stores the token (e.g., in memory/local storage) and sends it with subsequent requests.

### 10.2 Authorization Rules

- Gateway or downstream services validate JWT on each protected request.
- Role-based checks ensure that:
  - Customers cannot call admin-only operations.
  - Admins cannot call customer-only internal operations where restricted.

---

## 11. API Overview 🌐

For full details, refer to Swagger/OpenAPI documentation (if configured) or the controller classes.

### 11.1 Customer-Service (High-Level)

- Auth:
  - POST /customers/register
  - POST /customers/login
  - POST /customers/logout
- Accounts & Transactions:
  - GET /customers/{id}/accounts
  - POST /customers/{id}/deposit
  - POST /customers/{id}/withdraw
  - POST /customers/{id}/transfer
- Investments:
  - POST /customers/{id}/rd
  - POST /customers/{id}/fd
  - POST /customers/{id}/loan-transfer
- Loans:
  - POST /customers/{id}/loans/calc-emi
  - POST /customers/{id}/loans/apply
  - GET /customers/{id}/loans
- Chat:
  - GET /customers/{id}/chats
  - POST /customers/{id}/chats/messages

### 11.2 Admin-Service (High-Level)

- Auth:
  - POST /admin/register
  - POST /admin/login
  - POST /admin/logout
- Customer Management:
  - GET /admin/customers
  - GET /admin/customers/pending
  - PUT /admin/customers/{id}/activate
  - PUT /admin/customers/{id}/deactivate
- Loan Management:
  - GET /admin/loans/pending
  - PUT /admin/loans/{id}/approve
  - PUT /admin/loans/{id}/deny
- Chat:
  - GET /admin/chats
  - POST /admin/chats/messages

### 11.3 Notification-Service (High-Level)

- Notifications:
  - POST /notifications/email – send email based on payload.

---

## 12. Frontend / JSP Integration 🎨

- UI is primarily built with JSP pages rendered by Spring MVC controllers.
- JSPs use form submissions and/or Fetch API to call backend endpoints (usually through gateway).
- Main views include:
  - Customer registration & login pages.
  - Customer dashboard (account summary, quick actions, personal info, transactions).
  - Admin login page and admin dashboard.
  - Screens for pending approvals, all customers, transactions, loans, and chat.

---

## 13. Error Handling & Custom Exceptions 🚫

- Each microservice can define custom exception classes for business and validation errors (e.g., CustomerNotFoundException, InsufficientBalanceException, LoanNotEligibleException).
- A global exception handler (e.g., @ControllerAdvice) converts exceptions into:
  - Proper HTTP status codes (400, 404, 409, 500, etc.).
  - Structured JSON error responses for REST APIs.
- JSP-based flows continue to work as usual, with relevant messages displayed on the UI.

---

## 14. Future Enhancements 🔮

- Replace H2 with persistent databases (e.g., PostgreSQL/MySQL) for production.
- Add unit and integration tests for services.
- Implement API documentation with Swagger/OpenAPI.
- Introduce centralized logging and monitoring (ELK stack, Prometheus, Grafana).
- Enhance chat to use WebSockets for real-time communication.
- Add rate limiting and advanced security features (OAuth2, OpenID Connect).
- Add more customer products (credit cards, insurance, etc.) and richer analytics dashboards.
