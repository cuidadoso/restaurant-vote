Restaurant vote project.

1. 2 types of users: admin and regular users
2. Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
3. Menu changes each day (admins do the updates)
4. Users can vote on which restaurant they want to have lunch at
5. Only one vote counted per user
6. If user votes again the same day:
1) If it is before 11:00 we assume that he changed his mind.
2) If it is after 11:00 then it is too late, vote can't be changed

7. API

(GET) http://localhost:8080/api/v1/users         - get all users

(GET) http://localhost:8080/api/v1/users/1       - get user with id = 1

(POST) http://localhost:8080/api/v1/users        - create new user (entity in request body)

(PUT) http://localhost:8080/api/v1/users         - update new user (entity in request body)

(DELETE) http://localhost:8080/api/v1/users/1    - delete user with id = 1

(GET) http://localhost:8080/api/v1/restaurants   - get all restaurants

(GET) http://localhost:8080/api/v1/restaurants/2 - get restaurant with id = 2

(POST) http://localhost:8080/api/v1/restaurants   - create new restaurants (entity in request body)

(PUT) http://localhost:8080/api/v1/restaurants   - update new restaurants (entity in request body)

(DELETE) http://localhost:8080/api/v1/restaurants/2 - delete restaurants with id = 2

(GET) http://localhost:8080/api/v1/menus         - get all menus

(GET) http://localhost:8080/api/v1/menus/1       - get menu with id = 1

(POST) http://localhost:8080/api/v1/menus        - create new menus (entity in request body)

(PUT) http://localhost:8080/api/v1/menus         - update new menus (entity in request body)

(DELETE) http://localhost:8080/api/v1/menus/1    - delete menu with id = 1

(GET) http://localhost:8080/api/v1/dishes        - get all dishes

(GET) http://localhost:8080/api/v1/dishes/1      - get dishes with id = 1

(POST) http://localhost:8080/api/v1/dishes       - create new dishes (entity in request body)

(PUT) http://localhost:8080/api/v1/dishes        - update new dishes (entity in request body)

(DELETE) http://localhost:8080/api/v1/dishes/1    - delete dish with id = 1

(GET) http://localhost:8080/api/v1/votes         - get all votes

(GET) http://localhost:8080/api/v1/votes/4       - get vote with id = 4

(PUT) http://localhost:8080/api/v1/votes         - create/delete vote (entity in request body)


8. CURL examples to get entities, for not authorized users access is not denied only for users url ((GET) http://localhost:8080/api/v1/users, (POST) http://localhost:8080/api/v1/users)

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/users         - get all users without authorization (access denied)

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/users/1       - get user with id = 1 without authorization (access denied)

curl -d "@user.json" -i -H "Content-Type: application/json" -H "Accept: application/json" -X POST http://localhost:8080/api/v1/users - create user from user.json file (access not denied)

9. Authorization.

curl -X POST --user "javaops:secret" -d "grant_type=password&username=user1@mail&password=password" http://localhost:8080/oauth/token - get authorization token

Response with token: {"access_token":"cc01f936-a8bc-404e-8adc-d131ee47cdb3","token_type":"bearer","refresh_token":"4a476b2a-2e35-4b53-90a7-b31aee15f1fd","expires_in":3599,"scope":"read write"}

10. Authorized user with ADMIN role (user1@mail) can create/update/delete entities. Authorized user with USER role (user2@mail) can update/delete itself user and vote for restaurants.

curl -i -H "Accept: application/json" -H "Authorization: Bearer cc01f936-a8bc-404e-8adc-d131ee47cdb3" -X GET http://localhost:8080/api/v1/users - get all users

curl -d "@restaurant.json" -i -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer cc01f936-a8bc-404e-8adc-d131ee47cdb3" -X POST http://localhost:8080/api/v1/restaurants - create new restaurant

curl -d "@vote.json" -i -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer cc01f936-a8bc-404e-8adc-d131ee47cdb3" -X PUT http://localhost:8080/api/v1/vote - create/delete vote

11. After starting application same test data are created in database/

12. As database used embedded MongoDB, that start with application in memory.

PS: TODO Create/Update objects with json file dsn't work.