package config;

import android.os.Environment;

public class ValueClass {
	
	public static final String KEY_DECODE_1D = "preferences_decode_1D";
	public static final String KEY_DECODE_QR = "preferences_decode_QR";
	public static final String KEY_DECODE_DATA_MATRIX = "preferences_decode_Data_Matrix";
	public static final String KEY_CUSTOM_PRODUCT_SEARCH = "preferences_custom_product_search";
	public static final String KEY_PLAY_BEEP = "preferences_play_beep";
	public static final String KEY_VIBRATE = "preferences_vibrate";
	public static final String KEY_COPY_TO_CLIPBOARD = "preferences_copy_to_clipboard";
	public static final String KEY_FRONT_LIGHT_MODE = "preferences_front_light_mode";
	public static final String KEY_BULK_MODE = "preferences_bulk_mode";
	public static final String KEY_REMEMBER_DUPLICATES = "preferences_remember_duplicates";
	public static final String KEY_SUPPLEMENTAL = "preferences_supplemental";
	public static final String KEY_AUTO_FOCUS = "preferences_auto_focus";
	public static final String KEY_SEARCH_COUNTRY = "preferences_search_country";
	public static final String KEY_DISABLE_CONTINUOUS_FOCUS = "preferences_disable_continuous_focus";
	public static final String KEY_DISABLE_EXPOSURE = "preferences_disable_exposure";
	public static final String KEY_HELP_VERSION_SHOWN = "preferences_help_version_shown";
	  
	public static String PackageName = "com.vikaa.legou";
	
	public static String PathForTakeCamera = "";
	
	public static final String FEEDBACK_SOURCE = "Android";
	
	public static final long TIMELIMIT = 24 * 3600 *1000;
	
	public static final String AUDIO_FILE_PATH = Environment.getExternalStorageDirectory() + "/legou/audio/";
	public static final String CACHE_IMAGE_FILE_PATH = Environment.getExternalStorageDirectory() + "/legou/image/";
	public static final String CACHE_CAMERA_FILE_PATH = Environment.getExternalStorageDirectory() + "/legou/";
	
	//refresh
	public static final int REFRESHINGTOP = 40;
	
	
	//url type
	public static final int URL_REQUEST_COUNTS = 1;
	public static final int URL_LEGAL = 0x01;
	public static final int URL_ILLEGAL = 0x02;
	
	
	//login intent
	public static final String INTENT_LOGIN_ISDISPLAYLOGO = "LOGINISDISPLAYLOGO";
	
	//Intent key
	public static final String IMAGES = "com.nostra13.example.universalimageloader.IMAGES";
	public static final String IMAGE_POSITION = "com.nostra13.example.universalimageloader.IMAGE_POSITION";
	
	// Timeline ->  StoryCam requestCode 
	public static final int TakePhoto = 1;
	// StoryCam ->  Timeline reponsedata 
	public static final String ResponsePathFromStoryCamToTimeline = "ResponsePathFromStoryCamToTimeline";
	
	// Timeline -> Share requestCode
	public static final int SavePhoto = 0x02;
	public static final String PhotoPathFromTimelineToShare = "PhotoPathFromTimelineToShare";
	// Share -> Timeline resposenCode
	public static final String ResponseActionFromShareToTimeline = "ResponseActionFromShareToTimeline";
	public static final String ResponsePhotoFromShareToTimeline = "ResponsePhotoFromShareToTimeline";
	public static final int PostTwitter = 0x03;
	public static final int CancelPostTwitterAndOpenCam = 0x04;
	
	//share -> oauth requestcode
	public static final int OauthLogin = 0x05;
	
	//storycam -> watermask choose
	public static final String DataFromStoryToWaterMaskChoose = "DataFromStoryToWaterMaskChoose";
	public static final int RequestCodeForPhotoChangedFromStoryCamToWaterMaskChoose = 0x06;
	public static final String DataFromWaterMaskChooseToStoryCam = "DataFromWaterMaskChooseToStoryCam";
}
