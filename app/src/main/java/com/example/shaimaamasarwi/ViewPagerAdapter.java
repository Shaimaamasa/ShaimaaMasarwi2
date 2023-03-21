package com.example.shaimaamasarwi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class ViewPagerAdapter extends PagerAdapter {
    //context object
    Context context;
    //array of images
    ArrayList<String> imagePaths =  new ArrayList<>();
    //layout inflater
    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(CustomGalleryActivity context, ArrayList<String> imagePaths){
        this.context = context;
        this.imagePaths = imagePaths;
        mLayoutInflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    @Override
    public int getCount(){
        //return the number of images
        return imagePaths.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object){
        return view == object;
    }
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        //inflating the item.xml
        View itemView = mLayoutInflater.inflate(R.layout.galleryitem, container, false);
        //referencing the image view from the item.xml file
        ImageView imageView = ((View) itemView).findViewById(R.id.imageViewMain);
        //setting the image in the imageView
        Bitmap myBitmap = BitmapFactory.decodeFile(imagePaths.get(position));
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(myBitmap, 0 , 0, myBitmap.getWidth(), myBitmap.getHeight(), matrix,true);
        imageView.setImageBitmap(rotatedBitmap);

        //adding the view
        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((LinearLayout) object);
    }
}
