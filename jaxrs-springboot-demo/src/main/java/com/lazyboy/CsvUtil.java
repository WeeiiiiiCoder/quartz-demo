package com.lazyboy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lazyboy.annotation.Excel;
import com.lazyboy.entity.CustomerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.supercsv.cellprocessor.Optional;
import org.supercsv.cellprocessor.*;
import org.supercsv.cellprocessor.constraint.LMinMax;
import org.supercsv.cellprocessor.constraint.NotNull;
import org.supercsv.cellprocessor.constraint.StrRegEx;
import org.supercsv.cellprocessor.constraint.UniqueHashCode;
import org.supercsv.cellprocessor.ift.CellProcessor;
import org.supercsv.io.CsvBeanReader;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * csv工具类
 *
 * @auther: zhouwei
 * @date: 2020/12/30 10:27
 * @see <a href="https://www.cnblogs.com/cjsblog/p/9260421.html">apache common csv</a>
 */
public class CsvUtil {

    private static final String fieldSeparator = ",";

    private static final String textDelimiter = "\"";

    public static final Logger log = LoggerFactory.getLogger(CsvUtil.class);


    /**
     * 解析csv文件
     *
     * @param in
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> List<T> parseCsv(InputStream in, Class<T> clazz) {
        return null;
    }

    /**
     * 解析csv文件
     *
     * @param file
     * @param clazz
     * @param processors super-csv数据处理器
     * @return
     */
    /*public static <T> List<T> parseCsv(File file, Class<T> clazz, CellProcessor[] processors) {
        //CsvBeanReader:读取每一行csv数据作为POJO
        CsvBeanReader inFile = null;
        List<T> csvBean = new ArrayList<>();
        try {
            inFile = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
            final String[] header = inFile.getHeader(true);
            T t;
            while ((t = inFile.read(clazz, header, processors)) != null) {
                csvBean.add(t);
            }
        } catch (FileNotFoundException e) {
//            e.printStackTrace();
            log.info("解析异常,异常原因{}",e,e.getMessage());
        } catch (IOException e) {
            //csvBeanReader.getHeader()
            //inFile.read
            log.info("解析异常,异常原因{}",e,e.getMessage());
//            e.printStackTrace();
        } finally {
            if (inFile != null) {
                try {
                    inFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return csvBean;
    }*/

    /**
     * 解析csv文件
     * csv文件编码为UTF-8 without BOM
     * @see <a href="https://github.com/super-csv/super-csv/issues/142">apache common csv</a>
     *
     *
     * @param file
     * @param clazz
     * @param processors super-csv数据处理器
     * @return
     */
    public static <T> List<T> parseCsv(File file, Class<T> clazz, CellProcessor[] processors) throws IOException {
        //CsvBeanReader:读取每一行csv数据作为POJO
        List<T> csvBean = new ArrayList<>();

        CsvBeanReader inFile = new CsvBeanReader(new FileReader(file), CsvPreference.STANDARD_PREFERENCE);
        final String[] header = inFile.getHeader(true);
        System.out.println(new ObjectMapper().writeValueAsString(header));
        T t;
        while ((t = inFile.read(clazz, header, processors)) != null) {
            csvBean.add(t);
        }
        //csvBeanReader.getHeader()
        //inFile.read
//            e.printStackTrace();
        if (inFile != null) {
            inFile.close();

        }
        return csvBean;
    }


    /**
     * 仅适用于Reading CSV:
     * ParseDate:按指定格式处理日期类型,否则会抛异常
     * ParseBigDecimal:将字符串通过new BigDecimal(String)构造成BigDecimal
     * ParseBool:将字符串转化为boolean,
     *           字符串"true"(忽略大小写), "1", "y", "t"可以转换为true
     *           字符串"false"(忽略大小写), "0", "n", "f"可以转换为false
     *
     * ParseInt,ParseLong,ParseDouble,ParseChar,ParseEnum:字符串转Int,Long,Double,Character,Enum
     *
     * 适用于Reading/Writing CSV:
     * HashMapper:读取的csv数据作为key去映射表中获取对应值,如果映射表中不存在key,则输出默认值
     * Optional:表示读取的列数据为可选,如果读取的数据为null就不会被CellProcessor处理
     * Collector:收集输入值到集合中
     * StrReplace:替换正则匹配的部分字符串
     * Token:验证字符串为指定的token,若是则输出新的值
     * Trim:等同于String.trim()
     * Truncate:确保字符串被截成最大的长度
     *
     * csv约束:
     * NotNull:读取的csv数据不能为null,否则会抛异常
     */
    public static CellProcessor[] getProcessors() {
        final String emailRegex = "[a-z0-9\\._]+@[a-z0-9\\.]+"; // just an example, not very robust!
        StrRegEx.registerMessage(emailRegex, "must be a valid email address");

        Map<Object, Object> lastNameHashMapper = new HashMap<>();
        lastNameHashMapper.put("Happy", "你好");
        lastNameHashMapper.put("error", "再见");
        final CellProcessor[] processors = new CellProcessor[]{new UniqueHashCode(), // customerNo (must be unique)
                new NotNull(), // firstName
                new HashMapper(lastNameHashMapper, "helloWorld"), // lastName
                new ParseDate("dd/MM/yyyy"), // birthDate
                new NotNull(), // mailingAddress
                new Optional(new ParseBool()), // married
                new Optional(new ParseInt()), // numberOfKids
                new NotNull(), // favouriteQuote
                new StrRegEx(emailRegex), // email
                new LMinMax(0L, LMinMax.MAX_LONG), // loyaltyPoints
                new Optional(new ParseBigDecimal()),//salary
//                new Optional(new ParseBool()),//vip
                new Optional(new ParseInt()),//age
                new Optional(new ParseLong()),//times
                new Optional(new ParseDouble()),//rate
                new Optional(new ParseChar()),//character
                new Optional(new ParseEnum(ExportType.class)),//character
                new Optional(new Collector(new ArrayList<>())),//items
                new ConvertNullTo("isNull"),//convertNullTo
                new Optional(new StrReplace(emailRegex, "correctEmail")),//emaildd
                new Optional(new Token("Star", "f*ck war")),//token
                new Optional(new Trim())//trimee
        };
        return processors;
    }


