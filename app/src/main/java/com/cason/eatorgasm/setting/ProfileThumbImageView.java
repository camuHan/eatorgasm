package com.cason.eatorgasm.setting;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.util.AttributeSet;

import androidx.activity.result.contract.ActivityResultContract;
import androidx.annotation.DrawableRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;

import com.bumptech.glide.signature.ObjectKey;
import com.cason.eatorgasm.R;
import com.cason.eatorgasm.util.CMLog;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 해당 클래스는 썸네일을 로딩하기 위한 이미지뷰를 제공합니다.
 * 만약 특정 위치에 서버에 등록된 사용자 썸네일을 보여주고 싶다면, XML에서 해당 이미지뷰를 그대로 사용하면 됩니다.
 *
 *
 * ===WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING===
 * ===WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING===
 *
 * 이 프로필 이미지 뷰는 일반적으로 Activity에 동기화가 되기 때문에
 * Fragment에서 사용할 경우, Fragment로 라이프사이클을 설정해야 합니다.
 * 그렇지 않으면 Fragment에 동기화가 되지 않습니다.
 * {@code
 *    fragment.lifecycle.addObserver(profileThumbImageView)
 * }
 *
 * ===WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING===
 * ===WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING==WARNING===
 */
public class ProfileThumbImageView extends AppCompatImageView implements LifecycleObserver {
    public ProfileThumbImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        loadProfileThumbnail();

