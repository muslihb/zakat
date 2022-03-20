package com.example.zakat.admin.cfreview.evidence;

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
import com.example.zakat.admin.rule.premis.UpdatePremis;
import com.example.zakat.model.EvidenceResponse;
import com.example.zakat.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListEvidence extends RecyclerView.Adapter<ListEvidence.HolderData> {
    private List<EvidenceResponse> listevidence;
    private Activity activity;

    public ListEvidence(List<EvidenceResponse> listevidence, Activity activity) {
        this.listevidence = listevidence;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ListEvidence.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_evidence, parent, false);
        ListEvidence.HolderData holder = new ListEvidence.HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListEvidence.HolderData holder, int position) {
        EvidenceResponse dm = listevidence.get(position);

        holder.tvid.setText(dm.getKodeevidence());
        holder.tvnama.setText(dm.getMb());
        holder.tvquest.setText(dm.getMd());
        holder.tvnm.setText(dm.getNama());
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle("Apakah anda ingin menghapus Akun ini?")
                        .setIcon(R.drawable.ic_nurulihsan)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                holder.hapuspremis();
                            }
                        })
                        .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .create();
                dialog.show();
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
                UpdateEvidence updateEvidence = new UpdateEvidence(dm.getKodeevidence(),
                        dm.getMb(), dm.getMd(), dm.getNama(), dm.getKodehipotesa());
                updateEvidence.show(manager, "form");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listevidence.size();
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

        }
        public void hapuspremis() {
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> hapusData = ardData.hapusevidencepenilaian(tvid.getText().toString());
            hapusData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    ((EvidenceActivity)activity).retrievePremisData();
                    Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(activity, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
