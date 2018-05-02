import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;


import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.event.ActionEvent;
import java.awt.Window.Type;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JTextArea;

public class mainFrame extends JFrame {

	
	private JPanel contentPane;
     
    private Directory dir;  
	
	
    static private String text1;
    static private String text2;
    static private String text3;
    static private String text4;

	/**
	 * Launch the application.
	 */
	/*public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainFrame frame = new mainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}*/

	/**
	 * Create the frame.
	 * @throws Exception 
	 */
	public mainFrame(String text) throws Exception {
		
		
		 //索引指定的路径  
        String indexDir1 = "E:\\Index1";  
        String indexDir2 = "E:\\Index2";
        
        String docDir1 = "E:\\医疗.xls";
        String docDir2 = "E:\\典型疾病查询数据.xls";
        
		index(indexDir1,docDir1); 
		
		setType(Type.POPUP);
		setTitle("\u533B\u7597\u81EA\u52A8\u95EE\u7B54\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 766, 463);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(10, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u60A8\u6240\u8F93\u5165\u7684\u67E5\u8BE2\u4FE1\u606F\uFF1A");
		label.setFont(new Font("华文隶书", Font.BOLD, 18));
		label.setBounds(34, 30, 208, 18);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("华文中宋", Font.PLAIN, 16));
		lblNewLabel.setBounds(57, 61, 185, 111);
		contentPane.add(lblNewLabel);
		
		JButton btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				AutoAnswering aFrame = new AutoAnswering();
				aFrame.setVisible(true);
				
			}
		});
		btnNewButton.setBackground(SystemColor.menu);
		btnNewButton.setIcon(new ImageIcon("C:\\Users\\madd\\Pictures\\arrow-small-09.png"));
		btnNewButton.setBounds(627, 345, 57, 46);
		contentPane.add(btnNewButton);
		
		JLabel label_1 = new JLabel("\u5206\u6790\uFF1A");
		label_1.setFont(new Font("华文隶书", Font.BOLD, 18));
		label_1.setBounds(424, 30, 72, 18);
		contentPane.add(label_1);
		         
        //查询的字段  
    
	        try {              
	            search(indexDir1,text);  
	                      
	        } catch (Exception e) {  
	        // TODO Auto-generated catch block  
	            e.printStackTrace();  
	        }  
        
	        JLabel lblNewLabel_1 = new JLabel("New label");
			lblNewLabel_1.setBounds(500, 89, 148, 18);
			lblNewLabel_1.setText(text1);
			contentPane.add(lblNewLabel_1);
			
			JLabel lblNewLabel_2 = new JLabel("New label");
			lblNewLabel_2.setBounds(500, 120, 157, 18);
			lblNewLabel_2.setText(text2);
			contentPane.add(lblNewLabel_2);
			
			JTextArea textArea = new JTextArea();
			textArea.setLineWrap(true);
			textArea.setBackground(SystemColor.menu);
			textArea.setBounds(483, 196, 229, 71);
			textArea.setText(text3);
			contentPane.add(textArea);
			
			JLabel lblNewLabel_4 = new JLabel("New label");
			lblNewLabel_4.setBounds(483, 289, 251, 43);
			lblNewLabel_4.setText(text4);
			contentPane.add(lblNewLabel_4);
			
			
        
        
	}
	
	private IndexWriter getWriter()throws Exception{  
		  
	        //中文分词器  
	        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();  
	          
	        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);  
	          
	        IndexWriter writer=new IndexWriter(dir, iwc);  
	          
	        return writer;  
	    }  
	      
	    /** 
	     * 获取indexDir 
	     * @param indexDir 
	     * @throws Exception 
	     */  
	  private void index(String indexDir,String docDir)throws Exception{  
	          
	        dir=FSDirectory.open(Paths.get(indexDir));  
	          
	        IndexWriter writer=getWriter();  
	        
	        Excel_reader test= new Excel_reader();   
	        ArrayList<ArrayList<String>> arr=test.xls_reader(docDir,0,1,2,3,4,9);  //后面的参数代表需要输出哪些列，参数个数可以任意  
	        
	        ArrayList<String> row1=arr.get(0);
	        String sColName[] = new String[6];
	        for(int m = 0 ; m < row1.size() ; m++ )
            {
            	sColName[m] = row1.get(m);
            }
	        
	        for(int i=1;i<arr.size();i++){  
	        	Document doc=new Document();
	            ArrayList<String> row=arr.get(i);

	            for(int j=0;j<row.size();j++){  
	            	doc.add(new TextField(sColName[j],row.get(j), Field.Store.YES));
	               // System.out.print(row.get(j)+" ");  
	            }  
	            writer.addDocument(doc);
	            System.out.println("");  
	        }  
      
	        writer.close();  
	    }
	  
	  public static void search(String indexDir, String par) throws Exception{  
          
          //得到读取索引文件的路径  
          Directory dir = FSDirectory.open(Paths.get(indexDir));  
                
          //通过dir得到的路径下的所有的文件  
          IndexReader reader = DirectoryReader.open(dir);  
                
          //建立索引查询器  
          IndexSearcher searcher = new IndexSearcher(reader);  
                
          //中文分词器  
          SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();  
                
          //建立查询解析器  
          /** 
           * 第一个参数是要查询的字段； 
           * 第二个参数是分析器Analyzer 
           * */  
          QueryParser parser = new QueryParser("symptoms", analyzer);  
                
          //根据传进来的par查找  
          Query query = parser.parse(par);  
                
          //计算索引开始时间  
          long start = System.currentTimeMillis();  
                
          //开始查询  
          /** 
           * 第一个参数是通过传过来的参数来查找得到的query； 
           * 第二个参数是要出查询的行数 
           * */  
          TopDocs topDocs = searcher.search(query, 10);  
                
          //索引结束时间  
          long end = System.currentTimeMillis();  
                
          System.out.println("匹配"+par+",总共花费了"+(end-start)+"毫秒,共查到"+topDocs.totalHits+"条记录。");  
                
                
          
                
              //遍历topDocs  
              /** 
               * ScoreDoc:是代表一个结果的相关度得分与文档编号等信息的对象。 
               * scoreDocs:代表文件的数组 
               * @throws Exception  
               * */  
              for(ScoreDoc scoreDoc : topDocs.scoreDocs){  
                    
                  //获取文档  
                  Document document = searcher.doc(scoreDoc.doc);  
                    
                  //输出全路径  
                  
                  
                  System.out.println(document.get("description"));  
                  /*text1 = "你的病出现在"+document.get("body_part"); 
                  text2 = "应该去"+document.get("clinic_department")+"治疗";
                  text3 =  document.get("description");
                  text4 =  document.get("name");*/
                    
                  
      }  
                
      reader.close();  
  }  
}
