package com.project.auth.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.auth.models.Customer;
import com.project.auth.models.Token;
import com.project.auth.utilities.CustomerSerializer;
import com.project.auth.utilities.JwtHelper;

import java.net.InetAddress;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/")
public class AccountController {

    // Responds to requests for JWT Tokens
    // Takes username and password as parameters
    // Checks the customer exists in the customer store (call to
    // localhost:8080/api/customers)
    // Check password matches the one listed for the customer in the customer store
    // Returns a JWT token suitable for connecting to the data service

    @PostMapping("/token")
    public ResponseEntity<?> getToken(@RequestBody Customer customer) {

        String name = customer.getName();
        String password = customer.getPassword();
        Boolean isValid = false;

        // Check customer exists
        Customer storedCustomer = checkCustomer(name);

        // Check password is valid
        if (storedCustomer != null)
            isValid = checkPassword(name, password);

        // Create Token if valid
        if (isValid == true) {
            Token token = createToken();

            return ResponseEntity.ok(token);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    // Takes name, email and password and add them to the customer data store

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Customer customer) {

        if (customer.getId() != 0 || customer.getName() == null || customer.getEmail() == null) {
            return ResponseEntity.badRequest().build();
        }

        String customerJson = CustomerSerializer.getCustomerAsJSONString(customer);

        createCustomer(customerJson);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(customer.getId()).toUri();
        ResponseEntity<?> response = ResponseEntity.created(location).build();
        return response;

    }

    // Function to create token
    private Token createToken() {
        String scope = "com.project.api";

        String token = JwtHelper.createToken(scope);

        return new Token(token);
    }

    // Function to check customer in customer store
    private Customer checkCustomer(String name) {
        InetAddress ip;
        String hostname = "";
        try
        {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();
            System.out.println("Your current IP address : " + ip);
            System.out.println("Your current Hostname : " + hostname);
        }catch(
        UnknownHostException e)
        {
            e.printStackTrace();
        }

        try {

            URL url = null;

            if (hostname.contains("local")) {
                url = new URI("http://localhost:8080/api/customers/byname/" + name).toURL();
            } else {
                url = new URI("http://api:8080/api/customers/byname/" + name).toURL();
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/json");

            Token token = createToken();
            connection.setRequestProperty("authorization", "Bearer " + token.getToken());

            if (connection.getResponseCode() != 200) {
                return null;
            } else {

                BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
                String output = "";
                String out = "";

                while ((out = br.readLine()) != null) {
                    output += out;
                }

                connection.disconnect();
                return CustomerSerializer.getCustomer(output);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Function to check a user's password
    private boolean checkPassword(String name, String password) {
        Customer customer = checkCustomer(name);

        if (customer != null && customer.getName().equals(name) && customer.getPassword().equals(password))
            return true;

        return false;
    }

    // Function to create and post a customer to the data store
    private void createCustomer(String customer) {

        try {
            InetAddress ip;
            String hostname = "";
            try
            {
                ip = InetAddress.getLocalHost();
                hostname = ip.getHostName();
                System.out.println("Your current IP address : " + ip);
                System.out.println("Your current Hostname : " + hostname);
            }catch(
            UnknownHostException e)
            {
                e.printStackTrace();
            }


            URL url = null;

            if (hostname.contains("local")) {
                url = new URI("http://localhost:8080/api/customers" ).toURL();
            } else {
                url = new URI("http://api:8080/api/customers").toURL();
            }

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            Token token = createToken();
            connection.setRequestProperty("authorization", "Bearer " + token.getToken());

            OutputStream os = connection.getOutputStream();
            os.write(customer.getBytes());
            os.flush();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
                throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
            }

            connection.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}