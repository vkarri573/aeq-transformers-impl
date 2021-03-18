package com.aeq.transformers.impl.app.exception;

import com.aeq.transformers.impl.app.model.ExceptionResponse;
import com.aeq.transformers.impl.app.service.BattleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

/**
 * Global exception handler of the API.
 *
 * If any exception occurs during the request process, that exception is handled here
 * and corresponding response is sent to the consumer.
 */
@ControllerAdvice
public class TransformerGlobalExceptionHandler {
    private Logger LOG = LoggerFactory.getLogger(BattleService.class);

    /**
     * Handles GameInterruptedException which is thrown when there is a battle between
     * special Transformers(Optimus Prime, Predaking)
     *
     * @param exception contains GameInterruptedException details.
     * @param request Contains WebRequest details.
     * @return ExceptionResponse includes appropriate exception details like error message,
     *         http status code etc.
     */
    @ExceptionHandler(GameInterruptedException.class)
    public ResponseEntity<ExceptionResponse> handleGameInterrupted(GameInterruptedException exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    /**
     * Handles TransformerNotFoundException which is thrown when Transformer not found at database.
     *
     * @param exception Contains TransformerNotFoundException details.
     * @param request Contains WebRequest details.
     * @return ExceptionResponse includes appropriate exception details like error message,
     *         http status code etc.
     */
    @ExceptionHandler(TransformerNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleTransformerNotFound(TransformerNotFoundException exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Handles the exceptions thrown when validation fails on API request parameters.
     *
     * @param exception Contains details about the validation failure.
     * @param request Contains WebRequest details.
     * @return ExceptionResponse includes appropriate exception details like error message,
     *         http status code etc.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ExceptionResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, WebRequest request){
        ExceptionResponse errorDetails = new ExceptionResponse(new Date(), "Validation Failed",
                exception.getBindingResult().toString());
        return new ResponseEntity(errorDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all generic exceptions.
     *
     * @param exception Contains details about the exception.
     * @param request Contains WebRequest details.
     * @return ExceptionResponse includes appropriate exception details like error message,
     *         http status code etc.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Prepares exception response.
     *
     * @param exception contains details about the exception.
     * @param request contails WebRequest details.
     * @return ExceptionRespose which includes consolidated exception details.
     */
    private ExceptionResponse prepareExceptionResponse(Exception exception, WebRequest request) {
        LOG.error("Exception Occurred: ", exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
                request.getDescription(false));
        return exceptionResponse;
    }
}
