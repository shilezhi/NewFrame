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
		
		
		 //����ָ����·��  
        String indexDir1 = "E:\\Index1";  
        String indexDir2 = "E:\\Index2";
        
        String docDir1 = "E:\\ҽ��.xls";
        String docDir2 = "E:\\���ͼ�����ѯ����.xls";
        
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
		label.setFont(new Font("��������", Font.BOLD, 18));
		label.setBounds(34, 30, 208, 18);
		contentPane.add(label);
		
		JLabel lblNewLabel = new JLabel(text);
		lblNewLabel.setFont(new Font("��������", Font.PLAIN, 16));
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
		label_1.setFont(new Font("��������", Font.BOLD, 18));
		label_1.setBounds(424, 30, 72, 18);
		contentPane.add(label_1);
		         
        //��ѯ���ֶ�  
    
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
		  
	        //���ķִ���  
	        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();  
	          
	        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);  
	          
	        IndexWriter writer=new IndexWriter(dir, iwc);  
	          
	        return writer;  
	    }  
	      
	    /** 
	     * ��ȡindexDir 
	     * @param indexDir 
	     * @throws Exception 
	     */  
	  private void index(String indexDir,String docDir)throws Exception{  
	          
	        dir=FSDirectory.open(Paths.get(indexDir));  
	          
	        IndexWriter writer=getWriter();  
	        
	        Excel_reader test= new Excel_reader();   
	        ArrayList<ArrayList<String>> arr=test.xls_reader(docDir,0,1,2,3,4,9);  //����Ĳ���������Ҫ�����Щ�У�����������������  
	        
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
          
          //�õ���ȡ�����ļ���·��  
          Directory dir = FSDirectory.open(Paths.get(indexDir));  
                
          //ͨ��dir�õ���·���µ����е��ļ�  
          IndexReader reader = DirectoryReader.open(dir);  
                
          //����������ѯ��  
          IndexSearcher searcher = new IndexSearcher(reader);  
                
          //���ķִ���  
          SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();  
                
          //������ѯ������  
          /** 
           * ��һ��������Ҫ��ѯ���ֶΣ� 
           * �ڶ��������Ƿ�����Analyzer 
           * */  
          QueryParser parser = new QueryParser("symptoms", analyzer);  
                
          //���ݴ�������par����  
          Query query = parser.parse(par);  
                
          //����������ʼʱ��  
          long start = System.currentTimeMillis();  
                
          //��ʼ��ѯ  
          /** 
           * ��һ��������ͨ���������Ĳ��������ҵõ���query�� 
           * �ڶ���������Ҫ����ѯ������ 
           * */  
          TopDocs topDocs = searcher.search(query, 10);  
                
          //��������ʱ��  
          long end = System.currentTimeMillis();  
                
          System.out.println("ƥ��"+par+",�ܹ�������"+(end-start)+"����,���鵽"+topDocs.totalHits+"����¼��");  
                
                
          
                
              //����topDocs  
              /** 
               * ScoreDoc:�Ǵ���һ���������ضȵ÷����ĵ���ŵ���Ϣ�Ķ��� 
               * scoreDocs:�����ļ������� 
               * @throws Exception  
               * */  
              for(ScoreDoc scoreDoc : topDocs.scoreDocs){  
                    
                  //��ȡ�ĵ�  
                  Document document = searcher.doc(scoreDoc.doc);  
                    
                  //���ȫ·��  
                  
                  
                  System.out.println(document.get("description"));  
                  /*text1 = "��Ĳ�������"+document.get("body_part"); 
                  text2 = "Ӧ��ȥ"+document.get("clinic_department")+"����";
                  text3 =  document.get("description");
                  text4 =  document.get("name");*/
                    
                  
      }  
                
      reader.close();  
  }  
}
