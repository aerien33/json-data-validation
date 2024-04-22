package com.domain.service.impl;

import junit.framework.TestCase;
import org.junit.jupiter.api.Test;

public class JsonServiceImplTest extends TestCase {

    private final JsonServiceImpl service;

    public JsonServiceImplTest() {
        this.service = new JsonServiceImpl();
    }

    @Test
    public void returnsFalseWhenResourceIsStar() throws Throwable {
        String fileName = "valid-with-star.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method returns true when 'Resource' field equals '*'", result.equals("false"));
    }

    @Test
    public void returnsTrueWhenResourceIsNotStar() throws Throwable {
        String fileName = "valid-no-star.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method returns false when 'Resource' field does not equal '*'", result.equals("true"));
    }

    @Test
    public void returnsErrorWhenFileIsNotValidJSON() throws Throwable {
        String fileName = "invalid-json-file.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when file is not a valid JSON", result.equals("File is not a valid JSON object or could not be found in src/main/resources (try typing 'fileName.json')"));
    }

    @Test
    public void returnsErrorWhenIsNoPolicyName() throws Throwable {
        String fileName = "no-policy-name.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when there is no PolicyName", result.equals("Invalid format - missing 'PolicyName' field"));
    }

    @Test
    public void returnsErrorWhenPolicyNameIsInvalid() throws Throwable {
        String fileName = "invalid-policy-name.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when PolicyName is invalid", result.equals("'PolicyName' field contains invalid policy name"));
    }

    @Test
    public void returnsErrorWhenIsNoPolicyDocument() throws Throwable {
        String fileName = "no-policy-document.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when there is no PolicyDocument", result.equals("Invalid format - missing 'PolicyDocument' field"));
    }

    @Test
    public void returnsErrorWhenPolicyDocumentIsString() throws Throwable {
        String fileName = "policy-document-string.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when PolicyDocument is a string", result.equals("class java.lang.String cannot be cast to class org.json.simple.JSONObject (java.lang.String is in module java.base of loader 'bootstrap'; org.json.simple.JSONObject is in unnamed module of loader 'app')"));
    }



    @Test
    public void returnsErrorWhenActionIsInvalid() throws Throwable {
        String fileName = "invalid-action.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when one of the actions is invalid", result.equals("One of the values from 'Action' field is invalid IAM action"));
    }

    @Test
    public void returnsErrorWhenVersionIsEmpty() throws Throwable {
        String fileName = "empty-version.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when Version is empty", result.equals("Invalid format - missing 'Version' field in 'PolicyDocument'"));
    }

    @Test
    public void returnsErrorWhenNoResource() throws Throwable {
        String fileName = "no-resource.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when there is no Resource", result.equals("Invalid format - missing 'Resource' field in 'Statement'"));
    }

    @Test
    public void returnsErrorWhenNoStatement() throws Throwable {
        String fileName = "no-statement.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when there is no Statement", result.equals("Invalid format - missing 'Statement' field in 'PolicyDocument'"));
    }

    @Test
    public void returnsErrorWhenNoVersion() throws Throwable {
        String fileName = "no-version.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when there is no Version", result.equals("Invalid format - missing 'Version' field in 'PolicyDocument'"));
    }

    @Test
    public void returnsErrorWhenNoEffect() throws Throwable {
        String fileName = "no-effect.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when there is no Effect", result.equals("Invalid format - missing 'Effect' field in 'Statement'"));
    }

    @Test
    public void returnsErrorWhenEffectIsInvalid() throws Throwable {
        String fileName = "invalid-effect.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when Effect is invalid", result.equals("'Effect' field should contain 'Allow' or 'Deny'"));
    }

    @Test
    public void returnsErrorWhenFileNotFound() throws Throwable {
        String fileName = "foo.json";
        String result = this.service.getResult(fileName);
        assertTrue("Method should return error when the file is not found", result.equals("File is not a valid JSON object or could not be found in src/main/resources (try typing 'fileName.json')"));
    }

}
