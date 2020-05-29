import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextPane;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.AbstractListModel;
import javax.swing.border.LineBorder;

public class AssetsUpdater {

	private JFrame frmAssetsupdater;
	private JTextField newAssetsField;
	private JTextField maxFolderField;
	private JTextField pdfPath;
	private JTextField templateLocation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AssetsUpdater window = new AssetsUpdater();
					window.frmAssetsupdater.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public AssetsUpdater() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	public void initialize() {
		frmAssetsupdater = new JFrame();
		frmAssetsupdater.setIconImage(Toolkit.getDefaultToolkit().getImage(AssetsUpdater.class.getResource("/pngimages/ratnasagar3.png")));
		frmAssetsupdater.setTitle("RSPL Teacher Assets Updater");
		frmAssetsupdater.setBounds(100, 100, 714, 460);
		frmAssetsupdater.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmAssetsupdater.getContentPane().setLayout(null);
		
		
		
		
		
		JTextPane statusPanel = new JTextPane();
		statusPanel.setText("Click On Options to Run the Task");
		statusPanel.setFont(new Font("Calibri", Font.PLAIN, 12));
		statusPanel.setForeground(Color.CYAN);
		statusPanel.setBackground(Color.BLACK);
		statusPanel.setEditable(false);
		statusPanel.setBounds(0, 308, 696, 113);
		frmAssetsupdater.getContentPane().add(statusPanel);
		
		JButton updateFileName = new JButton("UPDATE FILE NAME");
		updateFileName.setForeground(Color.WHITE);
		updateFileName.setBackground(Color.ORANGE);
		updateFileName.setFont(new Font("Tahoma", Font.BOLD, 12));
		updateFileName.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				NameUpdater obj = new NameUpdater();
				String newAssetsPath = newAssetsField.getText();
				statusPanel.setText("Starting...");
				obj.start(obj, newAssetsPath);
				
				statusPanel.setText("Total Chapters in current Folder: "+ obj.totalChapters+ "\n" + "File Updation Done");
			}
		});
		updateFileName.setBounds(458, 239, 160, 40);
		frmAssetsupdater.getContentPane().add(updateFileName);
		
		
		
		JLabel lblNewLabel = new JLabel("STATUS");
		lblNewLabel.setFont(new Font("Calibri", Font.BOLD, 14));
		lblNewLabel.setBounds(325, 291, 46, 14);
		frmAssetsupdater.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.GREEN);
		panel.setForeground(Color.GREEN);
		panel.setBounds(0, 289, 696, 19);
		frmAssetsupdater.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("UPDATE MAX");
		btnNewButton.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.setBackground(Color.BLUE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AssetsCounter obj = new AssetsCounter();
				String newAssetsPath = newAssetsField.getText();
				
				statusPanel.setText("Starting...");
				obj.start(obj, newAssetsPath);
				
				statusPanel.setText("Total Chapters in current Folder: "+ obj.totalChapters+ "\n" + "Index updated in chapters:"+"\n"+ obj.chapNames);
			}
		});
		btnNewButton.setBounds(296, 239, 145, 40);
		frmAssetsupdater.getContentPane().add(btnNewButton);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				statusPanel.setText("Help: \n1. Copy this application to required assets folder. \n2. The index.html file should contains this code: 'var Max = 1' \n3. The index.html file should contains this word: 'LessonPlan' as a file name.\n4. There is no problem if there is a thumbs.db file in images folder, it will only count the images.");
			}
		});
		lblNewLabel_2.setIcon(new ImageIcon(AssetsUpdater.class.getResource("/pngimages/help.png")));
		lblNewLabel_2.setBounds(650, 52, 30, 40);
		frmAssetsupdater.getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
								
				statusPanel.setText("This application helps to update images count and pdf file names in\nindex.html pages all at once.\n\n\n\t\t\t Developed by a Lazy Programmer :)");
			}
		});
		lblNewLabel_1.setIcon(new ImageIcon(AssetsUpdater.class.getResource("/pngimages/about1.png")));
		lblNewLabel_1.setBounds(650, 0, 46, 47);
		frmAssetsupdater.getContentPane().add(lblNewLabel_1);
		
		JLabel lblLocationForNew = new JLabel("Location For New Assets");
		lblLocationForNew.setBounds(21, 29, 145, 14);
		frmAssetsupdater.getContentPane().add(lblLocationForNew);
		
		newAssetsField = new JTextField();
		newAssetsField.setBounds(196, 26, 423, 20);
		frmAssetsupdater.getContentPane().add(newAssetsField);
		newAssetsField.setColumns(10);
		
		JLabel lblMaxFolders = new JLabel("Max folders");
		lblMaxFolders.setBounds(197, 75, 69, 14);
		frmAssetsupdater.getContentPane().add(lblMaxFolders);
		
		
		
		maxFolderField = new JTextField();
		maxFolderField.setBounds(276, 72, 46, 20);
		frmAssetsupdater.getContentPane().add(maxFolderField);
		maxFolderField.setColumns(10);
		
		JLabel lblPdfLocations = new JLabel("PDF Location");
		lblPdfLocations.setBounds(21, 117, 92, 14);
		frmAssetsupdater.getContentPane().add(lblPdfLocations);
		
		pdfPath = new JTextField();
		pdfPath.setBounds(196, 114, 423, 20);
		frmAssetsupdater.getContentPane().add(pdfPath);
		pdfPath.setColumns(10);
		
		JButton btnCreateAssets = new JButton("CREATE ASSETS");
		btnCreateAssets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				statusPanel.setText("Please Wait...");
				String newAssetsPath = newAssetsField.getText();
				String templatePath = templateLocation.getText();
				String pdfPATH = pdfPath.getText();
				int foldercount  = Integer.parseInt(maxFolderField.getText());
				FolderCreator obj = new FolderCreator();
				boolean flag = obj.createFolders(foldercount, newAssetsPath);
				if(flag) {
					try {
						obj.copyTemplate(templatePath, newAssetsPath, foldercount);
						obj.copyPdf(pdfPATH, newAssetsPath, foldercount);
						statusPanel.setText("Assets Creation Done");
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				statusPanel.setText("Assets created \nKindly click on 'UPDATE MAX' AND 'UPDATE FILE NAME' to update index file");
			}
		});
		btnCreateAssets.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCreateAssets.setBackground(Color.GREEN);
		btnCreateAssets.setBounds(21, 239, 176, 40);
		frmAssetsupdater.getContentPane().add(btnCreateAssets);
		
		JLabel lblTemplateLocation = new JLabel("Template location");
		lblTemplateLocation.setBounds(21, 169, 120, 14);
		frmAssetsupdater.getContentPane().add(lblTemplateLocation);
		
		templateLocation = new JTextField();
		templateLocation.setBounds(196, 166, 422, 20);
		frmAssetsupdater.getContentPane().add(templateLocation);
		templateLocation.setColumns(10);
		
		
		
		
	}
}
