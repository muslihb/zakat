package com.example.zakat.admin.reviewamil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zakat.API.APIAdapter;
import com.example.zakat.API.RetroServer;
import com.example.zakat.R;
import com.example.zakat.admin.cfreview.evidence.EvidenceActivity;
import com.example.zakat.admin.cfreview.evidence.UpdateEvidence;
import com.example.zakat.model.PenilaianResponse;
import com.example.zakat.model.PenilaianResponse;
import com.example.zakat.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Listreview extends RecyclerView.Adapter<Listreview.HolderData> {
    private List<PenilaianResponse> penilaianResponses;
    private Activity activity;

    public Listreview(List<PenilaianResponse> penilaianResponses, Activity activity) {
        this.penilaianResponses = penilaianResponses;
        this.activity = activity;
    }

    @NonNull
    @Override
    public Listreview.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_evidence, parent, false);
        Listreview.HolderData holder = new Listreview.HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Listreview.HolderData holder, int position) {
        PenilaianResponse dm = penilaianResponses.get(position);

        holder.tvid.setText(dm.getTbpenilaianid_nilai_amil());
        holder.tvnama.setText(dm.getTbpenilaianid_request());
        holder.tvquest.setText(dm.getTbmasternama());
        holder.tvnm .setText(dm.getTbpenilaiannilai_cf());
        holder.tvid.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return penilaianResponses.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvid, tvnama, tvquest, tvnm;
        ImageView btndelete;


        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvquest = itemView.findViewById(R.id.tvquest);
            btndelete = itemView.findViewById(R.id.delete);
            tvnm = itemView.findViewById(R.id.tvnamaevidence);
            btndelete.setVisibility(View.GONE);

        }
    }
}
