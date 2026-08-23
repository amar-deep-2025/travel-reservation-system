# Domain Analysis

## 1. Purpose

This document identifies the core business domains of the Travel Reservation System and analyzes their primary responsibilities.

The purpose of this analysis is to establish clear business boundaries before defining the microservice architecture.

The identified domains will be evaluated based on their responsibilities, data ownership, dependencies, scalability requirements, and potential for independent deployment.

The initial version of the system will support bus and flight reservations while maintaining an architecture that can support additional travel domains in the future.

## 2. Identified Business Domains

Based on the business requirements, the Travel Reservation System can be divided into the following core business domains.

### 2.1 Identity and Access Management

This domain is responsible for managing user identities, authentication, authorization, and access control.

Its primary responsibilities include:

- Customer registration and account management.
- User authentication.
- Role and permission management.
- Access control for customers, travel providers, and administrators.
- Password management and account recovery.
- Secure access to protected system resources.

This domain represents a cross-cutting business capability because multiple parts of the platform depend on user identity and authorization.

### 2.2 Travel Inventory Management

This domain is responsible for managing the travel services offered through the platform.

Its primary responsibilities include:

- Managing buses and flights.
- Managing travel providers.
- Defining routes and destinations.
- Managing travel schedules.
- Managing seat layouts.
- Maintaining travel availability.
- Managing travel pricing and operational status.

This domain owns the core travel inventory required for customers to search and reserve travel services.

### 2.3 Travel Search and Discovery

This domain is responsible for helping customers discover available travel options.

Its primary responsibilities include:

- Searching based on origin, destination, and travel date.
- Retrieving available bus and flight options.
- Filtering and sorting travel results.
- Displaying travel schedules, pricing, and availability.
- Providing detailed travel information.

The search domain may later require independent optimization because search traffic can grow differently from booking traffic.

### 2.4 Reservation and Booking Management

This domain is responsible for managing the complete booking lifecycle.

Its primary responsibilities include:

- Creating temporary seat reservations.
- Managing seat selection.
- Creating pending bookings.
- Confirming bookings after successful payment.
- Handling booking failures and expiration.
- Maintaining booking history and booking status.

This is one of the most critical domains because it must handle concurrent requests and prevent double booking.

### 2.5 Payment Management

This domain is responsible for managing payment transactions associated with bookings.

Its primary responsibilities include:

- Initiating payment requests.
- Tracking payment status.
- Processing payment confirmations.
- Handling failed or cancelled payments.
- Supporting refunds for eligible bookings.
- Maintaining payment transaction records.

The payment domain should maintain clear ownership of payment-related data and external payment provider integrations.

### 2.6 Cancellation and Refund Management

This domain is responsible for handling booking cancellations and related refund operations.

Its primary responsibilities include:

- Validating cancellation eligibility.
- Applying cancellation policies.
- Calculating refund amounts.
- Updating booking cancellation status.
- Initiating refund processing.
- Tracking refund status.

This domain is closely related to Booking and Payment but has its own business rules and lifecycle.

### 2.7 Notification Management

This domain is responsible for delivering notifications related to important platform events.

Its primary responsibilities include:

- Booking confirmation notifications.
- Payment status notifications.
- Cancellation notifications.
- Refund notifications.
- Email notification delivery.
- Future support for SMS, push notifications, and other channels.

Notification processing should remain independent from critical business operations so that notification failures do not affect booking or payment completion.

### 2.8 Administration and Platform Management

This domain is responsible for platform-level administrative operations.

Its primary responsibilities include:

- Managing customer accounts.
- Managing travel provider accounts.
- Monitoring bookings and travel services.
- Monitoring payments, cancellations, and refunds.
- Managing platform-level policies and configurations.
- Recording significant administrative actions.

This domain provides administrative capabilities without owning the core business data of other domains.

## 3. Domain Relationship Analysis

The identified business domains are connected through the overall travel reservation lifecycle. However, each domain has its own responsibilities, data ownership, dependencies, scalability requirements, and operational characteristics.

Identifying these relationships is important before defining microservice boundaries. Not every business domain necessarily needs to become an independent microservice.

### 3.1 Identity and Access Management Relationships

The Identity and Access Management domain provides authentication and authorization capabilities across the platform.

This domain is responsible for:

- User registration and account management.
- Authentication.
- Role and permission management.
- Access control.
- Password management and account recovery.

Multiple business domains depend on identity and access information, including:

- Travel Inventory Management.
- Reservation and Booking Management.
- Payment Management.
- Administration and Platform Management.

