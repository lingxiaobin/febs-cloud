package cc.mrbird.febs.server.ding.controller.req;

import cc.mrbird.febs.common.core.converter.KUserLeaveTypeExcelConverter;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Data
public class KUserReq extends  BaseReq{

    /**
     * 姓名
     */
    private String name;

    /**
     * 离职原因备注
     */
    private String leaveReasonType;

    private String[] userTypes;

    private String[] leaveTypes;

    private String[] dispatchFactorys;

    private boolean isAdmin;

    private String createTimeFrom;

    private String createTimeTo;

    private String inOutTimeFrom;

    private String inOutTimeTo;

    private boolean isOtherExcel= false;
}