/*
 * ICQRF NUOVA VERSIONE
 * LEONARDO CYS SPA
 */
package test.controller;

import static java.util.Optional.ofNullable;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ValidationException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TODO: Auto-generated Javadoc
/**
 * The Class GlobalExceptionHandler.
 */
@RestControllerAdvice

/** The Constant log. */
@Slf4j
public class GlobalExceptionHandler {

	/** The message source. */
	@Autowired
	MessageSource messageSource;

 
	/**
	 * Handle validation exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ValidationException.class)
	public ApiCallResponse<String> handleValidationException(HttpServletRequest request, ValidationException ex) {
		log.error("ValidationException {}\n", request.getRequestURI(), ex);
		return new ApiCallResponse<>("Errore di validazione", Arrays.asList(ex.getMessage()));
	}

	/**
	 * Handle missing servlet request parameter exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestParameterException.class)
	public ApiCallResponse<String> handleMissingServletRequestParameterException(HttpServletRequest request,
			MissingServletRequestParameterException ex) {
		log.error("handleMissingServletRequestParameterException {}\n", request.getRequestURI(), ex);
		return new ApiCallResponse<>("Parametro di request mancante", Arrays.asList(ex.getMessage()));
	}

	/**
	 * Handle data integrity violation exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
 

	/**
	 * Handle method argument type mismatch exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ApiCallResponse<Map<String, String>> handleMethodArgumentTypeMismatchException(HttpServletRequest request,
			MethodArgumentTypeMismatchException ex) {
		log.error("handleMethodArgumentTypeMismatchException {}\n", request.getRequestURI(), ex);
		HashMap<String, String> details = new HashMap<>();
		details.put("paramName", ex.getName());
		details.put("paramValue", ofNullable(ex.getValue()).map(Object::toString).orElse(""));
		details.put("errorMessage", ex.getMessage());

		return new ApiCallResponse<>("Tipo di argomento non valido per la richiesta", Arrays.asList(details));
	}

	/**
	 * Handle method argument not valid exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ApiCallResponse<Map<String, String>> handleMethodArgumentNotValidException(HttpServletRequest request,
			MethodArgumentNotValidException ex) {
		log.error("handleMethodArgumentNotValidException {}\n", request.getRequestURI(), ex);
		final StringBuilder message = new StringBuilder("Campi non validi:\n");
		List<Map<String, String>> details = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			Map<String, String> detail = new HashMap<>();
			detail.put("objectName", fieldError.getObjectName());
			detail.put("field", fieldError.getField());
			detail.put("rejectedValue", "" + fieldError.getRejectedValue());
			detail.put("errorMessage",  getMessage(fieldError));
			details.add(detail);
//			message.append(fieldError.getDefaultMessage() + "\n");
			message.append(detail.get("errorMessage") + "\n");
		});

		return new ApiCallResponse<>(message.toString(), details);
	}

	/**
	 * Handle bind exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ApiCallResponse<Map<String, String>> handleBindException(HttpServletRequest request, BindException ex) {
		log.error("handleBindException {}\n", request.getRequestURI(), ex);
		final StringBuilder message = new StringBuilder("Campi non validi:\n");
		List<Map<String, String>> details = new ArrayList<>();
		ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
			Map<String, String> detail = new HashMap<>();
			detail.put("objectName", fieldError.getObjectName());
			detail.put("field", fieldError.getField());
			detail.put("rejectedValue", "" + fieldError.getRejectedValue());
			detail.put("errorMessage", getMessage(fieldError));
			details.add(detail);
//			message.append(fieldError.getDefaultMessage() + "\n");
			message.append(detail.get("errorMessage") + "\n");
		});

		return new ApiCallResponse<>(message.toString(), details);
	}

	/**
	 * Gets the message.
	 *
	 * @param fieldError the field error
	 * @return the message
	 */
	private String getMessage(FieldError fieldError) {

		return messageSource.getMessage(fieldError,null);

	}

	/**
	 * Handle internal server error.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler(Exception.class)
	public ApiCallResponse<String> handleInternalServerError(HttpServletRequest request, Exception ex) {
		log.error("handleInternalServerError {}\n", request.getRequestURI(), ex);
		return new ApiCallResponse<>("Internal server error", Arrays.asList(ex.getMessage()));
	}
	
 
	
 

		
	/**
	 * Handle missing servlet request part exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MissingServletRequestPartException.class)
	public ApiCallResponse<String> handleMissingServletRequestPartException(HttpServletRequest request,
			MissingServletRequestPartException ex) {
		log.error("MissingServletRequestPartException {}\n", request.getRequestURI(), ex);
		return new ApiCallResponse<>("Parte mancante", Arrays.asList(ex.getRequestPartName()));
	}

//	@ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
//	@ExceptionHandler(DataIntegrityViolationException.class)
//	public ApiCallError<String> handleDataIntegrityViolationException(HttpServletRequest request, MissingServletRequestPartException ex) {
//		log.error("DataIntegrityViolationException {}\n", request.getRequestURI(), ex);
//		return new ApiCallError<>("Errore durante il salvataggio.");
//	}

	/**
 * Handle wrong enum value.
 *
 * @param request the request
 * @param ex      the ex
 * @return the api call error
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ApiCallResponse<String> handleWrongEnumValue(HttpServletRequest request, HttpMessageNotReadableException ex) {
		log.error("HttpMessageNotReadableException {}\n", request.getRequestURI(), ex);
		if (Objects.requireNonNull(ex.getMessage()).contains("model.enums")) {
			return new ApiCallResponse<>("Enum non accettato",
					Arrays.asList("Nessuno dei valori accettati per la classe Enum: ["
							+ StringUtils.substringBetween(ex.getMessage(), "[", "]") + "]"));
		} else {
			return new ApiCallResponse<>("Richiesta non valida.", Arrays.asList(ex.getMessage()));
		}
	}

	/**
	 * Handle access denied exception.
	 *
	 * @param request the request
	 * @param ex      the ex
	 * @return the api call error
	 */
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AccessDeniedException.class)
	public ApiCallResponse<String> handleAccessDeniedException(HttpServletRequest request, AccessDeniedException ex) {
		log.error("handleAccessDeniedException {}\n", request.getRequestURI(), ex);
		return new ApiCallResponse<>("Accesso negato", Arrays.asList(ex.getMessage()));
	}

	/**
	 * To string.
	 *
	 * @return the java.lang. string
	 */
	@Data
	
	/**
	 * Instantiates a new api call error.
	 */
	@NoArgsConstructor
	
	/**
	 * Instantiates a new api call error.
	 *
	 * @param message the message
	 * @param details the details
	 */
	@AllArgsConstructor
	public static class ApiCallResponse<T> {

		/** The message. */
		private String message;
		
		/** The details. */
		private List<T> details;
	}
}
