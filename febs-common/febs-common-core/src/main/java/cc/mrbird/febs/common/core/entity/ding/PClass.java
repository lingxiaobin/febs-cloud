package cc.mrbird.febs.common.core.entity.ding;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.JSON;
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
     * 授课地点
     */
    @TableField("train_site")
    private String trainSite;

    @TableField(exist = false)
    private String trainNameStr;
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
    @TableField("update_time")
    private Date updateTime;
    /**
     *
     */
    @TableField("create_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date createTime;



    @TableField(exist = false)
    private String giveLessonsStr;


    @TableField(exist = false)
    private String examineStr;


    @TableField(exist = false)
    private String trainStr;


    public String getTrainNameStr() {
        if (this.trainNameStr==null){
            return null;
        }
        String str= Arrays.toString(JSON.parseArray(this.trainNameStr).toArray());
        return str.substring(1,str.length()-1);
    }
}
