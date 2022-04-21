package com.cason.eatorgasm.util;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.StatFs;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.cason.eatorgasm.define.CMDefine;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;

public abstract class FileUtils {
	private static final String TAG = "FileUtils";
	
	public static String getFileName(String fullPath)
	{
		if (fullPath == null || fullPath.trim().length() == 0)
			return fullPath;
		
		int nIndex = fullPath.lastIndexOf('/');
		if (nIndex >= 0) 
			return fullPath.substring(nIndex+1);
		else
			return fullPath;
	}
	
	public static String getFileNameWithoutExt(String a_strFilename)
	{
		String strFileName = a_strFilename;
		if (strFileName == null || strFileName.length() == 0)
			return strFileName;
		
		int nIndex = strFileName.lastIndexOf(".");
		if (nIndex == -1)
			return strFileName;
		
		return strFileName.substring(0, nIndex);
	}

	public static boolean hasChildDirectory(File file, boolean isShowHidden)
	{
		File[] childList = file.listFiles();
		if (childList == null)
			return false;
		
		for (File childFile : childList)
		{
			if (childFile == null)
				break;
			
			if (!isShowHidden && childFile.isHidden())
				continue;
			
			if (childFile.isDirectory())
				return true;
		}
		
		return false;
	}
	
	public static String makeAbsolutePath(String folderPath, String filePath) {
		if (folderPath.endsWith("/")) {
			return folderPath + getFileName(filePath);
		} else {
			return folderPath + "/" + getFileName(filePath);
		}
	}

	@Deprecated	//TODO 이게 왜 saveAble????
	public static boolean isSavableDirectory(String path) {
		File file = new File(path);
		if (!file.isDirectory()) {
			if (file.getParent() != null) {
				file = new File(file.getParent());
			}
		}

		return !file.isHidden();
	}

	public static boolean isDirectory(String path) {
		File f = new File(path);
		return f.exists();
	}

	public static String makeDirectory(String path) {
		File file = new File(path);
		if (file.exists()) {
			if (file.isFile() && !file.delete()) {
				CMLog.e(TAG, "[makeDirectory] " + path + " could not delete.");
			}
		}

		if (!file.exists() && !file.mkdirs()) {
			CMLog.e(TAG, "[makeDirectory] " + path + " could not create.");
		}

		if (file.exists()) {
			return file.getPath() + "/";
		}

		return null;
	}

	public static String makeDirectories(String path)	//Creates the directory, including nonexistent parent directories
	{
		File file = new File(path);
		
		if (!file.exists()) {
			if(!file.mkdirs()) {
				return null;
			}
		}
		
		return file.getPath();
	}

	public static String makeThumbnailDir(long interfaceHandle) {
		File file = new File(CMDefine.OfficeDefaultPath.getThumbnailRootPath());
		if (!file.exists() && !file.mkdirs()) {
			CMLog.e(TAG, "[makeThumbnailDir] " + file.getAbsolutePath() + "mkdirs fail");
		}

		file = new File(file, "" + interfaceHandle);
		if (!file.exists() && !file.mkdirs()) {
			CMLog.e(TAG, "[makeThumbnailDir] " + file.getAbsolutePath() + "mkdirs fail");
		}

		return file.getAbsolutePath();
	}

	// polarisTemp directory 자체가 삭제되어, 이후 저장 시 Fail 문제
	public static void deleteAllFilesInDirectory(String path) {
		File dir = new File(path);
		if (!dir.exists()) {
			return;
		}

		if (dir.isDirectory()) {
			String[] files = dir.list();

			if (files == null) {
				return;
			}

			for (String file : files) {
				File f = new File(dir, file);
				if (f.isDirectory()) {
					deleteDirectory(f, false);
				} else {
					f.delete();
				}
			}
		}
	}

