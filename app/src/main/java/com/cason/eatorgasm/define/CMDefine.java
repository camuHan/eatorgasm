package com.cason.eatorgasm.define;

import android.net.Uri;
import android.os.Environment;

import java.io.File;

public class CMDefine {
	static public String InternalRootPath = null;
	static public String InternalFileRootPath = null;
	static public String ExternalFileRootPath = null;
	static public String ProviderRootPath = null;

	/**
	 * Nexus5
	 * /data/data/com.infraware.polarisoffice.entbiz3.sample7/app_polaris/
	 *
	 * Pixel 3a XL
	 * /data/user/0/com.infraware.polarisoffice.entbiz3.sample7/app_polaris/
	 */
	static public String InternalPath = null;
	/**
	 * Nexus5
	 * /storage/emulated/0/Android/data/com.infraware.polarisoffice.entbiz3.sample7/files/
	 *
	 * Pixel 3a XL
	 * /storage/emulated/0/Android/data/com.infraware.polarisoffice.entbiz3.sample7/files/
	 */
	static public String ExternalPath = null;
	/**
	 * Nexus5
	 * /data/data/com.infraware.polarisoffice.entbiz3.sample7/cache
	 *
	 * Pixel 3a XL
	 * /data/user/0/com.infraware.polarisoffice.entbiz3.sample7/cache
	 *
	 * provider_paths.xml
	 */
	static public String ProviderPath = null;

	public static class OfficeDefaultPath {
		static public String getHomeRootPath() {
			return ExternalPath + Environment.DIRECTORY_DOCUMENTS + "/";
		}

		static public String getSaveRootPath() {
			return getHomeRootPath();
		}

		static private String getOfficeRootPath() {
			return InternalPath;
		}

		static public String OFFICE_ROOT_PATH = getOfficeRootPath();

		//App Only
		static public String getRecoveryPath() {
			return OFFICE_ROOT_PATH + "recovery";
		}

		static public String getBookmarkPath() {
			return OFFICE_ROOT_PATH + "bookmark";
		}

		//업체 요청 : 끝이 -no sub 로 끝나면 poclip1 폴더를 만들지 않음 from brcommon.cpp
		static public String getClipboardPath() {
			return OFFICE_ROOT_PATH + "clipboard";
		}

		static public String getThumbnailRootPath() {
			return OFFICE_ROOT_PATH + "thumbnail";
		}

		static public String getTempPath() {
			return OFFICE_ROOT_PATH + "temp_engine";
		}

		static public String getUITempPath() {
			return OFFICE_ROOT_PATH + "temp_ui";
		}

		static public String getPrintPath() {
			return OFFICE_ROOT_PATH + "print";
		}

		static public String getProviderPath() {
			return ProviderPath;
		}

		static public String getExtraFontsFolderName() {
			return "fonts";
		}

		static public String getExtraFontsPath() {
			return ExternalPath + getExtraFontsFolderName();
		}
	}
	
	public class ConfigString
	{
		static public final String SO_LIB_OFFICE			= "polarisoffice8";
		static public final String SO_LIB_CRYPTO			= "polaris_crypto";
	}
	
	public class ConfigEnv
	{
		static public final boolean DM_LOCALE_GLOVAL		= true;
		static public final boolean DM_DEF_LOCALE_UKENG		= true;
	}
	
