package com.example.harsh.custom_ui;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Books extends Fragment {


    //a list to store all the products
    List<Product> productList;
    private Button add;
    //the recyclerview
    RecyclerView recyclerView;

    public Books() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_books, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //getting the recyclerview from xml
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        add = (Button) view.findViewById(R.id.add);
        //initializing the productlist
       /*
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),BookDetect.class));
            }
        });
        */
        productList = new ArrayList<>();


        //adding some items to our list
        productList.add(
                new Product(
                        1,
                        "Introduction to Algorithms By Cormen",
                        "1 year old ",
                        "300 ₹",
                        R.drawable.ada));

        productList.add(
                new Product(
                        1,
                        "System Programming By Dhamdhare",
                        "5 months old",
                        "200 ₹",
                        R.drawable.sp));

        productList.add(
                new Product(
                        1,
                        "The Complete Reference -JAVA",
                        "6 months old ",
                        "400 ₹" ,
                        R.drawable.java));
        productList.add(
                new Product(
                        1,
                        "Computer Graphics - Pearson",
                        "8 months old ",
                        "350 ₹" ,
                        R.drawable.cg));
        //creating recyclerview adapter
        ProductAdapter adapter = new ProductAdapter(getActivity(), productList);

        //setting adapter to recyclerview
        recyclerView.setAdapter(adapter);
    }
}
