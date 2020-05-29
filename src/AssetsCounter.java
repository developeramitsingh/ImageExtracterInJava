import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AssetsCounter {
	int imagesCount, totalChapters;
	ArrayList <String> chapNames = new ArrayList <String>();

	private int countImages(String path) {
		String[] totalImages;
		File file = new File(path);
		totalImages = file.list();
		int temp = totalImages.length;
		ArrayList <String> filter = new ArrayList <String>();
		for(int i=0;i<temp;i++) {
			filter.add(totalImages[i]);
		}
		
		filter.removeIf(str -> str.endsWith("db"));
		filter.removeIf(str -> str.endsWith("pdf"));
		filter.removeIf(str -> str.endsWith("exe"));
		filter.removeIf(str -> str.endsWith("ogg"));
		filter.removeIf(str -> str.endsWith("mp3"));
		filter.removeIf(str -> str.contains("Copy"));
		filter.removeIf(str -> !str.contains("."));
		imagesCount = filter.size();
		return imagesCount;
	}
	
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
		System.out.println("All files "+ chapNames);
		return totalChapters;
	}
	
	private void writeIndexFile(int max, String path, int chap) {
		try {
			//RandomAccessFile file = new RandomAccessFile(path, "rw");
			//String data  = "var MAX = "+ max;
			//file.seek(18);
			//file.write(data.getBytes());			
			//file.close();
			Path imagepath = Paths.get(path);
			Stream <String> streamLines = Files.lines(imagepath);
			List <String> replace = streamLines.map(str ->str.replaceAll("var MAX = 1", "var MAX = "+max)).collect(Collectors.toList());
			Files.write(imagepath, replace);
			streamLines.close();
			System.out.println("Index updated of chapter "+chap+" with Max images "+ max);
		}
		catch(Exception e) {
			System.out.println("Index file writing fail" + e);
		}
		
	}
	
	
	public void start(AssetsCounter obj,  String CurrentPath) {
		String currentPath = CurrentPath;
		String chaptersPath  = currentPath;
		String imagesPath, indexPath;
		obj.countChapters(chaptersPath);
				
		System.out.println("Total Chapters in current Folder: "+ obj.totalChapters);
		
		for(int i=0; i<obj.totalChapters; i++) {
			imagesPath = currentPath+"\\"+obj.chapNames.get(i)+"\\images";
			indexPath = currentPath+"\\"+obj.chapNames.get(i)+"\\index.html";
			System.out.println(imagesPath);
			System.out.println(indexPath);
			obj.writeIndexFile(obj.countImages(imagesPath), indexPath, i+1);
			
		}
			
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
			
	}
}
