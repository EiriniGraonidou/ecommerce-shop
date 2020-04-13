## ecommerce-shop
REST application for the stock management of the e-commerce-shop

### Conditions and Constraints
1. Feel free to use any Java-based framework and database
2. The application shall follow the multi-tier architecture paradigm
3. All interfaces shall be covered by unit tests
4. All functions shall be documented
5. All services are accessible via REST
6. The completed application shall be published to a publicly accessible GIT repository
like BitBucket or Github.

### Functional Requirements
1. The product stock has a predefined initial stock of 100 items
2. It must be possible to request the entire product and product stock information
3. It must be possible to request the stock of an individual product
4. It must be possible to refill the product stock
5. Buying the product decreases the stock
6. It is not allowed to buy more product items than its stock provides
7. Optional: product can be reserved for a specified time and must be released (back to
stock) later if it hasn’t been bought in the meantime

### Considerations and general notes about the task

There are many approaches one can take to implement the solution for the given constraints and functional requirements.
While reading those, I was able to extract one or two business logic units; for example the meaning of a stock, a product and an order.
I tried to keep those above mentioned units as independent as possible from each other keeping in mind that as time goes by, 
probably many, much more detailed requirements could be added.

The implementation of several concepts such as the following could be improved:
0. There is no concept of security for the e-shop. The API is currently publically available. 
1. The order API could group the products together and present the summary of the ordered amount.
2. Integration as well as mvc tests were not implemented due to lack of time capacity. 
3. The test coverage could be then dramatically improved :)
4. There is no versioning, everything was implemented in one branch. A maven plugin should be used for a proper release lifecycle.

### Tools the were used
- Implemented with openjdk14 (compiled with openjdk13)
- Built with maven
- Uses spring boot 2.2.6 (modules: web, data, hateaos, test etc..)
- h2 (in memory) DB

### Hints
To start the e-shop one can:
mvn install
and then java -jar ecommerce-shop-0.0.1-SNAPSHOT.jar otherwise just run as spring boot app.
An overview of the REST-API can be found here {host}:{port}/e-shop/swagger-ui.html#/ after the application is started.

