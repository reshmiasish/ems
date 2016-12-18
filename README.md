# Employee Management System
This is a set of API's to manage employee system in an organization. 
At present, system supports below functions:
* Register for API access
* Add an Employee
* Get an Employee
* Update an Employee Data - Full/Partial
* Delete Employee

## Technology Stack
* Language
    * Java 1.7
    * Servlet 3.1
* Frameworks
    * MVC - Spring 4
    * ORM - Hibernate & JPA
    * Logging - Log4J
    * Unit Test - JUnit/Mockito
* Database
    * MySQL (InMemory)
* Build Tools
    * Maven 3.2.3

## Building
This project use Java and Maven, before proceeding to next step please make sure that you have all required dependencies on your system.
#### - Verify Java
Open command prompt (Windows) or Terminal (Mac/Linux) and run below command
```
$ java -version
java version "1.7.51_16"
Java(TM) SE Runtime Environment (build 1.7.51_16-b02)
Java 
HotSpot(TM) 64-Bit Server VM (build 25.74-b02, mixed mode)
```

#### - Verify Maven 
Open command prompt (Windows) or Terminal (Mac/Linux) and run below command
```
$ mvn -v
Apache Maven 3.2.3 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T09:41:47-07:00)
Maven home: /usr/local/Cellar/maven/3.2.3/libexec
Java version: 1.7.51_164, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.7.51_16.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.12.1", arch: "x86_64", family: "mac"
```
*Warning:* If any of the above dependencies are not met, please install them before proceed.

Clone or Download the code to desired directory. You can build the code using maven as below.

```
$ mvn clean install
```
*Warning:* If you are behind a corporate proxy and/or have a an enterprise maven 
repository, the build may fail as the dependencies that are present in the project might not be available.
To fix this, you have to proxy the Enterprise Repo or update Enterprise repo with the required dependencies.
To get all dependencies, run below command.
```
$ mvn dependency:tree
```

For first time install, the build will take some time as the project need to download all dependencies from Maven Central.

## Run
The project use Jetty server for the development purpose. 
You can also deploy the code to any Servlet 3.0 supported containers like JBoss, Tomcat etc.
To run the project execute below command.
```
$ mvn jetty:run
```
This will start the Jetty Server and deploy the code inside it. You can test the application using url http://localhost:8080/ems/ 
#### Database
As the project is using MYSQL InMemory database, there is no datasource setup required.
All the database sqls (schema and data) are also part of the code. You can find in the **db** directory in resources folder.

The default port for the application is 8080, you can change that by supplying 
additional arguments during startup as below.
```
$ mvn jetty:run -Djetty.port=<port>
```
#### Application URL
Once the application is running, API endpoints are available at **http://localhost:8080/ems/api**
## APIs
### Getting Started
To access the APIs, the user should first register in the system. 
Once the user is successfully registered with the system, the system will 
provide an API Key which the user need to provide on the subsequent requests.

**Some highlights:**
* **Request Id:** Every API request is tagged with a unique Request ID, you'll be able to see that in the 
response header. This will offer additional help in debugging.
* **Content Type:** Every request is using **application/json** as content type.  

### Register
This will allow a user to register with the system

```
POST /api/registration/subscribe
```
**Request body parameters**

|Name|Description|Type|Optional|Max Length|
|----|-----------|----|--------|----------|
|name|Name of the API consumer|String|No|50|
|email| Email id of the API consumer|String|No|50|

**Response**
* HTTP Status 200
* apiKey - use this key for any future API access.

```
# Sample curl request
curl 'http://localhost:8080/ems/api/registration/subscribe' -H 'Content-Type: application/json' --data-binary $'{\n"name":"Gracy Dan",\n"email":"gracy1@ems.com"\n}'
# Sample Response
{"message":"Successfully added new user to the System","data":"API Key: 1724696d-e7c1-4c57-8a5f-35546abad15e"}
```

### Add Employee
This will allow a registered user to add an Employee to the system.

```
POST /api/employee/add
```
**Request body parameters**

