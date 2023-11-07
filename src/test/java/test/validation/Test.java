package test.validation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import test.controller.GlobalExceptionHandler.ApiCallError;
import test.model.RequestExtensionDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestClass {
	@LocalServerPort
	private int port;
	
	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void someTest() {
		
		 
		ResponseEntity<ApiCallError> response = testRestTemplate.postForEntity("http://localhost:"+port+"/test/simplePostChildDTO", new RequestExtensionDTO(), ApiCallError.class);
	    
		assertThat(response.getStatusCode().is4xxClientError());
		
	    assertThat(response.getBody().getDetails().size()==3);
		
	}
}
