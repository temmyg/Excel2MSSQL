package com.springboot_camel.base.excel;

import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.ArrayList;

public class SheetHolder {
    public ArrayList<HashMap<String, Object>>  sheetData;
    public int sheets;

    @Autowired
    private ExcelConverterBean xlsxConverter;
}
