package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;

import cc.mrbird.febs.common.core.converter.ExcelStateConverter;
import cc.mrbird.febs.common.core.converter.ExcelTimeConverter;
import cc.mrbird.febs.common.core.converter.KUserLeaveTypeExcelConverter;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
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


    @TableField("position_level")
    @ExcelProperty("岗位职级")
    private String positionLevel;
    /**
     * 职称
     */
    @TableField("position")
    @ExcelProperty("职称")
    private String position;

    /**
     * 部门
     */
    @TableField("dept_id")
    @ExcelProperty("部门id")
    private String deptId;
    /**
     * 部门
     */
    @TableField("dept_ids")
    @ExcelProperty("部门id拼接")
    private String deptIds;
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
     * 性别
     */
    @TableField("sex_type")
    @ExcelProperty("性别")
    private String sexType;
    /**
     * 身份证
     */
    @TableField("cert_no")
    @ExcelProperty("身份证")
    private String certNo;
    /**
     * 电话号码
     */
    @TableField("phone")
    @ExcelProperty("电话号码")
    private String phone;
    /**
     * 出生日期
     */
    @TableField("birth_time")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @ExcelProperty("出生日期")
    private Date birthTime;
    /**
     * 入职时间
     */
    @TableField("hired_date")
    @ExcelProperty("入职时间")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date hiredDate;
    /**
     * 离职时间
     */
    @TableField("last_work_day")
    @ExcelProperty(value = "离职时间")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date lastWorkDay;
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
    @DateTimeFormat("yyyy-MM-dd HH:mm")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date updateTime;


    @TableField("state")
    @ExcelProperty(value = "工号重复多选一" ,converter = ExcelStateConverter.class)
    private Integer state;

}