	public class LocaleType
	{
		static public final int DM_UK_ENGLISH 				= 0;
		static public final int DM_KOREAN 					= 1;
		static public final int DM_BULGARIAN 				= 2;
		static public final int DM_CROATIAN 				= 3;
		static public final int DM_CZECH 					= 4;
		static public final int DM_DANISH 					= 5;
		static public final int DM_DUTCH 					= 6;
		static public final int DM_FINNISH 					= 7;
		static public final int DM_FRENCH 					= 8;
		static public final int DM_GERMAN 					= 9;
		static public final int DM_GREEK 					= 10;
		static public final int DM_HUNGARIAN 				= 11;
		static public final int DM_ICELANDIC 				= 12;
		static public final int DM_ITALIAN 					= 13;
		static public final int DM_MACEDONIAN_FYROM 		= 14;
		static public final int DM_NORWEGIAN 				= 15;
		static public final int DM_POLISH	 				= 16;
		static public final int DM_PORTUGUESE				= 17;
		static public final int DM_ROMANIAN					= 18;
		static public final int DM_SERBIAN					= 19;
		static public final int DM_SLOVAK					= 20;
		static public final int DM_SLOVENIAN				= 21;
		static public final int DM_SPANISH					= 22;
		static public final int DM_SWEDISH					= 23;
		static public final int DM_TURKISH					= 24;
		static public final int DM_RUSSIAN					= 25;
		static public final int DM_ARABIC					= 26;
		static public final int DM_HEBREW					= 27;
		static public final int DM_S_CHINESE				= 28;
		static public final int DM_T_CHINESE_TW				= 29;
		static public final int DM_T_CHINESE_HK				= 30;
		static public final int DM_BRAZILIAN_PORTUGUESE		= 31;
		static public final int DM_SPANISH_MEXICO			= 32;
		static public final int DM_CANADIAN_FRENCH			= 33;
		static public final int DM_US_ENGLISH				= 34;
		static public final int DM_DUTCH_BELGIUM			= 35;
		static public final int DM_ENGLISH_AUSTRAILIA		= 36;
		static public final int DM_ENGLISH_CANADA			= 37;
		static public final int DM_ENGLISH_IRELAND			= 38;
		static public final int DM_FRENCH_SWITZERLAND		= 39;
		static public final int DM_FRENCH_BELGIUM			= 40;
		static public final int DM_GERMAN_SWITZERLAND		= 41;
		static public final int DM_ITALIAN_SWITZERLAND		= 42;
		static public final int DM_RUSSIAN_ISRAEL			= 43;
		static public final int DM_JAPANESE					= 44;
		static public final int DM_KAZAKHSTAN				= 45;
		static public final int DM_LITHUANIAN				= 46;
		static public final int DM_LATVIAN					= 47;
		static public final int DM_ESTONIAN					= 48;
		static public final int DM_VIETNAMESE				= 49;
		static public final int DM_HEBREW2					= 50;
		static public final int DM_THAI						= 51;
		static public final int DM_INDONESIA				= 52;
		static public final int DM_MALAY					= 53;
		static public final int DM_FARSI					= 54;
		static public final int DM_UKRAINIAN 				= 55;
		static public final int DM_SPANISH_SA 				= 56;
	}

	public class LocaleStr
	{
		static public final String DML_STR_UK_ENGLISH = "en";
		static public final String DML_STR_KOREAN = "ko";
		static public final String DML_STR_BULGARIAN = "bg";
		static public final String DML_STR_CROATIAN = "hr";
		static public final String DML_STR_CZECH = "cs";
		static public final String DML_STR_DANISH = "da";
		static public final String DML_STR_DUTCH = "nl";
		static public final String DML_STR_FINNISH = "fi";
		static public final String DML_STR_FRENCH = "fr";
		static public final String DML_STR_GERMAN = "de";
		static public final String DML_STR_GREEK = "el_GR";
		static public final String DML_STR_HUNGARIAN = "hu";
		static public final String DML_STR_ICELANDIC = "is";
		static public final String DML_STR_ITALIAN = "it";
		static public final String DML_STR_MACEDONIAN_FYROM = "mk";
		static public final String DML_STR_NORWEGIAN = "no";
		static public final String DML_STR_POLISH = "pl";
		static public final String DML_STR_PORTUGUESE = "pt";
		static public final String DML_STR_ROMANIAN = "ro";
		static public final String DML_STR_SERBIAN = "sr";
		static public final String DML_STR_SLOVAK = "sk";
		static public final String DML_STR_SLOVENIAN = "sl";
		static public final String DML_STR_SPANISH = "es";
		static public final String DML_STR_SWEDISH = "sv";
		static public final String DML_STR_TURKISH = "tr";
		static public final String DML_STR_RUSSIAN = "ru";
		static public final String DML_STR_ARABIC = "ar";
		static public final String DML_STR_HEBREW = "iw";
		static public final String DML_STR_CHINESE = "zh";
		static public final String DML_STR_S_CHINESE = "zh_CN";
		static public final String DML_STR_T_CHINESE_TW = "zh_TW";
		static public final String DML_STR_T_CHINESE_HK = "zh_HK";
		static public final String DML_STR_BRAZILIAN_PORTUGUESE = "pt_BR";
		static public final String DML_STR_SPANISH_MEXICO = "es_MX";
		static public final String DML_STR_CANADIAN_FRENCH = "CA";
		static public final String DML_STR_US_ENGLISH = "en_US";
		static public final String DML_STR_DUTCH_BELGIUM = "nl_BE";
		static public final String DML_STR_ENGLISH_AUSTRAILIA = "en_AU";
		static public final String DML_STR_ENGLISH_CANADA = "en_CA";
		static public final String DML_STR_ENGLISH_IRELAND = "en_IE";
		static public final String DML_STR_FRENCH_SWITZERLAND = "CH";
		static public final String DML_STR_FRENCH_BELGIUM = "BE";
		static public final String DML_STR_GERMAN_SWITZERLAND = "de_CH";
		static public final String DML_STR_ITALIAN_SWITZERLAND = "it_CH";
		static public final String DML_STR_RUSSIAN_ISRAEL = "ru_IL";
		static public final String DML_STR_JAPANESE = "ja";
		static public final String DML_STR_KAZAKHSTAN = "kk";
		static public final String DML_STR_LITHUANIAN = "lt";
		static public final String DML_STR_LATVIAN = "lv";
		static public final String DML_STR_ESTONIAN = "et";
		static public final String DML_STR_VIETNAMESE = "vi";		
		static public final String DML_STR_HEBREW2 = "he";
		static public final String DML_STR_THAI = "th";
		static public final String DML_STR_INDONESIA = "in";
		static public final String DML_STR_INDONESIA_1 = "id";
		static public final String DML_STR_MALAY = "ms";
		static public final String DML_STR_FARSI = "fa";
		static public final String DML_STR_UKRAINIAN = "uk";
		static public final String DML_STR_SPANISH_SA = "es_SA";
	}

