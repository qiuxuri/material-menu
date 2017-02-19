package com.example.myapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.MaterialMenuView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    private boolean          direction;
    private MaterialMenuView materialMenuView;
    private int              actionBarMenuState;
    RelativeLayout left;    //定义左边滑动的布局
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        left = (RelativeLayout) findViewById(R.id.drawer1);
        toolbar.setTitle("123213");
        setSupportActionBar(toolbar);//设置标题栏为toolbar


              /*以下代码是 为toolbar设置成为旋转图标的方法*/
        MaterialMenuDrawable materialMenu = new MaterialMenuDrawable(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN);
        toolbar.setNavigationIcon(materialMenu);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                // random state
                actionBarMenuState = generateState(actionBarMenuState);
                getMaterialMenu(toolbar).animateIconState(intToState(actionBarMenuState));
                if (!direction){
                drawerLayout.openDrawer(left);}
                else{
                    drawerLayout.closeDrawer(left);
                }
            }
        });
      /* *//**//***///*//**//***/*///*/**/*/

        inits();//此段代码为DrawLayout设置滑动监听，toolbar随着DrawLayout的滑动而变化

    }

    @Override
    public void onPostCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        refreshDrawerState();
    }
    private void refreshDrawerState() {
        this.direction = drawerLayout.isDrawerOpen(GravityCompat.START);
    }

    private void inits(){
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        drawerLayout.setScrimColor(Color.parseColor("#11000000"));
        drawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                getMaterialMenu(toolbar).setTransformationOffset(
                        MaterialMenuDrawable.AnimationState.BURGER_ARROW,
                        direction ? 2 - slideOffset : slideOffset
                );
            }

            @Override
            public void onDrawerOpened(android.view.View drawerView) {
                direction = true;
            }

            @Override
            public void onDrawerClosed(android.view.View drawerView) {
                direction = false;
            }
        });
    }



    private static int generateState(int previous) {
        int generated = new Random().nextInt(2);
        return generated != previous ? generated : generateState(previous);
    }
    private static MaterialMenuDrawable getMaterialMenu(Toolbar toolbar) {
        return (MaterialMenuDrawable) toolbar.getNavigationIcon();
    }
    private static MaterialMenuDrawable.IconState intToState(int state) {
        switch (state) {
            case 0:
                return MaterialMenuDrawable.IconState.BURGER;
            case 1:
                return MaterialMenuDrawable.IconState.ARROW;
            case 2:
                return MaterialMenuDrawable.IconState.X;
            case 3:
                return MaterialMenuDrawable.IconState.CHECK;
        }
        throw new IllegalArgumentException("Must be a number [0,3)");
    }

}
