package org.example.springboot_demo.server;




import lombok.Data;

import java.io.Serializable;

/**
 * Unified response wrapper for backend APIs.
 * @param <T> type of data payload
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // Code: 1 = success, 0 or other values = failure
    private String msg;   // Error message
    private T data;       // Data payload

    public static <T> Result<T> success() {
        Result<T> result = new Result<T>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}

