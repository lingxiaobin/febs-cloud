package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;

import cc.mrbird.febs.common.core.converter.KUserLeaveTypeExcelConverter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-09-07 11:15:39
 */
@Data
@TableName("k_user")
public class KUser {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ExcelProperty("userId")
    private String id;

    /**
     * 姓名
     */
    @TableField("name")
    @ExcelProperty("姓名")
    private String name;

    /**
     * 工号
     */
    @TableField("jobnumber")
    @ExcelProperty("工号")
    private String jobnumber;

    /**
     * 职称
     */
    @TableField("position")
    @ExcelProperty("职称")
    private String position;

    /**
     * 部门
     */
    @TableField("dept_name")
    @ExcelProperty("部门")
    private String deptName;

    /**
     * 是否离职 0 在职  1离职
     */
    @TableField("leave_type")
    @ExcelProperty(value = "是否离职", converter = KUserLeaveTypeExcelConverter.class)
    private Integer leaveType;

    /**
     * 员工类型
     */
    @TableField("user_type")
    @ExcelProperty("员工类型")
    private String userType;

    /**
     * 工资核算地
     */
    @TableField("pay_place")
    @ExcelProperty("工资核算地")
    private String payPlace;

    /**
     * 劳务派遣公司
     */
    @TableField("dispatch_factory")
    @ExcelProperty("劳务派遣公司")
    private String dispatchFactory;

    /**
     * 工资计算方式
     */
    @TableField("pay_compute_type")
    @ExcelProperty("工资计算方式")
    private String payComputeType;

    /**
     * 离职原因备注
     */
    @TableField("leave_reason_type")
    @ExcelProperty("离职原因备注")
    private String leaveReasonType;

    /**
     * 修改时间
     */
    @TableField("update_time")
    @ExcelProperty("修改时间")
    private Date updateTime;


    /**
     *
     */
    @TableField(exist = false)
    @ExcelIgnore
    private String[] payPlaces;

    /**
     *
     */
    @TableField(exist = false)
    @ExcelIgnore
    private String[] userTypes;

    /**
     *
     */
    @TableField(exist = false)
    @ExcelIgnore
    private String[] leaveTypes;

}