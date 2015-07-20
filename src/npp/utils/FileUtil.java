package npp.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.apache.poi.hwpf.HWPFDocument;

public class FileUtil {
	public static String getSuffix(String filename) {
		return filename.substring(filename.lastIndexOf("."));
	}

	public static long getTimestamp() {
		return System.currentTimeMillis();
	}

	/** */
	/**
	 * 文件重命名
	 *
	 * @param path
	 *            文件目录
	 * @param oldname
	 *            原来的文件名
	 * @param newname
	 *            新文件名
	 */
	public static void renameFile(String path, String oldname, String newname) {
		if (!oldname.equals(newname)) {// 新的文件名和以前文件名不同时,才有必要进行重命名
			File oldfile = new File(path + File.separator + oldname);
			File newfile = new File(path + File.separator + newname);
			if (newfile.exists())// 若在该目录下已经有一个文件和新文件名相同，则不允许重命名
				newfile.delete();
			oldfile.renameTo(newfile);
		}
	}

	public static void copyFile(String filepath, InputStream in) {
		try {
			File f = new File(filepath);
			if(!f.getParentFile().exists())
				f.getParentFile().mkdirs();
			OutputStream out = new FileOutputStream(new File(filepath));

			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = in.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			in.close();
			out.flush();
			out.close();
			System.out.println("New file created!");
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public static boolean isExist(String filePath) {
		File f = new File(filePath);
		return f.exists();
	}

	public static String getOrdinal(int number) {
		String tail = null;
		if (number <= 0) {
			return "number must > 0";
		} else if (1 == number) {
			return "1st";
		} else if (2 == number) {
			return "2nd";
		}
		else if(3 == number)
		{
			return "3rd";
		}
		else if (number >= 20) {
			int last = number % 10;
			if (1 == last) {
				tail = "st";
			} else if (2 == last) {
				tail = "nd";
			} else if (3 == last) {
				tail = "rd";
			} else {
				tail = "th";
			}
		} else {
			tail = "th";
		}
		return number + tail;
	}

	public static String readWordFromInputstream(InputStream inputstream) {


		HWPFDocument hdt = null;
        try {
            hdt = new HWPFDocument(inputstream);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        //Fields fields = hdt.getFields();
        //Iterator<Field> it = fields.getFields(FieldsDocumentPart.MAIN).iterator();


        org.apache.poi.hwpf.usermodel.Range range = hdt.getRange();
        return range.text();
	}
//	public static String readOdtFromInputstream(InputStream inputStream) {;
//		try {
//			OdfTextDocument odt = OdfTextDocument.loadDocument(inputStream);
//			System.out.println(odt.getContentRoot().toString());
//			return odt.getContentRoot().toString();
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			return "";
//		}
//	}

	public static String readTxtFromInputStream(InputStream inputstream) throws IOException {
		InputStreamReader reader = new InputStreamReader(inputstream, "utf-8"); // 建立一个输入流对象reader
        BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转成计算机能读懂的语言
        StringBuffer sb = new StringBuffer();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line).append('\n'); // 一次读入一行数据
        }
        return sb.toString();
	}

	public static boolean isWord(String fileName) {
		String suffix = getSuffix(fileName);
		return suffix.toLowerCase().equals(".doc");
	}
	public static boolean isTxt(String fileName) {
		String suffix = getSuffix(fileName);
		return suffix.toLowerCase().equals(".txt");
	}
//	public static boolean isOdt(String fileName) {
//		String suffix = getSuffix(fileName);
//		return suffix.toLowerCase().equals(".odt");
//	}
}
