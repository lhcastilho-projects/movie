# Movie project
Welcome to the Worst Award Movie project, a Java application to show you the worst producers! 

<br>

# Prerequisite
* Java 17 or more installed.
* JAVA_HOME envinronment variable set to the Java installation folder

<br>

# Project Description
The goal of this project is to create a simple loader of csv file and provide information about the producers with a less and greater intervals between two awards.

<br>

# Getting Started
To run the Java Worst Award Movie, follow these steps:

Clone the repository from Github.
```
git clone https://github.com/lhcastilho-projects/movie.git
```

Build and run the project.
```
./mvnw liberty:run
```

Make HTTP request to get details about the worst movie award.
```
curl http://localhost:9080/movie/api/producers/awards
```

Run Integration test
```
./mvnw verify
```

PS: It requires liberty up and running as explained on _build and run the project_)


<br>

# Contributions

The project was developed by lhcastilho.

