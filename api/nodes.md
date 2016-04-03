# /nodes
## GET
Get an array of `Node` objects.

### Parameters
* latitude: Float (required)
* longitde: Float (required)
* radius: Integer

### Return

#### 200 - OK
	[
		{
			"id": Integer,
			"user": {
				"id": Integer,
				"firstname": String,
				"lastname": String,
				"href": String
			},
			"reach": Integer,
			"location": {
				"latitude": Float,
				"longitude": Float
			},
			"number_of_comments": Integer,
			"date": Date
			"href": String
		},
		...
	]




## POST

### Parameters
* content: String (required)
* latitude: Float (required)
* longitude: Float (required)

### Return

#### 200 - OK
	[
		"status": 200
	]