Identity and Access Management should remain the owner of user identity, authentication, roles, and permissions. Other domains should rely on this information without directly managing authentication-related data.

---

### 3.2 Travel Inventory and Search Relationship

The Travel Search and Discovery domain depends on travel inventory information to provide customers with available travel options.

Travel Inventory Management is responsible for maintaining:

- Travel providers.
- Buses and flights.
- Routes.
- Schedules.
- Seat layouts.
- Seat availability.
- Pricing.
- Operational status.

The Search domain uses relevant travel information to allow customers to search, filter, sort, and view available travel options.

The relationship can be represented as:

````text
Travel Inventory
       ↓
Travel Search
       ↓
Customer Search Results

```md
### 3.3 Travel Inventory and Booking Relationship

The Reservation and Booking Management domain depends on travel inventory and availability information.

Before creating a reservation or booking, the system must access relevant information such as:

- Travel schedules.
- Available seats.
- Current seat availability.
- Travel pricing.
- Travel service status.

Travel Inventory Management remains responsible for maintaining travel-related data and availability.

Reservation and Booking Management remains responsible for:

- Seat selection.
- Temporary seat reservation.
- Booking creation.
- Booking status.
- Booking confirmation.
- Booking expiration.
- Booking cancellation.
- Booking history.

The relationship can be represented as:

```text
Travel Inventory
       ↓
Travel and Availability Information
       ↓
Reservation and Booking

### 3.3 Travel Inventory and Booking Relationship

The Reservation and Booking Management domain depends on travel inventory and availability information.

Before creating a reservation or booking, the system must access relevant information such as:

- Travel schedules.
- Available seats.
- Current seat availability.
- Travel pricing.
- Travel service status.

Travel Inventory Management remains responsible for maintaining travel-related data and availability.

Reservation and Booking Management remains responsible for:

- Seat selection.
- Temporary seat reservation.
- Booking creation.
- Booking status.
- Booking confirmation.
- Booking expiration.
- Booking cancellation.
- Booking history.

The relationship can be represented as:

```text
Travel Inventory
       ↓
Travel and Availability Information
       ↓
Reservation and Booking

### 3.4 Booking and Payment Relationship

Reservation and Booking Management and Payment Management are closely related, but they represent separate business responsibilities.

The Reservation and Booking Management domain is responsible for:

- Creating reservations.
- Managing the booking lifecycle.
- Maintaining booking status.
- Managing booking confirmation.
- Managing booking expiration.
- Managing booking cancellation.

The Payment Management domain is responsible for:

- Creating payment transactions.
- Initiating payment requests.
- Tracking payment status.
- Integrating with payment providers.
- Processing refunds.
- Maintaining payment transaction records.

The relationship can be represented as:

```text
Create Reservation
        ↓
Create Pending Booking
        ↓
Initiate Payment
        ↓
Receive Payment Result
       / \
      /   \
Success   Failure
   ↓         ↓
Confirm     Update Booking
Booking     Status
````

### 3.5 Booking, Cancellation, and Refund Relationship

Cancellation and refund operations are related to both the Reservation and Booking Management domain and the Payment Management domain.

The Reservation and Booking Management domain is responsible for determining whether a booking can be cancelled based on the applicable cancellation policy.

The Payment Management domain is responsible for processing and tracking refunds when a cancelled booking is eligible for a refund.

The cancellation and refund flow can be represented as:

```text
Booking
   ↓
Cancellation Request
   ↓
Validate Cancellation Eligibility
   ↓
Apply Cancellation Policy
   ↓
Calculate Refund Amount
   ↓
Cancel Booking
   ↓
Release Seat
   ↓
Initiate Refund
   ↓
Update Refund Status
```

### 3.6 Event and Notification Relationship

The Notification Management domain is responsible for informing customers about important events that occur during the travel reservation lifecycle.

Multiple business domains can generate events that require customer notifications.

Examples include:

- Booking confirmation.
- Payment success or failure.
- Booking cancellation.
- Refund initiation or completion.
- Reservation expiration.

The relationship can be represented as:

```text
Booking Confirmed
       ↓
Send Notification


Payment Successful
       ↓
Send Notification


Booking Cancelled
       ↓
Send Notification


Refund Processed
       ↓
Send Notification
```

### 3.6 Event and Notification Relationship

The Notification Management domain is responsible for informing customers about important events that occur during the travel reservation lifecycle.

Multiple business domains can generate events that require customer notifications.

Examples include:

- Booking confirmation.
- Payment success or failure.
- Booking cancellation.
- Refund initiation or completion.
- Reservation expiration.

The relationship can be represented as:

