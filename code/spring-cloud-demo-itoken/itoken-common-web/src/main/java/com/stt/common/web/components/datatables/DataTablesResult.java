package com.stt.common.web.components.datatables;

import com.stt.itoken.common.dto.BaseResult;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

/**
 * Bootstrap Datatables 结果集
 * <p>Title: DatatablesResult</p>
 * <p>Description: </p>
 *
 * @author Lusifer
 * @version 1.0.0
 * @date 2018/8/12 13:33
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class DataTablesResult extends BaseResult implements Serializable {
    private int draw;
    private long recordsTotal;
    private long recordsFiltered;
    private String error;
}
