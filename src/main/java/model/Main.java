package model;

import com.example.spring_3_1_4.Spring314Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


public class Main {
    static final String URL_USERS = "https://91.241.64.178:7081/api/users";
    static final String URL_USERS_DELETE = "https://91.241.64.178:7081/api/users/3";


    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(Spring314Application.class);
        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        User user = new User(3L, "James", "Brown", (byte) 26);
        User userUpdate = new User(3L, "Thomas", "Shelby", (byte) 20);


        HttpHeaders headers = new HttpHeaders();

        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<String>(headers);

        ResponseEntity<String> resultGet
                = restTemplate.exchange(URL_USERS, HttpMethod.GET, entity, String.class, User.class);


        headers.set(HttpHeaders.COOKIE, resultGet.getHeaders().getFirst(HttpHeaders.SET_COOKIE));


        HttpEntity<User> requestBody = new HttpEntity<>(user, headers);


        ResponseEntity<String> resultSave
                = restTemplate.exchange(URL_USERS, HttpMethod.POST, requestBody, String.class, User.class);

        requestBody = new HttpEntity<>(userUpdate, headers);

        ResponseEntity<String> resultUpdate
                = restTemplate.exchange(URL_USERS, HttpMethod.PUT, requestBody, String.class, User.class);

        requestBody = new HttpEntity<>(headers);

        ResponseEntity<String> resultDelete
                = restTemplate.exchange(URL_USERS_DELETE, HttpMethod.DELETE, requestBody, String.class, User.class);


        System.out.print(resultSave.getBody());
        System.out.print(resultUpdate.getBody());
        System.out.print(resultDelete.getBody());


    }

}