package dev.patika.loanapplicationsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CustomerDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty(example = "Quentin Tarantino")
    @NotBlank(message = "Customer name is mandatory")
    private String fullName;

    @ApiModelProperty(example = "11111111111")
    @NotNull(message = "National ID is mandatory")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private long idNumber;

    @ApiModelProperty(example = "3000")
    @NotNull(message = "Salary is mandatory")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private double salary;

    @ApiModelProperty(example = "05381234567")
    private String phoneNumber;
}
