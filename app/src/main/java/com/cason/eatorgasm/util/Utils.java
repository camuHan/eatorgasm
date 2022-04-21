package com.cason.eatorgasm.util;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.UserManager;
import android.provider.MediaStore.Audio;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Video;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import com.cason.eatorgasm.define.CMDefine;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public abstract class Utils {
	private static final String TAG = "Utils";

	public static int getScreenSize(Context context) {
		int screenLayout = 0;
		if(context != null)
			screenLayout = context.getResources().getConfiguration().screenLayout;

		screenLayout &= Configuration.SCREENLAYOUT_SIZE_MASK;

		return screenLayout;
	}
	
	public static int getLocaleType(Locale a_locale)
	{
		int locale = CMDefine.LocaleType.DM_US_ENGLISH;
		if (CMDefine.ConfigEnv.DM_DEF_LOCALE_UKENG) {
			locale = CMDefine.LocaleType.DM_UK_ENGLISH;
		}

		final String strLocaleCode = a_locale.toString();

		String strKey = strLocaleCode.substring(0, 2);
		if (CMDefine.ConfigEnv.DM_LOCALE_GLOVAL) {
			if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_KOREAN) == 0) {
				locale = CMDefine.LocaleType.DM_KOREAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_CHINESE) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_T_CHINESE_TW) == 0) {
					locale = CMDefine.LocaleType.DM_T_CHINESE_TW;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_T_CHINESE_HK) == 0) {
					locale = CMDefine.LocaleType.DM_T_CHINESE_HK;
				} else {
					locale = CMDefine.LocaleType.DM_S_CHINESE;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_PORTUGUESE) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_BRAZILIAN_PORTUGUESE) == 0) {
					locale = CMDefine.LocaleType.DM_BRAZILIAN_PORTUGUESE;
				} else {
					locale = CMDefine.LocaleType.DM_PORTUGUESE;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SPANISH) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SPANISH_MEXICO) == 0) {
					locale = CMDefine.LocaleType.DM_SPANISH_MEXICO;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SPANISH_SA) == 0) {
					locale = CMDefine.LocaleType.DM_SPANISH_SA;
				} else {
					locale = CMDefine.LocaleType.DM_SPANISH;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_FRENCH) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_CANADIAN_FRENCH) == 0) {
					locale = CMDefine.LocaleType.DM_CANADIAN_FRENCH;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_FRENCH_SWITZERLAND) == 0) {
					locale = CMDefine.LocaleType.DM_FRENCH_SWITZERLAND;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_FRENCH_BELGIUM) == 0) {
					locale = CMDefine.LocaleType.DM_FRENCH_BELGIUM;
				} else {
					locale = CMDefine.LocaleType.DM_FRENCH;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_DUTCH) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_DUTCH_BELGIUM) == 0) {
					locale = CMDefine.LocaleType.DM_DUTCH_BELGIUM;
				} else {
					locale = CMDefine.LocaleType.DM_DUTCH;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_GERMAN) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_GERMAN_SWITZERLAND) == 0) {
					locale = CMDefine.LocaleType.DM_GERMAN_SWITZERLAND;
				} else {
					locale = CMDefine.LocaleType.DM_GERMAN;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ITALIAN) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ITALIAN_SWITZERLAND) == 0) {
					locale = CMDefine.LocaleType.DM_ITALIAN_SWITZERLAND;
				} else {
					locale = CMDefine.LocaleType.DM_ITALIAN;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_RUSSIAN) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_RUSSIAN_ISRAEL) == 0) {
					locale = CMDefine.LocaleType.DM_RUSSIAN_ISRAEL;
				} else {
					locale = CMDefine.LocaleType.DM_RUSSIAN;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_UK_ENGLISH) == 0) {
				if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_US_ENGLISH) == 0) {
					locale = CMDefine.LocaleType.DM_US_ENGLISH;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ENGLISH_AUSTRAILIA) == 0) {
					locale = CMDefine.LocaleType.DM_ENGLISH_AUSTRAILIA;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ENGLISH_CANADA) == 0) {
					locale = CMDefine.LocaleType.DM_ENGLISH_CANADA;
				} else if (strLocaleCode.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ENGLISH_IRELAND) == 0) {
					locale = CMDefine.LocaleType.DM_ENGLISH_IRELAND;
				}
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_JAPANESE) == 0) {
				locale = CMDefine.LocaleType.DM_JAPANESE;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_BULGARIAN) == 0) {
				locale = CMDefine.LocaleType.DM_BULGARIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_CROATIAN) == 0) {
				locale = CMDefine.LocaleType.DM_CROATIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_CZECH) == 0) {
				locale = CMDefine.LocaleType.DM_CZECH;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_DANISH) == 0) {
				locale = CMDefine.LocaleType.DM_DANISH;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_FINNISH) == 0) {
				locale = CMDefine.LocaleType.DM_FINNISH;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_GREEK) == 0) {
				locale = CMDefine.LocaleType.DM_GREEK;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_HUNGARIAN) == 0) {
				locale = CMDefine.LocaleType.DM_HUNGARIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ICELANDIC) == 0) {
				locale = CMDefine.LocaleType.DM_ICELANDIC;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_MACEDONIAN_FYROM) == 0) {
				locale = CMDefine.LocaleType.DM_MACEDONIAN_FYROM;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_NORWEGIAN) == 0) {
				locale = CMDefine.LocaleType.DM_NORWEGIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_POLISH) == 0) {
				locale = CMDefine.LocaleType.DM_POLISH;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SERBIAN) == 0) {
				locale = CMDefine.LocaleType.DM_SERBIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SLOVAK) == 0) {
				locale = CMDefine.LocaleType.DM_SLOVAK;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SLOVENIAN) == 0) {
				locale = CMDefine.LocaleType.DM_SLOVENIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_SWEDISH) == 0) {
				locale = CMDefine.LocaleType.DM_SWEDISH;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_TURKISH) == 0) {
				locale = CMDefine.LocaleType.DM_TURKISH;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ARABIC) == 0) {
				locale = CMDefine.LocaleType.DM_ARABIC;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_HEBREW) == 0) {
				locale = CMDefine.LocaleType.DM_HEBREW;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_KAZAKHSTAN) == 0) {
				locale = CMDefine.LocaleType.DM_KAZAKHSTAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_LITHUANIAN) == 0) {
				locale = CMDefine.LocaleType.DM_LITHUANIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_LATVIAN) == 0) {
				locale = CMDefine.LocaleType.DM_LATVIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ESTONIAN) == 0) {
				locale = CMDefine.LocaleType.DM_ESTONIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_VIETNAMESE) == 0) {
				locale = CMDefine.LocaleType.DM_VIETNAMESE;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_HEBREW2) == 0) {
				locale = CMDefine.LocaleType.DM_HEBREW2;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_THAI) == 0) {
				locale = CMDefine.LocaleType.DM_THAI;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_INDONESIA) == 0 || strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_INDONESIA_1) == 0) {
				locale = CMDefine.LocaleType.DM_INDONESIA;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_MALAY) == 0)
				locale = CMDefine.LocaleType.DM_MALAY;
			else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_FARSI) == 0) {
				locale = CMDefine.LocaleType.DM_FARSI;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_UKRAINIAN) == 0) {
				locale = CMDefine.LocaleType.DM_UKRAINIAN;
			} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_ROMANIAN) == 0) {
				locale = CMDefine.LocaleType.DM_ROMANIAN;
			}
		} else if (strKey.compareToIgnoreCase(CMDefine.LocaleStr.DML_STR_KOREAN) == 0) {
			locale = CMDefine.LocaleType.DM_KOREAN;
		}

		return locale;
	}

	@RequiresApi(api = Build.VERSION_CODES.N)
	public static int getCurrentLocaleType(Resources res) {
		if (isAboveN()) {
			return getLocaleType(res.getConfiguration().getLocales().get(0));
		} else {
			return getLocaleType(res.getConfiguration().locale);
		}
	}

	public static CMDefine.MimeInfo getMimeInfo(ContentResolver resolver, String a_strFileName) {
		if (TextUtils.isEmpty(a_strFileName)) {
			return null;
		}

		int idx_ext = a_strFileName.lastIndexOf('.');
		String ext;
		if (idx_ext < 0)
			ext = "";
		else
			ext = a_strFileName.substring(idx_ext + 1);

		CMDefine.MimeInfo info;
		if (ext.toLowerCase().equals("rm")) {
			info = new CMDefine.MimeInfo(a_strFileName);
			info.mimeType = "video/mp4";
			return info;
		}
		if (ext.toLowerCase().equals("mkv")) {
			info = new CMDefine.MimeInfo(a_strFileName);
			info.mimeType = "video/mkv";
			return info;
		}

		info = getMimeInfo(resolver, a_strFileName, CMDefine.MediaType.IMAGE);
		if (info != null)
			return info;

		info = getMimeInfo(resolver, a_strFileName, CMDefine.MediaType.AUDIO);
		if (info != null)
			return info;

		info = getMimeInfo(resolver, a_strFileName, CMDefine.MediaType.VIDEO);
		if (info != null)
			return info;

		info = new CMDefine.MimeInfo(a_strFileName);
		info.mimeType = getMimeTypeInfo(ext);

		return info;
	}

	public static String getMimeTypeInfo(String ext) {
		String type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext.toLowerCase());

		if (type == null) {
			switch (ext) {
				case "mp2":
					type = "audio/mpeg";
					break;
				case "mkv":
					type = "video/mkv";
					break;
				case "m4v":
					type = "video/x-m4v";
					break;
				case "flv":
					type = "video/flv";
					break;
				case "vts":
					type = "text/x-vtodo";
					break;
				case "ics":
					type = "text/x-vCalendar";
					break;
				case "webm":
					type = "video/webm";
					break;
				case "vnt":
					type = "text/x-vnote";
					break;
				case "snb":
					type = "application/snb";
					break;
			}
		} else if (type.equalsIgnoreCase("text/richtext")) {
			if (ext.equals("rtx"))
				type = "audio/midi";
		} else if (type.equalsIgnoreCase("text/calendar")) {
			if (ext.equals("ics"))
				type = "text/x-vCalendar";
		} else if (ext.equals("rm"))
			type = "video/mp4";

		return type;
	}

	@SuppressLint("Range")
	public static CMDefine.MimeInfo getMimeInfo(ContentResolver resolver, String fileName, int mediaType)
	{
		CMDefine.MimeInfo info = null;
		Cursor cursor = null;

		int index = -1;

		try {
			switch (mediaType) {
			case CMDefine.MediaType.IMAGE:
				cursor = resolver.query(Images.Media.EXTERNAL_CONTENT_URI,
						null, "_data = ?", new String[] {"\"" + fileName + "\""}, null);
				if (cursor != null) {
					if (cursor.moveToFirst())
						index = cursor.getInt(0);

					if (index != -1) {
						info = new CMDefine.MimeInfo(fileName);

						info.id = index;
						info.mediaType = CMDefine.MediaType.IMAGE;
						info.mimeType = cursor.getString(cursor.getColumnIndex("mime_type"));
						info.mediaUri = Images.Media.EXTERNAL_CONTENT_URI;
						info.contentUri = ContentUris.withAppendedId(
								Images.Media.EXTERNAL_CONTENT_URI, index);

						return info;
					}
				}
				break;
			case CMDefine.MediaType.AUDIO:
				cursor = resolver.query(Audio.Media.EXTERNAL_CONTENT_URI, null,
						"_data = ?", new String[] {"\"" + fileName + "\""}, null);
				if (cursor != null) {
					if (cursor.moveToFirst())
						index = cursor.getInt(0);

					if (index != -1) {
						info = new CMDefine.MimeInfo(fileName);

						info.id = index;
						info.mediaType = CMDefine.MediaType.AUDIO;
						info.mimeType = cursor.getString(cursor.getColumnIndex("mime_type"));
						info.mediaUri = Audio.Media.EXTERNAL_CONTENT_URI;
						info.contentUri = ContentUris.withAppendedId(
								Audio.Media.EXTERNAL_CONTENT_URI, index);

						return info;
					}
				}
				break;
			case CMDefine.MediaType.VIDEO:
				cursor = resolver.query(Video.Media.EXTERNAL_CONTENT_URI, null,
						"_data = ?", new String[] {"\"" + fileName + "\""}, null);
				if (cursor != null) {
					if (cursor.moveToFirst())
						index = cursor.getInt(0);

					if (index != -1) {
						info = new CMDefine.MimeInfo(fileName);

						info.id = index;
						info.mediaType = CMDefine.MediaType.VIDEO;
						info.mimeType = cursor.getString(cursor.getColumnIndex("mime_type"));
						info.mediaUri = Video.Media.EXTERNAL_CONTENT_URI;
						info.contentUri = ContentUris.withAppendedId(
								Video.Media.EXTERNAL_CONTENT_URI, index);

						return info;
					}
				}
				break;
			case CMDefine.MediaType.DOCUMENTS:
				break;
			}
		} catch (Exception e) {
		} finally {
			if (cursor != null)
				cursor.close();
		}

		return null;
	}

	public static void setScreenMode(Window window, int nType) {
		if (nType == Configuration.ORIENTATION_LANDSCAPE) {
			window.setFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		} else {
			window.setFlags(
					WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
					WindowManager.LayoutParams.FLAG_FULLSCREEN);
		}
	}

	public static float dipToPx(Fragment a_fragment, float a_value) {
		return dipToPx(a_fragment.getActivity(), a_value);
	}
	
	public static float dipToPx(Context a_context, float a_value) {
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, a_value, a_context.getResources().getDisplayMetrics());
	}

	public static int dipToPixel(Fragment a_fragment, float a_value) {
		return dipToPixel(a_fragment.getActivity(), a_value);
	}
	
	public static int dipToPixel(Context a_context, float a_value) {
		if(a_context != null)
			return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, a_value, a_context.getResources().getDisplayMetrics()));
		else
			return 0;
	}

	public static int getDensityDpi(Fragment a_fragment) {
		return getDensityDpi(a_fragment.getActivity());
	}
	
	public static int getDensityDpi(Activity a_activity)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		a_activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.densityDpi;
	}
	
	public static int getDensityDpi(Context context)
	{
		return context.getResources().getDisplayMetrics().densityDpi;
	}
	
	public static float getDensity(Activity a_activity)
	{
		DisplayMetrics metrics = new DisplayMetrics();
		a_activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		return metrics.density;
	}

	public static float getDensity(Context context)
	{
		return context.getResources().getDisplayMetrics().density;
	}
	
	public static int getScreenWidthPixels(Activity a_activity)
	{
		try {
			DisplayMetrics displayMetrics = new DisplayMetrics();
			if(a_activity != null && a_activity.getWindowManager() != null
					&& a_activity.getWindowManager().getDefaultDisplay() != null)
				a_activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
			return displayMetrics.widthPixels;
		} catch(Exception e) {
			CMLog.trace(e.getStackTrace());
		}
		return 0;
	}

	public static int getScreenHightPixels(Activity a_activity) 
	{
		DisplayMetrics displayMetrics = new DisplayMetrics();
		a_activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		return displayMetrics.heightPixels;
	}
	
	public static boolean isBelowHdpiDensity(Context context){
		int density = getDensityDpi(context);
		switch(density){
			case DisplayMetrics.DENSITY_HIGH:
			case DisplayMetrics.DENSITY_MEDIUM:
			case DisplayMetrics.DENSITY_LOW:
				return true;
		}
		return false;
	}

	public static boolean isXXXHdpiDensity(Context context){
		int density = getDensityDpi(context);
		return density == DisplayMetrics.DENSITY_XXXHIGH;
	}

	/**
	 * 하나의 Button을 누른 직후, 연속으로 다른 Button(또는 같은 Button)이 눌리지 않도록 하기 위해 1초간 강제로 전체
	 * Button을 Diable시킴
	 */
	static private boolean mButtonEnable = true;

	static public boolean isButtonEnable() {
		return mButtonEnable;
	}

	static public void setButtonDisable()
	{
		mButtonEnable = false;

		Thread timer = new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(1000); // 1초간 sleep
				} catch (Exception e) {
				}

				mButtonEnable = true;
			}
		});

		timer.start();
	}

	static public void setButtonEnable() {
		mButtonEnable = true;
	}

	@SuppressLint("NewApi")
	public static void unbindDrawables(View a_view)
	{
		if (a_view == null) {
			return;
		}

		if (a_view.getBackground() != null) {
			a_view.getBackground().setCallback(null);
			a_view.setBackground(null);
		}

		if (a_view instanceof ImageView)
		{
			ImageView view = (ImageView) a_view;
			if(view.getDrawable() != null)
			{
				view.getDrawable().setCallback(null);
				view.setImageDrawable(null);
			}
		}

		if (a_view instanceof ViewGroup) {
			ViewGroup view = (ViewGroup)a_view;
			for (int i = 0; i < view.getChildCount(); i++)
				unbindDrawables(view.getChildAt(i));

			if (!(a_view instanceof AdapterView)) {
				try {
					view.removeAllViews();
				} catch (Exception e) {
					CMLog.trace((new Throwable()).getStackTrace());
				}
			}
		}
	}

	public static int getBitmapOfWidth(String a_strFileName)
	{
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(a_strFileName, options);
			return options.outWidth;
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getBitmapOfHeight(String a_strFileName)
	{
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(a_strFileName, options);
			return options.outHeight;
		} catch (Exception e) {
			return 0;
		}
	}

	public static int getExifOrientation(String a_strFilePath)
	{
		int degree = 0;
		ExifInterface exif = null;

		try {
			exif = new ExifInterface(a_strFilePath);
		} catch (IOException e) {
			CMLog.e(TAG, "cannot read exif");
		}

		if (exif != null) {
			int orientation = exif.getAttributeInt(
					ExifInterface.TAG_ORIENTATION, -1);
			if (orientation != -1) {
				// We only recognize a subset of orientation tag values.
				switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_90:
					degree = 90;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					degree = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_270:
					degree = 270;
					break;
				}
			}
		}

		return degree;
	}
	
	public static void setOrientationLock(Activity activity, boolean lock)
	{
		//[2014.12.18][윤주희] tablet일 경우, 단말에 따라 orientation rotation이 다름. phone일 경우만 사용하도록 함. 
		if(isTablet(activity))
			return;
			
		if(activity != null) {
			if(lock) {
				WindowManager wm = (WindowManager)activity.getSystemService(Context.WINDOW_SERVICE);
				Display disp = wm.getDefaultDisplay();
				int orientation = disp.getRotation();
				
				switch(orientation) {
				case Surface.ROTATION_0:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
					break;
				case Surface.ROTATION_90:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
					break;
				case Surface.ROTATION_180:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
					break;
				case Surface.ROTATION_270:
					activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
					break;
				}
			} else {
				activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
			}
		}
	}

	public static Bitmap getRotatedBitmap(Bitmap bitmap, int degrees)
	{
		if (degrees != 0 && bitmap != null) {
			Matrix m = new Matrix();
			m.setRotate(degrees, (float) bitmap.getWidth() / 2,
					(float) bitmap.getHeight() / 2);
			try {
				Bitmap b2 = Bitmap.createBitmap(bitmap, 0, 0,
						bitmap.getWidth(), bitmap.getHeight(), m, true);
				if (bitmap != b2) {
					bitmap.recycle();
					bitmap = null;
					bitmap = b2;
					b2 = null;
				}
			} catch (OutOfMemoryError ex) {
				// We have no memory to rotate. Return the original bitmap.
			}
		}

		return bitmap;
	}

	public static boolean isInstalled(Context a_context, String a_strPackageName)
	{
		try {
			a_context.getPackageManager().getPackageInfo(a_strPackageName, 0);
			return true;
		}catch (NameNotFoundException e) {
			return false;
		}
	}

	public static boolean isAboveLOLLIPOP() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP);
	}

	public static boolean isAboveM() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M);
	}

	public static boolean isAboveN() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N);
	}

	public static boolean isAboveO() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);
	}

	public static boolean isAboveQ() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q);
	}

	public static boolean isAboveR() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R);
	}

	public static boolean isAboveS() {
		return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S);
	}

	@RequiresApi(api = Build.VERSION_CODES.Q)
	public static boolean isAboveQWithNonStorageLegacy() {
		return isAboveQ() && !Environment.isExternalStorageLegacy();
	}

	public static int getPressure(MotionEvent event, int pos)
	{
		float pressure;
		if (pos < 0 || pos >= event.getHistorySize())
			pressure = event.getPressure();
		else
			pressure = event.getHistoricalPressure(pos);

		float maxDevicePressure = 1.0f;
		try {
			if (event.getDevice().getName().equals("sec_e-pen"))
				maxDevicePressure = event.getDevice().getMotionRange(MotionEvent.AXIS_PRESSURE).getMax();
			else
				pressure = maxDevicePressure;
		} catch (Exception e) {
			maxDevicePressure = 1.0f;
		}

		int maxPressure = 255;
		float multiplier = (float) (((float) maxPressure + 1.0) / maxDevicePressure);

		int nPressure = (int) (pressure * multiplier);
		if (nPressure > maxPressure)
			nPressure = maxPressure;

		return nPressure;
	}


	// Application Disable : User allows app gallery to be disalbe.
	public static boolean isEnabledImagePickIntent(PackageManager packageManager) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("image/*");

		List<ResolveInfo> resolveInfoList = packageManager.queryIntentActivities(intent, 0);
		return resolveInfoList.size() > 0;
	}

	public static boolean doesPackageExist(Context a_context, String a_strTargetPackage)
	{
		PackageManager pm = a_context.getPackageManager();
		try {
			pm.getPackageInfo(a_strTargetPackage, PackageManager.GET_META_DATA);
		} catch (NameNotFoundException e) {
			return false;
		}
		return true;
	}

	@SuppressLint("NewApi")
	public static void setCheckBoxAccessibility (View view)
	{
		//[13.08.09][bjh] #12302 TalkBack issue : not read checkbox state when check click check box
		final LinearLayout linearLayout;
		if(view instanceof LinearLayout)
		{
			view.setImportantForAccessibility(view.IMPORTANT_FOR_ACCESSIBILITY_NO);

			linearLayout = (LinearLayout)view;
			
			final CheckBox checkBox;
			int j=0;
			for(int i=0; i<linearLayout.getChildCount() ; i++)
			{
				if(linearLayout.getChildAt(i) instanceof CheckBox)
					j=i;
			}
			
			if(linearLayout.getChildAt(j) instanceof CheckBox)
				checkBox = (CheckBox) linearLayout.getChildAt(j);
			else
				checkBox = null;

			if(checkBox != null){
				new Handler().postDelayed(new Runnable() {
					public void run() {
						checkBox.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
						linearLayout.setImportantForAccessibility(linearLayout.IMPORTANT_FOR_ACCESSIBILITY_YES);
					}
				}, 300);
			}
			else {
				linearLayout.setImportantForAccessibility(linearLayout.IMPORTANT_FOR_ACCESSIBILITY_YES);
			}
		}
		//~#12302 TalkBack issue : not read checkbox state when check click check box
	}
	
	public static boolean isIntentSafe (String intentAction , Activity activity){
		Intent intent=new Intent(intentAction);
		PackageManager packageManager = activity.getPackageManager();
		List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
		boolean isIntentSafe = activities.size() > 0;
		return isIntentSafe;
	}

	public static String getVersionName(Context context) {
		try {
			PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			CMLog.trace(e.getStackTrace());
			return "";
		}
	}

	public static int compareAlphanum(String s1, String s2){
		char[] c1 = s1.toCharArray();
		char[] c2 = s2.toCharArray();
		int index1 = 0, index2 = 0;
		int count1 = c1.length, count2 = c2.length;
		long val1, val2;

		while (index1 < count1 && index2 < count2) {
			if (c1[index1] >= '0' && c1[index1] <= '9' && c2[index2] >= '0' && c2[index2] <= '9') {

				val1 = c1[index1] - '0';
				index1++;
				while (index1 < count1 && c1[index1] >= '0' && c1[index1] <= '9') {
					val1 *= 10;
					val1 += c1[index1] - '0';
					index1++;
				}
				val2 = c2[index2] - '0';
				index2++;
				while (index2 < count2 && c2[index2] >= '0' && c2[index2] <= '9') {
					val2 *= 10;
					val2 += c2[index2] - '0';
					index2++;
				}

				if (val1 < val2)
					return -1;
				else if (val1 > val2)
					return 1;
			}
			else if (c1[index1] == c2[index2]) {
				index1++;
				index2++;
			}
			else if (c1[index1] < c2[index2])
				return -1;
			else
				return 1;
		}

		if (index1 >= count1 && index2 >= count2)
			return 0;
		else if (index1 >= count1)
			return -1;
		else
			return 1;
	}
	
	// 1 inch = 1440 twip / 1 inch = 25.4 mm / 1 inch = 2.54cm
	public static float convertTwipToCm(int twip) {
		return convertTwipToInch(twip) * 2.54f;
	}
	
	public static float convertTwipToInch(int twip) {
		return twip / 1440f;
	}
	
	public static float convertTwipToMM(int twip) {
		return convertTwipToCm(twip) * 10f;
	}
	
	public static float convertInchToTwip(float inch) {
		return inch * 1440;
	}
	
	public static float convertCmToTwip(float cm) {
		return convertInchToTwip(cm / 2.54f);
	}
	
	public static float convertMmToTwip(float mm) {
		return convertCmToTwip(mm / 10f);
	}
	
	public static int convertPxToMm(int a_px) {
		return Math.max((int) (a_px * 25.4 / 200), 1); // Engine resolution(200dpi)
	}

	public static int convertMmToPx(int a_mm) {
		return (int) (a_mm * 200 / 25.4); // Engine resolution(200dpi)
	}

	// 1point = 20twip  / MS 기준 10pt 를 1글자로 표현. : 200
	// MS 또한 소숫점 2자리까지만 지원함으로, Twip 변환 시에는 무조건 Int만 나오게 됨.
	public static int convertMsCharPtToTwip(float MsCharPt) {
		BigDecimal charPointt = new BigDecimal(MsCharPt);
		BigDecimal MsCharPoint = new BigDecimal("200"); // MS Char Point : 10pt
		BigDecimal Result = charPointt.multiply(MsCharPoint); //곱셈
		return (int) Result.floatValue();
	}
	
	public static float convertTwipToMsCharPt(int twip) {
		BigDecimal charPointt = new BigDecimal(twip);
		BigDecimal MsCharPoint = new BigDecimal("200"); // MS Char Point : 10pt
		BigDecimal Result = charPointt.divide(MsCharPoint); //나누기
		return Result.floatValue();
	}
	
	public static boolean isPhone(Context context) {
		int screenLayout = Utils.getScreenSize(context);

		return screenLayout == Configuration.SCREENLAYOUT_SIZE_SMALL
				|| screenLayout == Configuration.SCREENLAYOUT_SIZE_NORMAL;
	}

	public static boolean isTablet(Context context) {
		return !isPhone(context);
	}
	
	public static int getStatusBarHeight(Window oWindow){
		Rect rect = new Rect();
		oWindow.getDecorView().getWindowVisibleDisplayFrame(rect);
		return rect.top;
	}

	public static boolean isRtolLocaleType(Locale locale)
	{
		boolean isRtolLocale = false;

		if (locale != null) {
			if (TextUtils.getLayoutDirectionFromLocale(locale) == View.LAYOUT_DIRECTION_RTL)
				isRtolLocale = true;
		}

		return isRtolLocale;
	}
	
	public static int getOutlineBtnSize(Context context){
		if(isPhone(context))
			return dipToPixel(context, 13);
		else
			return dipToPixel(context, 20);
	}

	public static boolean isEmojiChar(Character.UnicodeBlock unicodeBlock) {
		try {
			if(//Character.UnicodeBlock.HIGH_SURROGATES.equals(unicodeBlock)
//			|| Character.UnicodeBlock.LOW_SURROGATES.equals(unicodeBlock)
			Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_PICTOGRAPHS.equals(unicodeBlock))
//			|| Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.DINGBATS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.GEOMETRIC_SHAPES.equals(unicodeBlock)
//			|| Character.UnicodeBlock.MISCELLANEOUS_TECHNICAL.equals(unicodeBlock)
//			|| Character.UnicodeBlock.MISCELLANEOUS_SYMBOLS_AND_ARROWS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.ARROWS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.COMBINING_MARKS_FOR_SYMBOLS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.LETTERLIKE_SYMBOLS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.ENCLOSED_CJK_LETTERS_AND_MONTHS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.ENCLOSED_ALPHANUMERICS.equals(unicodeBlock)
//			|| Character.UnicodeBlock.SUPPLEMENTAL_ARROWS_A.equals(unicodeBlock)
//			|| Character.UnicodeBlock.SUPPLEMENTAL_ARROWS_B.equals(unicodeBlock)
//			|| Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION.equals(unicodeBlock)
//			|| Character.UnicodeBlock.GENERAL_PUNCTUATION.equals(unicodeBlock)
//			|| Character.UnicodeBlock.CURRENCY_SYMBOLS.equals(unicodeBlock))
				return true;
		} catch (Throwable e) {
		}

		return false;
	}

	public static InputFilter getEmojiFilter() {
		InputFilter imogiFilter = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				if (source.length() == 0) {
					return source;
				}

				String result = getFilterString(source, start, end);
				if (source.toString().equals(result)) {
					return null;
				} else {
					return result;
				}
			}
		};

		return imogiFilter;
	}

	public static boolean isEmoji(CharSequence source, int start, int end) {
		for (int i = start; i < end; i++) {
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(source.charAt(i));

			if (isEmojiChar(unicodeBlock)) {
				return true;
			}
		}

		return false;
	}

	public static String getFilterString(CharSequence source, int start, int end) {
		String result = source.toString();
		List<String> array = new ArrayList<>();

		for (int i = start; i < end; i++) {
			Character.UnicodeBlock unicodeBlock = Character.UnicodeBlock.of(source.charAt(i));

			if (isEmojiChar(unicodeBlock))
				array.add(String.valueOf(source.charAt(i)));
		}

		if (!array.isEmpty()) {
			for (String str : array) {
				result = result.replace(str, "");
			}
		}
		return result;
	}

	public static boolean isUsingTalkBackService(@NonNull Context context) {
		AccessibilityManager accessibilityManager = (AccessibilityManager) context.getSystemService(Activity.ACCESSIBILITY_SERVICE);
		List<AccessibilityServiceInfo> enabledAccessibilityServiceList = accessibilityManager.getEnabledAccessibilityServiceList(AccessibilityServiceInfo.DEFAULT);
		for (AccessibilityServiceInfo info : enabledAccessibilityServiceList) {
			if (info.getId().equals("com.google.android.marvin.talkback/.TalkBackService")) {
				ToastManager.INSTANCE.onMessage(context, "ttt", Toast.LENGTH_LONG);
				return true;
			}
		}

		return false;
	}

	public static int BGRtoRGB(int color) {
		if(color == 0)
			return 0;

		int a = 0xff000000;
		int r = (color & 0x000000ff) << 16;
		int g = (color & 0x0000ff00);
		int b = (color & 0x00ff0000) >> 16;

		return (a|r|g|b);
	}

