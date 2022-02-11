# Reddit Notifier

## How to build 

Building with `mvn clean install` will also run tests, which use TestContainers: please have docker running or use `-DskipTests`.

## How to start

1. Make sure MongoDB (version 4) is running (default settings will do fine) either via local installation or container.
    
   *  **Local installation:** download and run the installer. Then launch with `mongod --dbpath <path to an empty folder>` such as `~/Downloads/mongodb4/dbdata-reddit` (the folder must exist before the startup, so needs to be created with e.g. `mkdir`). 
   
   *  **Containers:** follow the [official instructions here](https://www.mongodb.com/compatibility/docker).


2. Make sure to start the app providing a value for the property `reddit.app.id` either by passing it via arguments `-Dreddit.app.id={value}` or by placing the value in the property file. You can use the start functionality from the IDE or `mvn spring-boot:run` from the command line.

## How to use it

1. Insert one or more users with cURL, like this (can actually copy-paste and execute the following).
   ```
   curl -X POST -H "Content-Type: application/json" -d '{"name":"Homer", "email":"homer.simpson@mail.com", "favorites":["bowling"]}' http://localhost:8182/user/
   ```
   Take note of the returned `userId` (something in a format similar to `6202722e7681d63229944915`), to be used to fetch the newsletter (next step).
   

2. point the browser to `http://localhost:8182/user/{userId}/newsletter/` replacing the placeholder `{userId}` with the actual value.


3. To try the functionality, you can change the subreddits with 
    ```
    curl -X PATCH -H "Content-Type: application/json" http://localhost:8182/user/6202722e7681d63229944915/favorites/ -d '{"addSet": ["funny", "technology"], "deleteSet": ["python"]}'
    ```
   and refresh the browser to see the changes.

## Misc

1. A rough sketch of the architecture / use cases.
   ![Architecture](reddit-newsletter.jpeg)


2. You can `GET` and `DELETE` users too, by Id. Examples:
   ```
   curl -X GET  http://localhost:8182/user/6202722e7681d63229944915/
   
   curl -X DELETE http://localhost:8182/user/6202722e7681d63229944915/
   ```
 