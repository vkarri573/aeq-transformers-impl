# Transformers API
## Introduction

Transformers API allows the following main functionality:
1) Create a Transformer
2) Update a Transformer
3) Delete a Transformer
4) List Transformers
5) Given a list of Transformer IDs, determine the winning team

## Steps to build and run the unit tests

1) Clone the project
   >git clone https://github.com/vkarri573/aeq-transformers-impl.git

2) Once project is cloned, go to project folder 'aeq-transformers-impl' 
   >cd aeq-transformers-impl

3) Run maven test goal to run unit tests
   >mvn clean test

## Steps to run the application

1) Once project is cloned, run maven package goal at project folder 'aeq-transformers-impl'
   >mvn clean package

2) Go to folder 'target'
    >cd target

3) Run below command to start the application
    >java -jar aeq-transformers-impl-0.0.1-SNAPSHOT.jar

## API endpoints

### Create a transformer

1) Endpoint URL: http://localhost:8080/transformers
2) HTTP method: POST
3) Request headers:
     "Content-Type" : "application/json"
4) JSON payload:
     {
        "name": "Predaking",
        "team": "A",
        "strength": 8,
        "intelligence": 9,
        "speed": 2,
        "endurance": 6,
        "rank": 7,
        "courage": 5,
        "firePower": 6,
        "skill": 10
    }
5) Sample response: {"status":"Transformer created"}

## Assumptions

1) Transformers in a team are sorted in decending order(10-1) by rank.
2) Transformers who don't have fight are considered as default survivors if their team loses the game.









