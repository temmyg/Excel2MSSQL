package com.springboot_camel.base.mssql;

import com.springboot_camel.base.excel.SheetHolder;
import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("jdbcTemplateBean")
public class XLSXSheetJdbcBean {

    @Autowired
    @Qualifier("sheetDataRepository")
    private XLSXSheetsRepository repository;

    public int insertRows(@Body Object rowData) throws Exception {
        int num = 0;
        try {
            num = repository.insertRow(rowData);
        }
        catch (Exception e) {
            throw e;
        }
        return num;
    }
}
