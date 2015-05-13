Dato Predictive Service Java Client
-----------------------------------

The purpose of the Dato Predictive Service Java Client library is to allow Java applications to easily query Dato Predictive Services.

To use the Dato Predictive Service Java Client library, simply add this dependency to your Maven POM file:
```xml
<dependency>
  <groupId>com.dato</groupId>
  <artifactId>DatoPredictiveServiceClient</artifactId>
  <version>1.0</version>
</dependency>
```

You can also download the artifact: [Maven Search](http://search.maven.org)

Requirements
------------

- Dato Predictive Service, launched by GraphLab-Create >= 1.4 installation

Usage
-----

### Construct Client

To use the Dato Predictive Service Java Client library, first you need to obtain the
following information from a running Dato Predictive Service:
* Predictive Service CNAME or DNS name (endpoint)
* API key from the Predictive Service

Once you have obtained the above information, simply construct the PredictiveServiceClient:
```java
import com.dato.DatoPredictiveServiceCLient.*;

PredictiveServiceClient client = new PredictiveServiceClient("CNAME or DNS name",
                           "API key", true); // enabled SSL certificate verification
``` 

To enable SSL certificate verification for this Predictive Service, set the last parameter
of the PredictiveServiceClient constructor to **true**. However, if you Predictive Service
is launched with a self-signed certificate or without certificate, please set to
**false** the last parameter of the constructor.

The PredictiveServiceClient can also be constructed from using a Predictive Service
[client configuration file](https://dato.com/products/create/docs/generated/graphlab.deploy.PredictiveService.save_client_config.html).
```java
PredictiveServiceClient client = new PredictiveServiceClient("path to the config file");
```

### Setup Request

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

### Query

To query your deployed model on the Predictive Serivce, simply do the following:
```java
PredictiveServiceClientResponse response = client.query("you model name here", request);
```

### Results

To obtain the query results from the Predictive Service, simply use the returned response:
```java
import org.json.simple.JSONObject;
JSONObject results = response.getResult();
```

``getResult()`` a JSONObject that contains the information passed back from the PredictiveService.

### Set timeout

To change the request timeout when querying the Predictive Service, use the following:
```java
client.setQueryTimeout(500); // 500ms
```

### Send feedback

To submit feedbacks data corresponding to a particular query result, we will need to construct
a JSONObject that contains a query result's UUID, and any other attributes associated with this
query. 

```java
JSONObject feedback_request = new JSONObject();
// must use "request_id" as the key to UUID
feedback_request.put("request_id", "a query result's UUID");
feedback_request.put("searched_terms", "accommodations");
```

Once we have constructed a feedback JSONObject, we can use the feedback method to send
this feedback to the Predictive Service for that queried model.
```java
PredictiveServiceClientResponse response = client.feedback("your model name", 
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
