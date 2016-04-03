# /nodes
## GET
This is the base endpoint for fetching nodes.

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
			"number_of_comments": Integer
		},
		...
	]




## POST

### Parameters
* content: String

### Return

#### 200 - OK
	[
		"status": 200
	]