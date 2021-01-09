package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
*  Entity
*
* @author MrBird
* @date 2021-01-04 08:39:20
*/
@Data
@TableName("s_eat")
public class SEat {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    @TableField("work_date")
    private Date workDate;


    /**
     * 部门
     */
    @TableField("dept_name")
    @ExcelProperty("部门")
    private String deptName;
    /**
     * 工资计算方式
     */
    @TableField("pay_compute_type")
    @ExcelProperty("工资计算方式")
    private String payComputeType;
    /**
     * 职称
     */
    @TableField("position")
    @ExcelProperty("职称")
    private String position;


    /**
     * 工号
     */
    @TableField("jobnumber")
    private String jobnumber;

    /**
     * 姓名
     */
    @TableField("name")
    private String name;

    /**
     * 出勤天数
     */
    @TableField("day_num")
    private BigDecimal dayNum;
    /**
     * 消费金额
     */
    @TableField("use_num")
    private BigDecimal useNum;
    /**
     * 标准补助
     */
    @TableField("rule_num")
    private BigDecimal ruleNum;
    /**
     *  最低消费
     */
    @TableField("min_num")
    private BigDecimal minNum;
    /**
     *  补扣金额
     */
    @TableField("num")
    private BigDecimal num;

    /**
     *
     */
    @TableField("is_update")
    private Integer isUpdate;
    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

    /**
     * 
     */
    @TableField("update_time")
    private Date updateTime;

}