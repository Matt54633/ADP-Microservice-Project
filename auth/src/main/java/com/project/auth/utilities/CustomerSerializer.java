package com.project.auth.utilities;

import org.json.JSONObject;

import com.project.auth.models.Customer;

public class CustomerSerializer {

    public static Customer getCustomer(String json_string) {

        JSONObject jobj = new org.json.JSONObject(json_string);

        int id = (int) jobj.get("id");
        String name = (String) jobj.get("name");
        String email = (String) jobj.get("email");
        String password = (String) jobj.get("password");

        Customer cust = new Customer();
        cust.setName(name);
        cust.setId(id);
        cust.setEmail(email);
        cust.setPassword(password);
        return cust;
    }

    public static String getCustomerAsJSONString(Customer customer) {
        JSONObject jo = new JSONObject();

        jo.put("name", customer.getName());
        jo.put("email", customer.getEmail());
        jo.put("password", customer.getPassword());
        jo.put("id", customer.getId());

        String out = jo.toString();
        return out;
    }

}
