package org.sanju.ml;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

/**
 *
 * @author Sanju Thomas
 *
 */
public class RequestProcessor {

	public static HttpResponse process(final HttpRequestBase request) throws ClientProtocolException, IOException {

		final CloseableHttpClient httpClient = HttpClients.createDefault();
		final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
		credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(
				MLConfiguration.getUsername(), MLConfiguration.getPassword()));
		final HttpClientContext localContext = HttpClientContext.create();
		localContext.setCredentialsProvider(credentialsProvider);
		return httpClient.execute(request, localContext);
	}
}
