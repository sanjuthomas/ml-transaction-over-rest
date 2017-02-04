package org.sanju.ml.service;

import org.apache.http.HttpEntity;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.sanju.ml.dto.Account;
import org.sanju.ml.dto.Client;
import org.sanju.ml.dto.Payload;
import org.sanju.ml.dto.QuoteRequest;
import org.sanju.ml.transaction.rest.MLTransactionManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Sanju Thomas
 *
 */
public class TestTransactionalDocumentService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	private TransactionalDocumentSevice tranactionalDocumentService;
	private DocumentService documentService;
	private Payload<QuoteRequest> payload;

	@Before
	public void setUp(){
		final Account account = new Account("A1");
		final Client client = new Client("C1", account);
		this.documentService = new DocumentServiceImpl();
		this.tranactionalDocumentService = new TransactionalDocumentServiceImpl();
		this.payload = new Payload<>(new QuoteRequest("Q1", "APPL", 100, client));
	}

	@After
	public void tearDown() throws Exception{
		Assert.assertEquals(204, this.documentService.delete("/C1/A1/Q1.json"));
	}

	@Test
	public void shouldFindAfterCommit() throws Exception{
		
		final String transactionId = MLTransactionManager.begin();
		int code = this.tranactionalDocumentService.save(this.payload, transactionId);
		Assert.assertEquals(201, code);
		HttpEntity entity = (HttpEntity) this.documentService.get("/C1/A1/Q1.json");
		JsonNode jsonNode = MAPPER.readTree(entity.getContent());
		Assert.assertEquals(404, jsonNode.get("errorResponse").get("statusCode").intValue());
		MLTransactionManager.commit(transactionId);
		
		entity = (HttpEntity) this.documentService.get("/C1/A1/Q1.json");
		jsonNode = MAPPER.readTree(entity.getContent());
		Assert.assertEquals("Q1", jsonNode.get("id").textValue());
	}
	
	@Test
	public void shouldRollback() throws Exception{
		
		final String transactionId = MLTransactionManager.begin();
		int code = this.tranactionalDocumentService.save(this.payload, transactionId);
		Assert.assertEquals(201, code);
		HttpEntity entity = (HttpEntity) this.documentService.get("/C1/A1/Q1.json");
		JsonNode jsonNode = MAPPER.readTree(entity.getContent());
		Assert.assertEquals(404, jsonNode.get("errorResponse").get("statusCode").intValue());
		MLTransactionManager.rollback(transactionId);
		
		entity = (HttpEntity) this.documentService.get("/C1/A1/Q1.json");
		jsonNode = MAPPER.readTree(entity.getContent());
		Assert.assertEquals(404, jsonNode.get("errorResponse").get("statusCode").intValue());
	}

}
