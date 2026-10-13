package ca.comp3074.paycalculator;

import android.app.Activity;
import android.graphics.Insets;
import android.os.Build;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowInsets;

/** Keeps screen content below system bars and the native ActionBar. */
final class ScreenInsets {
    private ScreenInsets() {}
    static void apply(Activity activity, View root) {
        if (Build.VERSION.SDK_INT < 35) return;
        final int left = root.getPaddingLeft(), top = root.getPaddingTop();
        final int right = root.getPaddingRight(), bottom = root.getPaddingBottom();
        root.setOnApplyWindowInsetsListener((view, windowInsets) -> {
            Insets bars = windowInsets.getInsets(WindowInsets.Type.systemBars());
            Insets keyboard = windowInsets.getInsets(WindowInsets.Type.ime());
            view.post(() -> {
                TypedValue value = new TypedValue();
                int actionBarHeight = 0;
                if (activity.getActionBar() != null && activity.getActionBar().isShowing()
                        && activity.getTheme().resolveAttribute(android.R.attr.actionBarSize, value, true)) {
                    actionBarHeight = TypedValue.complexToDimensionPixelSize(value.data,
                            activity.getResources().getDisplayMetrics());
                }
                int[] position = new int[2];
                view.getLocationOnScreen(position);
                // Subtract any spacing already supplied by the window decor.
                int extraTop = Math.max(0, bars.top + actionBarHeight - position[1]);
                view.setPadding(left + bars.left, top + extraTop, right + bars.right,
                        bottom + Math.max(bars.bottom, keyboard.bottom));
            });
            return windowInsets;
        });
        root.requestApplyInsets();
    }
}
