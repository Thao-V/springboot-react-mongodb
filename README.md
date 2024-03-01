# springboot-react-mongodb
## Backend: Spring Boot
## Frontend: React (TypeScript)
## Database: Mongo Atlas
## Common used annotations in Spring Boot
1. Core Annotations
* @SpringBootApplication: A convenience annotation that combines @Configuration, @EnableAutoConfiguration, and @ComponentScan. It's typically used on the main application class to enable auto-configuration and component scanning.
@Autowired: Used for automatic dependency injection. It allows Spring to resolve and inject collaborating beans into your bean.
* @Component: Indicates that a class is a Spring component. It's a generic stereotype for any Spring-managed component.
* @Service: Indicates that a class is a service component in the service layer.
* @Repository: Indicates that a class is a repository component in the data access layer.
* @Controller: Indicates that a class is a controller component in the presentation layer.
* @RestController: A convenience annotation that combines @Controller and @ResponseBody. It's used for creating RESTful controllers.
* @RequestMapping: Used for mapping web requests to specific handler methods or controller classes.
* @GetMapping, @PostMapping, @PutMapping, @DeleteMapping: Specific variants of @RequestMapping for handling HTTP GET, POST, PUT, and DELETE requests, respectively.
* @PathVariable: Used to extract values from the URI path.
* @RequestParam: Used to extract query parameters from the request.
* @RequestBody: Indicates that a method parameter should be bound to the body of the web request.
* @ResponseBody: Indicates that a method return value should be bound to the web response body.
2. Configuration and Properties
* @Configuration: Indicates that a class is a source of bean definitions.
* @Bean: Used to declare a bean in the application context.
* @Value: Used to inject property values into components.
* @PropertySource: Used to specify a location of properties files.
* @ConfigurationProperties: Used to bind a class to a properties file.
3. Security
* @EnableWebSecurity: Enables Spring Security's web security support.
* @Secured, @PreAuthorize, @PostAuthorize: Used for method-level security.
4. Data Access
* @Entity: Used with JPA to denote a database entity.
* @Document: Used with Spring Data MongoDB to denote a document.
* @Id: Used to specify the primary key of an entity.
* @GeneratedValue: Used to specify the generation strategy for the primary key.
* @Column: Used to specify the column mapping for an entity field.
* @Transient: Indicates that a field is not to be persisted in the database.
5. Testing
* @SpringBootTest: Used for integration testing with Spring Boot.
* @MockBean: Used to add mock objects to the Spring application context.
* @DataJpaTest, @WebMvcTest, @JsonTest: Used for specific testing slices of the application.