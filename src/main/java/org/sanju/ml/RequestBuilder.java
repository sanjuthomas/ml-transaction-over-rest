package org.sanju.ml;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.sanju.ml.dto.Payload;
import org.sanju.ml.enums.MLEndpoints;

/**
 *
 * @author Sanju Thomas
 *
 */
public class RequestBuilder {

	/**
	 *
	 * @param payload
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpPut put(final Payload<?> payload) throws URISyntaxException {

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.DOCUMENT);
		return buildPut(payload, uriBuilder);
	}
	
	/**
	 *
	 * @param payload
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpPut put(final Payload<?> payload, final String transactionId) throws URISyntaxException {

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.DOCUMENT, transactionId);
		return buildPut(payload, uriBuilder);
	}
	

	/**
	 * 
	 * @param payload
	 * @param uriBuilder
	 * @return
	 * @throws URISyntaxException
	 */
	private static HttpPut buildPut(final Payload<?> payload, final URIBuilder uriBuilder) throws URISyntaxException {
		
		uriBuilder.addParameter("uri", payload.getUri());
		final HttpPut request = new HttpPut(uriBuilder.build());
		final StringEntity params = new StringEntity(payload.json(), "UTF-8");
		params.setContentType(payload.getContentType().toString());
		request.setEntity(params);

		return request;
	}

	/**
	 *
	 * @param uri
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpGet get(final String uri) throws URISyntaxException{

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.DOCUMENT);
		return buildGet(uri, uriBuilder);
	}

	/**
	 * 
	 * @param uri
	 * @param uriBuilder
	 * @return
	 * @throws URISyntaxException
	 */
	private static HttpGet buildGet(final String uri,final URIBuilder uriBuilder) throws URISyntaxException {
		
		uriBuilder.addParameter("uri", uri);
		return  new HttpGet(uriBuilder.build());
	}
	
	/**
	 *
	 * @param uri
	 * @param transcationId
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpGet get(final String uri, final String transcationId) throws URISyntaxException{

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.DOCUMENT, transcationId);
		return buildGet(uri, uriBuilder);
	}


	/**
	 *
	 * @param uri
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpDelete delete(final String uri) throws URISyntaxException{

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.DOCUMENT);
		return buildDelete(uri, uriBuilder);
	}

	/**
	 * 
	 * @param uri
	 * @param uriBuilder
	 * @return
	 * @throws URISyntaxException
	 */
	private static HttpDelete buildDelete(final String uri, final URIBuilder uriBuilder) throws URISyntaxException {
		
		uriBuilder.addParameter("uri", uri);
		return new HttpDelete(uriBuilder.build());
	}
	
	/**
	 *
	 * @param uri
	 * @param transcationId
	 * @return
	 * @throws URISyntaxException
	 */
	public static HttpDelete delete(final String uri, final String transcationId) throws URISyntaxException{

		final URIBuilder uriBuilder = MLConfiguration.getURIBuilder(MLEndpoints.DOCUMENT);
		return buildDelete(uri, uriBuilder);
	}
}
