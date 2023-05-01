# `FullyHosted`
Fully Hosted is a showcase booking plafor.
You can check it running [`here`](http://fullybooked-lb-226867597.us-east-2.elb.amazonaws.com/)

![image](https://user-images.githubusercontent.com/13815416/235527806-6bfd8529-c1ad-4567-9117-ba146496b104.png)

## `API`

Built with Java 11 the API have the following functionalities:
- Create a booking
- Read a booking
- Update a booking
- Delete a booking
- Create a block
- Delete a block
- List unavailable dates

You can check the API Swagger [`here`](http://booking-api-lb-1790693286.us-east-2.elb.amazonaws.com/swagger-ui/index.html#)

![image](https://user-images.githubusercontent.com/13815416/235527476-df01ae02-aca6-4d76-a585-72ba5291fd8d.png)


## `Technologies`

### `API`
- Java 11
- Spring-Boot
- Maven
- Swagger
- H2 in memory

### `UI`
- React
- SASS

### `Infrastructure`
- AWS ECS Fargate
- AWS Container Registry
- Docker

## `How to run the project locally`

### `clone`

To clone the repository run the following command

    git clone https://github.com/Wtheodoro/bed-buddy

### `API`

after cloning you can go to the "api" folder

```
cd api
```

in the api folder build the .jar file with the command

```
mvn clean package
```

after building the .jar file we can run docker using the commands

```
docker build -t api . && docker run -it -p 8080:8080 api

```

After running the docker commands we have the API running

### `UI`

Now we have to go back to the project main folder

```
 cd ..
```

Go to the UI folder

```
cd booking-ui
```

Running using npm (faster)

```
npm install
npm start
```

For running the ui we can also use docker

```
docker build -t booking-ui && docker run -p 8888:80 app 
```

## `Techinical Decisions`
Considering the purpose and the size of the application some I took some decisions
- Didn't add Lombok 
- Didn't add Model Mapper
- Didn't Authentication
- Added a helper endpoint to list dates blocked for a given place
