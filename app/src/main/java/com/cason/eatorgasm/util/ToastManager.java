package com.cason.eatorgasm.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.lang.ref.WeakReference;

public class ToastManager {
    public static ToastManager INSTANCE = new ToastManager();

    private ToastManager() {} // don't make it

    static private final int SHOW = 1;
    static private final int CANCEL = 2;

    private Toast mToast;

    protected WeakToastHandler mToastHandler = new WeakToastHandler(this);

    protected static class WeakToastHandler extends Handler {
        private final WeakReference<ToastManager> ref;

        private WeakToastHandler(ToastManager outer) {
            ref = new WeakReference<>(outer);
        }

        @Override
        public void handleMessage(Message msg) {
            ToastManager toastManager = ref.get();
            if (toastManager != null) {
                toastManager.toastManagerHandleMessage(msg);
            }
        }
    }

    private void toastManagerHandleMessage(Message msg) {
        if (msg == null) {
            return;
        }

        switch (msg.what) {
            case SHOW:
                if (mToast != null) {
                    mToast.show();
                }
                break;
            case CANCEL:
                if (mToast != null) {
                    mToast.cancel();
                }
                break;
        }
    }

    public void onMessage(Context a_context, CharSequence text) {
        setToastText(a_context, text, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToastHandler.sendEmptyMessage(SHOW);
    }

    public void onMessage(Context a_context, int a_id) {
        if (a_context == null)
            return;

        onMessage(a_context, a_context.getString(a_id));
    }

    public void onMessage(Fragment a_fragment, int a_id) {
        if (a_fragment == null)
            return;

        onMessage(a_fragment.getActivity(), a_fragment.getString(a_id));
    }

    public void onMessage(Context a_context, CharSequence a_text, int a_duration) {
        setToastText(a_context, a_text, a_duration);
        mToast.setGravity(Gravity.BOTTOM, 0, 100);
        mToastHandler.sendEmptyMessage(SHOW);
    }

    public void onMessage(Fragment a_fragment, int a_id, int a_duration) {
        onMessage(a_fragment.getActivity(), a_fragment.getString(a_id), a_duration);
    }

    public void onMessage(Context a_context, int a_id, int a_duration) {
        onMessage(a_context, a_context.getString(a_id), a_duration);
    }

    public void onMessageCenter(Context a_context, CharSequence a_text) {
        setToastText(a_context, a_text, Toast.LENGTH_SHORT);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToastHandler.sendEmptyMessage(SHOW);
    }

    public void onMessageCenter(Context a_context, int a_id) {
        onMessageCenter(a_context, a_context.getString(a_id));
    }

    public void onMessage(Context a_context, CharSequence a_text, int a_duration, int a_gravity, int a_offsetX, int a_offsetY) {
        setToastText(a_context, a_text, a_duration);
        mToast.setGravity(a_gravity, a_offsetX, a_offsetY);
        mToastHandler.sendEmptyMessage(SHOW);
    }

    public void onMessage(Context a_context, int a_id, int a_duration, int a_gravity, int a_offsetX, int a_offsetY) {
        onMessage(a_context, a_context.getString(a_id), a_duration, a_gravity, a_offsetX, a_offsetY);
    }

    private void setToastText(Context ctx, CharSequence text, int duration){
        if (mToast == null)
            mToast = Toast.makeText(ctx, text, duration);
        else {
            cancel();
            mToast.setText(text);
        }
    }

    public void cancel() {
        if (mToast != null) {
            mToast.cancel();
        }
        if (mToastHandler != null) {
            mToastHandler.removeMessages(SHOW);
        }
    }
}