# markwilliamson-webtest


Mark Williamson
Web Test Submission
https://github.com/mcwilliamson/markwilliamson-webtest


Service Installation
====================
Download the source code. If downloaded as a ZIP, please unzip the file. 

Navigate to the folder containing the source code (which will contain the mvnw file and .mvn folder) - we'll refer to this as <base_source_code_folder>. 

Run: 

	mvn clean install


This will build the JAR file MarkWilliamsonWebTest-0.0.1-SNAPSHOT.jar in the folder <base_source_code_folder>\target\. 

The JAR contains an embedded instance of Tomcat web server. By default, the embedded Tomcat instance is configured to run the web service on port 8080 on localhost. If you wish to change the port, create a new "application.properties" file in the same folder as the MarkWilliamsonWebTest-0.0.1-SNAPSHOT.jar and add the following entry:

	server-port:<port-number>

e.g.

	server-port:8090
  
If necessary, stop other instance of Tomcat that may be running on the same port.

To start the service, navigate to <base_source_code_folder>\target\ and run the following command:

	java -jar MarkWilliamsonWebTest-0.0.1-SNAPSHOT.jar



Service Usage
=============

List all packages 
-----------------
This will retrieve a list of all packages

http://localhost:8080/api/v1/productpackages/


Get a package 
-------------
This will retrieve a specific package based on the package id

http://localhost:8080/api/v1/productpackages/{package_id}

e.g.

http://localhost:8080/api/v1/productpackages/coinpkg001


Get a package with specified currency
-------------------------------------
This will retrieve a specific package based on the package id, but with the total price converted to the specified currency using the latest exchange rates

http://localhost:8080/api/v1/productpackages/coinpkg001?currency={valid_3_char_currency_symbol}

e.g.

http://localhost:8080/api/v1/productpackages/coinpkg001?currency=GBP


Create a package
----------------
Create a new package, specifying the package id, name, description and a string list of ids of products to be included in the package.

http://localhost:8080/api/v1/productpackages/create

You must pass the new package data in the following JSON format:

{ "id":"{package_id}", "name":"{package_name}", "description":"{package_description}", "productIds":[{string_list_of_valid_product_ids}] }

e.g.

{ "id":"package3", "name":"Package 3", "description":"Package 3 description", "productIds": ["VqKb4tyj9V6i", "DXSQpv6XVeJm"] }


Update a package
----------------
Update an existing package, specifying the package id, name, description and a string list of ids of products to be included in the package.

http://localhost:8080/api/v1/productpackages/update

You must pass the updated package data in the following JSON format, where "id" must match an existing package:

{ "id":"{package_id}", "name":"{package_name}", "description":"{package_description}", "productIds":[{string_list_of_valid_product_ids}] }

e.g.

{ "id":"package3", "name":"Package 3b", "description":"Package 3b description", "productIds": ["500R5EHvNlNB", "IJOHGYkY2CYq"] }


Delete a package
--------------------------
Delete an existing package, based on the package id

http://localhost:8080/api/v1/productpackages/delete/{package_id}

e.g.

http://localhost:8080/api/v1/productpackages/delete/package3


List available products
-----------------------
Retrieve a list of all available products

http://localhost:8080/api/v1/productpackages/products
