package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;
import java.math.BigDecimal;

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
 * @date 2020-12-01 18:07:41
 */
@Data
@TableName("s_oa_award")
public class SOaAward {

    /**
     *
     */
    @TableId(value = "id", type = IdType.INPUT)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    /**
     * oA表单id
     */
    @TableField("unit_id")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long unitId;

    /**
     * oA表单id
     */
    @TableField("finishedflag")
    private Integer finishedflag;
    /**
     *工厂
     */
    @TableField("factory")
    private String factory;

    /**
     *类型
     */
    @TableField("award_type")
    private String awardType;

    /**
     * 时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date workDate;
    /**
     * 建立者
     */
    @TableField("from_user")
    private String fromUser;

    /**
     * 建立者工号
     */
    @TableField("from_jobnumber")
    private String fromJobnumber;

    /**
     *  奖励人
     */
    @TableField("to_add_user")
    private String toAddUser;

    /**
     *奖励人工号
     */
    @TableField("to_add_jobnumber")
    private String toAddJobnumber;

    /**
     *  奖励金额
     */
    @TableField("money_add")
    private BigDecimal moneyAdd;
    /**
     *
     */
    @TableField("money_add_text")
    private String moneyAddText;

    /**
     * 罚款人
     */
    @TableField("to_sub_user")
    private String toSubUser;

    /**
     *罚款人工号
     */
    @TableField("to_sub_jobnumber")
    private String toSubJobnumber;

    /**
     *罚款金额
     */
    @TableField("money_sub")
    private BigDecimal moneySub;
    /**
     *
     */
    @TableField("money_sub_text")
    private String moneySubText;

    /**
     *异常描述
     */
    @TableField("error_detail")
    private String errorDetail;

    /**
     *异常处理方案
     */
    @TableField("error_solve_idea")
    private String errorSolveIdea;

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
     * 钉钉对应的人名字
     */
    @TableField(exist = false)
    private String addName;
    @TableField(exist = false)
    private String subName;

    //表格合并字段
    @TableField(exist = false)
    private Integer spanNum;
}