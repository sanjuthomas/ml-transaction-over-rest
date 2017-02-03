package org.sanju.ml.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.sanju.ml.dto.Payload;

/**
 *
 * @author Sanju Thomas
 *
 */
public interface DocumentService {

	Object get(String uri) throws ClientProtocolException, IOException, URISyntaxException;
	int save(Payload<?> payload) throws IOException, URISyntaxException;
	int delete(String uri) throws ClientProtocolException, IOException, URISyntaxException;

}
