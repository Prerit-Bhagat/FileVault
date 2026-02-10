# FileVault / Secure File Management Platform

A **production-style, microservices-based file management system** built using **Micronaut**.  
The platform uses an **API Gateway** for centralized security and routing, supports **JWT authentication with expiration**, and enforces **role-based authorization** for sensitive operations.

---

## 🚀 Overview

The Secure File Management Platform allows users to upload, download, and manage files securely.  
It follows **modern backend architecture best practices**, separating concerns across multiple services and centralizing security at the gateway level.

---

## 🏗️ Architecture
                  ┌────────────────────┐
                  │      Frontend       │
                  │ (Future / Optional) │
                  └─────────┬──────────┘
                            │
                            ▼
                  ┌────────────────────┐
                  │    API Gateway      │
                  │ - JWT Authentication│
                  │ - Token Expiration  │
                  │ - Role Enforcement  │
                  │ - Request Routing   │
                  └─────────┬──────────┘
                            │
        ┌───────────────────┴───────────────────┐
        ▼                                       ▼
┌────────────────────┐               ┌────────────────────┐
│    File Service     │               │  Metadata Service  │
│ - Upload Files      │               │ - Store Metadata   │
│ - Download Files    │               │ - Fetch Metadata   │
│ - Delete Files      │               │ - Delete Metadata  │
│ - Disk Storage      │               │ - H2 Database      │
└────────────────────┘               └────────────────────┘


---
### Services
- **API Gateway**
  - Single entry point
  - JWT authentication & authorization
  - Token expiration
  - Routes requests to backend services

- **File Service**
  - Upload files
  - Download files
  - Delete files (ADMIN only)
  - Stores files on local disk

- **Metadata Service**
  - Stores file metadata (name, size, type, upload time)
  - Fetch metadata by file ID
  - Delete metadata (ADMIN only)
  - Uses H2 database

---

## 🔐 Security Features

- JWT Authentication (HS256)
- Token expiration (`exp`)
- Role-based authorization
  - Authenticated users → upload & download
- Gateway-level authentication
- Endpoint-level authorization using `@Secured`

---

## 🧩 Features

### Implemented
- File upload
- File download
- Metadata storage and retrieval
- API Gateway routing
- JWT login
- Token expiration
- End-to-end delete flow (file + metadata)

### Planned / Optional
- Frontend (React + Vite)
- Refresh tokens
- Role Based access
- Docker Compose setup

---

## 🛠️ Tech Stack

- **Java**
- **Micronaut**
- **JWT (HS256)**
- **H2 Database**
- **REST APIs**
- **Microservices Architecture**



