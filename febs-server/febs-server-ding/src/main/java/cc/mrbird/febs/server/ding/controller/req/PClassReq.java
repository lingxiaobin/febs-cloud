package cc.mrbird.febs.server.ding.controller.req;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Entity
 *
 * @author MrBird
 * @date 2020-11-06 08:35:28
 */
@Data
public class PClassReq {

    private Integer id;
    private String jobnumber;
    private Integer[] classIds;
    /**
     * 课程名称
     */
    private String className;

    private Integer[] giveLessonsArr;

    private Integer[] examineArr;

    private Integer[] trainArr;

    private boolean selOne = false;

}