	public class ExtraKey {
		static public final String OPEN_FILE						= "key_filename";
		static public final String OPEN_URI 						= "key_fileURI";
		static public final String NEW_FILE							= "key_new_file";
		static public final String CURRENT_FILE						= "key_current_file";
		static public final String CURRENT_FILE_DOC_TYPE			= "key_current_file_doc_type";
		static public final String CURRENT_FILE_DOC_PAGE			= "key_current_file_doc_page";
		static public final String CURRENT_FILE_DOC_WORDS			= "key_current_file_doc_words";
		static public final String CURRENT_FILE_DOC_CHAR_NO_SPACE	= "key_current_file_doc_char_no_space";
		static public final String CURRENT_FILE_DOC_CHAR_SPACE		= "key_current_file_doc_char_space";
		static public final String CURRENT_FILE_DOC_SHORT			= "key_current_file_doc_short";
		static public final String CURRENT_FILE_DOC_LINE			= "key_current_file_doc_line";
		static public final String CURRENT_FILE_DOC_SELECT_WORD		= "key_current_file_doc_select_word";

		static public final String CONTENT_NAME						= "key_content_name";
		static public final String CONTENT_TYPE						= "key_content_type";
		static public final String CONTENT_MODE						= "key_content_mode";
		static public final String NEW_PPT_TEMPLATE					= "key_ppt_template";
		static public final String PPS_FILE							= "key_pps_file";
		static public final String PPS_FILE_PATH					= "key_pps_file_path";
		static public final String TEMPLATE_FILE					= "key_template_file";
		static public final String SAVE_MENU_TYPE					= "key_save_menu";
		static public final String AUTO_SAVE_TEST					= "key_auto_save_test";
		static public final String OLE_DOCUMENT						= "key_is_ole";
		
		static public final String SAVE_PENDRAW						= "key_save_include_pendraw";
		//Content Search
		static public final String SEARCH_KEY						= "search-key";
		//~Content Search
		
		static public final String HOME_RECENT_FILE					= "key_home_recent_file";
		static public final String EXPORT_CURRENT_PAGE				= "key_export_current_page";
		
		static public final String OPEN_START_TIME 					= "key_open_start_time";//로딩 성능 측정용
		
		static public final String SAVE_BACK						= "key_save_back";
		
		static public final String ENCRYPTED_DOC_OPEN_PASSWORD		= "key_open_password";
		static public final String ENCRYPTED_DOC_WRITE_PASSWORD		= "key_write_password";
		static public final String ENCRYPTED_DOC_AUTH_FAIL			= "key_auth_fail";
		static public final String WRITE_ENCRYPTED_DOC				= "key_write_encrypted_doc";

		static public final String FROM_KB_APP						= "kboffice";

		static public final String ZERO_BYTE_PPT_DOCUMENT	= "zero_byte_ppt_document";
	}
	
	public class bundle_key{
		static public final String FILE_PATH			= "bundle_key_filename";
		static public final String CMD_TYPE			= "bundle_key_cmdtype";
		static public final String IS_VIEWER_MODE			= "bundle_key_isviewermode";
		static public final String SDK_INTERFACE_ID			= "bundle_key_Isdk_id";

	}

