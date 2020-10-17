package cc.mrbird.febs.common.core.api;


import cc.mrbird.febs.common.core.entity.constant.DingConstant;
import cc.mrbird.febs.common.core.entity.ding.KUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeFieldGrouplistRequest;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeListRequest;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeUpdateRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeFieldGrouplistResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeListResponse;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeUpdateResponse;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 获取access_token工具类
 */
@Slf4j
public class DingApiUtil {

    public static String getToken() throws RuntimeException {
        try {
            DefaultDingTalkClient client = new DefaultDingTalkClient(DingConstant.URL_GET_TOKKEN);
            OapiGettokenRequest request = new OapiGettokenRequest();

            request.setAppkey(DingConstant.APP_KEY);
            request.setAppsecret(DingConstant.APP_SECRET);
            request.setHttpMethod("GET");
            OapiGettokenResponse response = client.execute(request);
            String accessToken = response.getAccessToken();
            return accessToken;
        } catch (ApiException e) {
            log.error("getAccessToken failed", e);
            throw new RuntimeException();
        }

    }

    public static Map<String, String> findKUserOption() throws ApiException {
        String accessToken = getToken();

        DingTalkClient clientGrouplist = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/field/grouplist");
        OapiSmartworkHrmEmployeeFieldGrouplistRequest reqGrouplist = new OapiSmartworkHrmEmployeeFieldGrouplistRequest();
        reqGrouplist.setAgentid(335540530L);
        OapiSmartworkHrmEmployeeFieldGrouplistResponse rspGrouplist = clientGrouplist.execute(reqGrouplist, accessToken);
        JSONObject object = JSON.parseObject(rspGrouplist.getBody());
        Map<String, String> groupMap = new HashMap<>();
        for (Object result : object.getJSONArray("result")) {
            for (Object field_list : ((JSONObject) result).getJSONArray("field_list")) {
                JSONObject obj = (JSONObject) field_list;
                String fideName = obj.getString("field_name");
                String fideCode = obj.getString("field_code");
                String optionText = obj.getString("option_text");
                if (fideCode.equals("职位")) {
                    groupMap.put("positionList", optionText);
                } else if (fideCode.equals("sys01-employeeType")) {     //员工类型
                    groupMap.put("userTypeList", optionText);
                } else if (fideCode.equals("31a29b0a-4c9a-470e-bdf5-760c8c304704")) {   //工资核算地
                    groupMap.put("payPlaceList", optionText);
                } else if (fideCode.equals("379c084d-abfc-4af1-b777-a530fd282ada")) {  // 劳务派遣公司
                    groupMap.put("dispatchFactoryList", optionText);
                } else if (fideCode.equals("b2931cda-df37-4317-8bda-3edb9d3aa257")) {  // 工资计算方式
                    groupMap.put("payComputeTypeList", optionText);
                }
            }
        }
        return groupMap;
    }


    public static boolean updateKUserDingApi(Map<String, String> parMap) throws ApiException {
        String accessToken = getToken();

        DingTalkClient clientEmpList = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/list");
        OapiSmartworkHrmEmployeeListRequest reqEmpList = new OapiSmartworkHrmEmployeeListRequest();
        reqEmpList.setAgentid(DingConstant.AGENT_ID);

        reqEmpList.setUseridList(parMap.get("userId"));
        List<OapiSmartworkHrmEmployeeListResponse.EmpFieldVO> updateList = new ArrayList<>();
        OapiSmartworkHrmEmployeeListResponse rspEmpList = clientEmpList.execute(reqEmpList, accessToken);
        for (OapiSmartworkHrmEmployeeListResponse.EmpFieldInfoVO empFieldInfoVO : rspEmpList.getResult()) {
            for (OapiSmartworkHrmEmployeeListResponse.EmpFieldVO empFieldVO : empFieldInfoVO.getFieldList()) {
                String name = empFieldVO.getFieldName();
                String value = empFieldVO.getValue();
                if (parMap.get(empFieldVO.getFieldCode()) != null) {     //姓名
                    empFieldVO.setValue(parMap.get(empFieldVO.getFieldCode()));
                    updateList.add(empFieldVO);
                }
            }
        }

        DingTalkClient client = new DefaultDingTalkClient("https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/update");
        OapiSmartworkHrmEmployeeUpdateRequest req = new OapiSmartworkHrmEmployeeUpdateRequest();
        OapiSmartworkHrmEmployeeUpdateRequest.PreEntryEmployeeAddParam obj1 = new OapiSmartworkHrmEmployeeUpdateRequest.PreEntryEmployeeAddParam();
        obj1.setUserid(parMap.get("userId"));

        List<OapiSmartworkHrmEmployeeUpdateRequest.GroupMetaInfo> groups = new ArrayList<>();
        for (OapiSmartworkHrmEmployeeListResponse.EmpFieldVO empFieldVO : updateList) {
            OapiSmartworkHrmEmployeeUpdateRequest.GroupMetaInfo group = new OapiSmartworkHrmEmployeeUpdateRequest.GroupMetaInfo();
            groups.add(group);
            group.setGroupId(empFieldVO.getGroupId());   //所在的组

            List<OapiSmartworkHrmEmployeeUpdateRequest.EmpListFieldVO> sections = new ArrayList<>();
            group.setSections(sections);
            OapiSmartworkHrmEmployeeUpdateRequest.EmpListFieldVO sectionField = new OapiSmartworkHrmEmployeeUpdateRequest.EmpListFieldVO();
            sections.add(sectionField);

            List<OapiSmartworkHrmEmployeeUpdateRequest.EmpFieldVo> section = new ArrayList<>();
            sectionField.setSection(section);
            OapiSmartworkHrmEmployeeUpdateRequest.EmpFieldVo obj12 = new OapiSmartworkHrmEmployeeUpdateRequest.EmpFieldVo();
            section.add(obj12);
            obj12.setValue(empFieldVO.getValue());   //修改的值
            obj12.setFieldCode(empFieldVO.getFieldCode());   //code  列的标识
        }
        obj1.setGroups(groups);
        req.setParam(obj1);
        req.setAgentid(DingConstant.AGENT_ID);
        OapiSmartworkHrmEmployeeUpdateResponse rsp = client.execute(req, accessToken);
        System.out.println(rsp.getBody());
        return rsp.getSuccess();
    }

    public static void main(String[] args) throws ApiException {
        String accessToken = DingApiUtil.getToken();
        System.out.println(accessToken);
    }
}
