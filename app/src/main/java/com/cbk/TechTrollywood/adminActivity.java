package com.cbk.TechTrollywood;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class adminActivity extends AppCompatActivity {
    private ArrayAdapter<User> listAdapter;
    private static Firebase fb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        fb = new Firebase(getResources().getString(R.string.firebase_url));

        // Find the ListView resource.
        ListView mainListView = (ListView) findViewById(R.id.userListView);

        // When item is tapped, toggle checked properties of CheckBox and User.
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item,
                                    int position, long id) {
                User user = listAdapter.getItem(position);
                user.toggleChecked();
                UserViewHolder viewHolder = (UserViewHolder) item.getTag();
                viewHolder.getCheckBox().setChecked(user.isChecked());
                fb.child("users").child(user.getUid()).child("locked").setValue(user.isChecked());
            }
        });
        // Set our custom array adapter as the ListView's adapter.
        listAdapter = new UserArrayAdapter(this, new ArrayList<User>());
        mainListView.setAdapter(listAdapter);

        populateUserList();
    }

    /**
     * Holds User data.
     */
    private static class User {
        private String name = "";
        private boolean checked = false;
        private String uid = "";

        public User() {
        }

        public User(String name) {
            this.name = name;
        }

        public User(String name, String uid, boolean checked) {
            this.name = name;
            this.checked = checked;
            this.uid = uid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String toString() {
            return name;
        }

        public void toggleChecked() {
            checked = !checked;
        }
    }

    /**
     * Holds child views for one row.
     */
    private static class UserViewHolder {
        private CheckBox checkBox;
        private TextView textView;

        public UserViewHolder() {
        }

        public UserViewHolder(TextView textView, CheckBox checkBox) {
            this.checkBox = checkBox;
            this.textView = textView;
        }

        public CheckBox getCheckBox() {
            return checkBox;
        }

        public void setCheckBox(CheckBox checkBox) {
            this.checkBox = checkBox;
        }

        public TextView getTextView() {
            return textView;
        }

        public void setTextView(TextView textView) {
            this.textView = textView;
        }
    }

    /**
     * Custom adapter for displaying an array of user objects.
     */
    private static class UserArrayAdapter extends ArrayAdapter<User> {

        private LayoutInflater inflater;

        public UserArrayAdapter(Context context, List<User> userList) {
            super(context, R.layout.checked_list_item, R.id.rowTextView, userList);
            // Cache the LayoutInflate to avoid asking for a new one each time.
            inflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // user to display
            User user = this.getItem(position);

            // The child views in each row.
            CheckBox checkBox;
            TextView textView;

            // Create a new row view
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.checked_list_item, null);

                // Find the child views.
                textView = (TextView) convertView.findViewById(R.id.rowTextView);
                checkBox = (CheckBox) convertView.findViewById(R.id.CheckBox01);

                // Optimization: Tag the row with it's child views, so we don't have to
                // call findViewById() later when we reuse the row.
                convertView.setTag(new UserViewHolder(textView, checkBox));

                // If CheckBox is toggled, update the user it is tagged with.
                checkBox.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        CheckBox cb = (CheckBox) v;
                        User user = (User) cb.getTag();
                        user.setChecked(cb.isChecked());
                        fb.child("users").child(user.getUid()).child("locked").setValue(user.isChecked());
                    }
                });
            }
            // Reuse existing row view
            else {
                // Because we use a ViewHolder, we avoid having to call findViewById().
                UserViewHolder viewHolder = (UserViewHolder) convertView.getTag();
                checkBox = viewHolder.getCheckBox();
                textView = viewHolder.getTextView();
            }

            // Tag the CheckBox with the user it is displaying, so that we can
            // access the user in onClick() when the CheckBox is toggled.
            checkBox.setTag(user);

            // Display user data
            checkBox.setChecked(user.isChecked());
            textView.setText(user.getName());

            return convertView;
        }

    }

    private void populateUserList() {
        fb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot user : snapshot.child("users").getChildren()) {
                    String uid = user.getKey();
                    String email = user.child("email").getValue().toString();
                    Boolean locked = (Boolean) user.child("locked").getValue();
                    if(locked==null){
                        locked=Boolean.FALSE;
                    }
                    listAdapter.add(new User(email, uid, locked));
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d("TAG", firebaseError.getMessage());
            }
        });
    }
    public void onBackPressed()
    {
        fb.unauth();
        this.finish();
    }
}
