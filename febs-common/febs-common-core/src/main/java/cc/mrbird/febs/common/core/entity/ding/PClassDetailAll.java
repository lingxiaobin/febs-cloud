package cc.mrbird.febs.common.core.entity.ding;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.commons.codec.BinaryDecoder;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-11-06 08:37:21
 */
@Data
public class PClassDetailAll {

    /**
     *
     */
    private Integer id;


    /**
     * 课程名称
     */
    @TableField("class_name")
    private String className;

    /**
     * 授课人
     */
    private String trainName;

    /**
     * 授课地点
     */
    @TableField("train_site")
    private String trainSite;

    /**
     * 授课方式
     */
    @TableField("give_lessons")
    private String giveLessons;

    /**
     * 考核方式
     */
    @TableField("examine")
    private String examine;

    /**
     * 培训类型
     */
    @TableField("train")
    private String train;

    /**
     *
     */
    @TableField("train_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date trainStartTime;

    /**
     *
     */
    @TableField("train_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date trainEndTime;

    /**
     *
     */
    @TableField("class_create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date classCreateTime;

    /**
     *
     */
    private String userId;
    /**
     * 姓名
     */
    @TableField("user_name")
    @ExcelProperty("姓名")
    private String userName;

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


    @TableField("fraction_state")
    private String fractionState;
    /**
     * 考试分数
     */
    @ExcelProperty("考试分数")
    private BigDecimal fraction;

    /**
     * 考试时间
     */
    @ExcelProperty("考试时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date fractionTime;
    /**
     * 签到状态
     */
    @ExcelProperty("签到状态")
    private String signInState;

    /**
     * 签到时间
     */
    @ExcelProperty("签到时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date signInTime;

    /**
     *
     */
    @ExcelProperty("记录创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;

    /**
     *
     */
    @ExcelProperty("日期标题")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date updateTime;




    public String getTrainNameStr() {
        String str=Arrays.toString(JSON.parseArray(this.trainName).toArray());
        return str.substring(1,str.length()-1);
    }
}