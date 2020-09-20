package com.wed18305.assignment1.Responses;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Use for returning all POST GET requests Example, body can be whatever we want
 * to return, this example the body is a user entity { "success": true,
 * "message": "user found!", "error": "", "body": { "id": 6, "name": "neil",
 * "username": "neil", "password": "1234", "contactNumber": "0425000000",
 * "type": null } }
 */
public class Response {
    private Boolean success;
    private String message;
    private Object errors;
    private Object body;

    // Getters
    public Boolean getSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Object getErrors() {
        return errors;
    }

    public Object getBody() {
        return body;
    }

    /**
     * Constructor
     */
    public Response(Boolean success, String message, Object errors, Object body) {
        this.success = success;
        this.message = message;
        this.errors = errors;
        this.body = body;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        JSONObject json = new JSONObject();
        try {
            json.put("success", this.success);
            json.put("message", this.message);
            json.put("errors", this.errors);
            json.put("body", this.body);    
        } catch (JSONException e) {
            e.printStackTrace();
        }

		String contentSTRING = json.toString();
        return contentSTRING;
    }

}