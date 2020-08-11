package com.wed18305.assignment1.Responses;

/**Use for returning all POST GET requests
Example, body can be whatever we want to return, this example the body is a user entity
{
    "success": true,
    "message": "user found!",
    "error": "",
    "body": {
        "id": 6,
        "name": "neil",
        "username": "neil",
        "password": "1234",
        "contactNumber": "0425000000",
        "type": null
    }
}*/
public class Response {
    private Boolean success;
    private String message;
    private Object errors; 
    private Object body;

    //Getters
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
    public Response(Boolean success, 
                    String message,
                    Object errors,
                    Object body){
                        this.success = success;
                        this.message = message;
                        this.errors = errors;
                        this.body = body;
    }


}