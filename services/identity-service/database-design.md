# Identity Service Database Design

## 1. Purpose

The Identity Service is responsible for managing user identity, authentication, authorization, and account-related information.

The service owns the data required to answer the following questions:

- Who is the user?
- How does the user authenticate?
- What roles does the user have?
- Is the user account active?
- Is the user authorized to access a protected resource?

The Identity Service does not manage travel inventory, bookings, payments, or provider business data. These responsibilities belong to other services.

## 2. Initial Database Scope

The initial Identity Service database will contain the following core tables:

```text
users
roles
user_roles
```

---

## 3. Users Table Design

The `users` table is the primary table of the Identity Service.

It stores the core identity, account, and authentication-related information required to identify and authenticate a user.

The `users` table will not store business-specific information related to travel providers, bookings, payments, or travel services.

Its primary responsibility is to maintain:

- User identity.
- Account information.
- Authentication credentials.
- Account status.
- Audit information.

The detailed attributes, data types, constraints, and indexes of the `users` table will be defined next.

### 3.1 Users Table Attributes

The `users` table stores the core identity, authentication, and account lifecycle information required by the Identity Service.

The initial authentication mechanism is based on email and password.

| Column           | Data Type    | Nullable | Constraint    | Purpose                                                                         |
| ---------------- | ------------ | -------- | ------------- | ------------------------------------------------------------------------------- |
| `id`             | BIGINT       | No       | Primary Key   | Unique internal identifier for the user.                                        |
| `first_name`     | VARCHAR(100) | No       | —             | Stores the user's first name.                                                   |
| `last_name`      | VARCHAR(100) | Yes      | —             | Stores the user's last name when available.                                     |
| `email`          | VARCHAR(255) | No       | UNIQUE        | Unique email address used as the login identifier.                              |
| `password_hash`  | VARCHAR(255) | No       | —             | Stores the securely hashed password. Plain-text passwords must never be stored. |
| `account_status` | VARCHAR(20)  | No       | —             | Represents the current lifecycle status of the user account.                    |
| `email_verified` | BOOLEAN      | No       | DEFAULT FALSE | Indicates whether the user's email address has been verified.                   |
| `created_at`     | TIMESTAMP    | No       | —             | Records when the user account was created.                                      |
| `updated_at`     | TIMESTAMP    | No       | —             | Records when the user account was last modified.                                |

The initial supported values for `account_status` are:

```text
ACTIVE
INACTIVE
SUSPENDED
```

---

## 4. Roles Table Design

The `roles` table stores the roles available in the Travel Reservation System.

Roles are used for authorization and determine the type of access a user has within the system.

The initial roles are:

```text
CUSTOMER
TRAVEL_PROVIDER
ADMIN
```

### 4.1 Roles Table Attributes

The `roles` table contains the role definitions used for authorization.

| Column       | Data Type   | Nullable | Constraint  | Purpose                                  |
| ------------ | ----------- | -------- | ----------- | ---------------------------------------- |
| `id`         | BIGINT      | No       | Primary Key | Unique internal identifier for the role. |
| `name`       | VARCHAR(50) | No       | UNIQUE      | Unique name of the role.                 |
| `created_at` | TIMESTAMP   | No       | —           | Records when the role was created.       |
| `updated_at` | TIMESTAMP   | No       | —           | Records when the role was last modified. |

The initial role values are:

```text
CUSTOMER
TRAVEL_PROVIDER
ADMIN
```

---

## 5. User Roles Table Design

The `user_roles` table represents the relationship between users and roles.

A user can have one or more roles, and the same role can be assigned to multiple users.

Therefore, the relationship between `users` and `roles` is implemented using the `user_roles` junction table.

```text
User
  │
  │ 1
  ▼
User_Roles
  ▲
  │ *
  │
Role
```

### 5.1 Primary Key Strategy

The `user_roles` table is a junction table and does not require an independent identifier.

The combination of `user_id` and `role_id` will be used as a composite primary key.

```text
PRIMARY KEY (user_id, role_id)
```

### 5.2 User Roles Table Attributes

The `user_roles` table stores the relationship between users and roles.

| Column    | Data Type | Nullable | Constraint               | Purpose                                   |
| --------- | --------- | -------- | ------------------------ | ----------------------------------------- |
| `user_id` | BIGINT    | No       | Primary Key, Foreign Key | References the user assigned to the role. |
| `role_id` | BIGINT    | No       | Primary Key, Foreign Key | References the role assigned to the user. |

The primary key is composed of both columns:

```text
PRIMARY KEY (user_id, role_id)
```

---

## 6. Database Relationships

The Identity Service database contains a many-to-many relationship between `users` and `roles`.

This relationship is implemented through the `user_roles` junction table.

```text
┌──────────────────────┐
│        users         │
├──────────────────────┤
│ id (PK)              │
│ first_name           │
│ last_name            │
│ email                │
│ password_hash        │
│ account_status       │
│ email_verified       │
│ created_at           │
│ updated_at           │
└──────────┬───────────┘
           │
           │ 1
           │
           ▼
┌──────────────────────┐
│      user_roles      │
├──────────────────────┤
│ user_id (PK, FK)     │
│ role_id (PK, FK)     │
└──────────┬───────────┘
           ▲
           │
           │ *
           │
┌──────────┴───────────┐
│        roles         │
├──────────────────────┤
│ id (PK)              │
│ name (UNIQUE)        │
│ created_at           │
│ updated_at           │
└──────────────────────┘
```

---

## 7. Constraints and Indexes

Database constraints are used to maintain data integrity and prevent invalid or duplicate data.

### 7.1 Primary Key Constraints

Each main table has a primary key:

```text
users
└── id

roles
└── id

user_roles
└── (user_id, role_id)
```

### 7.2 Unique Constraints

Unique constraints are used to prevent duplicate values where uniqueness is required by the business rules.

The following columns must have unique values:

```text
users.email

roles.name
```

### 7.3 Foreign Key Constraints

Foreign key constraints are used to maintain referential integrity between related tables.

The `user_roles` table contains foreign keys that reference the `users` and `roles` tables.

```text
user_roles.user_id
        │
        └──→ users.id


user_roles.role_id
        │
        └──→ roles.id
```

### 7.4 Index Strategy

Indexes are used to improve query performance for frequently accessed data.

The following indexes are required for the initial database design:

| Table        | Column    | Reason                                                            |
| ------------ | --------- | ----------------------------------------------------------------- |
| `users`      | `email`   | Used frequently during authentication and user lookup.            |
| `roles`      | `name`    | Used when retrieving a role by its name.                          |
| `user_roles` | `role_id` | Supports queries that retrieve users assigned to a specific role. |

### Index Design Considerations

The `users.email` and `roles.name` columns are unique.

The unique constraints will also provide efficient lookup capabilities for these columns.

The `user_roles` table uses the following composite primary key:

```text
(user_id, role_id)
```

---

## 8. Database Design Summary

The initial Identity Service database consists of three tables:

```text
users
roles
user_roles
```
┌─────────────┐          ┌─────────────┐
│    users    │          │    roles    │
│             │          │             │
│ id (PK)     │          │ id (PK)     │
└──────┬──────┘          └──────▲──────┘
       │                        │
       │                        │
       ▼                        │
   ┌────────────────────────────┴──┐
   │          user_roles           │
   ├───────────────────────────────┤
   │ user_id (PK, FK → users.id)   │
   │ role_id (PK, FK → roles.id)   │
   └───────────────────────────────┘