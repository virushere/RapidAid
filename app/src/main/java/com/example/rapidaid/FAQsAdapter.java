package com.example.rapidaid;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FAQsAdapter extends RecyclerView.Adapter<FAQsAdapter.FAQVH> {

    List<FAQsModel> faQsModelList;

    public FAQsAdapter(List<FAQsModel> faQsModelList) {
        this.faQsModelList = faQsModelList;
    }

    @NonNull
    @Override
    public FAQVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
        return new FAQVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FAQVH holder, int position) {
        FAQsModel faQsModel = faQsModelList.get(position);
        holder.questionTxt.setText(faQsModel.getQuestion());
        holder.answerTxt.setText(faQsModel.getAnswer());

        boolean getExpandable = faQsModelList.get(position).getExpandable();
        holder.expandableLayout.setVisibility(getExpandable ? View.VISIBLE : View.GONE);
    }

    @Override
    public int getItemCount() {
        return faQsModelList.size();
    }

    public class FAQVH extends RecyclerView.ViewHolder {

        TextView questionTxt, answerTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public FAQVH(@NonNull View itemView) {
            super(itemView);

            questionTxt = itemView.findViewById(R.id.question);
            answerTxt = itemView.findViewById(R.id.answer);

            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FAQsModel faQsModel = faQsModelList.get(getAdapterPosition());
                    faQsModel.setExpandable(!faQsModel.getExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}
