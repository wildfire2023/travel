package com.waner.primary.web.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Monster
 * @since 0.0.1-SNAPSHOT
 */
@AllArgsConstructor
@Data
public class TableResult<T> {
    /**
     * 返回代码，200：成功
     */
    private int code;
    /**
     * 服务异常返回响应
     */
    private String msg;
    /**
     * 数据长度
     */
    private int count;
    /**
     * 数据
     */
    private T data;
}
