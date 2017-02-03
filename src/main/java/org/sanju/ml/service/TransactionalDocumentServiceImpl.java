package org.sanju.ml.service;

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
public class TransactionalDocumentServiceImpl implements TransactionalDocumentSevice {

	@Override
	public Object get(final String uri, final String transactionId) throws Exception{

		final HttpGet httpGet = RequestBuilder.get(uri, transactionId);
		return RequestProcessor.process(httpGet).getEntity();
	}

	@Override
	public int save(final Payload<?> payload, final String transactionId) throws Exception {

		final HttpPut httpPut = RequestBuilder.put(payload, transactionId);
		return RequestProcessor.process(httpPut).getStatusLine().getStatusCode();
	}

	@Override
	public int delete(final String uri, final String transactionId) throws Exception {

		final HttpDelete httpDelete = RequestBuilder.delete(uri, transactionId);
		return RequestProcessor.process(httpDelete).getStatusLine().getStatusCode();
	}
	
	
}
