package cc.mrbird.febs.common.core.entity.ding;

import java.math.BigDecimal;
import java.util.Date;

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
 * @date 2020-11-06 08:37:21
 */
@Data
@TableName("p_class_detail")
public class PClassDetail {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     *
     */
    @TableField("class_id")
    private Integer classId;

    /**
     *
     */
    @TableField("user_id")
    private String userId;


    @TableField("jobnumber")
    private String jobnumber;


    @TableField("fraction_state")
    private String fractionState;
    /**
     * 考试分数
     */
    @TableField("fraction")
    private BigDecimal fraction;

    /**
     * 考试时间
     */
    @TableField("fraction_time")
    private Date fractionTime;
    /**
     * 响应状态
     */
    @TableField("sign_in_state")
    private String signInState;

    /**
     * 签到时间
     */
    @TableField("sign_in_time")
    private Date signInTime;

    @TableField("is_sign_in")
    private Boolean isSignIn;

    @TableField("is_franction")
    private Boolean isFranction;


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