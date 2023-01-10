package com.sv.system.annotation;

import com.sv.system.enums.BusinessType;
import com.sv.system.enums.OperatorType;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Log {
    /**
     * Model name
     */
    public String title() default "";

    /**
     * Function name, action name
     */
    public BusinessType businessType() default BusinessType.OTHER;

    /**
     * Operator type
     */
    public OperatorType operatorType() default OperatorType.MANAGE;

    /**
     * Save request data
     */
    public boolean isSaveRequestData() default true;

    /**
     * Save response data
     */
    public boolean isSaveResponseData() default true;
}
