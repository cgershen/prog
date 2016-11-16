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
import io.realm.Sort;

public class MessageActivity extends AppCompatActivity implements MessageListViewAdapter.LoadEarlierMessages {
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
    private boolean loadEarlierMsgs = true;
    int max_id_message = 0;
    int min_id_message = 0;

    private int id = 10;
    private int MESSAGES_IN_LIST = 5;
    int max_id_ac = 0;
    int min_id_ac = MESSAGES_IN_LIST;
    public static int id2 = 1; //default id for server conection
    Match match;
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


        try{
            max_id_message = getMessageId(id2,max_id_ac);
            max_id_ac +=MESSAGES_IN_LIST;
        }catch(Exception e){
        }
        try{
            min_id_message = getMessageId(id2,min_id_ac);
            min_id_ac +=MESSAGES_IN_LIST;
        }catch(Exception e){
            try{
                min_id_message = getMessageId(id2,-1);
                loadEarlierMsgs = false;
            }catch(Exception e2){
                loadEarlierMsgs = false;
            }
        }

        RealmResults<Message> messages = realm.where(Message.class).equalTo("user_id2",id2).
                equalTo("user_id",id).lessThanOrEqualTo("id",max_id_message).greaterThanOrEqualTo("id",min_id_message).or().
                equalTo("user_id2",id).equalTo("user_id",id2).lessThanOrEqualTo("id",max_id_message).greaterThanOrEqualTo("id",min_id_message).
                findAllSorted("id");

        RealmChangeListener changeListener = new RealmChangeListener() {
            @Override
            public void onChange(Object element) {
                mAdapter.notifyDataSetChanged();
            }
        };
        // Tell Realm to notify our listener when the customers results
        // have changed (items added, removed, updated, anything of the sort).
        messages.addChangeListener(changeListener);

        this.messages = new ArrayList<Message>(messages);
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
                Toast.makeText(getApplicationContext(),"hola " + s,Toast.LENGTH_SHORT).show();
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
        match = realm.where(Match.class).equalTo("id",id2).findFirst();
        View mCustomView = mInflater.inflate(R.layout.activity_message_custom_actionbar, null);
        TextView tvUserName = (TextView) mCustomView.findViewById(R.id.activity_message_custom_actionbar_title);
        tvUserName.setText(match.getFirst_name());
        ImageView ivProfile = (ImageView) mCustomView.findViewById(R.id.activity_message_custom_actionbar_image);
        if(match.getImage()!=null) {
            Glide.with(this).load(match.getImage()).into(ivProfile);
        }
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
    public int getMessageId(int userId,int position){

        RealmResults<Message> messages=  realm.where(Message.class).equalTo("user_id",userId).or().equalTo("user_id2",userId).findAllSorted("id",Sort.DESCENDING);
        if(position == -1){
            position = messages.size()-1;
        }
        return messages.get(position).getId();
    }
    public int getNextKey()
    {
        return realm.where(Message.class).max("id").intValue() + 1;
    }
    public void init(){
        mAdapter = new MessageListViewAdapter(messages,this);
        rvMessage.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        rvMessage.setLayoutManager(layoutManager);
        rvMessage.setHasFixedSize(true);
        if(loadEarlierMsgs) {
            mAdapter.setLoadEarlierMsgs(true);
        }

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
        realm.copyToRealmOrUpdate(match);
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
        LocalBroadcastManager.getInstance(this).registerReceiver((receiver),new IntentFilter(MessageService.MESSAGE_RESULT));
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
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onLoadEarlierMessages() {
        Toast.makeText(getApplicationContext(),"cargar mensajes",Toast.LENGTH_SHORT).show();
        loadMore();
    }
    public void loadMore(){
        int s = 10;
        try{
            //obtenemos el id más grande
            max_id_message = getMessageId(id2,max_id_ac);
            max_id_ac +=MESSAGES_IN_LIST;
            //obtenemos el id en la posición 5
        }catch(Exception e){

        }
        try{
            min_id_message = getMessageId(id2,min_id_ac);
            min_id_ac +=MESSAGES_IN_LIST;

        }catch(Exception e){
            min_id_message = getMessageId(id2,-1);
            mAdapter.setLoadEarlierMsgs(false);
        }

        RealmResults<Message> messages = realm.where(Message.class).equalTo("user_id2",id2).
                equalTo("user_id",id).lessThanOrEqualTo("id",max_id_message).greaterThanOrEqualTo("id",min_id_message).or().
                equalTo("user_id2",id).equalTo("user_id",id2).lessThanOrEqualTo("id",max_id_message).greaterThanOrEqualTo("id",min_id_message).
                findAllSorted("id");
        for(int i = messages.size()-2; i >= 0; i--){
            this.messages.add(0,messages.get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        setResult(Activity.RESULT_OK);
        super.onDestroy();
    }
}
