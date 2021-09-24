package dev.patika.loanapplicationsystem.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.NumberFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDTO {

    @ApiModelProperty(hidden = true)
    private long id;

    @ApiModelProperty(example = "Quentin Tarantino")
    @NotBlank(message = "Customer name is mandatory")
    private String fullName;

    @ApiModelProperty(example = "36071499368")
    @NotNull(message = "National ID is mandatory")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    //@Pattern(regexp = "^\\d{11}$", message = "Enter a valid National ID!")
    private long idNumber;

    @ApiModelProperty(example = "3000")
    @NotNull(message = "Salary is mandatory")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    private double salary;

    @ApiModelProperty(example = "5381234567")
    //@Pattern(regexp ="^(5)[0-9]{9}$", message = "Enter a valid number!")
    private String phoneNumber;
}
