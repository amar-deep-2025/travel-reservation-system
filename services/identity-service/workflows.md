# Identity Service Workflows

## 1. Purpose

This document describes the core workflows handled by the Identity Service.

The workflows define how requests move through the system, including validation, authentication, authorization, database operations, and possible outcomes.

The initial workflows covered by the Identity Service are:

1. User Registration
2. User Login
3. JWT Authentication
4. Authorization

---

## 2. User Registration Workflow

The user registration workflow is responsible for creating a new user account and assigning the appropriate initial role.

The initial registration flow supports:

- Customer registration.
- Travel Provider user registration.

Administrator accounts will not be created through the public registration flow.

The high-level registration workflow is:

```text
User
  │
  │ Registration Request
  ▼
Identity Service
  │
  ├── Validate Request
  │
  ├── Check Email Availability
  │
  ├── Hash Password
  │
  ├── Create User Account
  │
  ├── Assign Role
  │
  └── Return Registration Response
```

### 2.1 Registration Flow Steps

The public registration process follows these steps:

```text
1. User submits registration request
            ↓
2. Identity Service receives the request
            ↓
3. Validate request data
            ↓
4. Check whether the email already exists
            │
            ├── Yes → Return email already registered error
            │
            └── No
                  ↓
5. Hash the password
            ↓
6. Create the user account
            ↓
7. Assign the default CUSTOMER role
            ↓
8. Save the user and role assignment
            ↓
9. Return successful registration response
```

### 2.2 Registration Business Rules

The following business rules apply to public user registration:

1. A user must provide all required registration information.

2. The email address must be unique.

3. A user cannot create multiple accounts using the same email address.

4. The password must meet the application's defined security requirements.

5. The plain-text password must never be stored in the database.

6. The password must be securely hashed before the user account is saved.

7. Every user created through the public registration process must be assigned the `CUSTOMER` role by default.

8. Users cannot select, assign, or modify their own roles during registration.

9. Roles such as `TRAVEL_PROVIDER` and `ADMIN` can only be assigned through an authorized administrative process.

10. User creation and default role assignment must be completed within a single database transaction.

11. If the registration process fails, partial user or role assignment data must not remain in the database.

### 2.3 Registration Failure Scenarios

The registration process can fail under several conditions.

| Scenario                                     | System Behavior                                                                   |
| -------------------------------------------- | --------------------------------------------------------------------------------- |
| Required information is missing              | Return a validation error.                                                        |
| Email format is invalid                      | Return a validation error.                                                        |
| Email already exists                         | Reject the registration request and return an appropriate error.                  |
| Password does not meet security requirements | Return a validation error.                                                        |
| User creation fails                          | Roll back the transaction and return an error response.                           |
| Default role assignment fails                | Roll back the transaction to prevent creating a user without the `CUSTOMER` role. |
| Database operation fails                     | Roll back the transaction and return an appropriate error response.               |

The registration workflow must not leave partially created data in the database.

For example:

```text
User Created
      ↓
CUSTOMER Role Assignment Failed
      ↓
Transaction Rolled Back
      ↓
No Partial User Data Remains
```

---

## 3. User Login Workflow

The user login workflow is responsible for authenticating a registered user and generating the required authentication tokens.

The initial authentication mechanism uses:

```text
Email + Password
```

The high-level workflow is :

```
User
  │
  │ Login Request
  ▼
Identity Service
  │
  ├── Validate Request
  │
  ├── Find User by Email
  │
  ├── Verify Account Status
  │
  ├── Verify Password
  │
  ├── Load User Roles
  │
  ├── Generate Authentication Tokens
  │
  └── Return Login Response
```

### 3.1 Login Flow Steps

The login process follows these steps:

```text
1. User submits email and password
            ↓
2. Identity Service receives the login request
            ↓
3. Validate request data
            ↓
4. Find user by email
            │
            ├── User not found
            │        ↓
            │   Return authentication error
            │
            └── User found
                     ↓
5. Verify account status
            │
            ├── Account is not ACTIVE
            │        ↓
            │   Reject login request
            │
            └── Account is ACTIVE
                     ↓
6. Verify submitted password against password_hash
            │
            ├── Password does not match
            │        ↓
            │   Return authentication error
            │
            └── Password matches
                     ↓
7. Load user roles
            ↓
8. Generate authentication tokens
            ↓
9. Return successful login response
```

### 3.2 Login Business Rules

The following business rules apply to user authentication:

1. A user must provide a valid email address and password.

2. The user account must exist in the system.

3. The user account must have an `ACTIVE` account status before authentication is allowed.

4. The submitted password must be verified against the stored `password_hash`.

5. The system must never expose or return the user's password or `password_hash`.

6. The system should return a generic authentication error for invalid credentials and should not reveal whether the email address or password was incorrect.

7. User roles must be loaded after successful authentication.

8. Authentication tokens must only be generated after the user credentials and account status have been successfully verified.

9. Suspended or inactive users must not receive authentication tokens.

