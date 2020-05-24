package com.example.rescuenow_dev.diseases;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rescuenow_dev.R;

import java.util.List;

public class DiseaseAdapter extends RecyclerView.Adapter<DiseaseAdapter.DiseaseViewHolder> {

    //We need constructor and context
    private Context mContext;
    private List<Diseases> diseasesList;

    public DiseaseAdapter(Context mContext, List<Diseases> diseasesList) {
        this.mContext = mContext;
        this.diseasesList = diseasesList;
    }

    @NonNull
    @Override
    public DiseaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        //Disease View holder instance
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.symptoms_list_item, null);


        DiseaseViewHolder holder = new DiseaseViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull DiseaseViewHolder holder, int position) {

        //Bind Data to View holder

        Diseases diseases = diseasesList.get(position);

        holder.tv_name.setText(diseases.getD_name());
        holder.tv_symptoms.setText(diseases.getD_symptoms());
        holder.tv_description.setText(diseases.getD_description());

    }

    @Override
    public int getItemCount() {
        //size of list
        return diseasesList.size();
    }

    class DiseaseViewHolder extends  RecyclerView.ViewHolder{
        //Use context to inflate the layout
        //UI elements
        TextView tv_name, tv_description, tv_symptoms;

        public DiseaseViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.text_view_disease_name);
            tv_description = itemView.findViewById(R.id.text_view_disease_description);
            tv_symptoms = itemView.findViewById(R.id.text_view_disease_symptoms);
        }


    }

}
