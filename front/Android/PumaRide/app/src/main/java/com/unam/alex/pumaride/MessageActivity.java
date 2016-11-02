package com.unam.alex.pumaride;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.google.gson.Gson;
import com.unam.alex.pumaride.adapters.MessageListViewAdapter;
import com.unam.alex.pumaride.models.Match;
import com.unam.alex.pumaride.models.Message;
import com.unam.alex.pumaride.services.MessageService;
import com.unam.alex.pumaride.utils.Statics;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

public class MessageActivity extends AppCompatActivity {
    public static boolean active = false;
    BroadcastReceiver receiver;
    @BindView(R.id.activity_message_rv)
    RecyclerView rvMessage;
    @BindView(R.id.activity_message_et_message)
    EditText etMessage;
    @BindView(R.id.activity_message_btn_send)
    ImageButton ibSend;
    MessageListViewAdapter mAdapter;
    ArrayList<Message> messages;
    private int id = 10;
    public static int id2 = 1; //default id for server conection
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket(Statics.CHAT_SERVER_BASE_URL);
        } catch (URISyntaxException e) {}
    }
    Realm realm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        // Initialize Realm
        Realm.init(getBaseContext());
        // Get a Realm instance for this thread
        realm = Realm.getDefaultInstance();
        initActionBar();
        SharedPreferences sp = getSharedPreferences("pumaride", Activity.MODE_PRIVATE);
        id = sp.getInt("userid",1);
        mSocket.connect();

        int maxid = 0;
        try{
            maxid = getMaxMessageByUserId(id2)-4;
        }catch(Exception e){

        }

        RealmResults<Message> messages = realm.where(Message.class).equalTo("user_id2",id2).equalTo("user_id",id).greaterThanOrEqualTo("id",maxid).or().equalTo("user_id2",id).equalTo("user_id",id2).greaterThanOrEqualTo("id",maxid).findAllSorted("id");
        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                mAdapter.notifyDataSetChanged();
            }
        };
        // Tell Realm to notify our listener when the customers results
        // have changed (items added, removed, updated, anything of the sort).
        messages.addChangeListener(changeListener);

        this.messages = new ArrayList<>(messages);
        realm.beginTransaction();
        for(Message m:this.messages){
            m.setReaded(true);
            realm.copyToRealmOrUpdate(m);
        }
        realm.commitTransaction();
        init();
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String s = intent.getStringExtra(MessageService.MESSAGE);
                Message m = new Gson().fromJson(s,Message.class);
                //Toast.makeText(getApplicationContext(),"hola " + s,Toast.LENGTH_SHORT).show();
                addMessage(m);
            }
        };

    }
    TextView tvUserName;
    public void initActionBar(){
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayShowHomeEnabled(false);
        mActionBar.setDisplayShowTitleEnabled(false);
        LayoutInflater mInflater = LayoutInflater.from(this);
        Match match = realm.where(Match.class).equalTo("id",id2).findFirst();
        View mCustomView = mInflater.inflate(R.layout.activity_message_custom_actionbar, null);
        TextView tvUserName = (TextView) mCustomView.findViewById(R.id.activity_message_custom_actionbar_title);
        tvUserName.setText(match.getName());
        ImageView ivProfile = (ImageView) mCustomView.findViewById(R.id.activity_message_custom_actionbar_image);
        Glide.with(this).load(match.getImage()).into(ivProfile);

        ivProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),MatchDetailActivity.class);
                startActivity(i);
            }
        });

        mActionBar.setCustomView(mCustomView);
        mActionBar.setDisplayShowCustomEnabled(true);
    }
    public int getMaxMessageByUserId(int userId){

        return realm.where(Message.class).equalTo("user_id",userId).equalTo("user_id",id).max("id").intValue();
    }
    public int getNextKey()
    {
        return realm.where(Message.class).max("id").intValue() + 1;
    }
    public void init(){
        mAdapter = new MessageListViewAdapter(messages,getApplicationContext());
        rvMessage.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvMessage.setLayoutManager(layoutManager);
        rvMessage.setHasFixedSize(true);
    }
    @OnClick(R.id.activity_message_btn_send)
    public void send(View v){
        String message = etMessage.getText().toString();
        etMessage.setText("");
        Message m = new Message();
        m.setType_(0);
        m.setDatetime(getTimeInMillis());
        m.setUser_id(id);
        m.setUser_id2(id2);

        m.setMessage(message);
        addMessage(m);
        sendServer(m);
    }
    private void addMessage(Message m){
        messages.add(m);
        m.setId(1);
        try{
            m.setId(getNextKey());
        }catch(Exception e){

        }
        m.setReaded(true);
        realm.beginTransaction();
        Message realmMessage = realm.copyToRealm(m);
        realm.commitTransaction();
        rvMessage.scrollToPosition(rvMessage.getAdapter().getItemCount() - 1);
    }
    private void sendServer(Message m) {
        String s = new Gson().toJson(m);
        mSocket.emit("chat", s);
    }
    public long getTimeInMillis(){
        Calendar c = Calendar.getInstance();
        return c.getTimeInMillis();
    }
    @Override
    public void onStart() {
        super.onStart();
        active = true;
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),
                new IntentFilter(MessageService.MESSAGE_RESULT)
        );
    }

    @Override
    public void onStop() {
        active = false;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
        super.onStop();

    }
    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        // This activity implements OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.new_game:
                        Toast.makeText(getApplicationContext(),"new_game",Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.help:

                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.inflate(R.menu. messages);
        popup.show();
    }
}