10. A successful authentication response should contain only the information required by the client to access protected resources.

11. Failed authentication attempts must not modify the user's authentication credentials or account status.

### 3.3 Login Failure Scenarios

The login process can fail under several conditions.

| Scenario                              | System Behavior                                                      |
| ------------------------------------- | -------------------------------------------------------------------- |
| Required login information is missing | Return a validation error.                                           |
| Email format is invalid               | Return a validation error.                                           |
| User does not exist                   | Return a generic authentication error.                               |
| Password is incorrect                 | Return a generic authentication error.                               |
| Account status is `INACTIVE`          | Reject the login request.                                            |
| Account status is `SUSPENDED`         | Reject the login request.                                            |
| User roles cannot be loaded           | Authentication must fail and no tokens should be generated.          |
| Token generation fails                | Return an authentication error and do not return partial token data. |
| Database operation fails              | Return an appropriate server error.                                  |

For invalid credentials, the system should return a generic response such as:

```text
Invalid email or password
```

---

## 4. JWT Authentication Workflow

After a user successfully logs in, the Identity Service issues authentication tokens.

These tokens are used to authenticate subsequent requests to protected resources without requiring the user to provide their email and password again.

The initial authentication mechanism will use:

````text
JWT Access Token

### 4.1 JWT Authentication Flow Steps

After a successful login, the client uses the JWT access token to access protected resources.

The authentication flow follows these steps:

```text
1. User successfully logs in
            ↓
2. Identity Service generates a JWT access token
            ↓
3. JWT access token is returned to the client
            ↓
4. Client sends a request to a protected API
            ↓
5. Client includes the access token

Authorization: Bearer <JWT>
            ↓
6. API extracts the JWT from the request
            ↓
7. Validate the JWT
            │
            ├── Invalid or expired
            │        ↓
            │   Reject request with authentication error
            │
            └── Valid
                     ↓
8. Extract user identity and roles
            ↓
9. Create authenticated security context
            ↓
10. Continue to authorization check
            ↓
11. Allow or deny access to the requested resource
````

### 4.2 JWT Token Claims and Validation Rules

The JWT access token contains the information required to identify the authenticated user and support authorization.

The initial JWT payload should contain:

| Claim   | Purpose                                       |
| ------- | --------------------------------------------- |
| `sub`   | Identifies the authenticated user.            |
| `roles` | Contains the roles assigned to the user.      |
| `iat`   | Indicates when the token was issued.          |
| `exp`   | Indicates when the token expires.             |
| `iss`   | Identifies the service that issued the token. |

Example token payload:

```json
{
  "sub": "123",
  "roles": ["CUSTOMER"],
  "iat": 1780000000,
  "exp": 1780000900,
  "iss": "identity-service"
}
```

### 4.3 JWT Failure Scenarios

Authentication using a JWT access token can fail under several conditions.

| Scenario                    | System Behavior                        |
| --------------------------- | -------------------------------------- |
| JWT is missing              | Reject the request as unauthenticated. |
| JWT format is invalid       | Reject the request.                    |
| JWT signature is invalid    | Reject the request.                    |
| JWT has expired             | Reject the request.                    |
| JWT issuer is invalid       | Reject the request.                    |
| Required claims are missing | Reject the request.                    |
| JWT cannot be parsed        | Reject the request.                    |

The system must not treat a request as authenticated if JWT validation fails.

The authentication flow can be represented as:

```text
Request with JWT
       ↓
Extract JWT
       ↓
Validate JWT
       │
       ├── Invalid / Expired / Missing
       │             ↓
       │        Reject Request
       │
       └── Valid
              ↓
       Create Security Context
              ↓
       Continue to Authorization
```

---

## 5. Authorization Workflow

Authentication and authorization are separate responsibilities.

Authentication answers:

```text
Who is the user?
```

### 5.1 Authorization Flow Steps

The authorization process follows these steps:

```text
1. Client sends a request to a protected API
            ↓
2. Client includes the JWT access token

Authorization: Bearer <JWT>
            ↓
3. API validates the JWT
            │
            ├── Invalid or expired
            │        ↓
            │   Reject request with authentication error
            │
            └── Valid
                     ↓
4. Extract authenticated user identity and roles
            ↓
5. Identify the access requirements for the requested resource or operation
            ↓
6. Check whether the user has the required role
            │
            ├── Required role is present
            │        ↓
            │   Allow request to continue
            │
            └── Required role is not present
                     ↓
                Reject request with authorization error
