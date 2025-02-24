package com.empSkl.mvcProject.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HomeController {

    public String skillStr = "", levelStr = "";


    @RequestMapping("/")
    public String home(){
        return "index.jsp";
    }

    @RequestMapping("/add") // "add"
    public String add(HttpServletRequest req) {

        skillStr = req.getParameter("skill");
        levelStr = req.getParameter("level");

        filterAndSaveExcel(skillStr, levelStr);

        return "index.jsp";
    }

    @Autowired
    private FileUploadController fuc;

    public static final String INPUT_FILE2 = "EmployeeFile2.xlsx";
    private static final String OUTPUT_FILE = "target/classes/copied.xlsx";

    public void filterAndSaveExcel(String skill, String inputLevel) {
        try {
            File file = new ClassPathResource(INPUT_FILE2).getFile();
            FileInputStream inputStream = new FileInputStream(file);
            XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);

            XSSFSheet sheet = inputWorkbook.getSheetAt(0);

            Iterator<Row> rowIterator = sheet.iterator();

            List<String> list1 = new ArrayList<>();

            ArrayList<Integer> arr = new ArrayList<>();

            arr.add(0);

            while (rowIterator.hasNext()) {

                Row row = rowIterator.next();

                Iterator<Cell> cellIterator
                        = row.cellIterator();

                String temp = "";

                int currentRowInd = -1;

                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();

                    cell.getColumnIndex();
                    currentRowInd = cell.getRowIndex();
                    if (cell.getRowIndex() > 0 && (cell.getColumnIndex() == 16 || cell.getColumnIndex() == 17 || cell.getColumnIndex() == 18)) {
//                        System.out.println(cell.getColumnIndex() + " - " + cell.getStringCellValue());
                        list1.add(cell.getStringCellValue());
//                        temp.concat(cell.getStringCellValue());
                        temp = temp + cell.getStringCellValue();
                    }

                }

                String[] parts = temp.split(" ;");

                List<String> list3 = new ArrayList<>(Arrays.asList(parts));

                if (checkSubstringPresent(list3, skill, inputLevel)) {
                    arr.add(currentRowInd);
                }
            }

            addRowsToNewSheet(arr, skill, inputLevel);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    boolean checkSubstringPresent(List<String> list1, String skill, String level) {

        boolean skillFound = false, levelFound = false;

        level = level.toLowerCase();

        int n = list1.size();

        for (int i = 0; i < n; i++) {
            String bigger = list1.get(i).toLowerCase();

            if (bigger.contains(skill.toLowerCase())) {

                // minimum level logic
                boolean checkLevel =  (level.equals("e0") && bigger.matches(".*e[0-3].*"))
                        || (level.equals("e1") && bigger.matches(".*e[1-3].*"))
                        || (level.equals("e2") && bigger.matches(".*e[2-3].*"))
                        || (level.equals("e3") && bigger.contains("e3"))
                        || (level.equals("l1") && bigger.matches(".*l[1-3].*"))
                        || (level.equals("l2") && bigger.matches(".*l[2-3].*"))
                        || (level.equals("l3") && bigger.contains("l3"));

                if(checkLevel) return true;
            }
        }

        return false;
    }

    public static void addRowsToNewSheet(ArrayList<Integer> arr, String skill, String level) throws IOException {

        File file = new ClassPathResource(HomeController.INPUT_FILE2).getFile();
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);

        Sheet originalSheet = inputWorkbook.getSheetAt(0);

        Workbook newWorkbook = new XSSFWorkbook();

        Sheet newSheet = newWorkbook.createSheet("CopiedRows");

        int newRowIndex = 0;
        for (int rowIndex : arr) {
            Row originalRow = originalSheet.getRow(rowIndex);
            if (originalRow != null) {

                Row newRow = newSheet.createRow(newRowIndex++);

                for (int cellIndex = 0; cellIndex < originalRow.getPhysicalNumberOfCells(); cellIndex++) {
                    Cell originalCell = originalRow.getCell(cellIndex);
                    if (originalCell != null) {
                        Cell newCell = newRow.createCell(cellIndex);

                        switch (originalCell.getCellType()) {
                            case STRING:
                                newCell.setCellValue(originalCell.getStringCellValue());
                                break;
                            case NUMERIC:
                                newCell.setCellValue(originalCell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                newCell.setCellValue(originalCell.getBooleanCellValue());
                                break;
                            default:
                                newCell.setCellValue("");
                                break;
                        }
                    }
                }
            }
        }

        String outputFilePath = "target/classes/output_" + skill + "_" + level + ".xlsx";;

        try (FileOutputStream outFile = new FileOutputStream(new File(outputFilePath))) {
            newWorkbook.write(outFile);
            System.out.println("File is generated at path = " + outputFilePath);
        }

    }
}

// file upload option |^






