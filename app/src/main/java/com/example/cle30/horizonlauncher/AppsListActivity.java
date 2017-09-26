package com.example.cle30.horizonlauncher;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.List;


public class AppsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apps_list);
        loadApps();
        loadGridView();
        addClickListener();
    }
    private PackageManager manager;
    private List<AppDetail> apps;
    private void loadApps() {
        manager = getPackageManager();
        apps = new ArrayList<AppDetail>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for(ResolveInfo ri:availableActivities) {
            AppDetail app = new AppDetail();
            app.name = ri.loadLabel(manager);
            app.appName = ri.activityInfo.packageName;
            app.icon = ri.loadIcon(manager);
            apps.add(app);
        }
    }
    private void onTextChange() {

    }
    private GridView list;
    private void loadGridView(){
        list = (GridView)findViewById(R.id.apps_list);
        ArrayAdapter<AppDetail> adapter = new ArrayAdapter<AppDetail>(this,
                R.layout.list_item,
                apps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                }
                ImageView appIcon = (ImageView)convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                SearchView search = (SearchView)convertView.findViewById(R.id.search_app_bar);
                TextView appName = (TextView)convertView.findViewById(R.id.item_app_name);
                search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

                    @Override
                    public boolean onQueryTextSubmit(String s) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String s) {
                        return false;
                    }
                });
                appName.setText(apps.get(position).name);

                return convertView;
            }
        };
        list.setAdapter(adapter);
    }

    private void addClickListener(){
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                    Intent i = manager.getLaunchIntentForPackage(apps.get(pos).appName.toString());
                    AppsListActivity.super.startActivity(i);
            }
        });
    }
}
