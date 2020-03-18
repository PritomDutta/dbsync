package com.claudiodegio.dbsync.sample.db5;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;

import com.claudiodegio.dbsync.provider.CloudProvider;
import com.claudiodegio.dbsync.DBSync;
import com.claudiodegio.dbsync.provider.GDriveCloudProvider;
import com.claudiodegio.dbsync.TableToSync;
import com.claudiodegio.dbsync.sample.BaseMainDbActivity;
import com.claudiodegio.dbsync.sample.R;
import com.claudiodegio.dbsync.sample.core.TableViewerFragment;

public class MainDb5Activity extends BaseMainDbActivity implements TableViewerFragment.OnEditListener {

    private final static String TAG = "MainDb5Activity";

    TableViewerFragment mFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_main_db5);
        super.onCreate(savedInstanceState);


        mFragment = TableViewerFragment.newInstance("db5", "category");
        mFragment.setOnItemClicked(this);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.add(R.id.flFragment, mFragment, "TAG").commit();
    }

    @Override
    public void onItemEdit(long id, String[] data) {
        Intent intent = new Intent(this, InsertNameActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
    }

    @Override
    public void onAdd() {
        startActivity(new Intent(this, InsertNameActivity.class));
    }

    @Override
    public void onPostSync() {
        mFragment.reload();
    }

    @Override
    public void onPostSelectFile() {
        Log.d(TAG, "onPostSelectFile");

        CloudProvider gDriveProvider = new GDriveCloudProvider.Builder(this.getBaseContext())
                .setDriveID(driveId)
                .setDriveService(googleDriveService)
                .build();
        TableToSync tableToSync = new TableToSync.Builder("category")
                .addMatchRule("NAME = :NAME")
                .build();

        dbSync = new DBSync.Builder(this.getBaseContext())
                .setCloudProvider(gDriveProvider)
                .setSQLiteDatabase(app.db5OpenHelper.getWritableDatabase())
                .setDataBaseName(app.db5OpenHelper.getDatabaseName())
                .addTable(tableToSync)
                .setSchemaVersion(2)
                .build();
    }
}
