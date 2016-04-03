# /nodes/:id/comments



## GET
Get all comments on `Node` with specified `:id
### Return

#### 200 - OK
	[
		{
			"id": Integer,
			"user": {
				"id": Integer,
				"firstname": String,
				"lastname": String,
				"href": URL
			},
			"content": String
		},
		...
	]





## POST
Post a comment to `Node` with specified `:id

### Parameters
* content: String


### Return

#### 200 - OK

	{
		"status": 200
	}