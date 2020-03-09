TasK:
	Create simple REST API for news. 
	Each entity should contain the following information: 
	id, date, title, short description, text.
	All requests and responses should be in JSON. 
	API results should be sortable by date and/or title. 
	API results may be filtered by date and/or title. 
	You can use a framework and database on your choice.

I build it using SpringBoot + REST + H2 in memory Database

I implemented two versions 

## 1- without filtering just pagination and Sort 
Sample:
http://localhost:8080/v1/news?pageSize=5&pageNo=3&sortBy=+date,-title

Class :
io.cryptoapis.news.controller.v1.NewsController

## 2- Using help of the following link to implement Filtering and Sorting in generic way with any sort or filtering/Searching
https://github.com/vijjayy81/spring-boot-jpa-rest-demo-filter-paging-sorting

Sample:
http://localhost:8080/news?title=eq:anytext&pageSize=5&pageNo=3&sort=+date,-title

Class :
io.cryptoapis.news.controller.NewsApiController


Description:


# news-api-filter-paging-sorting

An example application using Spring boot MVC, Spring Data JPA with the ability to do filter, pagination and sorting.

## Filter

REST APIs handles filter as followed in the demo application. 

It parses and converts the following notions as 'org.springframework.data.jpa.domain.Specification'. 
The operation and specification mapping are kept as operation to lambda functions.

|   Symbol       |Operation                      |example filter query param                     |
|----------------|-------------------------------|-----------------------------|
|eq              | Equals                        |'city=eq:Sydney'
|neq             | Not Equals                    |'country=neq:uk'             |
|gt              | Greater Than                  |'amount=gt:10000'            |
|gte             | Greater Than or equals to     |'amount=gte:10000'           |
|lt              | Less Than                     |'amount=lt:10000'            |
|lte             | Less Than or equals to        |'amount=lte:10000'           |
|in              | IN                            |'country=in:uk, usa, au'            |
|nin             | Not IN                        |'country=nin:fr, de, nz'            |
|btn             | Between                       |'joiningDate=btn:2018-01-01, 2016-01-01'            |
|like            | Like                          |'firstName=like:John'            |

## Paging
The API's query params 'pageNumber' & 'pageSize' are converted to 'org.springframework.data.domain.PageRequest'

Sample
.....?pageSize=10&pageNumber=2

## Sorting
'sort' query param with comma separated attributes prefixed with either '+' (ASC Order) or '-' (DESC Order) are converted 
to 'org.springframework.data.domain.Sort' with 'org.springframework.data.domain.PageRequest'

Example: .....?sort=+salary,+joiningDate


## Data
I set dummy data for test in data.sql files 

## Testing 
Class -> NewsApplicationTests

## Technology stack
-   Spring Boot
-   Spring MVC
-   Spring Data JPA
-   Hibernate 5.x
-   Spring Boot Test/JUnit/Mockito/RestAssured

## Prerequisites
- JDK 8
- Maven

## Run
	mvn spring-boot:run
