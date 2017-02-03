package org.sanju.ml.service;

import org.sanju.ml.dto.Payload;

/**
 * 
 * @author Sanju Thomas
 *
 */
public interface TransactionalDocumentSevice {

	Object get(String uri, final String transactionId) throws Exception;
	int save(Payload<?> payload, final String transactionId) throws Exception;
	int delete(String uri, final String transactionId) throws Exception;

}