|Name|Description|Type|Optional|Max Length|
|----|-----------|----|--------|----------|
|firstName|First Name of Employee|String|No|25|
|lastName|Last Name of the Employee|String|No|25|
|role|Employee Role Id in the company|Number|No|-|
|department|Department Id of Employee|Number|No|-|
|email|Email Id of the employee|String|No|50|
|birthDate|Birth date in YYYY-MM-DD format|Date|No|-|
|joinDate|Joining date in YYYY-MM-DD format|Date|No|-|
|gender|Gender of Employee|String|No|10|
|salary|Salary of Employee|Number|No|-|

**Response**

* HTTP Status 201

```
# Sample curl request
curl 'http://localhost:8080/ems/api/employee/add' -H 'Content-Type: application/json' -H 'X-API-Key: 1724696d-e7c1-4c57-8a5f-35546abad15e' --data-binary $'{\n"firstName":"Gracy1",\n"lastName":"Dash1",\n"department": {\n\x09"departmentId": 1\n},\n"birthDate":"1990-01-08",\n"joinDate":"2012-02-05",\n"role": {\n\x09"roleId": 0\n},\n"gender":"Female",\n"salary": 54000,\n"email":"gracy1@ems.com"\n}'
# Sample Response
{"message":"Employee added to the system!","data":{"employeeId":100006,"firstName":"Gracy1","lastName":"Dash1","department":{"departmentId":1,"departmentName":null,"departmentDesc":null},"birthDate":"1990-01-07","joinDate":"2012-02-04","role":{"roleId":0,"roleName":null,"roleDescription":null},"gender":"Female","salary":54000,"email":"gracy1@ems.com"}}
```

### Get Employee
This will allow a registered user to get/read an Employee using the employee id.

```
GET /api/employee/{id}
```
**Request body parameters**

|Name|Description|Type|Optional|Max Length|
|----|-----------|----|--------|----------|
|id|Employee Id|Number|No|-|

**Response**

* HTTP Status 200
* A valid Employee Object in JSON format.
```
# Sample curl request
curl 'http://localhost:8080/ems/api/employee/id/100004' -H 'Content-Type: application/json' -H 'X-API-Key: 1724696d-e7c1-4c57-8a5f-35546abad15e'
# Sample Response
{"message":null,"data":{"employeeId":100004,"firstName":"Gracy1","lastName":"Dash1","department":{"departmentId":1,"departmentName":null,"departmentDesc":null},"birthDate":"1990-01-07","joinDate":"2012-02-04","role":{"roleId":0,"roleName":null,"roleDescription":null},"gender":"Female","salary":54000,"email":"gracy1@ems.com"}}
```

### Update Employee - Full
This will allow a registered user to update an Employee based on employee id. 
If you don't want to pass the complete object please see the next API.

```
PUT /api/employee/update/{id}
```
**Request body parameters**
|Name|Description|Type|Optional|Max Length|
|----|-----------|----|--------|----------|
|employeeId|Employee ID on the system|Number|No|-|
|firstName|First Name of Employee|String|Yes|25|
|lastName|Last Name of the Employee|String|Yes|25|
|role|Employee Role Id in the company|Number|Yes|-|
|department|Department Id of Employee|Number|Yes|-|
|email|Email Id of the employee|String|Yes|50|
|birthDate|Birth date in YYYY-MM-DD format|Date|Yes|-|
|joinDate|Joining date in YYYY-MM-DD format|Date|Yes|-|
|gender|Gender of Employee|String|Yes|10|
|salary|Salary of Employee|Number|Yes|-|

**Response**

