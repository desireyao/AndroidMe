package com.github.yuweiguocn.demo.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.github.yuweiguocn.demo.greendao.db.DaoMaster;
import com.github.yuweiguocn.demo.greendao.db.MySQLiteOpenHelper;

import greendao.MigrationHelper;

public class MainActivity extends AppCompatActivity {

    private DaoMaster daoMaster;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.include_toolbar);
        setSupportActionBar(toolbar);

        MigrationHelper.DEBUG = true;
        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "test.db", null);
        daoMaster = new DaoMaster(helper.getWritableDatabase());
    }
}
