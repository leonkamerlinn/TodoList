package com.kamerlin.leon.todolist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;


public class ThemeActivity extends AppCompatActivity {


    private static final String EXTRA_TOP = "extra_top";
    private static final String EXTRA_INDEX = "extra_index";
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String theme = sharedPref.getString(getString(R.string.pref_key_theme), null);
        final ThemeFactory themeFactory = new ThemeFactory(this);
        final String[] data = getResources().getStringArray(R.array.themes);
        if (theme != null) {
            setTheme(themeFactory.getThemeRes(theme));
        }
        setContentView(R.layout.activity_theme);




        mListView = findViewById(R.id.listView);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, data) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_theme, parent, false);
                }

                LinearLayout linearLayout = convertView.findViewById(R.id.circleLayout);

                int color = themeFactory.getThemeColor(getItem(position)).getColor(0, 0);
                linearLayout.getBackground().setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC));





                TextView textView = convertView.findViewById(R.id.textView);

                textView.setText(getItem(position));

                return convertView;
            }
        };

        mListView.setAdapter(adapter);

        if (getIntent().hasExtra(EXTRA_TOP)) {
            int top = getIntent().getIntExtra(EXTRA_TOP, 0);
            int index = getIntent().getIntExtra(EXTRA_INDEX, 0);
            mListView.setSelectionFromTop(index, top);
        }

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                sharedPref
                        .edit()
                        .putString(getString(R.string.pref_key_theme), data[position])
                        .apply();

                Intent intent = new Intent(ThemeActivity.this, ThemeActivity.class);
                // save index and top position
                int index = mListView.getFirstVisiblePosition();
                View v = mListView.getChildAt(0);
                int top = (v == null) ? 0 : (v.getTop() - mListView.getPaddingTop());

                intent.putExtra(EXTRA_INDEX, index);
                intent.putExtra(EXTRA_TOP, top);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });

    }



}