* HTTP Status 200
* A updated Employee Object in JSON format.
```
# Sample curl request - Updaing the Email Address
curl --request PUT 'http://localhost:8080/ems/api/employee/update/100004' -H 'Content-Type: application/json' -H 'X-API-Key: 1724696d-e7c1-4c57-8a5f-35546abad15e --data-binary $'{\n"employeeId":100004,\n"firstName":"Gracy1",\n"lastName":"Dash1",\n"department": {\n\x09"departmentId": 1\n},\n"birthDate":"1990-01-08",\n"joinDate":"2012-02-05",\n"role": {\n\x09"roleId": 0\n},\n"email":"gracy1.dash@ems.com"\n}'
# Sample response
{"message":"Employee data updated!","data":{"employeeId":100004,"firstName":"Gracy1","lastName":"Dash1","department":{"departmentId":1,"departmentName":null,"departmentDesc":null},"birthDate":"1990-01-07","joinDate":"2012-02-04","role":{"roleId":0,"roleName":null,"roleDescription":null},"gender":null,"salary":0,"email":"gracy1.dash@ems.com"}}
```

### Update Employee - Partial
This will allow a registered user to update an Employee based on employee id. 
If you are having the entire Employee object in your input, please see previous API details.

```
PATCH /api/employee/patch/{id}
```
**Request body parameters**
|Name|Description|Type|Optional|Max Length|
|----|-----------|----|--------|----------|
|employeeId|Employee ID on the system|Number|No|-|
|firstName|First Name of Employee|String|Yes|25|
|lastName|Last Name of the Employee|String|Yes|25|
|role|Employee Role Id in the company|Number|Yes|-|
|department|Department Id of Employee|Number|Yes|-|
|email|Email Id of the employee|String|Yes|50|
|birthDate|Birth date in YYYY-MM-DD format|Date|Yes|-|
|joinDate|Joining date in YYYY-MM-DD format|Date|Yes|-|
|gender|Gender of Employee|String|Yes|10|
|salary|Salary of Employee|Number|Yes|-|

**Response**

* HTTP Status 200
* A updated Employee Object in JSON format.
```
# Sample curl request
curl --request PATCH 'http://localhost:8080/ems/api/employee/patch/100004' -H 'Content-Type: application/json' -H 'X-API-Key: 1f0131b2-98c0-405d-9279-c6260d01ec46' --data-binary $'{\n"employeeId":100004,\n"department": {\n\x09"departmentId": 2\n}\n}'
# Sample response
{"message":"Employee data patched!","data":{"employeeId":100004,"firstName":"Gracy1","lastName":"Dash1","department":{"departmentId":2,"departmentName":null,"departmentDesc":null},"birthDate":"1990-01-07","joinDate":"2012-02-04","role":{"roleId":0,"roleName":null,"roleDescription":null},"gender":null,"salary":0,"email":"gracy1.dash@ems.com"}}
```

### Delete Employee
This will allow a registered user to delete an Employee based on employee id. 

```
DELETE /api/employee/delete/{id}
```
**Request body parameters**
|Name|Description|Type|Optional|Max Length|
|----|-----------|----|--------|----------|
|id|Employee Id|Number|No|-|

**Response**

* HTTP Status 200
```
# Sample curl request
curl --request DELETE 'http://localhost:8080/ems/api/employee/delete/100005' -H 'Content-Type: application/json' -H 'X-API-Key: 1f0131b2-98c0-405d-9279-c6260d01ec46'
# Sample Response
{"message":"Employee data deleted successfully!"}
```

## Response Errors
This section explains the generic errors, that are applicable to the API calls.

```
HTTP 401
API Key is not present. Use the header X-API-Key to provide the apiKey

This is due to the missing API Key. API Key obtained using POST /api/registration/subscribe has to supplied alongwith every other calls. You need to add the API Key as part of the header - X-API-Key
```

```
HTTP 401
Invalid Key provided : 3d35531a-6537-4b21-980b-b6dacac450ea

This is due to old/invalid key. Given that we are using an In-memory db, we need to register each time the application starts.
```

```
HTTP 422
<Validation Errors>

This can happen due to two conditions.
- Validation Failure - Validation present in certain fields. You'll be able to get specific details on the error message.
- Trying to add/modify/delete an Employee data with wrong employee id.
```

## Next Steps...
- Cache Implementation - This will improve the system performance.
- Security using oAuth - As of now, the system uses a Static API key, we can enhance this by using oAuth.
- More APIs on Role, Department management.
- Improve Error management and add custom error messages

