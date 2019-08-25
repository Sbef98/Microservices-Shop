

## SMART SHOP PROJECT

# Aim

_This software creates dynamic environment conditions to fit people moods in order to generate a more suitable place for purpose of the area._

It can be used to make a shop more endearing, to encourage the customers to buy our products, or it can be used in a garden to let the area more relaxing for visitors, or it can be used in a cinema to get more addictive the projections.

# How it works

It works on sensorial environment conditions like lights, temperature, color and music to generate a more suitable place for the people inside. Linking this software with sensors and actuators let the system to control all these variables and modify them with a custom logic implemented in a decision service.

# Structure

This prototype software deploys a sample **REST microservice architecture** which allows different microservice to communicate between HTTP protocol. The microservices net is used to collect all the data from sensors microservices, to process them in a decision service and to communicate the action needed to actuator microservices.
#
Written using https://stackedit.io/app#
