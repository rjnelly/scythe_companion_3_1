package com.example.scythecompanion;

import android.app.Activity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public abstract class UtilMethods {

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        View focusedView = activity.getCurrentFocus();
        if (focusedView != null)
            inputMethodManager.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
    }
    public static void showSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }
}
