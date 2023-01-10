package com.sv.system.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SvException extends RuntimeException {

    private Integer code;
    private String msg;

}
