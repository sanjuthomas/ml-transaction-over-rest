package org.sanju.ml.service;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.client.ClientProtocolException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sanju.ml.dto.Account;
import org.sanju.ml.dto.Client;
import org.sanju.ml.dto.Payload;
import org.sanju.ml.dto.QuoteRequest;


/**
 *
 * @author Sanju Thomas
 *
 */
public class TestDocumentService {

	private DocumentService documentService;
	private Payload<QuoteRequest> payload;

	@Before
	public void setUp(){
		final Account account = new Account("A1");
		final Client client = new Client("C1", account);
		this.documentService = new DocumentServiceImpl();
		this.payload = new Payload<>(new QuoteRequest("Q1", "APPL", 100, client));
	}

	@After
	public void tearDown() throws ClientProtocolException, IOException, URISyntaxException{
		Assert.assertEquals(204, this.documentService.delete("/C1/A1/Q1.json"));
	}

	@Test
	public void shouldSave() throws IOException, URISyntaxException{
		Assert.assertEquals(201, this.documentService.save(this.payload));
	}
	
	@Test
	public void shouldFind() throws ClientProtocolException, IOException, URISyntaxException{
		this.documentService.save(this.payload);
		Assert.assertNotNull(this.documentService.get("/C1/A1/Q1.json"));
	}
}
