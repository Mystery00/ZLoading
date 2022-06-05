package com.zyao89.zcustomview;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;
import com.zyao89.zcustomview.loading.ShowActivity;
import com.zyao89.zcustomview.loading.ShowTimeAllActivity;

import java.util.Locale;

public class MainActivity extends AppCompatActivity
{
    private              int    mSelectedItemIndex = 1;
    private static final String INDEX_01           = "切换为新页面";
    private static final String INDEX_02           = "切换为Dialog";
    private Toast mToast;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LinearLayout containerLinearLayout = (LinearLayout) findViewById(R.id.container);
        createAllButton(containerLinearLayout);
        createButtons(containerLinearLayout);

        // 修改基本尺寸， 原尺寸为 56.0f
//        ZLoadingBuilder.DEFAULT_SIZE = 100.0f;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        menu.add(Menu.NONE, 0, Menu.NONE, INDEX_01);
        menu.add(Menu.NONE, 1, Menu.NONE, INDEX_02);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (mToast == null)
        {
            mToast = Toast.makeText(MainActivity.this, "Error, 出错了", Toast.LENGTH_LONG);
        }
        switch (item.getItemId())
        {
            case 0:
                mToast.setText(INDEX_01);
                break;
            case 1:
                mToast.setText(INDEX_02);
                break;
        }
        mToast.show();
        mSelectedItemIndex = item.getItemId();
        return super.onOptionsItemSelected(item);
    }

    private void createAllButton(LinearLayout containerLinearLayout)
    {
        AppCompatButton button = new AppCompatButton(this);
        int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
        button.setPadding(padding, padding, padding, padding);
        containerLinearLayout.addView(button, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setText("Show Time");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(MainActivity.this, ShowTimeAllActivity.class);
                startActivity(intent);
            }
        });
    }

    private void createButtons(LinearLayout containerLinearLayout)
    {
        Z_TYPE[] types = Z_TYPE.values();
        for (Z_TYPE type : types)
        {
            AppCompatButton button = new AppCompatButton(this);
            int padding = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            button.setPadding(padding, padding, padding, padding);
            containerLinearLayout.addView(button, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            button.setText(String.format(Locale.getDefault(), "【%d】%s LOADING", type.ordinal(), type.name()));
            setupListener(button, type);
        }
    }

    private void setupListener(View view, final Z_TYPE type)
    {
        final int index = type.ordinal();
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                switch (mSelectedItemIndex)
                {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, ShowActivity.class);
                        intent.putExtra(ShowActivity.LOADING_TYPE, index);
                        startActivity(intent);
                        break;
                    case 1:
                    default:
                        ZLoadingDialog dialog = new ZLoadingDialog(MainActivity.this);
                        dialog.setLoadingBuilder(type)
                                .setLoadingColor(Color.parseColor("#ff5305"))
                                .setHintText("正在加载中...")
//                                .setHintTextSize(16) // 设置字体大小
                                .setHintTextColor(Color.GRAY)  // 设置字体颜色
//                                .setDurationTime(0.5) // 设置动画时间百分比
                                .setDialogBackgroundColor(Color.parseColor("#cc111111")) // 设置背景色
                                .show();
                        break;
                }
            }
        });
    }
}
