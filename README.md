# Assignment_Two

# How to run

# Beer Side
* View all Beers - GET http://localhost:8888/beer/allBeers
* View Beer by ID - GET http://localhost:8888/beer/beer/{id}
* Get Beer Details by ID - GET http://localhost:8888/beer/details/{id}
* Return Large or Thumbnail Image - GET http://localhost:8888/beer/image/{id}/{imageType}
* Edit a Beer - PUT  http://localhost:8888/beer/{id}
* Insert a Beer - POST  http://localhost:8888/beer
* Delete a Beer - DELETE  http://localhost:8888/beer/{id}
* Get Zip of Beer Images - GET  http://localhost:8888/beer/zip
* Download a PDF - GET  http://localhost:8888/beer/pdf/{id}

# Brewery Side
* View all Breweries - GET http://localhost:8888/brewery/allBrewery
* View Brewery by ID - GET http://localhost:8888/brewery/brewery/{id}
* Edit a Brewery - PUT  http://localhost:8888/brewery/{id}
* Insert a Brewery - POST  http://localhost:8888/brewery
* Delete a Brewery - DELETE  http://localhost:8888/brewery/{id}
* Display Map of Brewery - GET http://localhost:8888/brewery/map/{id}
* Generate QR Code - GET http://localhost:8888/brewery/qrcode/{id}

# Functionality Implemented
* TASK 1
- Hateoas Links :heavy_check_mark:
- GET, PUT , POST, DELETE with JSON response :heavy_check_mark:
- Do not rehash functionality for brewery side :x:
- Consume a client application written in another language :x:
- Pagination :x:

* TASK 2
- Return google map for a specific brewery :heavy_check_mark:

* TASK 3
- Generate QR code for a specific brewery :heavy_check_mark:

* TASK 4
- Return large or thumnail image :heavy_check_mark:

* TASK 5
- Return a compressed (zipped) file containing all beer images :heavy_check_mark:

* TASK 6 
- Return, as a PDF, a brochure for a specified beer :heavy_check_mark:

* TASK 7
- Report :heavy_check_mark:

* Extra
- Handle Errors :x: