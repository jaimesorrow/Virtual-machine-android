package com.cloudconsole.app.model

object MockDataRepository {

    val projects = listOf(
        GCPProject("my-project-123", "My First Project", "123456789012"),
        GCPProject("analytics-prod", "Analytics Production", "234567890123"),
        GCPProject("dev-sandbox-456", "Development Sandbox", "345678901234")
    )

    val vmInstances = listOf(
        VMInstance("vm-001", "web-server-1", "us-central1-a", "e2-medium",
            VMStatus.RUNNING, "10.128.0.2", "34.56.78.90", "2024-01-15", 20, "Debian GNU/Linux 11"),
        VMInstance("vm-002", "api-backend", "us-central1-b", "n2-standard-2",
            VMStatus.RUNNING, "10.128.0.3", "34.56.78.91", "2024-01-20", 50, "Ubuntu 22.04 LTS"),
        VMInstance("vm-003", "database-primary", "us-east1-b", "n2-standard-4",
            VMStatus.RUNNING, "10.132.0.2", "", "2024-02-01", 200, "Debian GNU/Linux 11"),
        VMInstance("vm-004", "batch-processor", "europe-west1-b", "e2-standard-4",
            VMStatus.STOPPED, "10.132.0.5", "", "2024-02-10", 30, "CentOS 7"),
        VMInstance("vm-005", "ml-training", "us-west1-a", "n1-highmem-8",
            VMStatus.TERMINATED, "10.138.0.2", "", "2024-02-15", 100, "Deep Learning VM")
    )

    val storageBuckets = listOf(
        StorageBucket("my-app-assets", "US-CENTRAL1", "STANDARD", "2024-01-10",
            "Not public", 1523, 2_500_000_000L),
        StorageBucket("backup-data-2024", "US", "NEARLINE", "2024-01-25",
            "Not public", 342, 150_000_000_000L),
        StorageBucket("static-website", "US-EAST1", "STANDARD", "2024-02-05",
            "Public to internet", 89, 500_000_000L),
        StorageBucket("ml-training-data", "US-CENTRAL1", "COLDLINE", "2024-02-12",
            "Not public", 5678, 2_000_000_000_000L),
        StorageBucket("logs-archive", "ASIA", "ARCHIVE", "2024-03-01",
            "Not public", 234567, 50_000_000_000L)
    )

    val gkeClusters = listOf(
        GKECluster("prod-cluster", "us-central1", "1.28.3-gke.1286000", 3,
            "RUNNING", 6, 24),
        GKECluster("staging-cluster", "us-east1", "1.28.3-gke.1286000", 2,
            "RUNNING", 4, 8),
        GKECluster("analytics-cluster", "europe-west1", "1.27.7-gke.1121000", 5,
            "RUNNING", 20, 80)
    )

    val cloudFunctions = listOf(
        CloudFunction("process-image", "us-central1", "nodejs20", "HTTP",
            "ACTIVE", "2024-03-01 14:23", 256),
        CloudFunction("send-notification", "us-east1", "python311", "Pub/Sub",
            "ACTIVE", "2024-02-28 09:15", 128),
        CloudFunction("data-transformer", "us-central1", "java21", "Cloud Storage",
            "ACTIVE", "2024-02-25 16:45", 512),
        CloudFunction("auth-webhook", "us-central1", "go121", "HTTP",
            "FAILED", "2024-02-20 11:30", 256),
        CloudFunction("cleanup-scheduler", "europe-west1", "python311", "Cloud Scheduler",
            "ACTIVE", "2024-01-15 08:00", 128)
    )

    val bigQueryDatasets = listOf(
        BigQueryDataset("analytics_data", "analytics_data", "US", 12, "2024-03-01"),
        BigQueryDataset("user_events", "user_events", "US-CENTRAL1", 5, "2024-02-28"),
        BigQueryDataset("sales_reports", "sales_reports", "EU", 8, "2024-02-20"),
        BigQueryDataset("ml_features", "ml_features", "US", 3, "2024-02-15")
    )

    val cloudSQLInstances = listOf(
        CloudSQLInstance("prod-mysql", "MYSQL_8_0", "us-central1", "db-n1-standard-2",
            "RUNNABLE", "34.56.78.100", 100),
        CloudSQLInstance("analytics-postgres", "POSTGRES_15", "us-east1", "db-n1-standard-4",
            "RUNNABLE", "34.56.78.101", 500),
        CloudSQLInstance("dev-mysql", "MYSQL_8_0", "us-central1", "db-f1-micro",
            "SUSPENDED", "", 10)
    )

