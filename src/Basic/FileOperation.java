package Basic;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;

public class FileOperation extends FairyBase{
	/*
	 * Reference - https://www.cnblogs.com/wsg25/p/7499227.html
	 */
	// Read 2 files, Merge and remove duplication
	public static void main(String arg[]) throws Exception {
		// 绝对路径
//		String path = "C:\\Myprograms\\workspaceFairy\\algorithm\\src\\Basic\\data\\readFile.txt";
		// 相对路径
//		URL url = FileOperation.class.getResource("");
//		println(url.toString());
//		String userDir = System.getProperty("user.dir");
//		print(userDir);
		String path = "src\\Basic\\data\\readFile.txt";
//		
		// Read: 注意 一定要关闭流和流关闭的顺序。
//		readByBite(path);
//		readByCharactor(path);
//		readByLines(path);
//		readByRandomAccess(path);
		
		// Write
//		appendMethodB(path,"Line");
//		appendMethodA(path,"AppendContentB");
		
//		// Merge文件
//		String mergeFile1 = "src\\Basic\\data\\mergeFile1.txt";
//		String mergeFile2 = "src\\Basic\\data\\mergeFile2.txt";
//		String writeFile = "src\\Basic\\data\\writeFile.txt";
//		mergeFile(mergeFile1, mergeFile2,writeFile);
		
		
//		 //Split File
//		splitFileOrAvoidMemeryOverflow(path);
		
		
		// 读取大文件 - Refer to- https://blog.csdn.net/zjkc050818/article/details/78814311
		// Method1 - Scan类, 不保持引用- 不放进内存， refer： http://www.importnew.com/14512.html
//		InputStream inputStream = new FileInputStream(path);
//		Scanner sc = new Scanner(inputStream, "UTF-8");
//		sc.nextLine();
		
//		// Method2， Apache Commons IO流？？？
//		LineIterator it = FileUtils.lineIterator(path, "UTF-8");
		
		// Method3, RandomAccessFile 或 BufferedReader读取大文件，避免内存泄漏的情况 -- refer to ‘splitFileOrAvoidMemeryOverflow’
		
		// Method4， 内存映射
		
		// Method5, 多线程
		readLargeFileWithThread(path);
	}
	
