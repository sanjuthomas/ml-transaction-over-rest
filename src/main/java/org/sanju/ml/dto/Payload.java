package org.sanju.ml.dto;

import org.apache.http.entity.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 *
 * @author Sanju Thomas
 *
 */
public class Payload<T>{

	private static final Logger logger = LoggerFactory.getLogger(Payload.class);
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private T entity;
	private final ContentType DEFAULT_CONTENT_TYPE = ContentType.APPLICATION_JSON;

	public Payload(final T t){
		this.entity = t;
	}

	public String getUri(){
		return this.entity.toString();
	}

	public T getEntity() {
		return this.entity;
	}

	public void setEntity(final T entity) {
		this.entity = entity;
	}

	public ContentType getContentType() {
		return this.DEFAULT_CONTENT_TYPE;
	}

	public String json(){
		try {
			return MAPPER.writeValueAsString(this.entity);
		} catch (final JsonProcessingException e) {
			logger.error("JSON conversion failed", e);
		}
		return null;
	}

}