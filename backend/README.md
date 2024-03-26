# Getting Started
# Run MySql with docker
1. Create `docker-compose.yml`
```JavaScript
version: '3.8'
services:
  mongodb:
    image: mongo:latest
    environment:
      MONGO_INITDB_ROOT_USERNAME: rootuser
      MONGO_INITDB_ROOT_PASSWORD: rootpass
    ports:
      - "27018:27017"
    volumes:
      - mongodb-data:/data/db
    networks:
      - my_network

volumes:
  mongodb-data:

networks:
  my_network:
    driver: bridge
```
2. Run: `docker-compose up -d`
# Run in Debug mode
1. Change the application.properties: `spring.data.mongodb.uri=mongodb://myuser:mypassword@localhost:27018/mydb`
2. Run: mvn spring-boot:run
# Run with docker
1. Change the application.properties: `spring.data.mongodb.uri=mongodb://myuser:mypassword@mongodb-mongodb-1:27017/mydb`
2. Build the docker-image: `mvn spring-boot:build-image -DskipTests`
3. Run: `docker run --name my-backend-app --network mongodb_my_network -t -p 8000:8080 backend:0.0.1-SNAPSHOT`

