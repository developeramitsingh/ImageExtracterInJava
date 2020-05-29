
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.tools.imageio.ImageIOUtil;



public class FolderCreator {
	int folderCount;
	
	 boolean createFolders(int foldcount, String destination) {
		 folderCount = foldcount;
		 
		 Path parent,  parent2;
		 		 
		 for(int i=1;i<=folderCount;i++) {
			 try {
				 String Destination = destination + "\\ch"+i +"\\images";
				 String Destination2 = destination + "\\ch"+i +"\\js";
				 parent  = Paths.get(Destination);
				 parent2 = Paths.get(Destination2);
				Files.createDirectories(parent);
				Files.createDirectories(parent2);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 
			 System.out.println("Folder created Successfully");
			 
		 }
		
		return true;
	}
	
	private String[] getChapName(String path) {
		File chapsPath  = new File(path);
		String allChaps[] = chapsPath.list();
		
		String[] filter = Arrays.stream(allChaps).filter(str -> !str.contains(".")).toArray(String[] :: new);
		
		return filter;
	}
	
	 boolean copyTemplate(String source, String destination, int foldCount) throws IOException {
		File sourceJs = new File(source+"\\js");
		File sourceParent = new File(source+"\\");
		String stringJs[] = sourceJs.list();
		String stringParent[] = sourceParent.list();
		
		String nameIndex[] = Arrays.stream(stringParent).filter(str -> str.contains(".html")).toArray(String[] :: new);
		String imageName[]  = Arrays.stream(stringParent).filter(str -> str.contains(".png")).toArray(String[]:: new);
		
		Path sourceIndex = Paths.get(source+"\\"+nameIndex[0]);
		Path sourceJs1 = Paths.get(source+"\\js\\"+stringJs[0]);
		Path sourceJs2 = Paths.get(source+"\\js\\"+stringJs[1]);
		Path sourceBtnImg = Paths.get(source+"\\"+imageName[0]);
				
		String[] chapNames = getChapName(destination);
		
		for(int i=0;i<foldCount;i++) {
			
			Path DestinationIndex = Paths.get(destination+"\\"+chapNames[i]+"\\" + nameIndex[0]);
			Path DestinationJs1 = Paths.get(destination+"\\"+ chapNames[i]+ "\\js\\"+ stringJs[0]);
			Path DestinationJs2 = Paths.get(destination+"\\"+ chapNames[i]+ "\\js\\"+ stringJs[1]);
			Path DestinationBtnImg = Paths.get(destination+"\\"+chapNames[i]+"\\" + imageName[0]);

			Files.copy(sourceIndex, DestinationIndex, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(sourceJs1, DestinationJs1, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(sourceJs2, DestinationJs2, StandardCopyOption.REPLACE_EXISTING);
			Files.copy(sourceBtnImg, DestinationBtnImg, StandardCopyOption.REPLACE_EXISTING);
	
		}
						
		System.out.println("folder copied");
		
		return true;
	}
	 
	 void copyPdf(String source, String destination, int maxFolder) {
		 File file = new File(source);
		 String[] sourcePDF = file.list();
		 String[] destinationPDF = new String[30];
		 int length = sourcePDF.length;
		 
		 for(int i=0;i<maxFolder;i++) {
			 String filter = sourcePDF[i];
			 String temp = "_"+(i+1);
			 filter = filter.replaceAll(temp,"");
			 destinationPDF[i] = filter;
		}
		 

		 String[] chapNames = getChapName(destination);
		 
		 for(int i=0;i<maxFolder;i++) {
			 Path sourcePath = Paths.get(source+"\\"+ sourcePDF[i]);
			 Path destinationPath = Paths.get(destination+"\\"+chapNames[i]+"\\"+destinationPDF[i]);
			 try {
				Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		 
		 System.out.println("copied successfully");
		 extractPDF(destination);
	 }
	 
	 private void extractPDF(String destination) {
		 File folders = new File(destination);
		 String allFolders[] = folders.list();
		 int length = allFolders.length;
		 
 
		 for(int i =0;i<length;i++) {
			 File filePdf = new File(destination+"\\"+allFolders[i]);
			 
			 String[] allFiles = filePdf.list();

			 allFiles = Arrays.stream(allFiles).filter(str -> str.contains(".pdf")).toArray(String[]::new);
			 
			 File newPath = new File(destination+"\\"+allFolders[i]+"\\"+allFiles[0]);
			 
			 try {
					PDDocument document = PDDocument.load(newPath);
					PDFRenderer pdfRender = new PDFRenderer(document);
					int documentLength  = document.getNumberOfPages();
					System.out.println("length =" + documentLength);
					int p;
					for(int page=0;page<documentLength;++page) {
						System.out.println("page "+page);
						BufferedImage bim = pdfRender.renderImageWithDPI(page, 150, ImageType.RGB);
						String filename = new String();
						p = page+1;
						if(p<10) {
							System.out.println("p no:= "+p);
							filename = destination+"\\"+allFolders[i]+"\\images"+"\\"+ "image_00"+p+".jpg";
							System.out.println("file name= "+filename);
						}
						else if(p>=10&& p <=99) {
							System.out.println("p no:= "+p);
							filename = destination+"\\"+allFolders[i]+"\\images"+"\\"+ "image_0"+p+".jpg";
							System.out.println("file name= "+filename);
						}
						else if(p>99) {
							System.out.println("p no:= "+p);
							filename = destination+"\\"+allFolders[i]+"\\images"+"\\"+ "image_"+p+".jpg";
							System.out.println("file name= "+filename);
						}
						ImageIOUtil.writeImage(bim, filename, 150);
						System.out.println("filename " +filename);
					}
					
					document.close();
					System.out.println("Extraction done");
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("Exception while creating pdf to images:" + e);
				}

			
		 }
		 

	 }

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		FolderCreator obj = new FolderCreator();
		//obj.createFolders(5, "e:\\test\\");
		//obj.copyTemplate("h:\\template", "h:\\Worksheets", 3);
		//obj.copyPdf("h:\\pdf", "h:\\Worksheets");
		//obj.extractPDF("h:\\Worksheets", "h:\\Worksheets");
	}

}
