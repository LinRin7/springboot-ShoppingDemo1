package com.example.demo.util;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

/**
* 檔案上傳工具包
*/
public class FileUtils {

	/**
	*
	* @param file 檔案
	* @param path 檔案存放路徑
	* @param fileName 原始檔名
	* @return
	*/
	//5-30有範例
	public static boolean upload(MultipartFile file, String path, String fileName){
	// 生成新的檔名
	//String realPath = path   "/"   FileNameUtils.getFileName(fileName);
	//使用原檔名
	String realPath = path  +  fileName;
	File dest = new File(realPath);
	//判斷檔案父目錄是否存在
	if(!dest.getParentFile().exists()){
	dest.getParentFile().mkdir();
	}
	try {
	//儲存檔案
	file.transferTo(dest);
	return true;
	} catch (IllegalStateException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	return false;
	} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
	return false;
	}
	}
}
