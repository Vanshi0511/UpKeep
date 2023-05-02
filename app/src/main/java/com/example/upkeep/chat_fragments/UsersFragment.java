//package com.example.upkeep.chat_fragments;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//
//import com.example.upkeep.R;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UsersFragment extends Fragment {
//
//private RecyclerView recycler_view_user;
//private UserAdapter userAdapter;
//private List<User> users;
//    public UsersFragment() {
//        // Required empty public constructor
//    }
//
//
//
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_users, container, false);
//
//
//        recycler_view_user = view.findViewById(R.id.recycler_view_user);
//        recycler_view_user.setHasFixedSize(true);
//        recycler_view_user.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        users = new ArrayList<>();
//        readUsers();
//        return view;
//    }
//
//    private void readUsers() {
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                users.clear();
//                for (DataSnapshot snapshot1 : datasnapshot.getChildren())
//                {
//                    User user = snapshot1.getValue(User.class);
//                    if (!user.getId().equals(firebaseUser.getUid())){
//                        users.add(user);
//                    }
//                }
//                userAdapter = new UserAdapter(getContext(),users);
//                recycler_view_user.setAdapter(userAdapter);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//}