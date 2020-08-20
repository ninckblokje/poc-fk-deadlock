# PoC Foreign Key Deadlock

This is a small PoC project for demonstrating deadlock's on foreign keys when there is no index on the foreign key
columns.

Although there should always be an index on a foreign key column, I had some problems with this on SQL Server. Deadlock's
continued even after adding the indexes, so I created this project to help me debug the issue. 

For a successful test: Remove the comments before the two `@Index` annotations in the class [Message](src/main/java/ninckblokje/poc/db/deadlock/fk/entity/Message.java).
For an unsuccessful test: Add comments before the two `@Index` annotations in the class [Message](src/main/java/ninckblokje/poc/db/deadlock/fk/entity/Message.java).

## JUnit tests

The behaviour is tested in the class [PocFkDeadlockApplicationTests](src/test/java/ninckblokje/poc/db/deadlock/fk/PocFkDeadlockApplicationTests.java).
By default, Maven and the test will fail. Provide one of the following profiles to test it with the corresponding database:

- sqlserver-local (using a local SQL Server)
- sqlserver (using a docker SQL Server)

## Transaction isolation

According to Camunda, SQL Server uses a different implementation of *READ_COMMITTED*. This can cause deadlocks. To
change this, execute the following SQL commands on a databsae (not on a system database):

````sql
ALTER DATABASE [process-engine]
SET ALLOW_SNAPSHOT_ISOLATION ON

ALTER DATABASE [process-engine]
SET READ_COMMITTED_SNAPSHOT ON
````

Documentation:
- https://docs.camunda.org/manual/7.13/user-guide/process-engine/database/#configuration-for-microsoft-sql-server
- https://docs.microsoft.com/en-us/dotnet/framework/data/adonet/sql/snapshot-isolation-in-sql-server
