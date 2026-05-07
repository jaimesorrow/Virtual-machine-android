# Google Cloud Console for Android

A feature-rich Android application that brings the power of Google Cloud Console to your mobile device.

## Features

The app provides a comprehensive mobile interface for managing Google Cloud Platform (GCP) resources, including:

### 🖥️ Compute Engine
- View and manage VM instances with status (Running, Stopped, Terminated)
- Start, stop, and delete VMs
- View instance details (zone, machine type, IP addresses, OS image)
- Create new VM instances with configurable options (zone, machine type, OS, disk)

### ⚙️ Kubernetes Engine (GKE)
- List all GKE clusters with version, node count, vCPUs, and memory details

### 🚀 Cloud Run
- List Cloud Run services with URLs, regions, and request metrics

### λ Cloud Functions
- View functions with runtime, trigger type, memory, and status

### 🗄️ Cloud Storage
- Browse buckets with location, storage class, size, and public access status

### 🐘 Cloud SQL
- Manage PostgreSQL and MySQL instances with tier and connection details

### 📊 BigQuery
- Browse datasets with table counts and locations

### 🔒 IAM & Admin
- View all IAM principals (users, service accounts, groups, domains) with roles

### 💳 Billing
- View cost vs. forecast vs. budget with breakdown by service and credits

### 🔌 APIs & Services
- Browse 15+ GCP APIs with enabled/disabled status and category

### 📈 Cloud Monitoring
- Metrics dashboard (CPU, memory, network, disk) with active alerts and uptime

### 📋 Cloud Logging
- Log entries with color-coded severity levels (Error, Warning, Info, Debug)

## Architecture

- **Language**: Kotlin
- **Min SDK**: 26 (Android 8.0 Oreo)
- **Target SDK**: 34 (Android 14)
- **Architecture**: Fragment-based navigation with Material Design 3
- **View Binding**: Enabled for type-safe view access
- **Data**: Mock repository with realistic GCP sample data

## Building

### Prerequisites
- Android Studio Hedgehog (2023.1.1) or newer
- Android SDK 34
- JDK 8 or higher

### Steps
1. Clone the repository
2. Open in Android Studio
3. Sync Gradle dependencies
4. Run on an emulator or physical device (API 26+)

```bash
./gradlew assembleDebug
```

## Navigation

The app uses a navigation drawer with all major GCP services organized by category:
- **Compute**: Compute Engine, Kubernetes Engine, Cloud Run, Cloud Functions
- **Storage & Databases**: Cloud Storage, Cloud SQL, BigQuery
- **Operations**: Cloud Monitoring, Cloud Logging
- **Security & Admin**: IAM & Admin, APIs & Services, Billing
