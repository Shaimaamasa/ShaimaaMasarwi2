package com.example.shaimaamasarwi;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import java.io.File;
import java.util.ArrayList;

public class CustomGalleryActivity extends AppCompatActivity {
    ArrayList<String> f = new ArrayList<>();
    File[] listFile;
    private String folderName = "myPhotoDir";
    //creating object of viewPaper
    ViewPager mViewPager;
    //creating object of viewPagerAdapter
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        getFromSdcard();
        //initializing the viewpager object
        mViewPager = findViewById(R.id.viewPaperMain);
        //initializing the Viewpageradapter
        mViewPagerAdapter = new ViewPagerAdapter(this, f);
        //adding the adapter to the viewPager
        mViewPager.setAdapter(mViewPagerAdapter);
    }



    public void setContentView(int activity_gallery) {
    }

    public void getFromSdcard(){
        File file = new File(getExternalFilesDir(folderName), "/");
        if(file.isDirectory()){
            listFile = file.listFiles();
            for (int i=0; i < listFile.length; i++){
                f.add(listFile[i].getAbsolutePath());
            }
        }
    }

    public File getExternalFilesDir(String folderName) {
        return null;
    }
}
