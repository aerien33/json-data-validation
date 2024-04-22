# JSON Data Validator

An application for validating JSON format as AWS:IAM:RolePolicy with a valid Resource field. <br> 
Returns false if an input JSON Resource field contains a single asterisk (`*`) and true in any other case.

### How to run

1. Click the blue "Code" button above the list of files and or download the ZIP file. 
<br> You can also clone the project using HTTPS link, SSH or GitHub CLI.
2. Add your custom JSON files to validate to **src/main/resources** folder.
2. If you don't have installed Maven _(ver. 4.0)_ on your device: <br> 
- Windows: Open Command Prompt at the root directory, type the command `mvnw.cmd clean install` and run the application.
- Mac/Linux: Open Terminal at the root directory, type the command `./mvnw clean install` and run the application.
3. The application will ask you to provide the name of the file in format `fileName.json`
4. To exit the application, type `x` and press `Enter`
