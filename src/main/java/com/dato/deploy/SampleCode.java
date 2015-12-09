import com.dato.deploy.PredictiveServiceClient;
import org.json.simple.JSONObject;
import com.dato.deploy.PredictiveServiceClientResponse;
import com.dato.deploy.PredictiveServiceClientException;
/* 
 * A sample Java main application that connects to a running 
 * GLC predictive services. This example assume an "add" function 
 * already deployed in predictive services. The schema of "add" function 
 * in Python is as follow:
 *
 * def add(a,b):
 *   return a + b
 *
 * Replace "end_point" and "api_key" to the corresponding values in your 
 * predictive services. To run this Java main application using maven:
 *
 * >>> mvn compile 
 * >>> mvn exec:java -Dexec.mainClass="SampleCode" 
 */

public class SampleCode { 
  public static String end_point = "http://localhost:9005";
  public static String api_key = "api_key";

  public static void main(String args[]) {
    PredictiveServiceClient client = new PredictiveServiceClient(end_point,api_key,false);
    System.out.println("schema version:" + client.getSchema());
    JSONObject request = new JSONObject();

    request.put("a", 10);
    request.put("b", 2);

    PredictiveServiceClientResponse response = client.query("add",request);
    if (response.getStatusCode() == 200) {
            // successfully connected
            JSONObject results = response.getResult();
            System.out.println(results.toString());
            System.exit(0); 
    } else {
          throw new PredictiveServiceClientException(
                  "Error connecting to service: response: " +
                  response.getErrorMessage());
    }

  }
}
