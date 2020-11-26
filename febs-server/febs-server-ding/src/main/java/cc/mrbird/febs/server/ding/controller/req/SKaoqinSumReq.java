package cc.mrbird.febs.server.ding.controller.req;

import cc.mrbird.febs.server.ding.controller.vo.DeptVo;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SKaoqinSumReq {
    private Integer currentPage;
    private Integer pageSize;
    private String userId;
    private String name;
    private String[] groups;
    private String[] payPlaces;
    private String[][] processTypes;
    private String[] lizhis;
    private String[][] depts;
    private Boolean isDetpSub = false;  //是否包含子部门
    private String workDate;

    private Boolean isError = false;
    private Boolean isUpdate = false;

    private String payComputeType = null ;

    private String deptsAndUsers;

}
