# /nodes/:id
## GET
Get `Node` specified by `:id`.

### Parameters
None.



### Return

#### 200 - OK

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
		"date": Date,
		"comments": [
			{
				"id": Integer,
				"user": {
					"id": Integer,
					"firstname": String,
					"lastname": String,
					"href": String
				},
				"content": String,
				"date_posted": Date
			},
			...
		]
	}