        // 이 썸네일 뷰를 라이프사이클과 동기화시켜 자동 갱신시킬 수 있도록 합니다.
        if(context instanceof LifecycleOwner){
            ((LifecycleOwner)context).getLifecycle().addObserver(this);
        }
    }

    /**
     * 사용자 레벨에 맞는 이미지 리소스 ID를 반환합니다.
     * @return 둥근 프로필 기본 이미지 리소스 ID
     */
    @DrawableRes
    public static int getDefaultThumbnailRes(){
//        if(PoLinkUserInfo.getInstance().isProServiceUser()) {
//            return R.drawable.img_profile_pro;
//        }
//        else if(PoLinkUserInfo.getInstance().isSmartServiceUser()) {
//            return R.drawable.img_profile_smart;
//        }
//        else if(PoLinkUserInfo.getInstance().isB2BUser()) {
//            return R.drawable.img_profile_business;
//        }
//        else {
            return R.drawable.home_tab_private_icon;
//        }
    }

    /**
     * 사용자 레벨에 맞는 기본 이미지를 둥글게 깎아 반환합니다.
     * @param context Context 객체
     * @return 둥근 프로필 기본 이미지
     */
    public static Drawable getDefaultThumbnailDrawable(Context context){
//        Bitmap src = BitmapFactory.decodeResource(context.getResources(), getDefaultThumbnailRes());
        Bitmap src = getBitmapFromVectorDrawable(context, getDefaultThumbnailRes());
        RoundedBitmapDrawable result = RoundedBitmapDrawableFactory.create(context.getResources(), src);
        result.setCornerRadius(Math.max(src.getWidth(), src.getHeight()) / 2.0f);
        return result;
    }

    public static Bitmap getBitmapFromVectorDrawable(Context context, int drawableId) {
        Drawable drawable = ContextCompat.getDrawable(context, drawableId);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = (DrawableCompat.wrap(drawable)).mutate();
        }

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    /**
     * 썸네일을 요청합니다. 만약 캐싱이 되어있다면 캐싱이 된 곳에서 썸네일을 가져옵니다.
     * 만약 동기화가 되어있을 경우(isSynchronized) Resume 상태에 도달하거나, 서버에 썸네일을 새로 등록할 때
     * 수동으로 호출하지 않아도 자동으로 호출됩니다.
     *
     * 주의1: 이 메소드는 반드시 메인 쓰레드에서만 호출하십시오.
     * 주의2: 메인 쓰레드에서 이 메소드를 핸들러에서 실행할 경우, 액티비티가 종료 상태에 도달할 때,
     *  액티비티가 파괴된 후 핸들러가 실행되어 GlideException 예외를 발생시킬 수 있습니다.
     *  따라서, 해당 메소드를 메인 쓰레드에서 실행시킬 때는 핸들러를 사용하지 마십시오.
     */
    @MainThread
    public void loadProfileThumbnail(){
        // 게스트 유저이거나 별도로 프로필 이미지를 설정하지 않았다면 기본 이미지만 로드
//        if (PoLinkUserInfo.getInstance().isGuestUser() || PoLinkUserInfo.getInstance().isUsingDefaultProfileImage()){
//            this.setImageDrawable(getDefaultThumbnailDrawable(getContext()));
//            return;
//        }

        this.setImageDrawable(getDefaultThumbnailDrawable(getContext()));

//        GlideUrl glideUrl = new GlideUrl(PoLinkHttpInterface.getInstance().IHttpAccountThumbnailDownloadUrl(),
//                GlideHeaderLoader.getGlideHeaders());

//        Glide.with(this)
//                .load(glideUrl)
//                .onlyRetrieveFromCache(true)
//                .signature(key)
//                .listener(new RequestListener<Drawable>() {
//                    @Override
//                    public boolean onLoadFailed(@Nullable @org.jetbrains.annotations.Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
//                        CMLog.d("THUMB_IMAGE", "이미지가 캐싱되어있지 않음(MyProfile) key="+key.toString());
//                        return false;
//                    }
//
//                    @Override
//                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
//                        CMLog.d("THUMB_IMAGE", "이미지가 캐싱되어있음(MyProfile) key="+key.toString());
//                        return false;
//                    }
//                })
//                .preload();
//
//        Glide.with(this)
//                .asBitmap()                                                         // 이미지 형식: 비트맵
//                .load(glideUrl)                                                     // URL에서 이미지 로딩
//                .diskCacheStrategy(DiskCacheStrategy.ALL)                           // 디스크 캐싱 사용. 네트워크에서 받아오므로, 전체 저장
//                .priority(Priority.NORMAL)
//                .signature(key)                                                     // 키 값을 명시하여 원할 때 캐시를 무시하도록 함. 이 값이 바뀌면 서버에 재요청
//                .circleCrop()                                                       // 원으로 자르기
//                .placeholder(getDefaultThumbnailDrawable(getContext()))             // Preload 이미지
//                .error(getDefaultThumbnailDrawable(getContext()))                   // 오류 시 기본 이미지 설정
//                .into(ProfileThumbImageView.this);
    }

    /**
     * 썸네일을 서버에서 새로 가져오도록 요청합니다.
     */
    public void reloadProfileThumbnail(){
        initThumbnailKey();
        loadProfileThumbnail();
    }

    /**
     * 이 이미지 뷰가 라이프사이클과 동기화되었는지를 나타냅니다.
     * @return 동기화 여부
     */
    public boolean isSynchronized() {
        return isSynchronized;
    }
    private boolean isSynchronized = false;

    /**
     * Thumbnail이 바라보는 Lifecycle이 onResume 상태에 도달했을 때 이미지를 갱신시킵니다.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    void onResumeEvent() {
        synchronized (imageViews){
            if(!imageViews.contains(this)){
                imageViews.add(this);
                isSynchronized = true;
                loadProfileThumbnail();
            }
        }
    }

    /**
     * Thumbnail이 바라보는 Lifecycle이 onPause 상태에 도달했을 때
     * 서버에서 갱신 요청이 들어와도 프로필 이미지를 변경시키지 못하도록
     * 리스트에서 안전하게 제외시킵니다.
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPauseEvent() {
        synchronized (imageViews){
            imageViews.remove(this);
            isSynchronized = false;
        }
    }

    // 썸네일을 서버에 요청할 때 식별용 키. 이 값이 바뀌면 캐싱이 되어있어도 서버에 재요청
    private static ObjectKey key = new ObjectKey(System.currentTimeMillis());
    /**
     * 썸네일 키를 갱신하여 이후 로딩을 시도하면 이미지를 서버에서 새로 다운로드합니다.
     */
    public static void initThumbnailKey(){
        key = new ObjectKey(System.currentTimeMillis());
        CMLog.w("PROFILE_IMAGE", "Profile Glide Key Reset : "+key);
    }

    /**
     * 사용자 썸네일을 이미지뷰 없이 프리로드하여 디스크 캐시에 저장합니다.
     * 미리 호출해 두면 홈 화면 진입 시 프로필 로드 속도를 향상시킬 수 있습니다.
     */
    @MainThread
    public static void preloadProfileThumbnail(@NonNull Context context){
//        GlideUrl glideUrl = new GlideUrl(PoLinkHttpInterface.getInstance().IHttpAccountThumbnailDownloadUrl(),
//                GlideHeaderLoader.getGlideHeaders());
//
//        Glide.with(context.getApplicationContext())
//                .asBitmap()                                                         // 이미지 형식: 비트맵
//                .load(glideUrl)                                                     // URL에서 이미지 로딩
//                .diskCacheStrategy(DiskCacheStrategy.ALL)                           // 디스크 캐싱 사용. 네트워크에서 받아오므로, 전체 저장
//                .priority(Priority.NORMAL)
//                .signature(key)                                                     // 키 값을 명시하여 원할 때 캐시를 무시하도록 함. 이 값이 바뀌면 서버에 재요청
//                .circleCrop()                                                       // 원으로 자르기
//                .submit();
    }

    public static ActivityResultContract<Void,Uri> getPickProfileContract(){
        return new ProfileImageChooserContract();
    }

    final static List<ProfileThumbImageView> imageViews = new ArrayList<>();

    /**
     * 서버에 썸네일 이미지를 등록하고, 등록에 성공하면 전체 썸네일 이미지를 변경합니다.
     * 만약 동기화 되어있는 경우(isSynchronized()) 자동으로 변경 결과가 적용됩니다.
     * @param bitmap 등록할 비트맵
     */
    public static void requestRegisterUserPortrait(Bitmap bitmap){
//        PoLinkUserInfo.getInstance().addLinkUserInfoListener(RegisterUserPortraitListener.getInstance());
//        PoLinkUserInfo.getInstance().requestRegistUserPortrait(bitmap);
    }
    public static void requestRegisterUserPortrait(Context context, Uri uri){
        BitmapFactory.Options options = new BitmapFactory.Options();
        InputStream is;
        Bitmap bitmap;
        try {
            is = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();

            final int default_portrait_size = 248;

            int width, height;
            float scale = Math.max((float)default_portrait_size / (float)bitmap.getWidth(), (float)default_portrait_size / (float)bitmap.getHeight());
            width = (int)((float)bitmap.getWidth() * scale);
            height = (int)((float)bitmap.getHeight() * scale);

            // before crop
            bitmap = Bitmap.createScaledBitmap(bitmap, width, height, true);

            // auto crop
            int startX = 0, startY = 0;
            if (width > height)
                startX = (int)((width - height) / 2);
            else
                startY = (int)((height - width) / 2);

            bitmap = Bitmap.createBitmap(bitmap, startX, startY, default_portrait_size, default_portrait_size);

            // Calculate Orientation
            int orientation;
            Cursor cursor = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                cursor = context.getContentResolver().query(uri,
                        new String[]{MediaStore.Images.ImageColumns.ORIENTATION}, null, null, null);
            }
            if (cursor == null || cursor.getCount() != 1)
                orientation = -1;
            else{
                cursor.moveToFirst();
                orientation = cursor.getInt(0);
            }

            // rotate
            if (orientation != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(orientation);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            }
            requestRegisterUserPortrait(bitmap);

            if (cursor != null) {
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//class RegisterUserPortraitListener implements PoLinkUserInfo.PoLinkUserInfoListener{
//    public static RegisterUserPortraitListener getInstance() {
//        if(listener==null) listener = new RegisterUserPortraitListener();
//        return listener;
//    }
//    private static RegisterUserPortraitListener listener;
//
//    private RegisterUserPortraitListener(){}
//
//    @Override
//    public void onAccountUserInfoModified(PoLinkUserInfoData prevUserInfo, PoLinkUserInfoData newUserInfo) {
//
//    }
//
//    @Override
//    public void OnAccountResult(PoAccountResultData oAccountResultData) {
//        if( oAccountResultData.requestSubCategory.equals(PoHTTPDefine.ServerAPISubCategory.API_CATEGORY_ACCOUNT_REGISTPORTRAIT)) {
//            if( oAccountResultData.resultCode ==  PoServerResponseCode.SUCCESS ) {
//                ProfileThumbImageView.initThumbnailKey();
//                for(ProfileThumbImageView imageView:ProfileThumbImageView.imageViews)
//                    imageView.loadProfileThumbnail();
//            } else {
//                CMLog.w("PROFILE_IMAGE", "이미지 등록 요청 실패" + oAccountResultData.resultCode);
//            }
//        }
//    }
//
//    @Override
//    public void OnAccountResultUserInfo(PoAccountResultUserInfoData oAccountResultUserInfoData) {
//
//    }
//
//    @Override
//    public void OnAccountResultDeviceList(PoAccountResultDeviceListData oAccountResultDeviceListData) {
//
//    }
//
//    @Override
//    public void OnAccountResultCurrentDeviceInfo(PoAccountResultCurrentDeviceData oAccountResultCurrentDeviceData) {
//
//    }
//
//    @Override
//    public void OnAccountResultDownLoadComplete() {
//
//    }
//
//    @Override
//    public void OnAccountCreateOneTimeLogin(String a_strLoginUrl) {
//
//    }
//
//    @Override
//    public void OnAccountResultDisconnectDeviceInfo(IPoResultData oPoResultData) {
//
//    }
//
//    @Override
//    public void OnAccountResultRecentPremiumExpiryInfo(PoAccountResultPremiumExpiryData oAccountResultPremiumExpireData) {
//
//    }
//
//    @Override
//    public void OnAccountResultSecurityKeyGenerate(PoAccountResultData oAccountResultData) {
//
//    }
//
//    @Override
//    public void OnAccountResultEmailLoginInfo(PoAccountResultEmailLoginInfoData oPoResultData) {
//
//    }
//
//    @Override
//    public void OnAccountResultLandingType(PoAccountResultLandingType oPoResultData) {
//
//    }
//
//    @Override
//    public void OnAccountResultPasswordCheck(boolean passwordSame) {
//
//    }
//
//    @Override
//    public void OnAccountResultDeviceExist(PoAccountResultDeviceExist oPoResultData) {
//
//    }
//
//    @Override
//    public void OnAccountResultDeviceEmailList(PoAccountResultDeviceEmailList oPoResultData) {
//
//    }
//
//    @Override
//    public void OnHttpFail(PoHttpRequestData requestData, int httpResult, String exceptionData) {
//
//    }
//}

class ProfileImageChooserContract extends ActivityResultContract<Void, Uri> {
    @NonNull
    @Override
    public Intent createIntent(@NonNull Context context, @NonNull Void input) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        return Intent.createChooser(intent, context.getString(R.string.inser_image_chooser_title));
    }

    @Override
    public Uri parseResult(int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent intent) {
        return intent!=null?intent.getData():null;
    }
}