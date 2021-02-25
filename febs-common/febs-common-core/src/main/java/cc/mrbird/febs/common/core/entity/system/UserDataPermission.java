package cc.mrbird.febs.common.core.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author MrBird
 */
@Data
@TableName("t_user_data_permission")
public class UserDataPermission {

    @TableField("USER_ID")    //TableId
    private Long userId;
    @TableField("DEPT_ID")   //TableId
    private Long deptId;

    @TableField(exist = false)
    private String deptName;

}