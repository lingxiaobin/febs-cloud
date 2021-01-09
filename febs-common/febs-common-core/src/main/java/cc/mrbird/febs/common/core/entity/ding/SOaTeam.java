package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;

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
* @date 2021-01-02 11:43:09
*/
@Data
@TableName("s_oa_team")
public class SOaTeam {

    /**
     * 
     */
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    /**
     * 
     */
    @TableField("work_date")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")
    private Date workDate;

    /**
     * 所属团队
     */
    @TableField("team")
    private String team;

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
     * 岗位
     */
    @TableField("jobs")
    private String jobs;

    /**
     * 岗位系数
     */
    @TableField("jobs_xs")
    private String jobsXs;

    /**
     * 产量
     */
    @TableField("production")
    private String production;

    /**
     * 单价
     */
    @TableField("price")
    private String price;

    /**
     * 工资总额
     */
    @TableField("total_wages")
    private String totalWages;

    /**
     * 团队工时
     */
    @TableField("team_wh")
    private String teamWh;

    /**
     * 个人工时
     */
    @TableField("working_hours")
    private String workingHours;

    /**
     * 工时比例
     */
    @TableField("proportion_wh")
    private String proportionWh;

    /**
     * 计件工资
     */
    @TableField("piece_rate")
    private String pieceRate;

    /**
     * 时间段
     */
    @TableField("period_time")
    private String periodTime;

    /**
     * 工序代码
     */
    @TableField("process_code")
    private String processCode;

    /**
     * 产出工序
     */
    @TableField("production_pro")
    private String productionPro;

    /**
     * 产出组
     */
    @TableField("output_group")
    private String outputGroup;

    /**
     * 调整比例
     */
    @TableField("adjust_pro")
    private String adjustPro;

    /**
     * 调整金额
     */
    @TableField("adjust_money")
    private String adjustMoney;

    /**
     * 实际工资
     */
    @TableField("actual_wage")
    private String actualWage;

    /**
     * 总调整额
     */
    @TableField("tatol_money")
    private String tatolMoney;

    /**
     * 代加工产量
     */
    @TableField("djg_production")
    private String djgProduction;

    /**
     * 文件图片
     */
    @TableField("wj_img")
    private String wjImg;

    /**
     * 基本产量
     */
    @TableField("jb_production")
    private String jbProduction;

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

    @TableField(exist = false)
    private String workNumber;

    @TableField(exist = false)
    private String ddName;

}