package com.unam.alex.pumaride;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.unam.alex.pumaride.adapters.MessageListViewAdapter;
import com.unam.alex.pumaride.models.Message;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends AppCompatActivity {
    @BindView(R.id.activity_message_rv)
    RecyclerView rvPackage;
    @BindView(R.id.activity_message_et_message)
    EditText etMessage;
    @BindView(R.id.activity_message_btn_send)
    ImageButton ibSend;
    MessageListViewAdapter mAdapter;
    ArrayList<Message> messages;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        init();
    }
    public void init(){
        Message m = new Message();
        m.setMessage("hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola hola ");
        m.setType(1);
        Message m2 = new Message();
        m2.setMessage("Hello Hello Hello Hello Hello Hello");
        m2.setType(0);
        Message m3 = new Message();
        m3.setMessage("Hi");
        m3.setType(1);
        Message m4 = new Message();
        m4.setMessage("Que onda");
        m4.setType(0);
        messages = new ArrayList<Message>();
        messages.add(m);
        messages.add(m2);
        messages.add(m3);
        messages.add(m4);
        mAdapter = new MessageListViewAdapter(messages,getApplicationContext());
        rvPackage.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvPackage.setLayoutManager(layoutManager);
        rvPackage.setHasFixedSize(true);
    }
    @OnClick(R.id.activity_message_btn_send)
    public void send(View v){
        String message = etMessage.getText().toString();
        etMessage.setText("");
        Message m = new Message();
        m.setType(0);
        m.setMessage(message);
        messages.add(m);
        mAdapter.notifyDataSetChanged();
    }
}
