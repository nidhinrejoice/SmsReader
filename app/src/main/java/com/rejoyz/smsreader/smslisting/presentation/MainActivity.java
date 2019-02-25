package com.rejoyz.smsreader.smslisting.presentation;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.rejoyz.smsreader.R;
import com.rejoyz.smsreader.smslisting.data.Message;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity implements MessagesAdapter.MessageInterface {

    private static final int MY_PERMISSIONS_REQUEST_READ_SMS = 1001;
    private MessageListingViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    private MessagesAdapter adapter;
    boolean newMessage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageListingViewModel.class);
        viewModel.observeOnMessages().observe(this, this::showMessages);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            showPermissionRequiredMsg();
            return;
        }
        if (getIntent().getExtras() != null)
            newMessage =true;

        viewModel.loadMessages();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        newMessage = true;
        viewModel.loadMessages();
    }

    void showPermissionRequiredMsg() {
        new AlertDialog.Builder(this)
                .setMessage("Read SMS permission is required for the app to function")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.RECEIVE_SMS, Manifest.permission.READ_SMS},
                            MY_PERMISSIONS_REQUEST_READ_SMS);
                })
                .setNegativeButton("EXIT APP", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    finish();
                })
                .create().show();
    }

    void showMessages(List<Message> list) {
        if (adapter == null) {
            adapter = new MessagesAdapter(this);
            recyclerView.setHasFixedSize(true);
            LinearLayoutManager mlayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(mlayoutManager);
            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                    mlayoutManager.getOrientation());
            recyclerView.addItemDecoration(dividerItemDecoration);
            adapter.setMessages(list, newMessage);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setMessages(list, newMessage);

        }
        newMessage = false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_SMS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    viewModel.loadMessages();
                } else {
                    Toast.makeText(this, "Permission denied, Exiting app", Toast.LENGTH_SHORT).show();
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    finish();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }

    @Override
    public void onMessageClicked(Message message) {
        new AlertDialog.Builder(this).setMessage(message.getBody())
                .setPositiveButton("OK", (dialogInterface, i) -> dialogInterface.dismiss()).create().show();
    }
}
