# Distributed Real-Time Satellite Telemetry System

## Overview

This project is a distributed, real-time telemetry processing system designed to handle and monitor satellite data streams. It leverages Apache Kafka for high-throughput message brokering and Spring Boot for backend services, all containerized using Docker for seamless deployment and scalability.

## System Architecture

- **Backend:** Built with Spring Boot. It includes Kafka Producers to simulate or ingest satellite telemetry data, and Kafka Consumers to process and route the incoming streams.
- **Frontend:** Client-side application designed to visualize the real-time telemetry data.
- **Infrastructure:** Apache Kafka and Zookeeper, fully managed and orchestrated via Docker Compose.

## Technologies Used

- Java / Spring Boot
- Apache Kafka & Zookeeper
- Docker & Docker Compose
- Web Technologies (Frontend)

## Project Structure

- `/backend` - Contains the Spring Boot application source code, including Kafka configuration, producers, and consumers.
- `/frontend` - Contains the client-side user interface source code.
- `docker-compose.yml` - Configuration file for standing up the Kafka broker, Zookeeper, and related containerized services.

## Getting Started

### Prerequisites

- Docker and Docker Compose installed on your local machine.
- Java Development Kit (JDK 17 or higher) if you plan to run or build the backend locally.

### How to Run

1. Clone the repository:

   ```bash
   git clone [https://github.com/selinisci/distributed-satellite-telemetry.git](https://github.com/selinisci/distributed-satellite-telemetry.git)
   cd distributed-satellite-telemetry
   ```

2. Start the infrastructure and applications using Docker:

   ```bash
   docker-compose up -d
   ```

3. Access the frontend application via your web browser to monitor the real-time data stream.

## Features

- Continuous real-time data ingestion and processing.

- Scalable message-driven architecture.

- Fully containerized environment ensuring consistency across different setups.
