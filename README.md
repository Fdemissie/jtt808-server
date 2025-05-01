# JTT808 Protocol Server

[![Java CI](https://github.com/fdemissie/jtt808-server/actions/workflows/build.yml/badge.svg)](https://github.com/fdemissie/jtt808-server/actions/workflows/build.yml)
[![GitHub Release](https://img.shields.io/github/release/fdemissie/jtt808-server.svg)](https://github.com/fdemissie/jtt808-server/releases)

The is just a quick start server implementation for the JTT808/T808 vehicle tracking protocol, built with Netty. It's far from complete and you are welcome to use/improve it. 

## Features

- JTT808 protocol v2011/v2013/v2019 support
- Message types supported:
  - Heartbeat (0x0001)
  - Location reporting (0x0200)
- MySQL/PostgreSQL storage

## Can be extended to include
- Terminal registration (0x0100)
- Terminal authentication (0x0102)
- Web dashboard
- REST API for device management
- Horizontal scaling support ...

## Quick Start

### Prerequisites
- Java 17+
- MySQL 8.0+ 

### Installation

#### 1.  Clone the repository:
   git clone https://github.com/fdemissie/jtt808-server.git
   cd jtt808-server
#### 2. Configure the database:

    mysql -u root -p < sql/schema.sql

##### 3. Build and run:

    mvn clean package
    java -jar target/jtt808-server.jar
