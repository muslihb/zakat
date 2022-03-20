package com.example.zakat.admin.rule.premis;

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
import com.example.zakat.admin.dataakun.ListAkunAdminActivity;
import com.example.zakat.admin.dataakun.UpdateAkunAdmin;
import com.example.zakat.model.PremisResponse;
import com.example.zakat.model.PremisResponse;
import com.example.zakat.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPremis extends RecyclerView.Adapter<ListPremis.HolderData> {
    private List<PremisResponse> listpremis;
    private Activity activity;

    public ListPremis(List<PremisResponse> listpremis, Activity activity) {
        this.listpremis = listpremis;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ListPremis.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_premis, parent, false);
        ListPremis.HolderData holder = new ListPremis.HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListPremis.HolderData holder, int position) {
        PremisResponse dm = listpremis.get(position);

        holder.tvid.setText(dm.getKodepremis());
        holder.tvnama.setText(dm.getNama());
        holder.tvquest.setText(dm.getQuest());
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
                UpdatePremis updatePremis = new UpdatePremis(dm.getKodepremis(),
                        dm.getNama(), dm.getQuest(), dm.getIfyes(),
                        dm.getIfno());
                updatePremis.show(manager, "form");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listpremis.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvid, tvnama, tvquest;
        ImageView btndelete;


        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvquest = itemView.findViewById(R.id.tvquest);
            btndelete = itemView.findViewById(R.id.delete);

        }
        public void hapuspremis() {
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> hapusData = ardData.ardDeletepzakat(tvid.getText().toString());
            hapusData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    ((ListPremisActivity)activity).retrievePremisData();
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
