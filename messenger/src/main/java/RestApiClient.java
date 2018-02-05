import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.newbieApps.messenger.model.Message;

public class RestApiClient {

	public static void main(String[] args) {
		Client client= ClientBuilder.newClient();
		
		String response=client.target("http://localhost:8085/messenger/webapi/messages").request().get(String.class);
		System.out.println(response);
		
		/*Message response=client.target("http://localhost:8085/messenger/webapi/messages/1").request().get(Message.class);
		//Message message=(Message) response.getEntity();
		System.out.println(response.getMessage());*/
		
		/*Message message = new Message(4,"New messsage from restapi client.","Pranav");
		WebTarget target=client.target("http://localhost:8085/messenger/webapi");
		WebTarget messagesTarget=target.path("/messages");
		Response resp=messagesTarget.request().post(Entity.json(message));
		System.out.println(resp);*/
		
		try {

			URL url = new URL("http://localhost:8085/messenger/webapi/messages");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader(
				(conn.getInputStream())));

			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}
			conn.disconnect();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	}

}
