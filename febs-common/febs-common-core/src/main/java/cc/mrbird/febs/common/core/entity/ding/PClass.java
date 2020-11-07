package cc.mrbird.febs.common.core.entity.ding;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
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
 * @date 2020-11-06 08:35:28
 */
@Data
@TableName(value = "p_class", autoResultMap = true)
public class PClass {

    /**
     *
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 课程名称
     */
    @TableField("name")
    private String name;

    /**
     * 授课人
     */
    @TableField(value = "train_name", typeHandler = FastjsonTypeHandler.class)
    private List<String> trainName;
    /**
     * 授课方式
     */
    @TableField("give_lessons")
    private Integer giveLessons;

    /**
     * 考核方式
     */
    @TableField("examine")
    private Integer examine;

    /**
     * 培训类型
     */
    @TableField("train")
    private Integer train;

    /**
     *
     */
    @TableField("train_start_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date trainStartTime;

    /**
     *
     */
    @TableField("train_end_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date trainEndTime;

    /**
     *
     */
    @TableField("update_time")
    private Date updateTime;
    /**
     *
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm",timezone="GMT+8")
    private Date createTime;

}