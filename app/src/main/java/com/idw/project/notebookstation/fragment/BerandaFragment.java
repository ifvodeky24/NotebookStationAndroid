package com.idw.project.notebookstation.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.freshchat.consumer.sdk.Freshchat;
import com.freshchat.consumer.sdk.FreshchatCallbackStatus;
import com.freshchat.consumer.sdk.UnreadCountCallback;
import com.idw.project.notebookstation.R;
import com.idw.project.notebookstation.activity.TransaksiKhususActivity;
import com.idw.project.notebookstation.adapter.ProdukAdapter;
import com.idw.project.notebookstation.model.Produk;
import com.idw.project.notebookstation.response.ProdukGetAllResponse;
import com.idw.project.notebookstation.rest.ApiClient;
import com.idw.project.notebookstation.rest.ApiInterface;
import com.idw.project.notebookstation.util.SessionManager;
import com.smarteist.autoimageslider.SliderLayout;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BerandaFragment extends Fragment {
    private RecyclerView recylerView;
    private ProdukAdapter produkAdapter;
    private ArrayList<Produk> produkArrayList = new ArrayList<>();
    private SessionManager sessionManager;
    private ApiInterface apiInterface;
    private TextView tv_notification_badge;

    private SliderLayout sliderLayout;

    public BerandaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_beranda, container, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Objects.requireNonNull(getActivity()).setTitle("Senapelan Computer");
        }

        sliderLayout = view.findViewById(R.id.imageSlider);
        sliderLayout.setIndicatorAnimation(SliderLayout.Animations.FILL);
        sliderLayout.setScrollTimeInSec(6);

        recylerView = view.findViewById(R.id.recylerView);

        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(getActivity());

        setHasOptionsMenu(true);

        setSliderViews();


        if (sessionManager.isLoggedIn()){
            setupBadge();
        }


        return view;
    }

    private void launchFreshchat() {
        Freshchat.showConversations(getActivity());
    }

    private void setSliderViews() {
        for (int i = 0; i <= 3; i++) {

            SliderView sliderView = new SliderView(getActivity());

            switch (i) {
                case 0:
                    sliderView.setImageDrawable(R.drawable.acerslider);
                    break;

                case 1:
                    sliderView.setImageDrawable(R.drawable.slider2);
                    break;

                case 2:
                    sliderView.setImageDrawable(R.drawable.slider3);
                    break;

                case 3:
                    sliderView.setImageDrawable(R.drawable.slider3);
                    break;

            }

            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);

            sliderView.setOnSliderClickListener(new SliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(SliderView sliderView) {

                }
            });

            sliderLayout.addSliderView(sliderView);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.options_menu, menu);
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);

        if (sessionManager.isLoggedIn()){

            final MenuItem menuItem = menu.findItem(R.id.chat);
            menuItem.setVisible(true);

            View actionView = menuItem.getActionView();

            tv_notification_badge = actionView.findViewById(R.id.tv_notification_badge_chat);

            actionView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onOptionsItemSelected(menuItem);
                }
            });

        }else {
            final MenuItem menuItem2 = menu.findItem(R.id.cek_status);
            menuItem2.setVisible(true);
        }
    }

    private void setupBadge() {
        Freshchat.getInstance(getActivity()).getUnreadCountAsync(new UnreadCountCallback() {
            @Override
            public void onResult(FreshchatCallbackStatus freshchatCallbackStatus, int unreadCount) {
                //Assuming "badgeTextView" is a text view to show the count on


                if (unreadCount == 0) {
                    if (tv_notification_badge.getVisibility() != View.GONE) {
                        tv_notification_badge.setVisibility(View.GONE);
                    }
                } else {
                    tv_notification_badge.setText(Integer.toString(unreadCount));
                    if (tv_notification_badge.getVisibility() != View.VISIBLE) {
                        tv_notification_badge.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        BroadcastReceiver unreadCountChangeReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Freshchat.getInstance(Objects.requireNonNull(getActivity())).getUnreadCountAsync(new UnreadCountCallback() {
                    @Override
                    public void onResult(FreshchatCallbackStatus freshchatCallbackStatus, int unreadCount) {
                        //Assuming "badgeTextView" is a text view to show the count on
                        if (unreadCount == 0) {
                            if (tv_notification_badge.getVisibility() != View.GONE) {
                                tv_notification_badge.setVisibility(View.GONE);
                            }
                        } else {
                            tv_notification_badge.setText(Integer.toString(unreadCount));
                            if (tv_notification_badge.getVisibility() != View.VISIBLE) {
                                tv_notification_badge.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });
            }
        };

        IntentFilter intentFilter = new IntentFilter(Freshchat.FRESHCHAT_UNREAD_MESSAGE_COUNT_CHANGED);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(unreadCountChangeReceiver, intentFilter);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.chat:
//                Toast.makeText(getActivity(), "Klik Chat", Toast.LENGTH_SHORT).show();
                launchFreshchat();
                return true;
            case R.id.cek_status:
//                Toast.makeText(getActivity(), "Cek status", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), TransaksiKhususActivity.class);
                startActivity(intent);
                return true;
            case R.id.search:
                if (getFragmentManager() != null) {
                    new FullBottomSheetDialogFragment().show(getFragmentManager(), "search_dialog");
                }
                return true;
            default:
                return true;
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getProduk();
    }

    private void getProduk() {
        apiInterface.produkGetAll().enqueue(new Callback<ProdukGetAllResponse>() {
            @Override
            public void onResponse(Call<ProdukGetAllResponse> call, Response<ProdukGetAllResponse> response) {
                System.out.println("responya"+response);
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        if(response.body().getMaster().size()>0){
                            produkArrayList.addAll(response.body().getMaster());
                            System.out.println(response.body().getMaster().get(0).getNamaProduk());

                            LinearLayoutManager manager = new GridLayoutManager(getActivity(), 2);
                            recylerView.setLayoutManager(manager);
                            recylerView.setHasFixedSize(true);
                            produkAdapter = new ProdukAdapter(getActivity(), produkArrayList);
                            recylerView.setAdapter(produkAdapter);

                        }else {
                            Toast.makeText(getActivity(), "Data Produk Kosong", Toast.LENGTH_SHORT).show();
                        }
                    }

                }else{
                    Toast.makeText(getActivity(), "Terjadi Kesalahan", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ProdukGetAllResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(getActivity(), "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
