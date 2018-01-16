Restaurant vote project.

1. 2 types of users: admin and regular users
2. Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
3. Menu changes each day (admins do the updates)
4. Users can vote on which restaurant they want to have lunch at
5. Only one vote counted per user
6. If user votes again the same day:
1) If it is before 11:00 we assume that he changed his mind.
2) If it is after 11:00 then it is too late, vote can't be changed

7. CURL examples to get entities

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/users         - get all users without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/users/1       - get user with id = 1 without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/restaurants   - get all restaurants without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/restaurants/2 - get restaurant with id = 2 without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/menus         - get all menus without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/menus/1       - get menu with id = 1 without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/dishes        - get all dishes without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/dishes/1       - get dishes with id = 1 without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/votes         - get all votes without authorization

curl -i -H "Accept: application/json" -X GET http://localhost:8080/api/v1/votes/4       - get vote with id = 4 without authorization

8. Authorization.

curl -X POST --user "javaops:secret" -d "grant_type=password&username=user1@mail&password=password" http://localhost:8080/oauth/token - get authorization token

Response with token: {"access_token":"cc01f936-a8bc-404e-8adc-d131ee47cdb3","token_type":"bearer","refresh_token":"4a476b2a-2e35-4b53-90a7-b31aee15f1fd","expires_in":3599,"scope":"read write"}

9. Authorized user

curl -i -H "Accept: application/json" -H "Authorization: Bearer cc01f936-a8bc-404e-8adc-d131ee47cdb3" -X GET http://localhost:8080/api/v1/users - get all users

curl -d "@restaurant.json" -i -H "Content-Type: application/json" -H "Accept: application/json" -H "Authorization: Bearer cc01f936-a8bc-404e-8adc-d131ee47cdb3" -X POST http://localhost:8080/api/v1/restaurants - create new restaurant