	private static void mergeFile(String mergeFile1,String mergeFile2,String writeFile) throws Exception  {
		// Method1: HashMap
//		if(writeFile == null || writeFile.equals("")) {
//			throw new Exception("The write File cannot be null");
//		}
//		Map map = new HashMap<String,String>();
//		String tempLine;
//		BufferedReader bufferedReader = null;
//		FileWriter fileWriter = null;
//		try {
//			bufferedReader = new BufferedReader(new FileReader(mergeFile1));
//			while((tempLine=bufferedReader.readLine())!=null){
//				String[] arr = tempLine.split(" ");
//				if(arr.length==2) { // filt out the invalid value
//					map.put(arr[0], arr[1]);
//				}
//			}
//			bufferedReader = new BufferedReader(new FileReader(mergeFile2));
//			while((tempLine=bufferedReader.readLine())!=null){
//				String[] arr = tempLine.split(" ");
//				if(arr.length==2) { // filt out the invalid value
//					map.put(arr[0], arr[1]);
//				}
//			}
//			
//			fileWriter = new FileWriter(writeFile);
//			Iterator iter = map.keySet().iterator();
//			while(iter.hasNext()) {
//				Object key = iter.next();
//				fileWriter.write(key.toString()+" "+map.get(key)+"\n");
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			try {
//				bufferedReader.close();
//				fileWriter.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
		
		
		// 方法2: 双指针法
		// 文件预处理 - 删除重复，排序依次往下找值
//		Comparator<Object> com=Collator.getInstance(java.util.Locale.CHINA);
//		String[] newArray={"上海","天津","广州","杭州","辽宁","南京","武汉","北京","厦门","内蒙"};
//		Arrays.sort(newArray,com);
	}
	
	private static void readLargeFileWithThread(String path) {
		splitFileOrAvoidMemeryOverflow(path);
	}
	
	private static void splitFileOrAvoidMemeryOverflow(String path) {
		long timer = System.currentTimeMillis(); 
		int bufferSize = 20 * 1024 * 1024;//设读取文件的缓存为20MB 
		
		File file=new File(path);
		BufferedReader bufferedReader = null;
		FileWriter fileWriter = null;
		try {
			// Use bufferRead to avoid memory overflow
			// Read
			bufferedReader = new BufferedReader(
					new InputStreamReader (
							new BufferedInputStream(
									new FileInputStream(file))),bufferSize);
			
			// Write
			int splitNum=2, lineNum=117, partLine = lineNum/splitNum;
			for (int i = 0; i <= splitNum; ++i)  {
//				File partFile=new File("/src/Basic/data"+"output/part" + i + ".txt");
//				if(!partFile.exists()) {
//					partFile.createNewFile();
//				}
				fileWriter = new FileWriter("src\\Basic\\data\\output\\part"+i+".txt");
	            String line = null;  
	            for (long lineCounter = 0; lineCounter < partLine && (line = bufferedReader.readLine()) != null; ++lineCounter)  
	            {  
	            	fileWriter.append(line + "\n");  
	            }
	            fileWriter.flush();
	            fileWriter.close();  
	            fileWriter = null; 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(fileWriter!=null) {
					fileWriter.close();
				}
				bufferedReader.close();
				timer = System.currentTimeMillis() - timer;
				print("处理时间："+timer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
     * 以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件。
     */
	private static void readByBite(String path) {
		System.out.print("按字节读取：");
		File file = new File(path);
		try {
			InputStream input = new FileInputStream(path);
			int temp;
			while((temp = input.read())!= -1) {
				print(temp);
			}
			input.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
//			input.close();
		}
		println("");
	}
	
	/**
     * 以字符为单位读取文件，常用于读文本，数字等类型的文件
     */
	private static void readByCharactor(String path) {
		System.out.print("按字符读取：");
		File file = new File(path);
		try {
			InputStream inputStream = new FileInputStream(path);
			Reader reader = new InputStreamReader(inputStream);
			int temp;
			while((temp = reader.read())!= -1) {
                // 对于windows下，\r\n这两个字符在一起时，表示一个换行。
                // 但如果这两个字符分开显示时，会换两次行。
                // 因此，屏蔽掉\r，或者屏蔽\n。否则，将会多出很多空行。
                if (((char) temp) != '\r') {
                    print((char) temp);
                }
			}
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
//			input.close();
		}
		println("");
	}
	
	/**
     * 以行为单位读取文件，常用于读面向行的格式化文件
     */
    public static void readByLines(String fileName) {
    	System.out.print("按行读取：");
        File file = new File(fileName);
        BufferedReader reader = null;
        try {
        	FileReader fileReader = new FileReader(file);
            BufferedReader bufferReader = new BufferedReader(fileReader);
            String tempString = null;
            int line = 1;
            while ((tempString = bufferReader.readLine()) != null) {
                System.out.println("line " + line + ": " + tempString);
                line++;
            }
            fileReader.close();
            bufferReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
        }
    }
    
    /**
     * 随机读取文件内容
     */
    public static void readByRandomAccess(String fileName) {
    	System.out.print("随机读取：");
    	RandomAccessFile randomFile=null;
        try {
            System.out.println("随机读取一段文件内容：");
            // 打开一个随机访问文件流，按只读方式
            randomFile = new RandomAccessFile(fileName, "r");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            // 读文件的起始位置
            int beginIndex = (fileLength > 10) ? 10 : 0;
            // 将读文件的开始位置移到beginIndex位置。
            randomFile.seek(beginIndex);
            byte[] bytes = new byte[10];
            int byteread = 0;
            // 一次读10个字节，如果文件内容不足10个字节，则读剩下的字节。
            // 将一次读取的字节数赋给byteread
            while ((byteread = randomFile.read(bytes)) != -1) {
                System.out.write(bytes, 0, byteread);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (randomFile != null) {
                try {
                    randomFile.close();
                } catch (IOException e1) {
                }
            }
        }
    }
    
    /**
     * A方法追加文件：使用RandomAccessFile
     */
    public static void appendMethodA(String fileName, String content) {
        try {
            // 打开一个随机访问文件流，按读写方式
            RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
            // 文件长度，字节数
            long fileLength = randomFile.length();
            //将写文件指针移到文件尾。
            randomFile.seek(fileLength);
            randomFile.writeBytes(content);
            randomFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * B方法追加文件：使用FileWriter
     */
    public static void appendMethodB(String fileName, String content) {
    	FileWriter writer = null;
    	int lineNumer = 100;
        try {
        	//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
        	writer = new FileWriter(fileName, true);
        	while((lineNumer--)>=0) {
                writer.write(content+" "+lineNumer+"\n");
        	}
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
        	try {
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }
	
}
