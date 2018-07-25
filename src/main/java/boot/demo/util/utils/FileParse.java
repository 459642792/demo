package boot.demo.util.utils;

import boot.demo.entity.bo.ExcelDTO;
import boot.demo.entity.bo.ExcelParseDTO;
import com.opencsv.CSVReader;

import net.sf.ehcache.util.NamedThreadFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParse {

    public static final Logger logger = LoggerFactory.getLogger(FileParse.class);


    private static final Pattern REPLACE_SPECIAL_PATTERN = Pattern.compile("\\s*|\t|\r|\n");

    /**
     * 解析数据
     *
     * @param file
     * @return
     */
    public static Map<Integer, List<ExcelParseDTO>> parse(File file, LinkedHashMap<Integer, LinkedHashMap<Integer, ? extends ExcelDTO>> map, Integer beginRow, Integer colNum) {
        logger.debug("开始解析文件：{}", file.getName());
        String fileType = file.getName().substring(file.getName().lastIndexOf("."));
        String type = StringUtils.isNotEmpty(fileType.substring(1)) ? fileType.substring(1) : null;
        logger.debug("文件名:{},文件类型:{}", file.getName(), type);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            if ("csv".equals(type)) {
                return readCSV(inputStream, map, beginRow, colNum);
            } else {
                return parseExcel(type, inputStream, map, beginRow, colNum);
            }
        } catch (FileNotFoundException e) {
            logger.error("解析文件出现错误.", e);
            file.delete();
            throw  e;
        } finally {
            logger.debug("关闭文件输入流");
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    private static Map<Integer, List<ExcelParseDTO>> parseExcel(String fileType, InputStream inputStream, LinkedHashMap<Integer, LinkedHashMap<Integer, ? extends ExcelDTO>> map, Integer beginRow, Integer colNum) {
        Workbook wb = readExcel(fileType, inputStream);
        int sheetNumber = wb.getNumberOfSheets();
        List<Object> lists = new ArrayList<>();
        logger.debug("解析Excel文件，共找到{}张Sheet", sheetNumber);
        Map<Integer, List<ExcelParseDTO>> auditSheet = null;

        for (int i = 0; i < sheetNumber; i++) {
            //构建数据行
            auditSheet = buildSheet(wb.getSheetAt(i), map, beginRow, colNum);
        }
        return auditSheet;
    }


    /**
     * 得到Excel中Sheet的详细数据；
     *
     * @param sheet
     * @return
     */
    private static Map<Integer, List<ExcelParseDTO>> buildSheet(Sheet sheet, LinkedHashMap<Integer, LinkedHashMap<Integer, ? extends ExcelDTO>> map, Integer beginRow, Integer colNum) {
        Map<Integer, List<ExcelParseDTO>> auditSheet = new HashMap<>();
        int maxRow = sheet.getLastRowNum() + 1;
        logger.debug("解析Excel具体Sheet:{}，sheet共有{}行", sheet.getSheetName(), maxRow);

        ThreadPoolExecutor pool = new ThreadPoolExecutor(3, 6, 10,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(6), new NamedThreadFactory("解析Excel数据"),new ThreadPoolExecutor.DiscardOldestPolicy());
        for (int i = 0; i < beginRow; i++) {
            Row row = sheet.getRow(i);
            if (null == row) {
                break;
            }
            matchTemplate(map, row);
        }

        final CountDownLatch latch = new CountDownLatch(maxRow / 500);
        for (int i = 0; i < maxRow / 500; i++) {
            try {
                SheetData sheetData =new SheetData();
                Future<SheetData> future = pool.submit(new MyThread(sheetData,sheet,colNum,i*50+beginRow), sheetData);
                auditSheet.putAll(future.get().getAuditSheet());
            } catch (Exception e) {
//                throw BizTaxException.build(BizTaxException.Codes.BizTaxException, e);
            } finally {
                latch.countDown();
            }
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            pool.shutdown();
        }
        return auditSheet;
    }

    private static class MyThread implements Runnable {
        SheetData sheetData;
        Integer colNum;
        Sheet sheet;
        Integer rowNum;

        MyThread(  SheetData sheetData,Sheet sheet, Integer colNum, Integer rowNum) {
            this.sheetData = sheetData;
            this.colNum = colNum;
            this.sheet = sheet;
            this.rowNum = rowNum;
        }

        @Override
       public void run() {
            try {
                Map<Integer, List<ExcelParseDTO>> auditSheet = new HashMap<>();
                for (int i = rowNum; i < rowNum+51; i++) {
                    Row row = sheet.getRow(i);
                    if (null == row) {
                        break;
                    }
                    List<ExcelParseDTO> listRow = new ArrayList<>();
                    for (int j = 0; j <= colNum; j++) {
                        Cell cell = row.getCell(j);
                        ExcelParseDTO excelParseDTO = new ExcelParseDTO();
                        excelParseDTO.setCol(j);
                        excelParseDTO.setColValue(getCellValue(cell));
                        listRow.add(excelParseDTO);
                    }
                    if (CollectionUtils.isNotEmpty(listRow)) {
                        auditSheet.put(i, listRow);
                    }
                }
                sheetData.setAuditSheet(auditSheet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * excel 解析日期格式
     *
     * @param cell
     * @return
     */
    private static String getCellValue(Cell cell) {
//        if (StringUtils.isEmpty(cell)) {
//            return null;
//        }
        String result;
        switch (cell.getCellTypeEnum()) {
            // 数字类型
            case NUMERIC:
                // 处理日期格式、时间格式
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    SimpleDateFormat sdf = null;
                    if (cell.getCellStyle().getDataFormat() == HSSFDataFormat
                            .getBuiltinFormat("h:mm")) {
                        sdf = new SimpleDateFormat("HH:mm");
                    } else {// 日期
                        sdf = new SimpleDateFormat("yyyy-MM-dd");
                    }
                    Date date = cell.getDateCellValue();
                    result = sdf.format(date);
                } else if (cell.getCellStyle().getDataFormat() == 58) {
                    // 处理自定义日期格式：m月d日(通过判断单元格的格式id解决，id的值是58)
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    double value = cell.getNumericCellValue();
                    Date date = DateUtil
                            .getJavaDate(value);
                    result = sdf.format(date);
                } else {
                    double value = cell.getNumericCellValue();
                    CellStyle style = cell.getCellStyle();
                    DecimalFormat format = new DecimalFormat();
                    String temp = style.getDataFormatString();
                    // 单元格设置成常规
                    if (temp.equals("General")) {
                        format.applyPattern("#");
                    }
                    result = format.format(value);
                }
                break;
            // String类型
            case STRING:
                result = cell.getStringCellValue();
                break;
            default:
                result = "";
                break;
        }
        return result;
    }
    static class SheetData {
        Map<Integer, List<ExcelParseDTO>> auditSheet;

        public Map<Integer, List<ExcelParseDTO>> getAuditSheet() {
            return auditSheet;
        }

        public void setAuditSheet(Map<Integer, List<ExcelParseDTO>> auditSheet) {
            this.auditSheet = auditSheet;
        }
    }
    private static void matchTemplate(LinkedHashMap<Integer, LinkedHashMap<Integer, ? extends ExcelDTO>> map, Row row) {
        if (null == row) {
//            throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, "请按照下载的模板提交");
        }
        LinkedHashMap<Integer, ? extends ExcelDTO> maps = map.get(row.getRowNum());
        for (Integer col : maps.keySet()) {
            Cell cell = row.getCell(col);
            if (null == cell) {
//                throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, "请按照下载的模板提交");
            }
            cell.setCellType(CellType.STRING);
            if (!maps.get(col).getName().equals(cell.getStringCellValue())) {
//                throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, "请按照下载的模板提交");
            }
        }
    }

    /**
     * 解析csv
     *
     * @param inputStream 流
     * @return
     */
    private static Map<Integer, List<ExcelParseDTO>> readCSV(InputStream inputStream, LinkedHashMap<Integer, LinkedHashMap<Integer, ? extends ExcelDTO>> map, Integer beginRow, Integer colNum) {
        Map<Integer, List<ExcelParseDTO>> auditSheet = new HashMap<>();
        CSVReader reader = null;
        try {
            reader = new CSVReader(new InputStreamReader(inputStream, "gbk"));
            String[] strArr = null;
            int i = 0;
            while ((strArr = reader.readNext()) != null) {
                if (beginRow > i) {
                    LinkedHashMap<Integer, ? extends ExcelDTO> maps = map.get(i);
                    for (Integer col : maps.keySet()) {
                        String cellValue = strArr[col].replace("\n", "\r\n");
                        ;
                        if (!map.get(i).get(col).getName().equals(cellValue)) {
//                            throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, "请按照下载的模板提交");
                        }
                    }
                    i++;
                } else {
                    String str = "";
                    for (int j = 0; j <= colNum; j++) {
                        str += strArr[j];
                    }
                    if (!"--".equals(str.trim()) && !"".equals(str.trim())) {
                        List<ExcelParseDTO> listRow = new ArrayList<>();
                        for (int j = 0; j <= colNum; j++) {
                            ExcelParseDTO excelParseDTO = new ExcelParseDTO();
                            excelParseDTO.setCol(j);
                            excelParseDTO.setColValue(strArr[j]);
                            listRow.add(excelParseDTO);
                        }
                        auditSheet.put(i, listRow);
                        i++;
                    }
                }


            }
        } catch (Exception e) {
            logger.debug("POI解析Excel输入流异常", e);
//            throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, "解析csv异常,");
        } finally {
            logger.debug("关闭文件输入流");
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return auditSheet;
    }


    public static Workbook readExcel(String fileType, InputStream inputStream) {
        Workbook wb = null;
        if (inputStream == null) {
            return null;
        }
        try {
            if ("xls".equals(fileType)) {
                return wb = new HSSFWorkbook(inputStream);
            } else if ("xlsx".equals(fileType) || "xlsm".equals(fileType)) {
                return wb = new XSSFWorkbook(inputStream);
            } else {
//                throw BizTaxException.build(BizTaxException.Codes.WRONG_DATA_FORMAT, fileType);
            }
        } catch (Exception e) {
            logger.debug("POI解析Excel输入流异常", e);
//            throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, "您的版本可能不兼容,请打开另存为csv格式,");
        }
    }


    public static Map<String, Boolean> getMonth(String date) {
        Map<String, Boolean> map = new HashMap<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
            Calendar now = Calendar.getInstance();
            now.setTime(sdf.parse(date));
            int month = now.get(Calendar.MONTH) + 1;
            int year = now.get(Calendar.YEAR);
            if (month == 1 || month == 2 || month == 3) {
                map.put(new StringBuilder().append(year).append("01").toString(), true);
                map.put(new StringBuilder().append(year).append("02").toString(), true);
                map.put(new StringBuilder().append(year).append("03").toString(), true);
            } else if (month == 4 || month == 5 || month == 6) {
                map.put(new StringBuilder().append(year).append("04").toString(), true);
                map.put(new StringBuilder().append(year).append("05").toString(), true);
                map.put(new StringBuilder().append(year).append("06").toString(), true);
            } else if (month == 7 || month == 8 || month == 9) {
                map.put(new StringBuilder().append(year).append("07").toString(), true);
                map.put(new StringBuilder().append(year).append("08").toString(), true);
                map.put(new StringBuilder().append(year).append("09").toString(), true);
            } else if (month == 10 || month == 11 || month == 12) {
                map.put(new StringBuilder().append(year).append("10").toString(), true);
                map.put(new StringBuilder().append(year).append("11").toString(), true);
                map.put(new StringBuilder().append(year).append("12").toString(), true);
            }
        } catch (Exception e) {
            logger.debug("会计区间格式错误{}", date);
//            throw ServiceTaxException.build(ServiceTaxException.Codes.EXPORT_EXCEL_ERROR, e);
        }
        return map;
    }

    public static String replaceSpecial(String value) {
        String repl = null;
        //去除字符串中的空格、回车、换行符、制表符等
        if (value != null) {
            Matcher m = REPLACE_SPECIAL_PATTERN.matcher(value);
            repl = m.replaceAll("");
        }
        return repl;
    }


}
