package com.springboot_camel.base.excel;


import com.springboot_camel.base.model.ClubMember;
import com.springboot_camel.base.model.VisitHistory;
import org.apache.camel.*;
import org.springframework.stereotype.Service;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.datatype.DatatypeFactory;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.xssf.usermodel.*;

@Service("excelConverterBean")
public class ExcelConverterBean {
    private final static Log logger = LogFactory.getLog(ExcelConverterBean.class);
    private String fileType = "Excel";

    public void readExcelSheets (Message requestMessage, Exchange exchange, @Body InputStream body) throws Exception{  //@Body InputStream body

        // simulates unchecked exception
        if(exchange.getException()!=null)
            throw new Exception("manually error");

       // throw new Exception("manually error");

        ProducerTemplate pt = requestMessage.getExchange().getContext().createProducerTemplate();

        //--Endpoint mpt = exchange.getContext().getEndpoint("direct:middle");

        //--pt.sendBody("direct:middle", (Object)null);
        try {
            //-- body.close();
            XSSFWorkbook workbook = new XSSFWorkbook(body);
            Iterator shtItr = workbook.sheetIterator();
            logger.info(logger.getClass());
            logger.info("sheet Count:" + workbook.getNumberOfSheets());
            XSSFSheet sheet = null;
            for (int shtIndex = 0; shtItr.hasNext() && (sheet = (XSSFSheet) shtItr.next()).getPhysicalNumberOfRows() != 0; shtIndex++) {

                boolean headersFound = false;
                //  List<Object> rows = new ArrayList<>();
                for (Iterator rit = sheet.rowIterator(); rit.hasNext(); ) {
                    XSSFRow row = (XSSFRow) rit.next();
                    if (!headersFound) {  // Skip the first row with column headers
                        headersFound = true;
                        continue;
                    }
//                    colNum = 0;
//                    //LineItemType item = factory.createLineItemType();
//                    for (Iterator cit = row.cellIterator(); cit.hasNext(); ++colNum) {
//                        XSSFCell cell = (XSSFCell) cit.next();

                    if (headersFound) {
                        Object rowData = null;
                        switch (shtIndex) {
                            case 0:
                                ClubMember member = new ClubMember();
                                member.setAge((int) row.getCell(3).getNumericCellValue());
                                member.setFirstName(row.getCell(0).getStringCellValue());
                                member.setLastName(row.getCell(1).getStringCellValue());
                                member.setOccupation(row.getCell(2).getStringCellValue());
                                rowData = member;
                                break;
                            case 1:
                                VisitHistory record = new VisitHistory();
                                record.setExerciseZone(row.getCell(2).getStringCellValue());
                                record.setVisitedDate(new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
                                        .parse(row.getCell(1).getStringCellValue()));
                                record.setMemberId((long) row.getCell(0).getNumericCellValue());
                                rowData = record;

                                break;
                            default:
                                break;
                        }
//--                        }
//--                            switch (colNum) {
//--                                case 0: //-- Date
//--                                    GregorianCalendar calendar = new GregorianCalendar();
//--                                    calendar.setTime(cell.getDateCellValue());
//--                                    //-- item.setOrderDate(dateFactory.newXMLGregorianCalendar(calendar));
//--                                    break;
//--                                case 1: //-- Price
//--                                    //-- item.setItemPrice(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
//--                                    break;
//--                                case 2: //-- Quantity
//--                                    //-- item.setQuantity((int)cell.getNumericCellValue());
//--                                    break;
//--                                case 3: //-- Total
//--                                    BigDecimal total = new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP);
//--                                    //-- BigDecimal computed = item.getItemPrice().multiply(new BigDecimal(item.getQuantity()));
//--//--                                if(!total.equals(computed)) {
//--//--                                    log.warn("COMPUTED TOTAL INVALID ("+item.getItemPrice()+"x"+item.getQuantity()+" <> "+total);
//--//--                                }
//--                                    break;
//--                                case 4: //-- Name
//--//--                                item.setDescription(cell.getRichStringCellValue().getString());
//--                                    break;
//--                                case 5: //-- ID
//--//--                                item.setProductId((long)cell.getNumericCellValue());
//--                                    break;
//--                            }

                        pt.sendBody("bean:jdbcTemplateBean", rowData);
                    }
                }

            }
        } catch (Exception e) {
            logger.error("Unable to import Excel sheet", e);
            throw new RuntimeException("Unable to import Excel Sheet", e);
        }
        System.out.println("done!!");

    }
}
