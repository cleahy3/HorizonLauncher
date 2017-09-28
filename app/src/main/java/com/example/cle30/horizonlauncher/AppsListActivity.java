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
import android.widget.Filter;

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
        setSearchListener();
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
    private void setSearchListener() {
        SearchView search = (SearchView)findViewById(R.id.search_app_bar);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            private String searchParams;
            @Override
            public boolean onQueryTextSubmit(String s) {
                setView(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                setView(s);
                return false;
            }
        });
    }

    private void setView (final String s) {
        list = (GridView)findViewById(R.id.apps_list);
        final ArrayAdapter<AppDetail> adapter = new ArrayAdapter<AppDetail>(this,
                R.layout.list_item,
                apps){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(apps.size() > position) {
                    if (convertView == null) {
                        convertView = getLayoutInflater().inflate(R.layout.list_item, null);
                    }

                    ImageView appIcon = (ImageView) convertView.findViewById(R.id.item_app_icon);
                    appIcon.setImageDrawable(apps.get(position).icon);
                    appIcon.setOnDragListener(View.OnDragListener() {
                        
                    });
                    TextView appName = (TextView) convertView.findViewById(R.id.item_app_name);
                    appName.setText(apps.get(position).name);


                }
                return convertView;
            }

            public Filter getFilter () {
                AppFilter appFilter = new AppFilter();
                return appFilter;
            }

            class AppFilter extends Filter {
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {

                    // Now we have to inform the adapter about the new list filtered
                    if (results.count == 0) {
                        loadApps();
                        notifyDataSetChanged();
                    }
                    else {
                        apps = (List<AppDetail>) results.values;
                        notifyDataSetChanged();
                    }
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();
                    // We implement here the filter logic
                    if (constraint == null || constraint.length() == 0) {
                        // No filter implemented we return all the list
                        results.values = apps;
                        results.count = apps.size();
                    }
                    else {
                        // We perform filtering operation
                        List<AppDetail> nAppList = new ArrayList<AppDetail>();

                        for (AppDetail a : apps ) {
                            if (a.name.toString().toUpperCase()
                                    .startsWith(constraint.toString().toUpperCase())) {
                                nAppList.add(a);
                            };
                        }

                        results.values = nAppList;
                        results.count = nAppList.size();
                    }
                    return results;
                }
            }
        };

        if(s != "main") {
            adapter.getFilter().filter(s);
        }

        list.setAdapter(adapter);
    }

    private GridView list;
    private void loadGridView(){
        setView("main");
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
