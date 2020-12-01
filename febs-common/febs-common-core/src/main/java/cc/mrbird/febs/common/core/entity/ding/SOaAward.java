package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;
import java.math.BigDecimal;
import lombok.Data;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

/**
*  Entity
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
    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    /**
     * 
     */
    @TableField("factory")
    private String factory;

    /**
     * 
     */
    @TableField("award_type")
    private String awardType;


    private Date workDate;
    /**
     * 
     */
    @TableField("from_user")
    private String fromUser;

    /**
     * 
     */
    @TableField("from_jobnumber")
    private String fromJobnumber;

    /**
     * 
     */
    @TableField("to_add_user")
    private String toAddUser;

    /**
     * 
     */
    @TableField("to_add_jobnumber")
    private String toAddJobnumber;

    /**
     * 
     */
    @TableField("money_add")
    private BigDecimal moneyAdd;
    /**
     * 
     */
    @TableField("money_add_text")
    private String moneyAddText;

    /**
     * 
     */
    @TableField("to_sub_user")
    private String toSubUser;

    /**
     * 
     */
    @TableField("to_sub_jobnumber")
    private String toSubJobnumber;

    /**
     * 
     */
    @TableField("money_sub")
    private BigDecimal moneySub;
    /**
     * 
     */
    @TableField("money_sub_text")
    private String moneySubText;

    /**
     * 
     */
    @TableField("error_detail")
    private String errorDetail;

    /**
     * 
     */
    @TableField("error_solve_idea")
    private String errorSolveIdea;

    /**
     * 
     */
    @TableField("is_update")
    private Byte isUpdate;

    /**
     * 
     */
    @TableField("create_time")
    private Date createTime;

}