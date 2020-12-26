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
* @date 2020-12-26 14:58:08
*/
@Data
@TableName("s_oa_kpi")
public class SOaKpi {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    @TableField("work_date")
    private Date workDate;

    /**
     * 
     */
    @TableField("jobnumber")
    private String jobnumber;

    /**
     * oa姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * oa角色
     */
    @TableField("role")
    private String role;

    /**
     * kpi 等级
     */
    @TableField("kpi_level")
    private String kpiLevel;

    /**
     * kpi 总分
     */
    @TableField("kpi_sum")
    private BigDecimal kpiSum;
    /**
     * kpi 系数
     */
    @TableField("kpi_number")
    private BigDecimal kpiNumber;
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