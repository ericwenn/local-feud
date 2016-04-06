# /posts
## GET
Get an array of `Post` objects.

### Parameters
* latitude: `Float` (required)
* longitde: `Float` (required)
* radius: `Integer`
* order: `String`


### Return

#### 200 - OK
	[
		{
			"id": Integer,
			"user": {
				"id": Integer,
				"href": String
			},
			"reach": Integer,
			"content":
				"type": String [ 'text' / 'image' ],
				"image_src": URL,
				"text": String
			}


			"location": {
				"distance": Integer 
			},

			"number_of_comments": Integer,
			"number_of_likes": Integer,
			"date_posted": Date
			"href": URL
		},
		...
	]

`location.distance` is an `Integer` from 0 to 10.

* `0` = close
* `10` = far 


## POST

### Parameters
* `latitude`: 		Float 						(required)
* `longitude`: 		Float 						(required)

* `content_type`: 	String [ 'image'/'text']  	(required)
* `text`: 			String 						(required if `content_type == 'text'`)
* `image`:			**TODO**


### Return

#### 200 - OK
	[
		"status": 200
	]