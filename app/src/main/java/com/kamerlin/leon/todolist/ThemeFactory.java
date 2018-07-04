package com.kamerlin.leon.todolist;

import android.content.Context;
import android.content.res.TypedArray;

public class ThemeFactory {

    private final Context mContext;

    public ThemeFactory(Context context) {
        mContext = context;
    }

    public int getThemeRes(String name) {
        switch (name) {
            case "Red": return R.style.AppThemeRed;
            case "Pink": return R.style.AppThemePink;
            case "Purple": return R.style.AppThemePurple;
            case "Deep Purple": return R.style.AppThemeDeepPurple;
            case "Indigo": return R.style.AppThemeIndigo;
            case "Blue": return R.style.AppThemeBlue;
            case "Light Blue": return R.style.AppThemeLightBlue;
            case "Cyan": return R.style.AppThemeCyan;
            case "Teal": return R.style.AppThemeTeal;
            case "Green": return R.style.AppThemeGreen;
            case "Light Green": return R.style.AppThemeLightGreen;
            case "Lime": return R.style.AppThemeLime;
            case "Yellow": return R.style.AppThemeYellow;
            case "Amber": return R.style.AppThemeAmber;
            case "Orange": return R.style.AppThemeOrange;
            case "Brown": return R.style.AppThemeBrown;
            case "Grey": return R.style.AppThemeGreen;
            case "Blue Grey": return R.style.AppThemeBlueGrey;
            default: return 0;
        }

    }

    public TypedArray getThemeColor(String theme) {
        return mContext.getTheme().obtainStyledAttributes(getThemeRes(theme), new int[] {R.attr.colorPrimary, R.attr.colorPrimaryDark, R.attr.colorAccent});
    }
}
