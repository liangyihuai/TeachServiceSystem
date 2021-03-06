package com.huai.core;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.huai.utils.MyPathUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;


public class UploadFile{

	/**
	 * 用此字段可以得到上传文件的文件名
	 */
	public static final String ABSOLUTE_PATH = "absolutePath";

	
	public static final String FILE_NAME = "fileName";
	/**
	 * delete the file
	 * @param absolutePath
	 */
	public void delect(String absolutePath){
		File file = new File(absolutePath);
		if (file.exists()){
			file.delete();
		}
	}

	/**
	 * 上传文件到服务器上面，并返回文件的绝对路径+文件名以及前端传来的parameter的name和value。
	 * 其中文件保存在uploadFilePlace下面
	 * @param request
	 * @param response
	 * @return 存放parameter的hash map，文件名的key是"fileName",路径的key是“absolutePath”
	 * @author LiangYiHuai
	 */
	 public Map<String,String> uploadFile(HttpServletRequest request, HttpServletResponse response){
		String filePathAndName= null;
		
		Map<String, String> parameters = new HashMap<String, String>();
		
		response.setContentType("text/plain");
		
		//第一个是暂时的缓存文件名
		//第二个是存放文件的地方
		 String tempFilePath = MyPathUtils.TEMP_UPLOAD_FILE;
		 String tempFilePath2 = MyPathUtils.UPLOAD_FILE;

		//获取绝对路径
		tempFilePath = request.getSession().getServletContext().getRealPath("/") +File.separator+ tempFilePath;
		tempFilePath2 =request.getSession().getServletContext().getRealPath("/") + File.separator+tempFilePath2;
		try{
			//创建一个基于硬盘的FileItem工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			//设置向硬盘写数据时所用的缓冲区的大小，此处为4k
			factory.setSizeThreshold(4*1024);
			//判断是否有没有这个文件目录
			File tempFile = new File(tempFilePath);
			if(!tempFile.exists() && !tempFile.isDirectory()){
				tempFile.mkdir();
			}
			//设置暂时缓存目录
			factory.setRepository(tempFile);
			
			ServletFileUpload upload = new ServletFileUpload(factory);
			//设置文件最大不能超过1G
			upload.setSizeMax(1024*1024*1024);
			
			List<FileItem> items = upload.parseRequest(request);
			
			Iterator<FileItem> iter = items.iterator();
			
			while(iter.hasNext()){
				FileItem item = iter.next();
				if(item.isFormField()){
					String name = item.getFieldName();
					String value = item.getString();
					parameters.put(name, value);
					System.out.println("name = "+name+"; value = "+value);
				}else{
					//获取文件名
					String fileName = item.getName();
					int index = fileName.lastIndexOf("\\");
					fileName = fileName.substring(index+1, fileName.length());
//					System.out.println("filePath = "+fileName);
					long fileSize = item.getSize();
					
					if("".equals(fileName) && fileSize==0){
						return null;
					}
					fileName = System.currentTimeMillis()+"_"+fileName;
					
					parameters.put(UploadFile.FILE_NAME, fileName);
					
					//判断并创建文件夹tempFilePath2
					File temp = new File(tempFilePath2);
					if(!temp.exists()&&!temp.isDirectory()){
						temp.mkdir();
					}
					
					filePathAndName = tempFilePath2+File.separator+fileName;
//					System.out.println("filePath and name = "+filePathAndName);
					File tempFile2 = new File(filePathAndName);
					
					//将文件路径放到map中
					parameters.put(UploadFile.ABSOLUTE_PATH, filePathAndName);
					//将上传的文件放到tempFilePath2目录下面
					try {
						item.write(tempFile2);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		
		}catch(Exception e){
			e.printStackTrace();
		}
		return parameters;
	}

}
