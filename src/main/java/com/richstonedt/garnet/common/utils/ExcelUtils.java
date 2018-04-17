package com.richstonedt.garnet.common.utils;

import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;


/*
 * ExcelUtil工具类实现功能:
 * 导出时传入list<T>,即可实现导出为一个excel,其中每个对象Ｔ为Excel中的一条记录.
 * 导入时读取excel,得到的结果是一个list<T>.T是自己定义的对象.
 * 需要导出的实体对象只需简单配置注解就能实现灵活导出,通过注解您可以方便实现下面功能:
 * 1.实体属性配置了注解就能导出到excel中,每个属性都对应一列.
 * 2.列名称可以通过注解配置.
 * 3.导出到哪一列可以通过注解配置.
 * 4.鼠标移动到该列时提示信息可以通过注解配置.
 * 5.用注解设置只能下拉选择不能随意填写功能.
 * 6.用注解设置是否只导出标题而不导出内容,这在导出内容作为模板以供用户填写时比较实用.
 * 本工具类以后可能还会加功能,请关注我的博客: http://blog.csdn.net/lk_blog
 */
public class ExcelUtils<T> {
    Class<T> clazz;

    public ExcelUtils(Class<T> clazz) {
        this.clazz = clazz;
    }


    /**
     * 导入 Excel
     * @param sheetName 工作簿名称，不存在默认为第一个工作簿
     * @param input     文件输入流
     * @param excelVersion   excel 版本
     * @return
     */
    public List<T> importExcel(String sheetName, InputStream input,ExcelVersion excelVersion) {
        int maxCol = 0;
        List<T> list = new ArrayList<T>();
        try {

            Workbook workbook;

            switch (excelVersion) {
                case EXCEL_VERSION_03:
                    workbook = new HSSFWorkbook(input);
                    break;
                case EXCEL_VERSION_07:
                    workbook = new XSSFWorkbook(input);
                    break;
                default:
                    workbook = new HSSFWorkbook(input);
                    break;
            }

            Sheet sheet = workbook.getSheet(sheetName);

            if (!sheetName.trim().equals("")) {
                sheet = workbook.getSheet(sheetName);// 如果指定sheet名,则取指定sheet中的内容.
            }
            if (sheet == null) {
                sheet = workbook.getSheetAt(0); // 如果传入的sheet名不存在则默认指向第1个sheet.
            }
            int rows = sheet.getPhysicalNumberOfRows();

            if (rows > 0) {// 有数据时才处理
                // Field[] allFields = clazz.getDeclaredFields();// 得到类的所有field.
                List<Field> allFields = getMappedFiled(clazz, null);

                Map<Integer, Field> fieldsMap = new HashMap<Integer, Field>();// 定义一个map用于存放列的序号和field.
                for (Field field : allFields) {
                    // 将有注解的field存放到map中.
                    if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                        ExcelVOAttribute attr = field
                                .getAnnotation(ExcelVOAttribute.class);
                        int col = getExcelCol(attr.column());// 获得列号
                        maxCol = Math.max(col, maxCol);
                        field.setAccessible(true);// 设置类的私有字段属性可访问.
                        fieldsMap.put(col, field);
                    }
                }
                for (int i = 1; i < rows; i++) {// 从第2行开始取数据,默认第一行是表头.
                    Row row = sheet.getRow(i);
                    int cellNum = maxCol;
                    T entity = null;
                    for (int j = 0; j <= cellNum; j++) {
                        Cell cell = row.getCell(j);
                        if (cell == null) {
                            continue;
                        }
                        int cellType = cell.getCellType();
                        int dataFormat = cell.getCellStyle().getDataFormat();
                        String c = "";
                        if (cellType == HSSFCell.CELL_TYPE_BOOLEAN) {
                            c = String.valueOf(cell.getBooleanCellValue());
                        } else if (dataFormat == 20 || dataFormat == 32) {
                            SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                            Date date = DateUtil.getJavaDate(Double.parseDouble(cell.getStringCellValue()));
                            c = format.format(date);
                        }else {
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            c = cell.getStringCellValue();
                        }
                        if (c == null || c.equals("")) {
                            continue;
                        }
                        entity = (entity == null ? clazz.newInstance() : entity);// 如果不存在实例则新建.
                        Field field = fieldsMap.get(j);// 从map中得到对应列的field.
                        if (field == null) {
                            continue;
                        }
                        // 取得类型,并根据对象类型设置值.
                        Class<?> fieldType = field.getType();
                        cell2Field(entity, c, field, fieldType);

                    }
                    if (entity != null) {
                        list.add(entity);
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            return list;
        }
        return list;
    }


    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetNames 工作表的名称
     * @param output     java输出流
     * @return
     */
    public boolean exportExcel(List<T> lists[], String sheetNames[],
                               OutputStream output,ExcelVersion excelVersion) {
        if (lists.length != sheetNames.length) {
            return false;
        }

        Workbook workbook;// 产生工作薄对象

        // 根据版本选择接口实现类
        switch (excelVersion) {
            case EXCEL_VERSION_03:
                workbook = new HSSFWorkbook();
                break;
            case EXCEL_VERSION_07:
                workbook = new XSSFWorkbook();
                break;
            default:
                workbook = new HSSFWorkbook();
                break;
        }


        for (int ii = 0; ii < lists.length; ii++) {
            List<T> list = lists[ii];
            String sheetName = sheetNames[ii];

            List<Field> fields = getMappedFiled(clazz, null);

            Sheet sheet = workbook.createSheet();// 产生工作表对象

            workbook.setSheetName(ii, sheetName);

            Row row;
            Cell cell;// 产生单元格
            CellStyle style = workbook.createCellStyle();
            style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);
            style.setFillBackgroundColor(HSSFColor.GREY_40_PERCENT.index);
            row = sheet.createRow(0);// 产生一行
            // 写入各个字段的列头名称
            for (int i = 0; i < fields.size(); i++) {
                Field field = fields.get(i);
                ExcelVOAttribute attr = field
                        .getAnnotation(ExcelVOAttribute.class);
                int col = getExcelCol(attr.column());// 获得列号
                cell = row.createCell(col);// 创建列
                cell.setCellType(HSSFCell.CELL_TYPE_STRING);// 设置列中写入内容为String类型
                cell.setCellValue(attr.name());// 写入列名

                // 如果设置了提示信息则鼠标放上去提示.
                if (!attr.prompt().trim().equals("")) {
                    setPrompt(sheet, "", attr.prompt(), 1, 100, col, col,excelVersion);// 这里默认设了2-101列提示.
                }
                // 如果设置了combo属性则本列只能选择不能输入
                if (attr.combo().length > 0) {
                    setHSSFValidation(sheet, attr.combo(), 1, 100, col, col,excelVersion);// 这里默认设了2-101列只能选择不能输入.
                }
                cell.setCellStyle(style);
            }

            int startNo = 0;
            int endNo = list.size();
            // 写入各条记录,每条记录对应excel表中的一行
            for (int i = startNo; i < endNo; i++) {
                row = sheet.createRow(i + 1 - startNo);
                T vo = (T) list.get(i); // 得到导出对象.
                for (int j = 0; j < fields.size(); j++) {
                    Field field = fields.get(j);// 获得field.
                    field.setAccessible(true);// 设置实体类私有属性可访问
                    ExcelVOAttribute attr = field
                            .getAnnotation(ExcelVOAttribute.class);
                    try {
                        // 根据ExcelVOAttribute中设置情况决定是否导出,有些情况需要保持为空,希望用户填写这一列.
                        if (attr.isExport()) {
                            cell = row.createCell(getExcelCol(attr.column()));// 创建cell
                            cell.setCellType(HSSFCell.CELL_TYPE_STRING);
                            cell.setCellValue(field.get(vo) == null ? ""
                                    : String.valueOf(field.get(vo)));// 如果数据存在就填入,不存在填入空格.
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        try {
            output.flush();
            workbook.write(output);
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * 对list数据源将其里面的数据导入到excel表单
     *
     * @param sheetName 工作表的名称
     *                  //     * @param sheetSize
     *                  每个sheet中数据的行数,此数值必须小于65536
     * @param output    java输出流
     */
    @SuppressWarnings("unchecked")
    public boolean exportExcel(List<T> list, String sheetName,
                               OutputStream output,ExcelVersion excelVersion) {
        //此处 对类型进行转换
        List<T> ilist = new ArrayList<>();
        for (T t : list) {
            ilist.add(t);
        }
        List<T>[] lists = new ArrayList[1];
        lists[0] = ilist;

        String[] sheetNames = new String[1];
        sheetNames[0] = sheetName;

        return exportExcel(lists, sheetNames, output,excelVersion);
    }

    /**
     * 将EXCEL中A,B,C,D,E列映射成0,1,2,3
     *
     * @param col
     */
    public static int getExcelCol(String col) {
        col = col.toUpperCase();
        // 从-1开始计算,字母重1开始运算。这种总数下来算数正好相同。
        int count = -1;
        char[] cs = col.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            count += (cs[i] - 64) * Math.pow(26, cs.length - 1 - i);
        }
        return count;
    }

    /**
     * 设置单元格上提示
     *
     * @param sheet         要设置的sheet.
     * @param promptTitle   标题
     * @param promptContent 内容
     * @param firstRow      开始行
     * @param endRow        结束行
     * @param firstCol      开始列
     * @param endCol        结束列
     * @return 设置好的sheet.
     */
    public static Sheet setPrompt(Sheet sheet, String promptTitle,
                                  String promptContent, int firstRow, int endRow, int firstCol,
                                  int endCol,ExcelVersion excelVersion) {
        // 构造constraint对象
        DataValidationConstraint constraint = DVConstraint
                .createCustomFormulaConstraint("DD1");


        // 四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation data_validation_view;

        switch (excelVersion) {
            case EXCEL_VERSION_03:
                data_validation_view = new HSSFDataValidation(
                        regions, constraint);
                break;
            case EXCEL_VERSION_07:
                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createCustomConstraint("DD1");
                data_validation_view = dvHelper.createValidation(
                        dvConstraint, regions);
                break;
            default:
                data_validation_view = new HSSFDataValidation(
                        regions, constraint);
                break;
        }

        // 插入提示信息
        data_validation_view.createPromptBox(promptTitle, promptContent);
        // 开启提示信息显示（03默认开启，07默认不开启）
        data_validation_view.setShowPromptBox(true);
        sheet.addValidationData(data_validation_view);
        return sheet;
    }

    /**
     * 设置某些列的值只能输入预制的数据,显示下拉框.
     *
     * @param sheet    要设置的sheet.
     * @param textlist 下拉框显示的内容
     * @param firstRow 开始行
     * @param endRow   结束行
     * @param firstCol 开始列
     * @param endCol   结束列
     * @return 设置好的sheet.
     */
    public static Sheet setHSSFValidation(Sheet sheet,
                                          String[] textlist, int firstRow, int endRow, int firstCol,
                                          int endCol,ExcelVersion excelVersion) {
        // 加载下拉列表内容
        DVConstraint constraint = DVConstraint
                .createExplicitListConstraint(textlist);
        // 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
        CellRangeAddressList regions = new CellRangeAddressList(firstRow,
                endRow, firstCol, endCol);
        // 数据有效性对象
        DataValidation data_validation_list;
        switch (excelVersion) {
            case EXCEL_VERSION_03:
                data_validation_list = new HSSFDataValidation(
                        regions, constraint);
                break;
            case EXCEL_VERSION_07:
                XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper((XSSFSheet) sheet);
                XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper
                        .createExplicitListConstraint(textlist);
                data_validation_list = dvHelper.createValidation(
                        dvConstraint, regions);
                break;
            default:
                data_validation_list = new HSSFDataValidation(
                        regions, constraint);
                break;
        }
        sheet.addValidationData(data_validation_list);
        return sheet;
    }

    /**
     * 得到实体类所有通过注解映射了数据表的字段
     *
     * @return
     */
    @SuppressWarnings("rawtypes")
    private List<Field> getMappedFiled(Class clazz, List<Field> fields) {
        if (fields == null) {
            fields = new ArrayList<Field>();
        }

        Field[] allFields = clazz.getDeclaredFields();// 得到所有定义字段
        // 得到所有field并存放到一个list中.
        for (Field field : allFields) {
            if (field.isAnnotationPresent(ExcelVOAttribute.class)) {
                fields.add(field);
            }
        }
        if (clazz.getSuperclass() != null
                && !clazz.getSuperclass().equals(Object.class)) {
            getMappedFiled(clazz.getSuperclass(), fields);
        }

        return fields;
    }

    /**
     * 将单元格中的数据根据类型填入对象
     * @param entity
     * @param cell
     * @param field
     * @param fieldType
     * @throws IllegalAccessException
     */
    private void cell2Field(T entity, String cell, Field field, Class<?> fieldType) throws IllegalAccessException {
        if (String.class == fieldType) {
            // 如果是时间类型，转换成 HH:mm 格式
            if (field.getAnnotation(ExcelVOAttribute.class).type() == ExcelType.TIME) {
                SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                Date date = DateUtil.getJavaDate(Double.parseDouble(cell));
                field.set(entity, format.format(date));
            } else {
                field.set(entity, String.valueOf(cell));
            }
        } else if ((Integer.TYPE == fieldType)
                || (Integer.class == fieldType)) {
            field.set(entity, Integer.parseInt(cell));
        } else if ((Long.TYPE == fieldType)
                || (Long.class == fieldType)) {
            // 如果是日期类型，格式化成时间戳
            if (field.getAnnotation(ExcelVOAttribute.class).type() == ExcelType.DATE) {
                field.set(entity, (Long.valueOf(cell) - 25569) * 86400);  //(Long.valueOf(c) - 70 * 365 - 19) * 86400)
            } else {
                field.set(entity, Long.valueOf(cell));
            }
        } else if ((Float.TYPE == fieldType)
                || (Float.class == fieldType)) {
            field.set(entity, Float.valueOf(cell));
        } else if ((Short.TYPE == fieldType)
                || (Short.class == fieldType)) {
            field.set(entity, Short.valueOf(cell));
        } else if ((Double.TYPE == fieldType)
                || (Double.class == fieldType)) {
            field.set(entity, Double.valueOf(cell));
        } else if (Character.TYPE == fieldType) {
            if ((cell != null) && (cell.length() > 0)) {
                field.set(entity, Character
                        .valueOf(cell.charAt(0)));
            }
        }
    }

    public enum ExcelVersion {
        // 03 版本
        EXCEL_VERSION_03,

        // 07 版本
        EXCEL_VERSION_07
    }

} 