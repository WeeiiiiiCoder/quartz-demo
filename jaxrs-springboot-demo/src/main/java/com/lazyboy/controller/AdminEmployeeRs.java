package com.lazyboy.controller;

import com.lazyboy.CsvUtil;
import com.lazyboy.FileUploadUtils;
import com.lazyboy.entity.CommBase;
import com.lazyboy.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.supercsv.cellprocessor.HashMapper;
import org.supercsv.cellprocessor.ParseBool;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.exception.SuperCsvConstraintViolationException;
import org.supercsv.exception.SuperCsvException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工管理接口
 *
 * @auther: zhouwei
 * @date: 2020/8/17 10:42
 */
@Component
@Path("/admin/employee")
@Slf4j
public class AdminEmployeeRs {


    /**
     * 导入员工csv数据,只支持导入指定部门的员工
     *
     * @param request
     * @return
     */
    @POST
    @Path("/csvImport")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public CommBase csvImport(@Context HttpServletRequest request) throws UnsupportedEncodingException {
        System.out.println(request.getParameterMap());
        request.setCharacterEncoding("UTF-8");

//        int deptId = 1;
        //获取上传文件
        Map<String, Object> stringObjectMap = FileUploadUtils.parseUploadFile(request);
        Integer deptId = Integer.parseInt(String.valueOf(stringObjectMap.get("deptId")));
        File importCsvFile = (File) stringObjectMap.get("file");
        System.out.println("deptId->" + deptId);
//        File importCsvFile = (File) stringObjectMap;
        //解析csv
        //工号重复
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");

        //设置部门表
        Map<Object, Object> deptIdMap = new HashMap<>();
        deptIdMap.put("dept1", "100");
        deptIdMap.put("dept2", "101");
        Map<Object, Object> genderMap = new HashMap<>();
        genderMap.put("M", 0);
        genderMap.put("F", 1);
        final CellProcessor[] processors = new CellProcessor[]{
                new NotNull(), // name
//                new HashMapper(deptIdMap, "-1"), // dept_id
                new StrRegEx(emailRegex), // email
                new HashMapper(genderMap, "0"), // gender
                new NotNull(new ParseBool()), // push_qn
        };
        try {
            List<Employee> employees = CsvUtil.parseCsv(importCsvFile, Employee.class, processors);
        } catch (SuperCsvConstraintViolationException ex) {
            if (log.isErrorEnabled()) {
                log.error("解析csv文件失败,错误原因{}", ex.getMessage());
            }
            System.out.println("log.isNotInfoEnabled");
            return CommBase.fail(1000, String.format("解析csv文件失败,错误原因{%s}", ex.getMessage()));
        } catch (IOException e) {
            if (log.isInfoEnabled()) {
                System.out.println("log.isInfoEnabled");
                log.info("解析csv文件失败,错误原因{}", e);
            }
            System.out.println("log.isNotInfoEnabled");
            return CommBase.fail(1000, String.format("解析csv文件失败,错误原因{%s}", e));
//            e.printStackTrace();
        }
        //执行业务操作
        /*if (!CollectionUtils.isEmpty(employees)) {
            int size = employees.size();
            //100条分批插入表中
            int insertCount = (int) Math.ceil((double) size / 100);
            employeeService.insertFromCsv(employees, supplierId);
        }*/
        return CommBase.success("success");
    }
}