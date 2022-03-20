package com.example.zakat.amil.terkumpul;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.SessionManager;
import com.example.zakat.amil.request.InfoPengambilanActivity;
import com.example.zakat.amil.request.PendingamilFragment;
import com.example.zakat.model.RequestResponse;
import com.example.zakat.model.ResponseData;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListTerkumpul extends RecyclerView.Adapter<ListTerkumpul.HolderData> {
    private List<RequestResponse> listrequestzakat ;
    private Activity activity;

    public ListTerkumpul(List<RequestResponse> listrequestzakat, Activity activity) {
        this.listrequestzakat = listrequestzakat;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_info_terkumpul_zakat, parent, false);
        HolderData holder = new HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        RequestResponse dm = listrequestzakat.get(position);
        holder.tvjz.setText(dm.getJeniszakat());
        if (holder.tvjz.getText().toString().equals("Sapi") || holder.tvjz.getText().toString().equals("Kambing")){
            holder.tvtlz.setText(dm.getJmlZakat()+ " Ekor");
        }else if (holder.tvjz.getText().toString().equals("Emas") || holder.tvjz.getText().toString().equals("Perak")){
            holder.tvtlz.setText(dm.getJmlZakat()+ " gram");
        }else {
            holder.tvtlz.setText(dm.getJmlZakat()+ " KG");
        }
    }

    @Override
    public int getItemCount() {
        return listrequestzakat.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvjz, tvtlz;
        public HolderData(@NonNull View itemView) {
            super(itemView);
            tvjz = itemView.findViewById(R.id.tvJZ);
            tvtlz = itemView.findViewById(R.id.tvtlz);

        }
    }
}
