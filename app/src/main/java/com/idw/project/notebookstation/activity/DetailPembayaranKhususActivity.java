package com.idw.project.notebookstation.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.response.BatalKhususResponse;
import com.idw.project.notebookstation.response.BayarKhususResponse;
import com.idw.project.notebookstation.response.PesananKhususDetailResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPembayaranKhususActivity extends AppCompatActivity {
    private static final long START_TIME_IN_MILLIS = 86400000;

    private TextView mTextViewCountDown, tv_tanggal_tenggat, tv_kode_pesanan, tv_total_tagihan;

    private CountDownTimer mCountDownTimer;

    private long mTimeLeftInMillis = START_TIME_IN_MILLIS;
    private long mEndTime;

    private Button btn_batal;

    String id_konsumen, id_pesanan;

    String kode_pesanan, total_tagihan;

    private boolean mTimerRunning;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pembayaran_khusus);

        setTitle("Pembayaran");

        mTextViewCountDown = findViewById(R.id.text_view_countdown);
        tv_tanggal_tenggat = findViewById(R.id.tv_tanggal_tenggat);
        tv_kode_pesanan = findViewById(R.id.tv_kode_pesanan);
        tv_total_tagihan = findViewById(R.id.tv_total_tagihan);
        btn_batal = findViewById(R.id.btn_batal);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        startTimer();

        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        System.out.println("Current time => " + dt);

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String formattedDate = df.format(dt);
        System.out.println("cek"+formattedDate);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        if (extras != null){
            kode_pesanan = extras.getString("kode_pesanan");
            System.out.println("garong"+kode_pesanan);

            if (kode_pesanan != null) {
                tv_kode_pesanan.setText(kode_pesanan);

                apiInterface.pesananKhususDetail(kode_pesanan).enqueue(new Callback<PesananKhususDetailResponse>() {
                    @Override
                    public void onResponse(Call<PesananKhususDetailResponse> call, Response<PesananKhususDetailResponse> response) {
                        if (response.isSuccessful()){
                            if (response.body() != null && response.body().getMaster().size() > 0) {
                                id_pesanan = response.body().getMaster().get(0).getIdPesananKhusus();
                                total_tagihan = response.body().getMaster().get(0).getTotalTagihan();

                                DecimalFormat df = new DecimalFormat("#,###");

                                tv_total_tagihan.setText("Rp. "+df.format(Double.valueOf(total_tagihan)));

                                apiInterface.bayarKhusus(id_pesanan, "", String.valueOf(0), "Belum Lunas").enqueue(new Callback<BayarKhususResponse>() {
                                    @Override
                                    public void onResponse(Call<BayarKhususResponse> call, Response<BayarKhususResponse> response) {
                                        System.out.println("cek dulu"+response);
                                        if (response.isSuccessful()){
                                            Toast.makeText(getApplicationContext(), "Berhasil bayar", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BayarKhususResponse> call, Throwable t) {
                                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<PesananKhususDetailResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }

        btn_batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiInterface.batalkanPesananKhusus(id_pesanan, "Batal").enqueue(new Callback<BatalKhususResponse>() {
                    @Override
                    public void onResponse(Call<BatalKhususResponse> call, Response<BatalKhususResponse> response) {
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Berhasil batalkan", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else {
                            Toast.makeText(getApplicationContext(), "Terjadi kesalahan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<BatalKhususResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Gagal Terhubung ke Server", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });



        tv_tanggal_tenggat.setText("Sebelum "+formattedDate+" WIB");
    }

    private void startTimer() {

        mEndTime = System.currentTimeMillis() + mTimeLeftInMillis;

        mCountDownTimer = new CountDownTimer(mTimeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mTimeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {

            }
        }.start();

        mTimerRunning = true;
    }

    private void updateCountDownText() {
        int hours = (int) (mTimeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((mTimeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (mTimeLeftInMillis / 1000) % 60;

        String timeLeftFormatted;
        if (hours > 0) {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%d Jam : %02d Menit : %02d Detik", hours, minutes, seconds);
        } else {
            timeLeftFormatted = String.format(Locale.getDefault(),
                    "%02d Menit : %02d Detik", minutes, seconds);
        }

        mTextViewCountDown.setText(timeLeftFormatted);
    }

    @Override
    public void onBackPressed() {
        goToMainActivity();
        super.onBackPressed();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("millislLeft", mTimeLeftInMillis);
        outState.putBoolean("timerRunning", mTimerRunning);
        outState.putLong("endTime", mEndTime);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mTimeLeftInMillis = savedInstanceState.getLong("millislLeft");
        mTimerRunning = savedInstanceState.getBoolean("timerRunning");


        if (mTimerRunning){
            mEndTime = savedInstanceState.getLong("endTime");
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();
            startTimer();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);

//        id_pesanan = preferences.getString("id_pesanan", "missing");
        mTimeLeftInMillis = preferences.getLong("millislLeft", START_TIME_IN_MILLIS);
        mTimerRunning = preferences.getBoolean("timerRunning", false);

        updateCountDownText();

        if (mTimerRunning){
            mEndTime = preferences.getLong("endTime",0);
            mTimeLeftInMillis = mEndTime - System.currentTimeMillis();

            if (mTimeLeftInMillis <0){
                mTimeLeftInMillis = 0;
                mTimerRunning = false;
                updateCountDownText();
            }else {
                startTimer();
            }

        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences preferences = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

//        editor.putString("id_pesanan", id_pesanan);
        editor.putLong("millislLeft", mTimeLeftInMillis);
        editor.putBoolean("timerRunning", mTimerRunning);
        editor.putLong("endTime", mEndTime);

        editor.apply();



    }
}