//package com.empSkl.mvcProject;
//
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.poi.ss.usermodel.*;
//import org.apache.poi.xssf.usermodel.XSSFSheet;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.io.*;
//import java.util.*;
//
//@Controller
//public class HomeController {
//
//    public String skillStr = "", levelStr = "";
//
//    @RequestMapping("/")
//    public String home(){
//        return "index.jsp";
//    }
//    @RequestMapping("/add") // "add"
//    public String add(HttpServletRequest req){
//
//        skillStr = req.getParameter("skill");
//        levelStr = req.getParameter("level");
//
//        filterAndSaveExcel(skillStr, levelStr);
//
//        return "result.jsp";
//    }
//
//    public static final String INPUT_FILE2 = "EmployeeFile2.xlsx";
//    private static final String OUTPUT_FILE = "target/classes/copied.xlsx";
//
//    public void filterAndSaveExcel(String skill, String inputLevel) {
//        try {
//            File file = new ClassPathResource(INPUT_FILE2).getFile();
//            FileInputStream inputStream = new FileInputStream(file);
//            XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);
//
//            XSSFSheet sheet = inputWorkbook.getSheetAt(0);
//
//            Iterator<Row> rowIterator = sheet.iterator();
//
//            List<String> list1 = new ArrayList<>();
//
//            ArrayList<Integer> arr = new ArrayList<>();
//
//            arr.add(0);
//
//            while (rowIterator.hasNext()) {
//
//                Row row = rowIterator.next();
//
//                Iterator<Cell> cellIterator
//                        = row.cellIterator();
//
//                String temp = "";
//
//                int currentRowInd = -1;
//
//                while (cellIterator.hasNext()) {
//
//                    Cell cell = cellIterator.next();
//
//                    cell.getColumnIndex();
//                    currentRowInd = cell.getRowIndex();
//                    if (cell.getRowIndex() > 0 && (cell.getColumnIndex() == 16 || cell.getColumnIndex() == 17 || cell.getColumnIndex() == 18)) {
////                        System.out.println(cell.getColumnIndex() + " - " + cell.getStringCellValue());
//                        list1.add(cell.getStringCellValue());
////                        temp.concat(cell.getStringCellValue());
//                        temp = temp + cell.getStringCellValue();
//                    }
//
//                }
//
//                String[] parts = temp.split(" ;");
//
//                List<String> list3 = new ArrayList<>(Arrays.asList(parts));
//
//                if (checkSubstringPresent(list3, skill, inputLevel)) {
//                    arr.add(currentRowInd);
//                }
//            }
//
//            addRowsToNewSheet(arr, skill, inputLevel);
//
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    boolean checkSubstringPresent(List<String> list1, String skill, String level) {
//
//        boolean skillFound = false, levelFound = false;
//
//        level = level.toLowerCase();
//
//        int n = list1.size();
//
//        for (int i = 0; i < n; i++) {
//            String bigger = list1.get(i).toLowerCase();
//
//            if (bigger.contains(skill.toLowerCase())) {
//
//                // minimum level logic
//                boolean checkLevel =  (level.equals("e0") && bigger.matches(".*e[0-3].*"))
//                        || (level.equals("e1") && bigger.matches(".*e[1-3].*"))
//                        || (level.equals("e2") && bigger.matches(".*e[2-3].*"))
//                        || (level.equals("e3") && bigger.contains("e3"))
//                        || (level.equals("l1") && bigger.matches(".*l[1-3].*"))
//                        || (level.equals("l2") && bigger.matches(".*l[2-3].*"))
//                        || (level.equals("l3") && bigger.contains("l3"));
//
//                if(checkLevel) return true;
//            }
//        }
//
//        return false;
//    }
//
//    public static void addRowsToNewSheet(ArrayList<Integer> arr, String skill, String level) throws IOException {
//
//        File file = new ClassPathResource(HomeController.INPUT_FILE2).getFile();
//        FileInputStream inputStream = new FileInputStream(file);
//        XSSFWorkbook inputWorkbook = new XSSFWorkbook(inputStream);
//
//        Sheet originalSheet = inputWorkbook.getSheetAt(0);
//
//        Workbook newWorkbook = new XSSFWorkbook();
//
//        Sheet newSheet = newWorkbook.createSheet("CopiedRows");
//
//        int newRowIndex = 0;
//        for (int rowIndex : arr) {
//            Row originalRow = originalSheet.getRow(rowIndex);
//            if (originalRow != null) {
//
//                Row newRow = newSheet.createRow(newRowIndex++);
//
//                for (int cellIndex = 0; cellIndex < originalRow.getPhysicalNumberOfCells(); cellIndex++) {
//                    Cell originalCell = originalRow.getCell(cellIndex);
//                    if (originalCell != null) {
//                        Cell newCell = newRow.createCell(cellIndex);
//
//                        switch (originalCell.getCellType()) {
//                            case STRING:
//                                newCell.setCellValue(originalCell.getStringCellValue());
//                                break;
//                            case NUMERIC:
//                                newCell.setCellValue(originalCell.getNumericCellValue());
//                                break;
//                            case BOOLEAN:
//                                newCell.setCellValue(originalCell.getBooleanCellValue());
//                                break;
//                            default:
//                                newCell.setCellValue("");
//                                break;
//                        }
//                    }
//                }
//            }
//        }
//
//        String outputFilePath = "target/classes/output_" + skill + "_" + level + ".xlsx";;
//
//        try (FileOutputStream outFile = new FileOutputStream(new File(outputFilePath))) {
//            newWorkbook.write(outFile);
//            System.out.println("File is generated at path = " + outputFilePath);
//        }
//
//    }
//}

