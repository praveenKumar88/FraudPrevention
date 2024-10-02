	Core service layer for fraud detection system, exposing RESTful APIs that allow external systems (or Test mock data generator) to send transactions and receive fraud risk scores. 
	APIs will allow clients to:
		•	Submit transactions for fraud detection.
		•	Retrieve risk scores and reasons for fraud determination.
		•	Integration with external payment systems like Adyen, PayPal, and more, allowing for cross-platform fraud detection.

	 Swagger Integration: We will use Swagger to automatically generate API documentation. This documentation will cover:
		•	Authentication: How to authenticate requests (e.g., API key, OAuth).
		•	Endpoints: Explanation of available endpoints, their purpose, and required/requested fields.
		•	Examples: Sample payloads and responses to make integration easier for developers.
		•	A Swagger UI interface will be deployed alongside the API, allowing developers to test the APIs directly from the documentation.
