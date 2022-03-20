package com.example.zakat.admin.dataakun;

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
import com.example.zakat.model.LoginResponse;
import com.example.zakat.model.ResponseData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListAkun extends RecyclerView.Adapter<ListAkun.HolderData> {
    private List<LoginResponse> listDataAkun;
    private Activity activity;

    public ListAkun(List<LoginResponse> listDataAkun, Activity activity) {
        this.listDataAkun = listDataAkun;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ListAkun.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item_akun, parent, false);
        ListAkun.HolderData holder = new ListAkun.HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListAkun.HolderData holder, int position) {
        LoginResponse dm = listDataAkun.get(position);

        holder.tvid.setText(dm.getIduser());
        holder.tvusername.setText(dm.getUsername());
        holder.tvnama.setText(dm.getNama());
        holder.tvstatus.setText(dm.getStatus());
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
                UpdateAkunAdmin updateAdmin = new UpdateAkunAdmin(dm.getIduser(),
                        dm.getNama(), dm.getNomortelepon(), dm.getAlamat(),
                        dm.getUsername(), dm.getPassword(), dm.getStatus());
                updateAdmin.show(manager, "form");
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDataAkun.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvid, tvnama, tvusername, tvstatus;
        ImageView btndelete;


        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvusername = itemView.findViewById(R.id.tvusername);
            tvnama = itemView.findViewById(R.id.tvnama);
            tvstatus = itemView.findViewById(R.id.tvstatus);
            btndelete = itemView.findViewById(R.id.delete);

        }
        private void hapus() {
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> hapusData = ardData.ardDeleteLogin(tvid.getText().toString());
            hapusData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    ((ListAkunAdminActivity)activity).retrieveData();
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
