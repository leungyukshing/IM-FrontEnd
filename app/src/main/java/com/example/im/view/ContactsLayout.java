package com.example.im.view;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.im.HttpSend;
import com.example.im.ImEntities;
import com.example.im.PersonInfo;
import com.example.im.R;
import com.example.im.ResultCallbackListener;
import com.example.im.UserCenter;
import com.example.im.adapter.UserItemAdapter;
import com.example.im.utils.UserItem;
import com.hdl.elog.ELog;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.disposables.Disposable;

public class ContactsLayout extends Fragment {
    private View root;
    private Context context;
    private List<UserItem> groupList; // Group Chats List
    private List<UserItem> contactList; // Friends List
    private PictureAndTextButton group_patb;
    private PictureAndTextButton contact_patb;
    private RecyclerView group_recyclerview;
    private RecyclerView contact_recyclerview;
    UserItemAdapter contactAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.layout_contacts, container, false);
        initView();
        return root;
    }

    private void initView() {
        group_patb = root.findViewById(R.id.groups_patb);
        contact_patb = root.findViewById(R.id.contacts_patb);
        group_recyclerview = root.findViewById(R.id.groups_recyclerview);
        contact_recyclerview = root.findViewById(R.id.contacts_recyclerview);

        groupList = new ArrayList<>();
        contactList = new ArrayList<>();

        // Load Data to Lists
        loadContactList();

        // Set Group Adapter and Listener
        UserItemAdapter groupAdapter = new UserItemAdapter(context, groupList);
        group_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        group_recyclerview.setAdapter(groupAdapter);

        group_patb.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                if (group_recyclerview.getVisibility() == View.VISIBLE) {
                    group_recyclerview.setVisibility(View.GONE);
                    group_patb.setImageView(R.drawable.shink);
                }
                else {
                    group_recyclerview.setVisibility(View.VISIBLE);
                    group_patb.setImageView(R.drawable.rise);
                }
            }
        });


        // Set Contact Adapter and Listener
        contactAdapter = new UserItemAdapter(context, contactList);
        contact_recyclerview.setLayoutManager(new LinearLayoutManager(context));
        contact_recyclerview.setAdapter(contactAdapter);
        contactAdapter.setmOnItemClickListener(new UserItemAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int i) {
                // jump to personal info
                Intent intent = new Intent(context, PersonInfo.class);
                intent.putExtra("username", contactList.get(i).getUsername());
                intent.putExtra("useremail", contactList.get(i).getEmail());
                context.startActivity(intent);
            }
        });
        contact_patb.SetOnClickListener(new PictureAndTextButton.PictureAndTextButtonOnClickListener() {
            @Override
            public void onClick(View view) {
                if (contact_recyclerview.getVisibility() == View.VISIBLE) {
                    contact_recyclerview.setVisibility(View.GONE);
                    contact_patb.setImageView(R.drawable.shink);
                }
                else {
                    contact_recyclerview.setVisibility(View.VISIBLE);
                    contact_patb.setImageView(R.drawable.rise);
                }
            }
        });
    }

    private void loadContactList() {
        // call to server
        HttpSend.getInstance().getContactListByUserID(UserCenter.getInstance().getUser().getUserid() + "", new ResultCallbackListener<ImEntities.GetContactListResponse>() {
            @Override
            public void onSubscribe(Disposable d) { }

            @Override
            public void onNext(ImEntities.GetContactListResponse getContactListResponse) {
                ELog.e("GetContactList Result: code =" + getContactListResponse.getCode() + "\t msg = " + getContactListResponse.getMessage());
                if (getContactListResponse.getCode().equals("200") && getContactListResponse.getMessage().equals("GetContactList Success")) {
                    List<ImEntities.User> userList = getContactListResponse.getUserList();
                    for (int i = 0; i < userList.size(); i++) {
                        UserItem userItem = new UserItem();
                        userItem.setUsername(userList.get(i).getUsername());
                        userItem.setUserid(userList.get(i).getUserid());
                        userItem.setEmail(userList.get(i).getEmail());
                        contactList.add(userItem);
                    }
                    contactAdapter.setmDatas(contactList);
                }
            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                ELog.e("getContactListByUserID, error: " + e.getMessage());
                Toast.makeText(context, "Network Error!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() { }
        });
    }
}
