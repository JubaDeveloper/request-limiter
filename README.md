# Request Limiter

The project consists in a interceptor that will limiter the amount of requests by given time

## Usage

Make 5 continuously requests to /date endpoint in a period of 5 seconds and then will see the 403 request status code

### Running application

```bash 
mvn spring-boot:run
```

### Testing

```bash
mvn test
```

## Concepts involved

- The application of `Chain` structural pattern