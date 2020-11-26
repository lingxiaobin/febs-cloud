package cc.mrbird.febs.common.core.entity.ding;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 部门编制人数表 Entity
 *
 * @author MrBird
 * @date 2020-03-09 14:06:24
 */
@Data
@TableName("d_dept_num")
public class DeptNum {

    /**
     * 部门id
     */
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 部门名称
     */
    @TableField("name")
    private String name;

    /**
     * 现在人数
     */
    @TableField("now_num")
    private Integer nowNum;

    /**
     * 现在人数 包含子部门
     */
    @TableField("now_num_base")
    private Integer nowNumBase;

    /**
     * 预计人数
     */
    @TableField("expect_num")
    private Integer expectNum;

    /**
     * 待离职人数
     */
    @TableField("dimission_num")
    private Integer dimissionNum;

    /**
     * 上级部门id
     */
    @TableField("parent_id")
    private String parentId;

    /**
     * 层级
     */
    @TableField("level")
    private Integer level;

    /**
     * 修改时间
     */
    @TableField("update_time")
    private Date updateTime;

}
