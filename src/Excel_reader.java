

import java.io.File;  
import java.io.FileInputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.util.ArrayList;  
import java.util.Properties;  
  
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;  
import org.apache.poi.xssf.usermodel.XSSFRow;  
import org.apache.poi.xssf.usermodel.XSSFSheet;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  
  
public class Excel_reader {  
      
    //*************xls文件读取函数************************  
    //excel_name为文件名，arg为需要查询的列号  
    //返回二维字符串数组  
    @SuppressWarnings({ "resource", "unused" })  
    public ArrayList<ArrayList<String>> xls_reader(String excel_url,int ... args) throws IOException {  
  
        //读取xls文件  
        HSSFWorkbook hssfWorkbook = null;  
        //寻找目录读取文件  
        File excelFile = new File(excel_url); 
        InputStream is = new FileInputStream(excelFile);  
        hssfWorkbook = new HSSFWorkbook(is);  
        
        if(hssfWorkbook==null){  
            System.out.println("未读取到内容,请检查路径！");  
            return null;  
        }  
          
        ArrayList<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();  
        //遍历xls中的sheet(表)  
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {  
        	
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);  
            
            if (hssfSheet == null) {  
                continue;  
            }  
            // 对于每个sheet，读取其中的每一行  
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {  
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);  
                
                if (hssfRow == null) continue;   //如果是空行则不做处理
                
                ArrayList<String> curarr=new ArrayList<String>();  
                
                for(int columnNum = 0 ; columnNum<args.length ; columnNum++){  //从参数中获取所需列数
                	
                    HSSFCell cell = hssfRow.getCell(args[columnNum]);  //依次获取每个块中的内容
                      
                    curarr.add( Trim_str( getValue(cell) ) );  //将内容添加进curarr数组储存
                }  
                ans.add(curarr);  //将每行的curarr数组添加进ans二维数组保存
            }  
        }  
        return ans;  
    }  
      

      
    //判断后缀为xls的excel文件的数据类型  
    @SuppressWarnings("deprecation")  
    private static String getValue(HSSFCell hssfCell) {  
        if(hssfCell==null){  
            return "---";  
        }  
        if (hssfCell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {  
            return String.valueOf(hssfCell.getBooleanCellValue());  
        } else if (hssfCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {  
            double cur=hssfCell.getNumericCellValue();  
            long longVal = Math.round(cur);  
            Object inputValue = null;  
            if(Double.parseDouble(longVal + ".0") == cur)    
                    inputValue = longVal;    
            else    
                    inputValue = cur;   
            return String.valueOf(inputValue);  
        } else if(hssfCell.getCellType() == Cell.CELL_TYPE_BLANK || hssfCell.getCellType() == Cell.CELL_TYPE_ERROR){  
            return "---";  
        }  
        else {  
            return String.valueOf(hssfCell.getStringCellValue());  
        }  
    }  
      
  //字符串修剪  去除所有空白符号 ， 问号 ， 中文空格  
    static private String Trim_str(String str){  
        if(str==null)  
            return null;  
        return str.replaceAll("[\\s\\?]", "").replace("　", "");  
    }  
}