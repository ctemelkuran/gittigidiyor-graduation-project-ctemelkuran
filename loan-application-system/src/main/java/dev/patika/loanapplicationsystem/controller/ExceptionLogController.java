package dev.patika.loanapplicationsystem.controller;

import dev.patika.loanapplicationsystem.entity.ExceptionLogger;
import dev.patika.loanapplicationsystem.service.ExceptionLoggerService;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/exception-logs")
public class ExceptionLogController {
    private final ExceptionLoggerService exceptionLoggerService;

    @GetMapping
    public ResponseEntity<Page<List<ExceptionLogger>>> getAllExceptionsByDate(
            @ApiParam(value = "Exception application query by date", example = "24/09/2021", required = true)
            @RequestParam String exceptionDate,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @PageableDefault(page = 0, size = 10) Pageable pageable){
        return new ResponseEntity<>(this.exceptionLoggerService.getAllExceptionsByDate(exceptionDate, pageNumber, pageSize, pageable), HttpStatus.OK);
    }

    @GetMapping("/by-type")
    public ResponseEntity<Page<List<ExceptionLogger>>> getAllExceptionsByType(
            @ApiParam(value = "Exception application query by type", example = "InvalidIdNumberException", required = true)
            @RequestParam String exceptionType,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize,
            @PageableDefault(page = 0, size = 10) Pageable pageable){
        return new ResponseEntity<>(this.exceptionLoggerService.getAllExceptionsByType(exceptionType, pageNumber, pageSize, pageable), HttpStatus.OK);
    }

}
