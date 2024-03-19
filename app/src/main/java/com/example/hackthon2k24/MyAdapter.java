package com.example.hackthon2k24;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Context context;
    ArrayList<route> list;
    SparseBooleanArray expandState = new SparseBooleanArray();

    public MyAdapter(Context context, ArrayList<route> list) {
        this.context = context;
        this.list = list;
        for (int i = 0; i < list.size(); i++) {
            expandState.append(i, false);
        }
    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder holder, final int position) {
        final route route = list.get(position);
        holder.a.setText(route.getA());
        holder.b.setText(route.getB());

        // Check if the card is expanded
        if (expandState.get(position)) {
            holder.c.setVisibility(View.VISIBLE);

            // Replace "\n" with actual newline character
            String stepsText = route.getC().replace("\\n", "\n");

            // Set the formatted text to the TextView
            holder.c.setText(stepsText);
        } else {
            holder.c.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandState.put(position, !expandState.get(position));
                notifyItemChanged(position);
            }
        });
    }




    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView a,b,c;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            a = itemView.findViewById(R.id.a);
            b = itemView.findViewById(R.id.b);
            c = itemView.findViewById(R.id.c);
        }
    }
}