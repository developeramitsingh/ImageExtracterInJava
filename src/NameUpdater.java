
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class NameUpdater {
	
	int totalChapters;
	private String pdfName;
	ArrayList <String> chapNames = new ArrayList <String>();

		
	private int countChapters(String path) {
		int temp;
		File file = new File(path);
		String allChapters[] = file.list();
		allChapters = file.list();
		ArrayList <String> filter = new ArrayList <String>();
		temp = allChapters.length;
		for(int i=0;i<temp;i++) {
			filter.add(allChapters[i]);
		}
		
		filter.removeIf(str -> str.contains("."));
		totalChapters = filter.size();
		chapNames = filter;
		System.out.println("All Folders "+ chapNames);
		return totalChapters;
	}
	
	private String getPdfName(String path, String chap) {
		String files[];
		File file = new File(path);
		files = file.list();
		ArrayList <String> filter = new ArrayList <String> ();
		for(int i=0;i<files.length;i++) {
			filter.add(files[i]);
		}
				
		filter.removeIf(str -> !str.endsWith("pdf"));
		String temp = filter.get(0);
		pdfName= temp.replace(".pdf", "");
		System.out.println("File found: "+ pdfName + " in chapter: "+chap);
		return pdfName;
	}
	

	private void writeIndexFile(String path, String name, int chap) {
		try {
			//RandomAccessFile file = new RandomAccessFile(path, "rw");
			//file.seek(10);
			//file.write(name.getBytes());
			//file.seek(28);
			//file.write(name.getBytes());
			//file.close();
			Path pdfpath = Paths.get(path);
			Stream <String> streamLines = Files.lines(pdfpath);
			List <String> replace = streamLines.map(str -> str.replaceAll("LessonPlan", name)).collect(Collectors.toList());
			Files.write(pdfpath, replace);
			streamLines.close();
			System.out.println("Index updated of chapter "+chap+" with this name "+ name + " at two locations");
		}
		catch(Exception e) {
			System.out.println("Index file writing fail" + e);
		}
		
	}
	
	
	public void start(NameUpdater obj, String CurrentPath) {
		String currentPath = CurrentPath;
		String chaptersPath  = currentPath;
		String pdfPath,indexPath;  
		obj.countChapters(chaptersPath);
				
		System.out.println("Total Chapters in current Folder: "+ obj.totalChapters);
		
		for(int i=0; i<obj.totalChapters; i++) {
			pdfPath  = currentPath+"\\"+obj.chapNames.get(i);
			obj.getPdfName(pdfPath, obj.chapNames.get(i));
			indexPath = currentPath+"\\"+obj.chapNames.get(i)+"\\index.html";
			obj.writeIndexFile(indexPath, obj.pdfName, i+1);
			
		}
			
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
