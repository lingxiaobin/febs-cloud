package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
*  Entity
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
    private String id;

    /**
     * 
     */
    @TableField("name")
    private String name;

    /**
     * 
     */
    @TableField("jobnumber")
    private String jobnumber;

    /**
     * 职称
     */
    @TableField("position")
    private String position;

    /**
     * 部门
     */
    @TableField("dept_name")
    private String deptName;

    /**
     * 是否离职 0 在职  1离职
     */
    @TableField("leave_type")
    private Integer leaveType;

    /**
     * 员工类型
     */
    @TableField("user_type")
    private String userType;

    /**
     * 工资核算地
     */
    @TableField("pay_place")
    private String payPlace;

    /**
     * 劳务派遣公司
     */
    @TableField("dispatch_factory")
    private String dispatchFactory;

    /**
     * 工资计算方式
     */
    @TableField("pay_compute_type")
    private String payComputeType;

    /**
     * 离职原因备注
     */
    @TableField("leave_reason_type")
    private String leaveReasonType;

    /**
     * 
     */
    @TableField("update_time")
    private Date updateTime;

}