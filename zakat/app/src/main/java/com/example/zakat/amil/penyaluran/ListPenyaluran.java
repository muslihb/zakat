package com.example.zakat.amil.penyaluran;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.example.zakat.model.MasterMutashikResponse;
import com.example.zakat.model.MasterMutashikResponse;
import com.example.zakat.model.MasterMutashikResponse;
import com.example.zakat.model.ResponseData;
import com.example.zakat.muzakki.History.InfoZakatActivity;
import com.example.zakat.muzakki.HistoryMuzakkiActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListPenyaluran extends RecyclerView.Adapter<ListPenyaluran.HolderData> {
    private List<MasterMutashikResponse> listmutashik ;
    private Activity activity;

    public ListPenyaluran(List<MasterMutashikResponse> listmutashik, Activity activity) {
        this.listmutashik = listmutashik;
        this.activity = activity;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_muzakki_riwayat, parent, false);
        HolderData holder = new HolderData(layout);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        MasterMutashikResponse dm = listmutashik.get(position);

        holder.tvidmutashik.setText(dm.getTbmutashikidmutashik());
        holder.tvmutashik.setText(dm.getTbmutashiknamamutashik());
        holder.tvgolongan.setText(dm.getTbmutashikgolonganmutashik());
        holder.tvidamil.setText(dm.getTbmasternama());
        holder.tvjz.setText(dm.getTbmutashikjenis_zakat());
        holder.tglterima.setText(dm.getTbmutashiktglmenerima());
        if (holder.tvjz.getText().toString().equals("Sapi") || holder.tvjz.getText().toString().equals("Kambing")){
            holder.tvtlz.setText(dm.getTbmutashikjumlah()+ " Ekor");
        }else if (holder.tvjz.getText().toString().equals("Emas") || holder.tvjz.getText().toString().equals("Perak")){
            holder.tvtlz.setText(dm.getTbmutashikjumlah()+ " gram");
        }else {
            holder.tvtlz.setText(dm.getTbmutashikjumlah()+ " KG");
        }
//        if (!holder.tvjz.getText().toString().equals("Sapi") ||
//                !holder.tvjz.getText().toString().equals("Kambing") ||
//        !holder.tvjz.getText().toString().equals("Emas") || !holder.tvjz.getText().toString().equals("Perak")){
//            holder.tvtlz.setText(dm.getTbmutashikjumlah()+ " KG");
//        }
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                FragmentManager manager = ((AppCompatActivity)activity).getSupportFragmentManager();
//                UpdatePenyaluran updatePenyaluran = new UpdatePenyaluran(dm.getTbmutashikidmutashik(),
//                        dm.getTbmutashiknamamutashik(), dm.getTbmutashikgolonganmutashik(), dm.getTbmutashikjumlah(),
//                        dm.getTbmutashikjenis_zakat());
//                updatePenyaluran.show(manager, "form");
//            }
//        });
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
    }

    @Override
    public int getItemCount() {
        return listmutashik.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvidmutashik, tvmutashik, tvgolongan, tvidamil, tvjz, tvtlz, tglterima;
        Button btnkonfirmasi;
        ImageView btndelete;
        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvidmutashik = itemView.findViewById(R.id.tvid);
            tvmutashik = itemView.findViewById(R.id.tvtipezakat);
            tvgolongan = itemView.findViewById(R.id.tvtgltransaksi);
            tvjz = itemView.findViewById(R.id.tvJZ);
            tvtlz = itemView.findViewById(R.id.tvtlz);
            tvidamil = itemView.findViewById(R.id.tvstatus);
            btnkonfirmasi = itemView.findViewById(R.id.btnkonfirmasi);
            btndelete = itemView.findViewById(R.id.delete);
            tglterima = itemView.findViewById(R.id.tglterima);
            tvidamil.setBackground(null);
            tvidmutashik.setVisibility(View.GONE);
            tglterima.setVisibility(View.VISIBLE);
            btnkonfirmasi.setVisibility(View.GONE);
            btndelete.setVisibility(View.VISIBLE);

        }

        public void hapus() {
            APIAdapter ardData = RetroServer.konekRetrofit().create(APIAdapter.class);
            Call<ResponseData> hapusData = ardData.hapusmutashik(tvidmutashik.getText().toString());
            hapusData.enqueue(new Callback<ResponseData>() {
                @Override
                public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    ((ZakatPenyaluranActivity)activity).retrieveDataPenyaluran();
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
