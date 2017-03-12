package com.fc.focus.api.common;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import groovy.util.GroovyScriptEngine;
import groovy.util.ResourceException;
import groovy.util.ScriptException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.sound.midi.MidiDevice;
import java.io.*;
import java.util.*;

/**
 * Note : 解析Excel工具类
 * Author : hwa
 * Date : 2016/1/27
 */
public class ExcelUtil {


    //得到工作簿
    public static XSSFSheet getSheet() throws FileNotFoundException {

        String filePath = PropertiesUtil.getProperties().getProperty("excelUrl");
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 读取第一章表格内容
        XSSFSheet sheet = wb.getSheetAt(0);

        return sheet;
    }

    public static List<XSSFSheet> getSheetList() throws FileNotFoundException {

        String filePath = PropertiesUtil.getProperties().getProperty("excelUrl");
        XSSFWorkbook wb = null;
        try {
            wb = new XSSFWorkbook(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 通过sheet名获取sheet
        String sheetName = PropertiesUtil.getProperties().getProperty("sheets");
        String[] sheets = sheetName.split(",");
        List<XSSFSheet> sheetList = new ArrayList<XSSFSheet>();
        for(String str : sheets){
            sheetList.add(wb.getSheet(str));
        }
        return sheetList;
    }



    private static int getRowNumSum(List<XSSFSheet> sheetList){

        int sum = 0 ;
        for(XSSFSheet sheet : sheetList){
            sum += (getRealRowNum(sheet)-1);
        }
        return sum;
    }

    public static Object[][] getRowDataSum(List<XSSFSheet> sheetList){

        int rowNums = getRowNumSum(sheetList);
        Object[][] dataProvider = new Object[rowNums][1];
        int index = 0 ;
        for(XSSFSheet sheet : sheetList){
            Object[][] dataObj = getRowData(sheet);
            for(int i = 0 ; i<dataObj.length; i++){
                dataProvider[index][0] = dataObj[i][0];
                index++;
            }
        }
        System.out.println("===============开始测试=================");
        return dataProvider;
    }

    //解析每一行数据
    public static Object[][] getRowData(Sheet sheet) {


        int rowNums = getRealRowNum(sheet) - 1;
        Object[][] dataProvider = new Object[rowNums][1];

        for (int i = 1; i < rowNums + 1; i++) {


            String type = sheet.getSheetName()+"_"+"第"+(i+1)+"行"+"_"+sheet.getRow(i).getCell(0).toString();
            try {
                TestCaseExcel testCase = new TestCaseExcel();
                String URL = sheet.getRow(i).getCell(1).toString();

                String paramJson = "";
                if (sheet.getRow(i).getCell(2) != null) {
                    paramJson = sheet.getRow(i).getCell(2).toString();
                }

                String str = sheet.getRow(i).getCell(3).toString();
                String[] headers = str.split("\\n");
                HashMap<String, String> header = new HashMap<String, String>();
                for (String s : headers) {
                    header.put(s.substring(0, s.indexOf(":")), s.substring(s.indexOf(":") + 1, s.length()));
                }

                String method = sheet.getRow(i).getCell(4).toString();
                String auth = sheet.getRow(i).getCell(5).toString();
                String assertType = sheet.getRow(i).getCell(6).toString();

                String groovyScript = "";
                if (sheet.getRow(i).getCell(7) != null) {
                    groovyScript = sheet.getRow(i).getCell(7).toString();
                }

                String expected = "";
                if (sheet.getRow(i).getCell(8).getCellType() == Cell.CELL_TYPE_NUMERIC) {
                    Object obj = (Object) Math.round(sheet.getRow(i).getCell(8).getNumericCellValue());
                    expected = obj.toString();
                } else {
                    expected = sheet.getRow(i).getCell(8).toString();
                }

                testCase.setType(type);
                testCase.setUrl(URL);
                testCase.setParamJson(paramJson);
                testCase.setHeader(header);
                testCase.setMethod(method);
                testCase.setAuth(auth);
                testCase.setGroovyScript(groovyScript);
                testCase.setAssertType(assertType);
                testCase.setExpected(expected);

                //将数据放入[][]
                dataProvider[i - 1][0] = testCase;
                System.out.println("读取成功："+type);
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("读取失败："+type);
            }


        }

        return dataProvider;
    }

    private TestCaseExcel getTestCase(Sheet sheet){


        return null;
    }

    //使用GroovyScriptEngine执行Groovy脚本
    public static Object getGroovyResult1(String json, String fileName) throws IOException, ResourceException, ScriptException {

        String dir = Class.class.getClass().getResource("/").getPath();
        String[] roots = new String[]{dir};

        GroovyScriptEngine engine = new GroovyScriptEngine(roots);

        Binding binding = new Binding();
        binding.setVariable("json", json);
        Object result = engine.run(fileName, binding);
        return result;
    }

    //使用GroovyShell.eval()执行Groovy脚本
    public static Object getGroovyResult(String json, String script) throws IOException, ResourceException, ScriptException {

        Binding bind = new Binding();
        bind.setVariable("json", json);
        GroovyShell shell = new GroovyShell(bind);
        Object result = shell.evaluate(script);

        return result;
    }

    public static String generateGroovyScript(String script) throws IOException {
        String dir = Class.class.getClass().getResource("/").getPath();
        String filename = "a" + UUID.randomUUID().toString().replace("-", "") + ".groovy";
        FileWriter fileWriter = new FileWriter(dir + filename);
        BufferedWriter writer = new BufferedWriter(fileWriter);
        writer.write(script);
        writer.close();
        return filename;
    }

    public static void deleteGroovyFile(String filePath) {
        String dir = Class.class.getClass().getResource("/").getPath();
        File file = new File(dir + filePath);
        if (file.isFile()) {
            file.delete();
        }
    }

    public static int getRealRowNum(Sheet sheet) {

        int lastRowNum = sheet.getLastRowNum();
        int count = 0;
        for (int i = 0; i <= lastRowNum; i++) {
            if (sheet.getRow(i).getCell(1).toString() != "") {
                count++;
            }
        }
        return count;
    }


    public static void main(String[] args) throws IOException {

        getRowData(getSheet());

    }


}