    public static void main(String[] args) throws IOException {
        File file = new File("C:\\Users\\ZHOUWEI\\Desktop\\examplecsv.csv");
        List<CustomerBean> customerBeans = parseCsv(file, CustomerBean.class, getProcessors());
        customerBeans.forEach(customerBean -> {
            try {
                System.out.println(new ObjectMapper().writeValueAsString(customerBean));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        });
    }


    public static <T> void export(File file, List<String> selectHeaders, List<T> data, Class clazz) throws IllegalAccessException, IOException {
        exportCSV(file, getCsvData(selectHeaders, data, clazz));
    }

    /**
     * csv文件导出
     *
     * @param csvData
     * @param csvData
     * @throws IOException
     */
    private static void exportCSV(File file, List<List<String>> csvData) throws IOException {
        //获取文件
        if (!file.exists()) {
            boolean createFile = file.createNewFile();
        }
        BufferedWriter csvBufWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
        //标记文件为UTF-8编码
        csvBufWriter.write(getBOM());
        boolean newLine = true;
        //写入header
        if (!CollectionUtils.isEmpty(csvData)) {
            for (List<String> csvArr : csvData) {
                for (String csv : csvArr) {
                    if (!newLine) {
                        csvBufWriter.write(fieldSeparator);
                    } else {
                        newLine = false;
                    }
                    if (Objects.isNull(csv)) {
                        csvBufWriter.write(textDelimiter);
                        csvBufWriter.write(textDelimiter);
                    } else {
                        csvBufWriter.write(csv);
                    }
                }
                //换行
                newLine = true;
                csvBufWriter.newLine();
            }
        }
        //写入csv文件内容
        csvBufWriter.close();
    }

    /**
     * 标记文件为UTF-8编码
     *
     * @return
     */
    private static String getBOM() {
        byte[] b = {(byte) 0xEF, (byte) 0xBB, (byte) 0xBF};
        return new String(b);
    }

    /**
     * 获取数据
     *
     * @param selectHeaders 导出的字段列
     * @param data          原始数据
     * @param clazz         指定class对象
     * @return
     * @throws IllegalAccessException
     */
    private static <T> List<List<String>> getCsvData(List<String> selectHeaders, List<T> data, Class clazz) throws IllegalAccessException {
        List<List<String>> csvData = new ArrayList<>();
        if (CollectionUtils.isEmpty(selectHeaders)) {
            return csvData;
        }
        csvData.add(selectHeaders);
        Field[] allFields = clazz.getDeclaredFields();
        List<Field> fields = new ArrayList<>(selectHeaders.size());
        if (allFields.length > 0) {
            for (String selectHeader : selectHeaders) {
                for (Field field : allFields) {
                    Excel annotation = field.getAnnotation(Excel.class);
                    if (null == annotation || !annotation.name().equals(selectHeader)) {
                        continue;
                    }
                    fields.add(field);
                    break;
                }
            }
        }
        for (T t : data) {
            List<String> values = new ArrayList<>();
            for (Field field : fields) {
                field.setAccessible(true);
                Object value = field.get(t);
                if (value instanceof Integer) {
                    if ((int) value <= 0) {
                        values.add("");
                    } else {
                        values.add(value.toString());
                    }
                } else {
                    values.add(value.toString());
                }
            }
            csvData.add(values);
        }
        return csvData;
    }


    /**
     * 解析导出值 0=男,1=女,2=未知
     *
     * @param propertyValue 参数值
     * @param converterExp  翻译注解
     * @param separator     分隔符
     * @return 解析后值
     * @throws Exception
     */
    private static String convertByExp(String propertyValue, String converterExp, String separator) throws Exception {
        StringBuilder propertyString = new StringBuilder();
        try {
            String[] convertSource = converterExp.split(",");
            for (String item : convertSource) {
                String[] itemArray = item.split("=");
                //属性值为多值,例如 0,1 => 那么需要转换成男,女
                if (propertyValue.contains(separator)) {
                    for (String value : propertyValue.split(separator)) {
                        if (itemArray[0].equals(value)) {
                            if (propertyString.length() > 0) {
                                propertyString.append(separator);
                            }
                            propertyString.append(itemArray[1]);
                            break;
                        }
                    }
                } else {
                    //属性值为单值,直接从表达式中获取
                    if (itemArray[0].equals(propertyValue)) {
                        return itemArray[1];
                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return propertyString.toString();
    }

    /**
     * 设置响应
     *
     * @param fileName
     * @param bytes
     * @param response
     */
    public static void setCsvResponse(String fileName, byte[] bytes, HttpServletResponse response) {
        try {
            fileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            response.setContentType("application/csv");
            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(bytes);
            outputStream.flush();
        } catch (IOException e) {
            log.error("io stream error:{}", e.getMessage(), e);
        }
    }

    public enum ExportType {
        DAILY,
        MONTHLY
    }


}