	public static void deleteDirectory(File folder, boolean bDeleteDir) {
		if (folder == null || !folder.exists()) {
			CMLog.e(TAG, "[deleteDirectory] folder does not exist.");
			return;
		}

		if (folder.isDirectory()) {
			try {
				for (File file : folder.listFiles())
					if (file.isDirectory()) {
						deleteDirectory(file, true);
					} else {
						file.delete();
					}
			} catch (NullPointerException e) {
				CMLog.trace(e.getStackTrace());
			}

			if (bDeleteDir) {
				folder.delete();
			}
		}
	}

	public static void deleteDirectory(String path, boolean bDeleteDir) {
		if (TextUtils.isEmpty(path)) {
			CMLog.e(TAG, "[deleteDirectory] " + path + " is empty.");
			return;
		}

		File dir = new File(path);
		deleteDirectory(dir, bDeleteDir);
	}

	public static ArrayList<String> getArrayChildFileName(String parentPath) {
		File parent = new File(parentPath);
		if (parent.exists() && parent.isDirectory()){
			ArrayList<String> arrFileName = new ArrayList<>();
			for (File file : parent.listFiles()) {
				if (file.isFile())
					arrFileName.add(file.getName());
			}
			return arrFileName;
		}
		return null;
	}
	
	public static String getFileExt(String strFile){
		int index = strFile.lastIndexOf('.');
		if (index < 0)
			return "";

		return strFile.substring(index + 1);
	}
	
	public static String getFileExtension(int type, boolean isDot)
	{
		String ext = null;
		switch (type)
		{
		case CMDefine.ContentType.DOC :		ext = "doc";	break;
		case CMDefine.ContentType.PPT :		ext = "ppt";	break;
		case CMDefine.ContentType.HWP :		ext = "hwp";	break;
		case CMDefine.ContentType.XLS :		ext = "xls";	break;
		case CMDefine.ContentType.DOCX :	ext = "docx";	break;
		case CMDefine.ContentType.PPTX :	ext = "pptx";	break;
		case CMDefine.ContentType.XLSX :	ext = "xlsx";	break;
		case CMDefine.ContentType.PDF :		ext = "pdf";	break;
		case CMDefine.ContentType.TXT :		ext = "txt";	break;
		case CMDefine.ContentType.PPS :		ext = "pps";	break;
		case CMDefine.ContentType.PPSX :	ext = "ppsx";	break;
		case CMDefine.ContentType.RTF :		ext = "rtf";	break;
		case CMDefine.ContentType.CSV :		ext = "csv";	break;
		case CMDefine.ContentType.ODT :		ext = "odt";	break;
		case CMDefine.ContentType.XLSM :	ext = "xlsm";	break;
		case CMDefine.ContentType.PPTM :	ext = "pptm";	break;
		case CMDefine.ContentType.DOCM :	ext = "docm";	break;
		}
		
		if (ext != null && isDot)
			ext = "." + ext;
		
		return ext;
	}
	
	public static int getTypeByExt(String ext)
	{
		int type = CMDefine.ContentType.NONE;
		if (ext.compareToIgnoreCase("doc") == 0)
			type = CMDefine.ContentType.DOC;
		else if (ext.compareToIgnoreCase("ppt") == 0)
			type = CMDefine.ContentType.PPT;
		else if (ext.compareToIgnoreCase("hwp") == 0)
			type = CMDefine.ContentType.HWP;
		else if (ext.compareToIgnoreCase("xls") == 0)
			type = CMDefine.ContentType.XLS;
		else if (ext.compareToIgnoreCase("docx") == 0)
			type = CMDefine.ContentType.DOCX;
		else if (ext.compareToIgnoreCase("pptx") == 0)
			type = CMDefine.ContentType.PPTX;
		else if (ext.compareToIgnoreCase("xlsx") == 0)
			type = CMDefine.ContentType.XLSX;
		else if (ext.compareToIgnoreCase("pdf") == 0)
			type = CMDefine.ContentType.PDF;
		else if (ext.compareToIgnoreCase("txt") == 0)
			type = CMDefine.ContentType.TXT;
		
		return type;
	}
	
