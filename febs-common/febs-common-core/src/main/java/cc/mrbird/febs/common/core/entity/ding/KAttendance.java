package cc.mrbird.febs.common.core.entity.ding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-04-24 15:43:55
 */
@Data
@TableName("k_attendance_list")
public class KAttendance {


    /**
     * userid
     */
    @TableField("user_id")
    private String userId;

    /**
     * 考勤组ID
     */
    @TableField("group_id")
    private Long groupId;

    /**
     * 班次ID
     */
    @TableField("class_id")
    private Long classId;
    /**
     * 考勤组ID
     */
    @TableField("group_name")
    private String groupName;

    /**
     * 考勤组ID
     */
    @TableField("class_name")
    private String className;


    /**
     * 工作日
     */
    @TableField("work_date")
    private Date workDate;


    private String workDateStr;




}
