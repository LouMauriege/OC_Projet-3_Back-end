# Welcome to chatop API

First you'll need to have a MySQL server to store the database of this project.

Visit the [MySQL download page](https://dev.mysql.com/downloads/) and install the right version for your operating system.

Run your MySQL server with this command :

*Replace your_username with your actual MySQL server username.*

```bash
mysql -u your_username -p
```

Then enter your MySQL password.

Make sur you're running on port 3306 with :

```sql
SHOW VARIABLES LIKE 'port';
```

Then create and use a new database called "*chatop*"

```sql
CREATE DATABASE chatop;
```

``` sql
USE chatop;
```

And finally run the sql script you'll find in the root folder called "*script.sql*"

```sql
source /path/to/file/script.sql
```

Set-up the application.properties file in the root folder to match your MySQL credentials :

*Replace "your_username" with your actual username.*

```properties
spring.datasource.username=your_username
```

Create an env var called `OC_P3_DATASOURCE_PASSWD` with your MySQL password as value.

Run this command to start the api in the root folder of this repository :

```bash
./mvnw spring-boot:run
```

Then run the frontend and you're ready to go.

You'll find the swagger doc at the following url : [http://localhost:3001/api/swagger-ui/index.html](http://localhost:3001/api/swagger-ui/index.html)