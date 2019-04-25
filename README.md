# Parking Management System

## Functional Specifications

The parking management system is an application to manage the whole process of parking a customer’s vehicle in a parking lot or garage. This includes parking the vehicle as well as paying for the spot. Only the customer will interact with the application. The project is going to be a simple desktop application and will run on any desktop OS (Windows, Linux or Mac).

When the application begins, it shows two choices; Member or Register. If the customer selects Register, they can signup for a new account. Clicking Member will allow old customers to log in to their account. Once logged in, The member can add a vehicle, remove a vehicle, select a vehicle to park, checkout, or access settings. Selecting a vehicle to park allows them to select a spot (if available). In the settings menu, they can add credits, change their password or log out.


## How to run application

### GUI based application

1. Clone this repository.

2. Add jfoenix-8.0.8.jar as a library.

3. Run application/Main.java.

### Text based application

1. Run application/TextTester.java.

### Alternative presets

1. Run application/PresetCreator.java.

2. Pass one of the created *.ser* file as an argument to the above applications.

Alternatively, generate your own preset file following the same PresetCreator.java procedure.


## How to run javadocs

1. Add the following tags *precondition* and *postcondition*. That is pass these two arguments to the javadoc command:

```
-tag precondition:a:"Precondition:"
-tag postcondition:a:"Postcondition:"
```

2. Run javadoc.


## How to run unit Tests

1. Add junit 5.

2. Run Tests

## Github Link

Github link: https://github.com/rkhat/ood-project

Demo link: https://youtu.be/HjzBQH6cnNE