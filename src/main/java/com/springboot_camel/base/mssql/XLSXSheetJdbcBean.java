package com.springboot_camel.base.mssql;

import com.springboot_camel.base.excel.SheetHolder;
import org.apache.camel.Body;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service("jdbcTemplateBean")
public class XLSXSheetJdbcBean {

    @Autowired
    @Qualifier("sheetDataRepository")
    private XLSXSheetsRepository repository;

    public int insertRows(@Body SheetHolder sheetRows){
        int num;
        try {
            num = repository.insertRow(sheetRows);
        }
        catch (Exception e) {
            throw e;
        }
        return num;
    }
}
