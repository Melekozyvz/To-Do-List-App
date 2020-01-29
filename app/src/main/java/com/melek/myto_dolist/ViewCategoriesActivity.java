package com.melek.myto_dolist;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.melek.myto_dolist.Adapter.CategoryAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ViewCategoriesActivity extends AppCompatActivity {

    ListView listView;
    FloatingActionButton fab;
    private ArrayList<Category> categories;
    private ArrayList<String> selectedCategories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_categories);
        setTitle(getText(R.string.categories_title));
        listView=findViewById(R.id.list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

       fab=findViewById(R.id.add_fab);
       fab.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent=new Intent(getApplication().getApplicationContext(),AddActivity.class);
               startActivity(intent);
           }
       });

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, final int i, long l) {
               final PopupMenu popupMenu=new PopupMenu(ViewCategoriesActivity.this,view);
               popupMenu.getMenuInflater().inflate(R.menu.popup_menu,popupMenu.getMenu());
               popupMenu.show();
               final Category category=categories.get(i);
               popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                   public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId()==R.id.delete){
                            MainActivity.db.deleteCategory(category.getId());
                            onResume();
                        }
                        if(item.getItemId()==R.id.update){
                            Intent intent=new Intent(getApplicationContext(),UpdateCategory.class);
                            intent.putExtra("updateCategoryName",category.getCategory());
                            intent.putExtra("updateCategoryId",category.getId());
                            startActivity(intent);
                        }
                       return true;
                   }
               });
           }
       });
       listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
               selectedCategories=new ArrayList<>();
               selectedCategories.add(categories.get(i).getCategory());
               view.setBackgroundColor(Color.LTGRAY);
               return false;
           }
       });
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    protected void onResume() {
        super.onResume();

        categories=MainActivity.db.getAllCategories();
        CategoryAdapter adapter=new CategoryAdapter(getApplicationContext(),categories);
        listView.setAdapter(adapter);
    }
}
