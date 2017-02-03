package org.sanju.ml.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.sanju.ml.RequestBuilder;
import org.sanju.ml.RequestProcessor;
import org.sanju.ml.dto.Payload;

/**
 *
 * @author Sanju Thomas
 *
 */
public class DocumentServiceImpl implements DocumentService{


	@Override
	public Object get(final String uri) throws ClientProtocolException, IOException, URISyntaxException {

		final HttpGet httpGet = RequestBuilder.get(uri);
		return RequestProcessor.process(httpGet).getEntity();
	}

	@Override
	public int save(final Payload<?> payload) throws IOException, URISyntaxException {

		final HttpPut httpPut = RequestBuilder.put(payload);
		return RequestProcessor.process(httpPut).getStatusLine().getStatusCode();
	}

	@Override
	public int delete(final String uri) throws ClientProtocolException, IOException, URISyntaxException {

		final HttpDelete httpDelete = RequestBuilder.delete(uri);
		return RequestProcessor.process(httpDelete).getStatusLine().getStatusCode();
	}

}