	public class common_wheelButton {
		static public final String WHEEL_TYPE			= "key_wheel_type";
		static public final String DATA_TYPE			= "key_data_type";//integer or float
		static public final String MAX					= "key_max";
		static public final String MIN					= "key_min";
		static public final String TITLE_ID				= "key_title_id";//tile res id
		static public final String VALUE				= "key_value";
	}

	
	public class InternalCmdType
	{
		//extra key for document open
		static public final String DM_CMD_KEYSTR 		= "INTCMD";
		static public final int DM_INTCMD_NO_POLARIS 	= -1;
		static public final int DM_INTCMD_NONE 			= 0;
		static public final int DM_INTCMD_NEWDOC 		= 1;
		static public final int DM_INTCMD_TEMPLATE 		= 2;
		static public final int DM_INTCMD_MANUAL 		= 3;
		static public final int DM_INTCMD_AUTO_TEST		= 4;
	}
	
	public class ContentMode {
        static public final int SAVE_AS = 0;
        static public final int SAVE_AS_NEW = 1;
        static public final int SAVE_AS_REDUCE_CAPACITY = 2;
        static public final int SAVE_AS_TO_IMAGE = 3;
        static public final int EXTRACT_TO_PDF = 4;
        static public final int MERGE_PDF = 5;
        static public final int CREATE_PDF = 6;
    }
	
//	public static class Action
//	{
//		static public final String CLOSE			= CMModelDefine.PACKAGE_NAME + ".CLOSE";
//		static public final String DEACTIVE			= CMModelDefine.PACKAGE_NAME + ".DEACTIVE";
//		static public final String ADD_ACCOUNT		= CMModelDefine.PACKAGE_NAME + ".ADD_ACCOUNT";
//	}
	
	public class BaseRequest
	{
		static public final int CM					= 0x100;
		static public final int FM					= 0x200;
		static public final int DM					= 0x300;
		static public final int PO					= 0x1000;
	}
	
	public class BaseResult
	{
		static public final int CM					= 0x100;
		static public final int FM					= 0x200;
		static public final int DM					= 0x300;
		static public final int PO					= 0x1000;
	}
	
	public class ContentType
	{
		static public final int NONE				= 0;
		static public final int DOC					= 1;
		static public final int PPT					= 2;
		static public final int XLS					= 3;
		static public final int DOCX				= 4;
		static public final int PPTX				= 5;
		static public final int XLSX				= 6;
		static public final int PDF					= 7;
		static public final int TXT					= 8;
		static public final int HWP					= 9;
		static public final int IMG					= 10;
		static public final int ALL					= 11;
		static public final int PPS					= 12;
		static public final int PPSX				= 13;
		static public final int RTF					= 14;
		static public final int CSV					= 15;
		static public final int ODT					= 16;
		static public final int XLSM				= 17;
		static public final int PPTM				= 18;
		static public final int DOCM				= 19;
	}
	
	public class MediaType
	{
		static public final int OTHER				= 0;
		static public final int IMAGE				= 1;
		static public final int AUDIO				= 2;
		static public final int VIDEO				= 3;
		static public final int DOCUMENTS			= 4;
	}
	
	public static class MimeInfo
	{
		public int id;
		public String fileName;
		public int mediaType;
		public Uri fileUri;
		public Uri mediaUri;
		public Uri contentUri;
		public String mimeType;
		
		public MimeInfo(String fileName)
		{
			this.id = -1;
			this.fileName = fileName;
			this.mediaType = CMDefine.MediaType.OTHER;
			this.fileUri = Uri.fromFile(new File(fileName));
			this.mediaUri = null;
			this.contentUri = null;
			this.mimeType = "";
		}
	}
	
	public class Sort {
		static public final int NONE					= 0;
		static public final int NAME					= 1;
		static public final int DATE					= 2;
		static public final int SIZE					= 3;
		static public final int PATH					= 4;
		static public final int TYPE					= 5;
		static public final int RECENT					= 6;
	}

	///////////////////////////////////////////////////////////////////////
	//	Multi-instance
	//	[haha8304][#11001]
	//	To save a previous document and then open new document Handler
	public class MultiOpen_handler
	{
		static public final int SAVE_SUCCESS		= 1;
		static public final int START_DOCUMENT		= 2;
	}
	// PO Open type
	public class PO_RunningType
	{
		static public final int INTERNAL		= 0;
		static public final int EXTERNAL		= 1;
	}
	
	public class IMAGE_SHARE_TYPE
	{
		static public final String PNG = "png";
		static public final String JPG = "jpg";
	}
	
	public class AppInfo {
		static public final String ENGINE_REVISION		= "rev.89524";		// svn revision
		static public final String GD_SDK_VERSION		= "10.0.0.93";		// GD lib version
		static public final String GD_LAUNCHER_VERSION	= "3.3.0.215";		// GD launcher version
	}
}