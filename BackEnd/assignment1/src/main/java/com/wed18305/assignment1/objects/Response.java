package com.wed18305.assignment1.objects;

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

    /**
     * @return the success
     */
    public Boolean getSuccess() {
        return success;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @return the error
     */
    public Object getErrors() {
        return errors;
    }
    /**
     * @return the body
     */
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