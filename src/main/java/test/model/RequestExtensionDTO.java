package test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data

/**
 * Instantiates a new template documento DTO.
 */

/**
 * Instantiates a new template documento DTO.
 */
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties("code")
public class RequestExtensionDTO extends RequestDTO{
 
	public RequestExtensionDTO(){
		super();
	}

}
