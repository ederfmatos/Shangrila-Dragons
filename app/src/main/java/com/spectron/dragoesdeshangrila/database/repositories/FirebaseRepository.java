package com.spectron.dragoesdeshangrila.database.repositories;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.spectron.dragoesdeshangrila.database.models.ShangrilaModel;

import java.util.function.Consumer;

public abstract class FirebaseRepository<Entity extends ShangrilaModel> {

    protected final DatabaseReference reference;
    private final Class<Entity> entityClass;

    public FirebaseRepository(final Class<Entity> entityClass) {
        this.entityClass = entityClass;

        this.reference = FirebaseDatabase.getInstance().getReference(getEntityName());
    }

    public void create(Entity entity) {
        if (entity.getId() == null || entity.getId().isEmpty()) {
            entity.setId(reference.push().getKey());
        }

        update(entity);
    }

    public void update(Entity entity) {
        DatabaseReference databaseReference = reference.child(entity.getId());

        databaseReference.setValue(entity);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                onChange(dataSnapshot.getValue(entityClass));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void findById(String id, Consumer<Entity> consumer) {
        DatabaseReference databaseReference = reference.child(id);

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                consumer.accept(dataSnapshot.getValue(entityClass));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void deleteById(String id) {
        DatabaseReference databaseReference = reference.child(id);

        databaseReference.setValue(null);
    }

    public abstract void onChange(Entity value);

    public abstract String getEntityName();

}
