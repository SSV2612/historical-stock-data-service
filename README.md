
# Historical Stock Data Service

This service provides historical stock data using the Alpha Vantage API, with options for daily, weekly, and monthly intervals. 
It leverages **Spring Boot**, **MongoDB**, **Redis**, and is containerized with **Docker** for deployment.

---

## Features
- Fetches and stores historical stock data in MongoDB.
- Caching of fetched data using Redis for faster responses.
- Flexible querying for daily, weekly, or monthly intervals.
- Containerized deployment using Docker and Docker Compose.

---

## Prerequisites
1. [Docker](https://www.docker.com/get-started) - Required to run containers.
2. [Docker Compose](https://docs.docker.com/compose/install/) - For orchestrating multiple containers.
3. **MongoDB and Redis Containers**:
   - Ensure MongoDB and Redis containers are running in the same Docker network as this service.

---

## How to Run the Application

### 1. Clone the Repository
Download the repository:
```bash
git clone https://github.com/SSV2612/historical-stock-data-service.git
cd historical-stock-data-service
```

### 2. Start the application using Docker compose
```bash
docker-compose up
```
The above command:
- Pulls the pre-built docker image from docker hub.
- Starts MongoDB and redis containers
- Starts the Historic stock service container

### 3. Test the Application
Use Postman or your browser to test the following endpoints:

#### Fetch Historical Stock Data:
- **URL**: `http://localhost:8081/api/historicStocks/price/{ticker}?interval={interval}`
- **Method**: GET
- Replace `{ticker}` with the stock ticker (e.g., AAPL, MSFT).
- Replace `{interval}` with the desired interval (`DAILY`, `WEEKLY`, or `MONTHLY`).

Example:
```bash
http://localhost:8081/api/historicStocks/price/MSFT?interval=WEEKLY
```
### 4. To stop the application
```bash
docker-compose down
```
This stops all the running containers
---

## Logging and Debugging

### Enable Debug Logs
Debug logs for caching and data processing are enabled by default. Logs will show the following details:
- Keys being added to Redis.
- Data being fetched from Redis or MongoDB.
- API responses from Alpha Vantage.

---

### To View Logs:
Use the following command to check logs for the running container:
```bash
docker logs historical-stock-data-service
```
---

### Clear Redis Cache
To clear all cached keys in Redis:
```bash
docker exec -it redis redis-cli
FLUSHALL
```

---

## Contributing
Feel free to open issues or submit pull requests for bug fixes or enhancements.

---
