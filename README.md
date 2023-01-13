# Efficient visualization of the distributed urban traffic simulation
This application is used to visualize distributed urban traffic simulation. It is compatible with OSM maps in .osm or .pbf format and 
consumes messages from Apache Kafka broker instance.

### Goals
The goal of the project was to create an efficient visualization for presenting the results of distributed urban traffic simulation. 
The project is mainly aimed at people using the simulation, which will allow them to clearly observe the simulated car traffic.

### Requirements
- Java 17
- Running Apache Kafka broker instance
- Running Confluence Schema Registry instance

### Startup
- Compile model.proto file with maven compile command
- Run all required instances via provided docker-compose file
- Run SimulationVisualization main class
- Provide path to OSM map (.osm or .pbf formats are used)

Visualized map
![Alt text](img/cracow_map.PNG?raw=true "Map")


### Simulation Control
- User can send start message to Kafka broker with Start button, which will start simulation computations
- User can send stop message to Kafka broker with Stop button, which will stop simulation
- User can send resume message to Kafka broker with Resume button, which will resume simulation
- User can send end message to Kafka broker with End button, which will end simulation computations
- User can change simulation computation speed with Time Multiplier bar

### Visualization Control
- User can drag and zoom map
- Visualization show cars level or road density level depending on current zoom level

Cars level
![Alt text](img/map_cars.png?raw=true "Cars level")

Road density level
![Alt text](img/map_density.jpg?raw=true "Road density level")

### Configuration
- application.yaml: user provides endpoints to Apache Broker instance and Confluence Schema Registry
- model.proto: user specifies structure of consumed messages which need to be synchronized with simulation messages format via Schema Registry
- TopicConfiguration class: user can change the names of Kafka Topics from which visualization consumes messages and which are registered to Kafka at startup
- way_parameters.json: user can add or remove OSM map data definitions which will be visualized as roads or buildings.
- docker-compose.yml: user can change Kafka broker or Schema Registry enpoints there