```

### 5.2 Authorization Business Rules

The following business rules apply to authorization:

1. Authorization must only be evaluated after successful authentication.

2. An authenticated user can access only the resources and operations permitted by their assigned role or roles.

3. A user may have multiple roles.

4. Access must be denied if the authenticated user does not have the required role for the requested operation.

5. Users must not be allowed to assign or modify their own roles.

6. The `ADMIN` role is responsible for managing user roles through authorized administrative operations.

7. Public registration always assigns the `CUSTOMER` role by default.

8. Roles such as `TRAVEL_PROVIDER` and `ADMIN` must not be assigned through the public registration process.

9. Changes to a user's roles must be performed only by an authorized administrator.

10. Role-based access control must be enforced at the API level for protected operations.

11. Authentication does not automatically imply authorization. A valid JWT only proves the user's identity; access must still be checked against the required role or permission.

### 5.3 Authorization Failure Scenarios

Authorization can fail when an authenticated user does not have the required access to perform the requested operation.

| Scenario                                                                 | System Behavior                                   |
| ------------------------------------------------------------------------ | ------------------------------------------------- |
| JWT is missing or invalid                                                | Reject the request as unauthenticated.            |
| JWT has expired                                                          | Reject the request as unauthenticated.            |
| User does not have the required role                                     | Reject the request with an authorization error.   |
| User attempts to access an ADMIN-only operation                          | Deny access unless the user has the `ADMIN` role. |
| User attempts to modify their own role                                   | Deny the request.                                 |
| User attempts to perform an operation outside their assigned permissions | Deny access.                                      |
| User roles cannot be resolved during authorization                       | Do not allow access to the protected resource.    |

The authorization flow can be represented as:

```text
Authenticated Request
        ↓
Identify Required Role
        ↓
Check User Roles
        │
        ├── Required Role Present
        │          ↓
        │      Allow Access
        │
        └── Required Role Missing
                   ↓
               Deny Access
```

---

## 6. Role Management Workflow

The Role Management Workflow is responsible for managing user role assignments within the system.

Every user created through the public registration process is assigned the `CUSTOMER` role by default.

Additional roles or role changes, such as assigning the `TRAVEL_PROVIDER` or `ADMIN` role, can only be performed through an authorized administrative process.

The high-level role management flow is:

```text
ADMIN
  │
  │ Role Management Request
  ▼
Identity Service
  │
  ├── Authenticate Admin
  │
  ├── Verify ADMIN Role
  │
  ├── Validate Target User
  │
  ├── Validate Requested Role
  │
  ├── Assign / Remove Role
  │
  └── Return Response
```

### 6.1 Role Management Flow Steps

The role management process follows these steps:

```text
1. Admin sends a role management request
            ↓
2. Identity Service receives the request
            ↓
3. Authenticate the requesting user
            ↓
4. Verify that the requesting user has the ADMIN role
            │
            ├── ADMIN role not present
            │        ↓
            │   Deny the request
            │
            └── ADMIN role present
                     ↓
5. Validate the target user
            │
            ├── User does not exist
            │        ↓
            │   Return an appropriate error
            │
            └── User exists
                     ↓
6. Validate the requested role
            │
            ├── Role does not exist
            │        ↓
            │   Return an appropriate error
            │
            └── Role exists
                     ↓
7. Check the requested role operation
            │
            ├── Assign role
            │        ↓
            │   Create user-role assignment
            │
            └── Remove role
                     ↓
                Remove user-role assignment
            ↓
8. Save the changes
            ↓
9. Return a successful response
```

### 6.2 Role Management Business Rules

The following business rules apply to role management:

1. Only an authenticated and authorized user with the `ADMIN` role can manage user roles.

2. Users must not be allowed to assign, remove, or modify their own roles.

3. The target user must exist before any role assignment or removal operation is performed.

4. The requested role must exist in the `roles` table.

5. A user must not be assigned the same role more than once.

6. Public registration must always assign the `CUSTOMER` role by default.

7. Roles such as `TRAVEL_PROVIDER` and `ADMIN` must not be assigned through the public registration process.

8. Assigning an additional role does not automatically remove the user's existing roles.

9. A role must only be removed through an explicit authorized administrative operation.

10. The system should prevent an operation that would leave a user without any role, unless such behavior is explicitly supported by a future business requirement.

11. Role assignment and removal operations must maintain database consistency.

12. All role management operations must be protected by role-based authorization.

### 6.3 Role Management Failure Scenarios

Role management operations can fail under several conditions.

| Scenario                                                      | System Behavior                                          |
| ------------------------------------------------------------- | -------------------------------------------------------- |
| Requesting user is not authenticated                          | Reject the request as unauthenticated.                   |
| Requesting user does not have the `ADMIN` role                | Deny the request.                                        |
| Target user does not exist                                    | Return an appropriate error.                             |
| Requested role does not exist                                 | Return an appropriate error.                             |
| Role is already assigned to the user                          | Reject the duplicate role assignment.                    |
| Role removal is requested for a role not assigned to the user | Return an appropriate error.                             |
| Role removal would leave the user without any role            | Reject the operation.                                    |
| Database operation fails                                      | Roll back the operation and return an appropriate error. |

The role management operation must not leave the database in an inconsistent state.

For example:

```text
Role Assignment Requested
        ↓
Database Update Fails
        ↓
Transaction Rolled Back
        ↓
No Partial Role Assignment
```
