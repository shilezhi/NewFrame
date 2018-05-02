

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
      
    //*************xls�ļ���ȡ����************************  
    //excel_nameΪ�ļ�����argΪ��Ҫ��ѯ���к�  
    //���ض�ά�ַ�������  
    @SuppressWarnings({ "resource", "unused" })  
    public ArrayList<ArrayList<String>> xls_reader(String excel_url,int ... args) throws IOException {  
  
        //��ȡxls�ļ�  
        HSSFWorkbook hssfWorkbook = null;  
        //Ѱ��Ŀ¼��ȡ�ļ�  
        File excelFile = new File(excel_url); 
        InputStream is = new FileInputStream(excelFile);  
        hssfWorkbook = new HSSFWorkbook(is);  
        
        if(hssfWorkbook==null){  
            System.out.println("δ��ȡ������,����·����");  
            return null;  
        }  
          
        ArrayList<ArrayList<String>> ans=new ArrayList<ArrayList<String>>();  
        //����xls�е�sheet(��)  
        for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {  
        	
            HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);  
            
            if (hssfSheet == null) {  
                continue;  
            }  
            // ����ÿ��sheet����ȡ���е�ÿһ��  
            for (int rowNum = 0; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {  
                HSSFRow hssfRow = hssfSheet.getRow(rowNum);  
                
                if (hssfRow == null) continue;   //����ǿ�����������
                
                ArrayList<String> curarr=new ArrayList<String>();  
                
                for(int columnNum = 0 ; columnNum<args.length ; columnNum++){  //�Ӳ����л�ȡ��������
                	
                    HSSFCell cell = hssfRow.getCell(args[columnNum]);  //���λ�ȡÿ�����е�����
                      
                    curarr.add( Trim_str( getValue(cell) ) );  //��������ӽ�curarr���鴢��
                }  
                ans.add(curarr);  //��ÿ�е�curarr������ӽ�ans��ά���鱣��
            }  
        }  
        return ans;  
    }  
      

      
    //�жϺ�׺Ϊxls��excel�ļ�����������  
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
      
  //�ַ����޼�  ȥ�����пհ׷��� �� �ʺ� �� ���Ŀո�  
    static private String Trim_str(String str){  
        if(str==null)  
            return null;  
        return str.replaceAll("[\\s\\?]", "").replace("��", "");  
    }  
}