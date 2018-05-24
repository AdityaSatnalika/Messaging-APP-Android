package sendmessage.aditya.com.message;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class sendMessage extends AppCompatActivity
{
    private EditText message;
    private Button send;
    private EditText email;
    private RecyclerView recyclerView;
    int count;

    String formattedDate;
    String loginemail;
    Calendar c;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_screen);

        message=findViewById(R.id.edittext_chatbox);
        send=findViewById(R.id.button_chatbox_send);
        loginemail = getIntent().getExtras().getString("loginEmail");

        c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formattedDate = df.format(c.getTime());


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("SendMessage");
              // String userId = mDatabase.push().getKey();
                send_message_details m1 = new send_message_details(message.getText().toString(),formattedDate);
                message.setText("");
                mDatabase.child(loginemail.replace(".",",")).child(mDatabase.push().getKey()).setValue(m1);


            }
        });

        recyclerView=(RecyclerView)findViewById(R.id.reyclerview_message_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("/SendMessage");
        myref=myref.child(loginemail.replace(".",","));

        FirebaseRecyclerAdapter<Blog,BlogViewHolder> recyclerAdapter=new FirebaseRecyclerAdapter<Blog,BlogViewHolder>
                (
                Blog.class,
                R.layout.received_chats,
                BlogViewHolder.class,
                myref
        ) {
            @Override
            protected void populateViewHolder(BlogViewHolder viewHolder, Blog model, int position) {
                viewHolder.setTitle(model.gettime());
                viewHolder.setDescription(model.getmessage());
            }
        };
        recyclerView.setAdapter(recyclerAdapter);
       //recyclerView.scrollToPosition(count());

    }

    public static class BlogViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView textView_title;
        TextView textView_decription;
        ImageView imageView;
        public BlogViewHolder(View itemView)
        {
            super(itemView);
            mView=itemView;
            textView_title = (TextView)itemView.findViewById(R.id.text_message_time);
            textView_decription = (TextView) itemView.findViewById(R.id.text_message_body);

            imageView=(ImageView)itemView.findViewById(R.id.image_message_profile);
        }
        public void setTitle(String title)
        {
            textView_title.setText(title+"");
        }
        public void setDescription(String description)
        {
            textView_decription.setText(description);
        }

    }

    public int count() {
        DatabaseReference myref= FirebaseDatabase.getInstance().getReference().child("/SendMessage");
        myref=myref.child(loginemail.replace(".",","));
        myref.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                count = (int) dataSnapshot.getChildrenCount();
                Log.i("Count Value ", String.valueOf(count));
            }
            public void onCancelled(DatabaseError databaseError) { }
        });
        return count;
    }

}




