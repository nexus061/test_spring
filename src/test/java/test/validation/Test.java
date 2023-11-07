package test.validation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import test.controller.GlobalExceptionHandler.ApiCallResponse;
import test.model.RequestExtensionDTO;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestClass {
	@LocalServerPort
	private int port;

	@Autowired
	TestRestTemplate testRestTemplate;

	@Test
	public void someTest() {

		RequestExtensionDTO requestExtensionDTO = new RequestExtensionDTO();
		 

		ResponseEntity<ApiCallResponse> response = testRestTemplate.postForEntity(
				"http://localhost:" + port + "/test/simplePostChildDTO", requestExtensionDTO, ApiCallResponse.class);

		
		assertEquals(response.getStatusCode(), HttpStatusCode.valueOf(400));
		

		assertEquals(response.getBody().getDetails().size(), 3);

	}
}
