package com.aeq.transformers.impl.app.exception;

import com.aeq.transformers.impl.app.model.ExceptionResponse;
import com.aeq.transformers.impl.app.service.BattleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@ControllerAdvice
public class TransformerGlobalExceptionHandler {
    private Logger LOG = LoggerFactory.getLogger(BattleService.class);

    @ExceptionHandler(GameInterruptedException.class)
    public ResponseEntity<ExceptionResponse> handleGameInterrupted(GameInterruptedException exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception exception, WebRequest request) {
        ExceptionResponse exceptionResponse = prepareExceptionResponse(exception, request);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ExceptionResponse prepareExceptionResponse(Exception exception, WebRequest request) {
        LOG.error("Exception Occurred: ", exception);
        ExceptionResponse exceptionResponse = new ExceptionResponse(new Date(), exception.getMessage(),
                request.getDescription(false));
        return exceptionResponse;
    }
}
