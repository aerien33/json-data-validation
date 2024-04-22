package com.domain.service.impl;

import com.domain.service.JsonService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonServiceImpl implements JsonService {

    public String getResult(String fileName) {
        JSONObject rolePolicy = getRolePolicy(fileName);
        if (rolePolicy == null || rolePolicy.isEmpty()) {
            return "File is not a valid JSON object or could not be found in src/main/resources (try typing 'fileName.json')";
        }

        String isValid = validateRolePolicy(rolePolicy);
        if (!(isValid.equals("ok"))) {
            return isValid;
        }

        String rsc = getResource(rolePolicy);
        return String.valueOf(validateResource(rsc));
    }


    private JSONObject getRolePolicy(String fileName) {
        try (FileReader reader = new FileReader("src/main/resources/" + fileName)) {
            return (JSONObject) new JSONParser().parse(reader);

        } catch (Exception e) {
            return null;
        }
    }


    private String getResource(JSONObject rolePolicy) {
        JSONObject policyDocument = (JSONObject) rolePolicy.get("PolicyDocument");
        JSONArray statement = (JSONArray) policyDocument.get("Statement");
        JSONObject statementObj = (JSONObject) statement.get(0);
        return (String) statementObj.get("Resource");
    }


    private boolean validateResource(String rsc) {
        return !(rsc.equals("*"));
    }


    private String validateRolePolicy(JSONObject rolePolicy) {
        if (rolePolicy.get("PolicyName") == null || rolePolicy.get("PolicyName").equals("")) {
            return "Invalid format - missing 'PolicyName' field";
        } else if (rolePolicy.get("PolicyDocument") == null || rolePolicy.get("PolicyDocument").equals("")) {
            return "Invalid format - missing 'PolicyDocument' field";
        } else {

            String policyName = (String) rolePolicy.get("PolicyName");
            Pattern pattern = Pattern.compile("[\\w+=,.@-]");
            Matcher m = pattern.matcher(policyName);
            if (!(m.find()) || policyName.isEmpty() || policyName.length() > 128) {
                return "'PolicyName' field contains invalid policy name";
            }

            try {
                JSONObject policyDocument = (JSONObject) rolePolicy.get("PolicyDocument");

                if (policyDocument == null || policyDocument.isEmpty()) {
                    return "'PolicyDocument' field value is not a valid JSON object";
                } else if (policyDocument.get("Version") == null || policyDocument.get("Version").equals("")) {
                    return "Invalid format - missing 'Version' field in 'PolicyDocument'";
                } else if (!(policyDocument.get("Version").equals("2012-10-17")) && !(policyDocument.get("Version").equals("2008-10-17"))) {
                    return "'Version' field contains invalid IAM policy version";
                } else if (policyDocument.get("Statement") == null || policyDocument.get("Statement").equals("")) {
                    return "Invalid format - missing 'Statement' field in 'PolicyDocument'";
                } else {
                    return validateStatement(policyDocument);
                }

            } catch (Exception e) {
                return e.getMessage();
            }
        }
    }


    private String validateStatement(JSONObject policyDocument) {
        try {
            JSONArray statement = (JSONArray) policyDocument.get("Statement");
            if (statement == null || statement.isEmpty()) {
                return "'Statement' field contains invalid JSON array";
            } else {

                try {
                    JSONObject statementObj = (JSONObject) statement.get(0);

                    if (statementObj == null || statementObj.isEmpty()) {
                        return "'Statement' field value is not a valid JSON object";
                    } else if (statementObj.get("Effect") == null || statementObj.get("Effect").equals("")) {
                        return "Invalid format - missing 'Effect' field in 'Statement'";
                    } else if (!(statementObj.get("Effect").equals("Allow")) && !(statementObj.get("Effect").equals("Deny"))) {
                        return "'Effect' field should contain 'Allow' or 'Deny'";
                    } else if (statementObj.get("Resource") == null || statementObj.get("Effect").equals("")) {
                        return "Invalid format - missing 'Resource' field in 'Statement'";
                    } else if (statementObj.get("Action") == null || statementObj.get("Effect").equals("")) {
                        return "Invalid format - missing 'Action' field in 'Statement'";
                    } else {

                        try {
                            JSONArray actions = (JSONArray) statementObj.get("Action");

                            if (actions == null || actions.isEmpty()) {
                                return "'Action' field value is not a valid JSON array";
                            } else {

                                for (Object obj : actions) {
                                    String action = String.valueOf(obj);
                                    Pattern pattern = Pattern.compile("iam:+");
                                    Matcher m = pattern.matcher(action);
                                    if (!(m.find())) {
                                        return "One of the values from 'Action' field is invalid IAM action";
                                    }
                                }

                                return "ok";
                            }

                        } catch (Exception e) {
                            return e.getMessage();
                        }
                    }

                } catch (Exception e) {
                    return e.getMessage();
                }
            }

        } catch (Exception e) {
            return e.getMessage();
        }
    }

}
