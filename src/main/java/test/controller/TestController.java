package test.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import test.model.RequestDTO;
import test.model.RequestExtensionDTO;
 
@RestController
@RequestMapping("/test")
public class TestController {
	
	@PostMapping(value="/postFileChildDTO" , consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<?> postFileChildDTO( @RequestPart(value = "body")  @Parameter(schema = @Schema(type = "string", format = "binary"))
			@Valid RequestExtensionDTO template,
			@RequestPart(value = "file",required = true) MultipartFile file)
	{
		return ResponseEntity.ok().build();
	}
	
	@PostMapping(value="/simplePostChildDTO" )
	public ResponseEntity<?> simplePostChildDTO( @RequestBody
			@Valid RequestExtensionDTO template)
	{
		return ResponseEntity.ok().build();
	}
}