	public static int getTypeByFileName(String fileName)
	{
		if (fileName == null || fileName.length() == 0)
			return CMDefine.ContentType.NONE;
		
		int idx_ext = fileName.lastIndexOf('.');
		String ext;
		if (idx_ext < 0)
			ext = "";
		else
			ext = fileName.substring(idx_ext+1);
		
		return getTypeByExt(ext);
	}
	
	public static String getMimeTypeFromExtension(String ext)
	{
		return MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());
	}

	public static String getSizeString(long size)
	{
		String StrBuf;
		String strGiga = "GB", strMega = "MB", strKilo = "KB", strUnit, strBytes = "Bytes";
		float nGiga = (float) 1073741824.0, nMega = (float) 1048576.0, nKilo = (float) 1024.0;
		float nUnitSize = 0;
		
		if(size<0)
			return null; // checking..
		
		if (size >= nGiga)
		{
			nUnitSize = size/nGiga;
			strUnit = strGiga;
		}
		else if (size >= nMega)
		{
			nUnitSize = size/nMega;
			strUnit = strMega;
		}
		else if (size >= nKilo)
		{
			nUnitSize = size/nKilo;
			strUnit = strKilo;
		}
		else
		{
			nUnitSize = (float)size;
			strUnit = strBytes;
		}
		
		int nLen = (size >= nKilo)?Integer.toString( (int)nUnitSize ).length():-1;
		switch(nLen)
		{
			case 1:
				StrBuf = String.format(Locale.getDefault(), "%.2f%s", nUnitSize, strUnit);
				break;
			case 2:
				StrBuf = String.format(Locale.getDefault(), "%.1f%s", nUnitSize, strUnit);
				break;
			default:
				StrBuf = String.format(Locale.getDefault(), "%d%s", (int)nUnitSize, strUnit);
				break;
		}	
		
		return StrBuf;
	}

	public static long getFreeBlock(String path) {
		long freeBlock = 0;
		try {
			StatFs sf = new StatFs(path);
			freeBlock = sf.getAvailableBlocksLong();
		} catch (IllegalArgumentException e) {
			CMLog.trace(e.getStackTrace());
		}

		return freeBlock;
	}

	public static long getFreeSize(String path) {
		long freeSize;
		try {
			StatFs sf = new StatFs(path);
			freeSize = sf.getAvailableBlocksLong() * sf.getBlockSizeLong();
		} catch (IllegalArgumentException e) {
			CMLog.trace(e.getStackTrace());
			freeSize = 0;
		}
		CMLog.d("", "[getFreeSize] freeSize : " + freeSize);

		return freeSize;
	}

	public static long getFileSize(String path) {
		long result = 0;
		File file = new File(path);

		if (!file.isDirectory())
			return file.length();

		File[] fileList = file.listFiles();

		for (File child : fileList) {
			if (child.isDirectory())
				result += getFileSize(child.getAbsolutePath());
			else
				result += child.length();
		}

		return result;
	}

	public static boolean isExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static void changePermissions(File dir, boolean readable, boolean writable, boolean executable, boolean bRecursive) {
		if (dir == null || !dir.exists())
			return;

		try {
			boolean result = dir.setReadable(true, readable);
			result |= dir.setWritable(true, writable);
			result |= dir.setExecutable(true, executable);
		} catch (Exception e) {
			CMLog.trace(e.getStackTrace());
		}

		if (!bRecursive || !dir.isDirectory())
			return;

		try {
			for (File file : dir.listFiles()) {
				changePermissions(file, readable, writable, executable, true);
			}
		} catch (Exception e) {
			CMLog.trace(e.getStackTrace());
		}
	}

	static public String rootSeparator(String path, boolean separator) {		
		if(path.startsWith(File.separator)) {
			if(!separator)			
				return path.substring(File.separator.length());
		} else {
			if(separator)
				return File.separator + path;
		}
		
		return path;
	}

	@TargetApi(Build.VERSION_CODES.M)
	public static boolean isGrantedStoragePermission(Context context) {
		return context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
				&& context.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
	}

	public static boolean isReadable(String name) {
		File file = new File(name);
		return file.canRead();
	}
}