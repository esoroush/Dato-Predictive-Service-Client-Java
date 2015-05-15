Dato Predictive Service Java Client
-----------------------------------

The purpose of the Dato Predictive Service Java Client library is to allow Java applications to easily query Dato Predictive Services.

To use the Dato Predictive Service Java Client library, simply add this dependency to your Maven POM file:
```xml
<dependency>
  <groupId>com.dato</groupId>
  <artifactId>predictive-service-client</artifactId>
  <version>1.0</version>
</dependency>
```

You can also download the artifact: [Maven Search](http://search.maven.org)

Requirements
------------

- Dato Predictive Service, launched by GraphLab-Create >= 1.4 installation

Usage
-----

#### Construct Client

To use the Dato Predictive Service Java Client library, first you need to obtain the
following information from a running Dato Predictive Service:
* Predictive Service CNAME or DNS name (endpoint)
* API key from the Predictive Service

Once you have obtained the above information, simply construct the PredictiveServiceClient:
```java
import com.dato.deploy.PredictiveServiceClient;

PredictiveServiceClient client = new PredictiveServiceClient("CNAME or DNS name",
                           "API key", true); // enabled SSL certificate verification
``` 

To enable SSL certificate verification for this Predictive Service, set the last parameter
of the PredictiveServiceClient constructor to **true**. However, if you Predictive Service
is launched with a self-signed certificate or without certificate, please set to
**false** the last parameter of the constructor.

The PredictiveServiceClient can also be constructed by using a Predictive Service
[client configuration file](https://dato.com/products/create/docs/generated/graphlab.deploy.PredictiveService.save_client_config.html).
```java
PredictiveServiceClient client = new PredictiveServiceClient("path to config file");
```

#### Setup Request

To query your Predictive Service with this client, you need to create a simple JSON object. 
We use [json-simple](https://code.google.com/p/json-simple/) to perform JSON operations.

First add the method name that will be used to query your deployed model.
```java
import org.json.simple.JSONObject;

JSONObject request = new JSONObject();
request.put("method", "recommend");
```

Then construct the query data to your model and add it to the request JSONObject.

For example, 
```java
JSONObject data = new JSONObject();
data.put("users", new ArrayList<String>(Arrays.asList("new", "test")));

// add the data object to reqest
request.put("data", data);
```

The request needs to be JSON serializable in order to be used to query the Predictive Service.
Therefore, make sure all the objects that you add to the request are all JSON serializable.

**Notes**

- Different models could support different query methods (recommend, predict, query, etc.)
  and different syntax and format for **data**. You will need to know the
  supported methods and query data format before querying the model.

#### Set timeout

To change the request timeout when querying the Predictive Service, use the following:
```java
client.setQueryTimeout(500); // 500ms
```

The default timeout is 10 seconds.

#### Query

To query your deployed model on the Predictive Serivce, simply do the following:
```java
import com.dato.deploy.PredictiveServiceClientResponse;

PredictiveServiceClientResponse response = client.query("you model name here",
                                                        request);
```

#### Results

To obtain the query results from the Predictive Service, simply use the returned response:
```java
import org.json.simple.JSONObject;
JSONObject results = response.getResult();
```

``getResult()`` returns a JSONObject that contains the query results of your model
from the PredictiveService. 

#### Send feedback

Once you get the query result, you can submit feedback data corresponding to this query
back to the Predictive Service. This feedback data can be used for evaluating your
current model and training future models.

To submit feedback data corresponding to a particular query, you will need the UUID
of the query. The UUID can be easily obtained from the query result.

```java
String uuid = results.get("uuid");
```

For the feedback data, you can use any attributes or value pairs that you like.
However, it must be contained within a JSONObject and must be JSON serializable.

Example: 
```java
JSONObject feedback_request = new JSONObject();
feedback_request.put("searched_terms", "accommodations");
feedback_request.put("num_of_clicks", 3);
```

With that JSONObject constructed above, we can send this JSONObject to the Predictive
Service to associate this feedback with a particular query.
```java
PredictiveServiceClientResponse response = client.feedback(uuid,
                                                           feedback_request);
```

More Info
---------

For more information about the Dato Predictive Service, please read
the [API docs](https://dato.com/products/create/docs/generated/graphlab.deploy.PredictiveService.html)
and [userguide](https://dato.com/learn/userguide/deployment/pred-getting-started.html).

License
-------

The Dato Predictive Service Client is provided under the 3-clause BSD [license](LICENSE).
