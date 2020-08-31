package cc.mrbird.febs.auth.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 *  Entity
 *
 * @author MrBird
 * @date 2020-04-24 16:36:41
 */
@Data
@TableName("k_user")
public class KUser {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.INPUT)
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

    @TableField("position")
    private String position;

    @TableField("dept_name")
    private String deptName;


    /**
     * 是否离职 0 在职  1离职
     */
    @TableField("leave_type")
    private Integer leaveType;

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
     * 离职原因
     */
    @TableField("leave_reason_type")
    private String leaveReasonType;
}
