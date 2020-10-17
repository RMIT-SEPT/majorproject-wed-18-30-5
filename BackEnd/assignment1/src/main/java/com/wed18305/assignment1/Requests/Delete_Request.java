package com.wed18305.assignment1.Requests;

// import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

public class Delete_Request{

    @NotEmpty(message = "name or id is required")
    protected String[] input;


    public Delete_Request(String[] input, String empty) {
        this.input = input;
    }

    //Getters
    public String[] getObjects() {
        return this.input;
    }
    /**
     * This will check if the passed objects are strings
     * if so it will return a string array
     * May return null if not Long so ALWAYS check
     * @return String[] or null
     */
    public String[] getInput() {
        return this.input;
    }
    /**
     * This will check if the passed objects are Long
     * if so it will return a Long array
     * May return null if not Long so ALWAYS check
     * @return Long[] or null
     */
    public Long[] getLong() {
        Long[] newarr = new Long[this.input.length];
        for (int i = 0; i < this.input.length; i++) {
            try {
                newarr[i] = Long.parseLong(this.input[i]);
            } catch (Exception e) {
                //catch silent, pass -1 as the value since we dont have negative ids
                newarr[i] = (long)-1;
            }
        }
        return newarr;
    }
}