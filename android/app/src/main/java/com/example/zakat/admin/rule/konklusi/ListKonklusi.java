package com.example.zakat.admin.rule.konklusi;

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
import com.example.zakat.model.KonklusiResponse;
import com.example.zakat.model.KonklusiResponse;
import com.example.zakat.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListKonklusi extends RecyclerView.Adapter<ListKonklusi.HolderData> {
    private List<KonklusiResponse> listkonklusidata ;
    private Activity activity;

    public ListKonklusi(List<KonklusiResponse> listkonklusidata, Activity activity) {
        this.listkonklusidata = listkonklusidata;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_konklusi, parent, false);
        HolderData holder = new HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        KonklusiResponse dm = listkonklusidata.get(position);

        holder.tvid.setText(dm.getKodekonklusi());
        holder.tvnama.setText(dm.getNama());
        holder.btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog dialog = new AlertDialog.Builder(activity)
                        .setTitle("Apakah anda ingin menghapus Akun ini?")
                        .setIcon(R.drawable.ic_nurulihsan)
                        .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                holder.hapus();
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
                UpdateKonklusi updateKonklusi = new UpdateKonklusi(dm.getKodekonklusi(),
                        dm.getNama());
                updateKonklusi.show(manager, "form");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listkonklusidata.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvid, tvnama;
        ImageView btndelete;


        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvnama = itemView.findViewById(R.id.tvnama);
            btndelete = itemView.findViewById(R.id.delete);

        }
        private void hapus() {
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> hapusData = ardData.ardDeletekzakat(tvid.getText().toString());
            hapusData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    String kode = String.valueOf(response.body().getKode());
                    String pesan = response.body().getPesan();
                    if (!kode.equals("0")){
                        ((ListKonklusiActivity)activity).retrievekonklusiData();
                        Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(activity, pesan, Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseData> call, Throwable t) {
                    Toast.makeText(activity, "Gagal Menghubungkan ke Server", Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
