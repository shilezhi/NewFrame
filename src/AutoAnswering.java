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
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths; 
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.JProgressBar;

public class AutoAnswering extends JFrame {

	private JPanel contentPane;


    
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AutoAnswering frame = new AutoAnswering();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AutoAnswering() {
		setBackground(Color.GRAY);
		
		
		setTitle("\u533B\u7597\u81EA\u52A8\u95EE\u7B54\u7CFB\u7EDF");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(SystemColor.menu);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel label = new JLabel("\u6B22\u8FCE\u4F7F\u7528\u533B\u7597\u81EA\u52A8\u95EE\u7B54\u7CFB\u7EDF\uFF01");
		label.setFont(new Font("华文隶书", Font.BOLD, 18));
		label.setBounds(14, 30, 256, 29);
		contentPane.add(label);
		
		JLabel label_1 = new JLabel("\u8BF7\u8F93\u5165\u60A8\u8981\u67E5\u8BE2\u7684\u75C5\u75C7\uFF1A");
		label_1.setFont(new Font("楷体", Font.PLAIN, 17));
		label_1.setBounds(52, 102, 218, 18);
		contentPane.add(label_1);
		
		JTextArea textArea = new JTextArea();
		textArea.setFont(new Font("华文宋体", Font.PLAIN, 15));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(52, 134, 213, 63);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton("\u67E5\u8BE2");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String getText = textArea.getText().trim();
				if(getText.length() != 0)
				{
					JFrame mainFrame = null;
					try {
						mainFrame = new mainFrame(getText);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					dispose();
					mainFrame.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(292, 149, 113, 27);
		contentPane.add(btnNewButton);
	}
	
	
}


