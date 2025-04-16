# 🌌 Star Wars API Client

This is a Java + Spring Boot application that consumes data from an external Star Wars API. It allows users to search for information about films, people, starships, and vehicles. The system is secured using JWT authentication.

## 🚀 Technologies Used

- Java 17
- Spring Boot 3.4.4
- Spring Security (JWT)
- RestTemplate
- JUnit & Mockito (for unit testing)
- MockMvc (for integration testing)
- Render (for free deployment)
- Insomnia (for manual API testing)

## 📚 Main Endpoints
### 🎬 Films
>GET /films?page={pageNumber}&size={pageSize}: Returns all films (paginated)

>GET /films/{id}: Returns a film by ID

>GET /films/search?title={title}: Searches films by title

### 👤 People
>GET /people?page={pageNumber}&size={pageSize}: Returns all characters (paginated)

>GET /people/{id}: Returns a character by ID

>GET /people/search?name={name}: Searches characters by name

### 🛸 Starships
>GET /starships?page={pageNumber}&size={pageSize}: Returns all starships (paginated)

>GET /starships/{id}: Returns a starship by ID

>GET /starships/search?name={name}: Searches starships by name

### 🚗 Vehicles
>GET /vehicles?page={pageNumber}&size={pageSize}: Returns all vehicles (paginated)

>GET /vehicles/{id}: Returns a vehicle by ID

>GET /vehicles/search?name={name}: Searches vehicles by name

## 🧪 Testing the API

You can use the included Insomnia collection to test the endpoints. Make sure to follow these steps:

#### 1. Get the Authentication Token: Send a POST request to /auth/login with the following body to get a JWT token:

   ```json 
   {
    "username": "user",
    "password": "user123"
   }
```

You will receive a token that should be included in the Authorization header of the other requests, like so:

Authorization: Bearer <your_token_here>

#### 2. Import the Insomnia Collection

Download the file insomnia/Insomnia_2025-04-16.yaml.

Import the collection into Insomnia by choosing Import from the top bar, then selecting the downloaded file.

#### 3. Test the Endpoints

Make sure to replace the token in the headers of the requests with a valid one obtained from the login step above.

## 🌍 Deployment on Render
The app is deployed at:
🔗 https://starwars-api-806q.onrender.com

## ⚙️ Configuration for Local Running
To run the app locally, ensure your application.properties is configured with the following:

```properties
server.port=8080
```

## External Star Wars API

   ```properties 
api.baseUrl=https://www.swapi.tech/api

api.scheme=https

api.host=swapi.tech

api.path=/api
```

##  👤 Author
Developed by Franco Acosta

💼 Java Developer
