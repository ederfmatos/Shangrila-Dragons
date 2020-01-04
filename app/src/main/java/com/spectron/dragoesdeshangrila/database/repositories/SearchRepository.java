package com.spectron.dragoesdeshangrila.database.repositories;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.spectron.dragoesdeshangrila.database.models.SearchModel;

import java.util.function.Consumer;

public class SearchRepository extends FirebaseRepository<SearchModel> {

    public SearchRepository() {
        super(SearchModel.class);
    }

    @Override
    public void onChange(SearchModel matchModel) {
    }

    @Override
    public String getEntityName() {
        return "searching";
    }

    public void findAny(final Consumer<SearchModel> onSearchCompleted) {
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                SearchModel entity = dataSnapshot.getValue(SearchModel.class);

                onSearchCompleted.accept(entity);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