```text
Booking Confirmed
       ↓
Send Notification


Payment Successful
       ↓
Send Notification


Booking Cancelled
       ↓
Send Notification


Refund Processed
       ↓
Send Notification
```

### 3.7 Administration and Platform Management Relationship

The Administration and Platform Management domain interacts with multiple business domains to provide authorized management, monitoring, and operational capabilities.

However, the Administration domain should not own the core business data of other domains.

The ownership of core business data remains with the respective domains:

- User identity, authentication, roles, and permissions remain owned by Identity and Access Management.
- Travel services, schedules, seat layouts, availability, and pricing remain owned by Travel Inventory Management.
- Reservation and booking information remain owned by Reservation and Booking Management.
- Payment and refund transactions remain owned by Payment Management.
- Notification records and delivery status remain owned by Notification Management.

The Administration and Platform Management domain provides controlled capabilities to:

- Manage customer accounts.
- Manage travel provider accounts.
- Monitor travel services and operational status.
- Monitor bookings and reservation activity.
- Monitor payments, cancellations, and refunds.
- Manage platform-level configurations and policies.
- Review significant administrative and operational activities.

The relationship can be represented as:

```text
                    Administration
                          │
                          ▼
             Platform Management Capabilities
                          │
          ┌───────────────┼───────────────┐
          ▼               ▼               ▼
       Identity        Travel          Booking /
       Management      Inventory       Payment Data
```

### 3.8 Domain Relationship Summary

The domain relationship analysis identifies the primary responsibilities and dependencies between the core business domains of the Travel Reservation System.

| Domain                                 | Primary Relationships                                                                                                       |
| -------------------------------------- | --------------------------------------------------------------------------------------------------------------------------- |
| Identity and Access Management         | Provides authentication and authorization capabilities across the platform.                                                 |
| Travel Inventory Management            | Manages travel services, schedules, seats, availability, pricing, and operational status.                                   |
| Travel Search and Discovery            | Uses travel inventory information to provide search, filtering, sorting, and travel discovery capabilities.                 |
| Reservation and Booking Management     | Uses travel availability information and manages the reservation and booking lifecycle.                                     |
| Payment Management                     | Processes payment and refund transactions associated with bookings.                                                         |
| Notification Management                | Responds to important business events and delivers customer notifications.                                                  |
| Administration and Platform Management | Provides controlled management and monitoring capabilities across multiple domains without owning their core business data. |

The analysis shows that the identified domains have different levels of independence, complexity, data ownership, and scalability requirements.

Therefore, the identified business domains should not automatically be converted into separate microservices.

The next step is to evaluate these domains as potential service candidates and define appropriate service boundaries based on business responsibility, data ownership, coupling, scalability requirements, and deployment independence.

## 4. Domain Ownership and Service Candidates

The identified business domains must be evaluated before defining the microservice architecture.

A business domain should not automatically become an independent microservice. Service boundaries should be determined based on factors such as business responsibility, data ownership, coupling, scalability requirements, deployment independence, and operational complexity.

For the initial version of the Travel Reservation System, the following domains are considered as potential service candidates:

| Business Domain                        | Initial Service Decision                       | Reason                                                                                               |
| -------------------------------------- | ---------------------------------------------- | ---------------------------------------------------------------------------------------------------- |
| Identity and Access Management         | Independent Service Candidate                  | Owns user identity, authentication, authorization, roles, and permissions.                           |
| Travel Inventory Management            | Independent Service Candidate                  | Owns travel services, schedules, seats, availability, and pricing.                                   |
| Travel Search and Discovery            | Initially part of Travel Inventory             | Depends heavily on travel inventory data and does not initially require an independent deployment.   |
| Reservation and Booking Management     | Independent Service Candidate                  | Owns the reservation and booking lifecycle and contains critical concurrency-related business logic. |
| Payment Management                     | Independent Service Candidate                  | Owns payment and refund transactions and external payment provider integration.                      |
| Cancellation and Refund Management     | Split between Booking and Payment              | Cancellation belongs to the booking lifecycle, while refunds belong to payment processing.           |
| Notification Management                | Independent Service Candidate                  | Can process events independently and should not affect critical booking or payment operations.       |
| Administration and Platform Management | Initially a capability, not a separate service | Primarily provides controlled access and management across existing domains.                         |

### 4.1 Initial Service Candidates

Based on the domain analysis, the following domains are strong candidates for independent services:

1. Identity and Access Management
2. Travel Inventory Management
3. Reservation and Booking Management
4. Payment Management
5. Notification Management

These domains have relatively clear business responsibilities and data ownership boundaries.

