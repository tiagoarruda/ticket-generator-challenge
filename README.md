# Ticket Generator Challenge

A small challenge that involves building a [Bingo 90](https://en.wikipedia.org/wiki/Bingo_(United_Kingdom)) ticket generator.

## Background
The suggested solution will generate 18 random rows in order to create 6 tickets that comprise the ticket strip.
The entities have been modeled to be created by a builder that validates if the date is consistent.
The application is built using spring command line runner.

## Solution Salkthrough
1. The generator class contains an array with all the numbers in a ticket strip.
2. Each time we generate a new strip first we copy those numbers and shuffle them in a random order.
3. Now we iterate over the numbers and assign them into a random row
4. if a column conflict happens:
   1. find another row that can have that number and that don't have one of the columns the current row have. 
   2. After we identify those two rows we swap the values.


## Running the application
### Commands
- `Generate one ticket strip:`<br />
By running the spring application you will generate one ticket strip and end execution.
```shell
 mvn spring-boot:run
```
- `Generate a batch of ticket strips:`<br />
If you pass the argument batch-size the application will create the specified number of ticket strips.
```shell
 mvn spring-boot:run -Dspring-boot.run.arguments=--batch-size=10000
```
- `Generate a batch of ticket strips with validation set off:`<br />
  If you pass the argument no-validation the application entities will not do any data validation.
```shell
mvn spring-boot:run -Dspring-boot.run.arguments="--batch-size=10000 --no-validation=true"
```
### Arguments
- `batch-size`<br />
Integer that specifies how many ticket strips should be created.<br />
  Passing a string will throw and exception and end execution.
  Negative values will be ignored and one strip will be generated.

- `no-validation`<br />
Boolean that disables entity data validation improving performance considerably.
  
## Performance
The Object Oriented model suggested in this solution validates the contents of all the strips, those operations take time but guarantee that the object is consistent with it's model.
When those validations are removed it takes less than 1 second to generate 10k tickets.
It's worth mentioning that some of the validations are redundant since the generation class takes many of those rules into account.
Every run will print the duration needed to generate the strips.

## Improvements
I am pretty sure I could spend another week improving this, but I am happy with the overall solution.

1. The performance vs the object oriented design validations<br />
Is obvious that the validations need improvement, they take the time execution from less than a second to 6 seconds. This is something to look into.
   
2. Presentation<br />
The current implementation is not sorting the columns numbers, that extra step would be necessary if the strips were being printed or presented in another way.
   
3. Running<br />
Command line tools should have argument documentation. There are tools to better map and document arguments to improve usability. 