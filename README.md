## Installation
1. Install Java 11
2. Add Lombok plugin to IntelliJ IDEA
3. Install docker

## Run application 
1. Start DB using `docker run -p 5432:5432 -e POSTGRES_USER=user -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=db -d postgres:9.6.1`
3. Run the app