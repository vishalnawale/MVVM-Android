package com.demo.demo_application.service.modal.view;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.demo.demo_application.R;
import com.demo.demo_application.service.modal.Leaders;

import java.util.List;

public class LeaderAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<Leaders> leadersList;

    public LeaderAdapter(List<Leaders> leadersList) {
        this.leadersList = leadersList;
    }


    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.leader_view, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);

    }

    @Override
    public int getItemCount() {
        if (leadersList != null && leadersList.size() > 0) {
            return leadersList.size();
        } else {
            return 0;
        }
    }


    public class ViewHolder extends BaseViewHolder{

        ImageView leaderImage;
        TextView name,designation,description;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leaderImage=itemView.findViewById(R.id.image_leader);
            name=itemView.findViewById(R.id.txt_name);
            description=itemView.findViewById(R.id.txt_desc);
            designation=itemView.findViewById(R.id.txt_designation);

        }

        @Override
        protected void clear() {
            leaderImage.setImageDrawable(null);
            name.setText("");
            designation.setText("");
            description.setText("");

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);
            Leaders leaders=leadersList.get(position);
            if(leaders.getImage()!=null){
                Glide.with(itemView.getContext()).load(leaders.getImage()).into(leaderImage);

            }
            if(leaders.getName()!=null){
                name.setText(leaders.getName());
            }
            if(leaders.getDescription()!=null){
                name.setText(leaders.getDescription());
            }
            if(leaders.getDesignation()!=null){
                name.setText(leaders.getDesignation());
            }
        }
    }
}