//	public static int getAlertDialogTheme(GUIStyleInfo.StyleType styleType) {
//		switch (styleType) {
//			case eCOMMON:
//				return R.style.PolarisAlertDialogTheme_Word;
//			case eSheet:
//				return R.style.PolarisAlertDialogTheme_Sheet;
//			case eSlide:
//				return R.style.PolarisAlertDialogTheme_Slide;
//			case ePDF:
//				return R.style.PolarisAlertDialogTheme_Pdf;
//			default:
//				return R.style.PolarisAlertDialogTheme;
//		}
//	}

	public static void setNightMode(Context context, boolean setNightMode){
		int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
		switch (currentNightMode) {
			case Configuration.UI_MODE_NIGHT_YES:
				if(!setNightMode)
					AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
				break;
		}
	}

	@SuppressLint("NewApi")
	public static boolean isManagedProfile(Context context) {
		boolean isProfileOwnerApp = false;
		boolean isManagedProfile;

		try {
			DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(Context.DEVICE_POLICY_SERVICE);
			List<ComponentName> activeAdmins = devicePolicyManager.getActiveAdmins();
			if (activeAdmins != null) {
				for (ComponentName admin : activeAdmins) {
					String packageName = admin.getPackageName();
					isProfileOwnerApp = devicePolicyManager.isProfileOwnerApp(packageName);
					CMLog.d(TAG, "[isManagedProfile] packageName :" + packageName + ", isProfileOwnerApp :" + isProfileOwnerApp);

					if (isProfileOwnerApp) {
						break;
					}
				}
			}

			UserManager userManager = (UserManager) context.getSystemService(Context.USER_SERVICE);
			isManagedProfile = userManager.isManagedProfile();
			CMLog.d(TAG, "[isManagedProfile] isManagedProfile : " + isManagedProfile);
		} catch (Exception e) {
			isProfileOwnerApp = false;
			isManagedProfile = false;
			CMLog.trace(e.getStackTrace());
		}

		return isProfileOwnerApp || isManagedProfile;
	}

	public static ArrayList<Integer> getResouceIdList(Context context, int arrId) {
		ArrayList<Integer> resourceIdList = new ArrayList<>();
		TypedArray typedArray = context.getResources().obtainTypedArray(arrId);
		for (int i = 0; i < typedArray.length(); i++) {
			int resourceId = typedArray.getResourceId(i, -1);
			if(resourceId > 0) {
				resourceIdList.add(resourceId);
			}
		}
		typedArray.recycle();

		return resourceIdList;
	}
}