### 4.2 Domains Not Initially Separated

The following domains will not initially become independent microservices:

#### Travel Search and Discovery

Travel Search and Discovery will initially remain part of the Travel Inventory domain because it depends heavily on travel inventory data.

As search traffic, performance requirements, or search complexity increase, this capability can be extracted into an independent Search Service in the future.

#### Cancellation Management

Cancellation management will initially remain within the Reservation and Booking Management domain because cancellation is part of the booking lifecycle.

#### Refund Management

Refund processing will remain within the Payment Management domain because refunds are directly related to payment transactions.

#### Administration and Platform Management

Administration will initially be implemented as controlled administrative capabilities using existing service interfaces.

A separate Administration Service can be considered in the future if platform-level administrative workflows become sufficiently complex.

### 4.3 Initial Service Boundary Direction

The initial service boundary direction is:

```text
Identity and Access
        │
        ├── User Identity
        ├── Authentication
        └── Authorization


Travel Inventory
        │
        ├── Bus Management
        ├── Flight Management
        ├── Routes
        ├── Schedules
        ├── Seats and Availability
        └── Travel Search


Reservation and Booking
        │
        ├── Seat Selection
        ├── Temporary Reservation
        ├── Booking Lifecycle
        ├── Booking Cancellation
        └── Booking History


Payment
        │
        ├── Payment Processing
        ├── Payment Status
        ├── Refund Processing
        └── Payment Provider Integration


Notification
        │
        ├── Booking Notifications
        ├── Payment Notifications
        ├── Cancellation Notifications
        └── Refund Notifications
```

## 5. Data Ownership Analysis

Clear data ownership is required to maintain service independence and prevent unnecessary coupling between services.

Each service should be responsible for managing its own business data. Other services should access required information through well-defined service interfaces or events rather than directly modifying another service's database.

### 5.1 Identity and Access Data Ownership

The Identity and Access service will own data related to:

- Users.
- Customer accounts.
- Travel provider accounts.
- Administrator accounts.
- User roles.
- Permissions.
- Authentication credentials.
- Account status.
- Password and account recovery information.

Other services may store a user identifier when required for business operations, but they should not own or directly manage authentication credentials or authorization data.

---

### 5.2 Travel Inventory Data Ownership

The Travel Inventory service will own data related to:

- Travel providers.
- Buses.
- Flights.
- Routes.
- Locations and destinations.
- Travel schedules.
- Seat layouts.
- Seat availability.
- Travel pricing.
- Travel service operational status.

The Travel Inventory service will remain the source of truth for travel-related inventory information.

Other services should not directly modify travel inventory data.

---

### 5.3 Reservation and Booking Data Ownership

The Reservation and Booking service will own data related to:

- Temporary reservations.
- Seat reservation status.
- Bookings.
- Booking passengers.
- Booking status.
- Booking cancellation details.
- Booking history.
- Reservation expiration information.

The Reservation and Booking service will manage the lifecycle of a booking from reservation creation through confirmation, cancellation, or expiration.

The Booking service may store references to users, travel schedules, and payment transactions, but it will not own the complete data managed by those services.

---

### 5.4 Payment Data Ownership

The Payment service will own data related to:

- Payment transactions.
- Payment status.
- Payment attempts.
- Payment provider references.
- Refund transactions.
- Refund status.
- Payment-related failure information.

The Payment service will remain responsible for communication with external payment providers and for maintaining payment and refund transaction records.

The Booking service may reference payment information but should not directly manage payment transaction data.

---

### 5.5 Notification Data Ownership

The Notification service will own data related to:

- Notification requests.
- Notification type.
- Notification channel.
- Delivery attempts.
- Delivery status.
- Notification failure information.
- Notification history.

The Notification service will process events or requests generated by other services without taking ownership of the underlying booking, payment, or travel data.

---

### 5.6 Cross-Service Data References

Services may need to store identifiers that reference data owned by another service.

Examples include:

```text
Booking Service
    │
    ├── userId
    ├── travelScheduleId
    └── paymentId


Payment Service
    │
    └── bookingId


Notification Service
    │
    ├── userId
    ├── bookingId
    └── paymentId
```

### 5.7 Data Ownership Principle

The initial architecture will follow a clear data ownership principle to maintain service independence and reduce unnecessary coupling.

Each service should:

- Own its business logic.
- Own its data.
- Control modifications to its data.
- Expose required functionality through well-defined APIs or events.
- Avoid direct access to another service's database.

The principle can be represented as:

```text
One Service
     ↓
Owns Its Business Logic
     +
Owns Its Data
     +
Controls Data Modification
```