    val iamMembers = listOf(
        IAMMember("admin@mycompany.com", "Owner", MemberType.USER),
        IAMMember("developer@mycompany.com", "Editor", MemberType.USER),
        IAMMember("analyst@mycompany.com", "Viewer", MemberType.USER),
        IAMMember("ci-cd@my-project-123.iam.gserviceaccount.com",
            "roles/container.developer", MemberType.SERVICE_ACCOUNT),
        IAMMember("storage-admin@my-project-123.iam.gserviceaccount.com",
            "roles/storage.admin", MemberType.SERVICE_ACCOUNT),
        IAMMember("devs@mycompany.com", "Editor", MemberType.GROUP),
        IAMMember("mycompany.com", "Viewer", MemberType.DOMAIN)
    )

    val apiServices = listOf(
        APIService("compute.googleapis.com", "Compute Engine API",
            "Creates and runs virtual machines", true, "Compute"),
        APIService("storage.googleapis.com", "Cloud Storage API",
            "Stores and retrieves any amount of data", true, "Storage"),
        APIService("container.googleapis.com", "Kubernetes Engine API",
            "Builds and manages container-based applications", true, "Containers"),
        APIService("cloudfunctions.googleapis.com", "Cloud Functions API",
            "Manages lightweight user-provided functions", true, "Serverless"),
        APIService("bigquery.googleapis.com", "BigQuery API",
            "Enterprise data warehouse", true, "Analytics"),
        APIService("sqladmin.googleapis.com", "Cloud SQL Admin API",
            "Creates and manages Cloud SQL databases", true, "Databases"),
        APIService("iam.googleapis.com", "Identity and Access Management API",
            "Manages identity and access control", true, "Security"),
        APIService("monitoring.googleapis.com", "Cloud Monitoring API",
            "Manages your Cloud Monitoring data", true, "Operations"),
        APIService("logging.googleapis.com", "Cloud Logging API",
            "Writes log entries and manages logs", true, "Operations"),
        APIService("ml.googleapis.com", "AI Platform Training & Prediction API",
            "Train and deploy ML models", false, "AI & ML"),
        APIService("vision.googleapis.com", "Cloud Vision API",
            "Integrates Google Vision features", false, "AI & ML"),
        APIService("translate.googleapis.com", "Cloud Translation API",
            "Language translation features", false, "AI & ML"),
        APIService("pubsub.googleapis.com", "Cloud Pub/Sub API",
            "Real-time messaging service", true, "Messaging"),
        APIService("redis.googleapis.com", "Cloud Memorystore for Redis API",
            "Fully managed Redis service", false, "Databases"),
        APIService("run.googleapis.com", "Cloud Run API",
            "Deploy and manage containerized apps", true, "Serverless")
    )

    val logEntries = listOf(
        LogEntry("2024-03-01 14:23:45", LogSeverity.INFO, "gce_instance/web-server-1",
            "Instance started successfully", "my-project-123"),
        LogEntry("2024-03-01 14:20:12", LogSeverity.WARNING, "cloudfunctions.googleapis.com/auth-webhook",
            "Function execution took 9847ms, approaching timeout limit", "my-project-123"),
        LogEntry("2024-03-01 14:15:33", LogSeverity.ERROR, "cloudfunctions.googleapis.com/auth-webhook",
            "Function execution failed: connection timeout", "my-project-123"),
        LogEntry("2024-03-01 13:45:00", LogSeverity.INFO, "gcs_bucket/my-app-assets",
            "Object uploaded: assets/logo.png (45.2 KB)", "my-project-123"),
        LogEntry("2024-03-01 13:30:22", LogSeverity.INFO, "gke_cluster/prod-cluster",
            "Pod prod-cluster/nginx-deployment-abc started successfully", "my-project-123"),
        LogEntry("2024-03-01 12:00:00", LogSeverity.CRITICAL, "cloudsql/prod-mysql",
            "High CPU utilization: 94% for 5 minutes", "my-project-123"),
        LogEntry("2024-03-01 11:55:18", LogSeverity.DEBUG, "compute.googleapis.com",
            "API request: instances.list completed in 234ms", "my-project-123"),
        LogEntry("2024-03-01 11:30:05", LogSeverity.NOTICE, "iam.googleapis.com",
            "IAM policy updated by admin@mycompany.com", "my-project-123")
    )
}
