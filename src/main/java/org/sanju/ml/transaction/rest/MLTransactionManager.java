package org.sanju.ml.transaction.rest;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.sanju.ml.MLConfiguration;
import org.sanju.ml.RequestProcessor;
import org.sanju.ml.enums.MLEndpoints;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Sanju Thomas
 *
 */
public class MLTransactionManager {

	public enum TransactionStatus{

		ACTIVE("active"),
		ENDED("ended");

		private final String status;

		TransactionStatus(final String status){
			this.status = status;
		}

		public String getStatus() {
			return this.status;
		}
	}

	private static final ObjectMapper MAPPER = new ObjectMapper();

	/**
	 *
	 * @return
	 * @throws Exception
	 */
	public static String begin() throws Exception{

		final HttpPost request = buildRequest(null, new ArrayList<>());
		request.addHeader(new BasicHeader("Accept", ContentType.APPLICATION_JSON.getMimeType()));
		final HttpResponse response = RequestProcessor.process(request);
		final HttpEntity entity = response.getEntity();
		if(null != entity){
			final JsonNode transactionResult = MAPPER.readTree(entity.getContent());
			return transactionResult.get("transaction-status").get("transaction-id").textValue();
		}else{
			throw new Exception("Failed to create the transaction. Please check MarkLogic server error log for more details.");
		}
	}

	private static HttpPost buildRequest(final String path, final List<NameValuePair> nameValuePairs) throws URISyntaxException {

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.TRANSACTION);
		uriBuilder.addParameters(nameValuePairs);
		if(null != path){
			uriBuilder.setPath(uriBuilder.getPath()+ "/" + path);
		}
		final HttpPost request = new HttpPost(uriBuilder.build());
		final StringEntity params = new StringEntity("", "UTF-8");
		request.setEntity(params);
		return request;
	}

	/**
	 *
	 * @param transactionId
	 * @throws Exception
	 */
	public static void commit(final String transactionId) throws Exception{

		final List<NameValuePair> params = buildParams("commit");
		final HttpPost request = buildRequest(transactionId, params);
		final HttpResponse response = RequestProcessor.process(request);
		if(204 != response.getStatusLine().getStatusCode()){
			throw new Exception("Failed to commit the transcation. Please check MarkLogic server error log for more details.");
		}
	}

	public static TransactionStatus status(final String transactionId) throws ClientProtocolException, IOException, URISyntaxException{

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.TRANSACTION);
		uriBuilder.setPath(uriBuilder.getPath() + "/" + transactionId);
		final HttpResponse response = RequestProcessor.process(new HttpGet(uriBuilder.build()));
		if(200 == response.getStatusLine().getStatusCode()){
			return TransactionStatus.ACTIVE;
		}
		return TransactionStatus.ENDED;
	}

	private static List<NameValuePair> buildParams(final String value) {

		final List<NameValuePair> params = new ArrayList<>();
		final NameValuePair result = new BasicNameValuePair("result", value);
		params.add(result);
		return params;
	}

	/**
	 *
	 * @param transactionId
	 * @throws Exception
	 */
	public static void rollback(final String transactionId) throws Exception{

		final List<NameValuePair> params = buildParams("rollback");
		final HttpPost request = buildRequest(transactionId, params);
		final HttpResponse response = RequestProcessor.process(request);
		if(204 != response.getStatusLine().getStatusCode()){
			throw new Exception("Failed to rollback the transcation. Please check MarkLogic server error log for more details.");
		}
	}

}
