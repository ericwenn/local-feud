# /nodes/:id/likes
## GET
Get all likes of `Node` with specified `:id`.
### Parameters
None.

### Return

#### 200 - OK

	[
		{
			"id": Integer,
			"date_added": Date,
			"user": {
				"id": Integer,
				"firstname": String,
				"lastname": String,
				"href": URL
			}
		},
		...
	]





## POST
Like `Node` with specified `:id

### Parameters
None.

### Return

#### 200 - OK

	{
		"status": 200
	}



## DELETE
Remove like on `Node` with specified `:id

### Parameters
None.

### Return

#### 200 - OK

	{
		"status": 200
	}