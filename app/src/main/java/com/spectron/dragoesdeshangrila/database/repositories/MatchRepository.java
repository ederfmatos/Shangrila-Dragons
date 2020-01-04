package com.spectron.dragoesdeshangrila.database.repositories;

import android.content.Context;
import android.widget.Toast;

import com.spectron.dragoesdeshangrila.database.models.MatchModel;

public class MatchRepository extends FirebaseRepository<MatchModel> {

    private Context context;

    public MatchRepository(Context context) {
        super(MatchModel.class);
        this.context = context;
    }

    @Override
    public void onChange(MatchModel matchModel) {
        if (matchModel != null) {
            if (matchModel.getDragons() == 0) {
                matchModel.finish();
                Toast.makeText(context, "O Jogo acabou", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Restam " + matchModel.getDragons() + " dragões", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public String getEntityName() {
        return "matches";
    }
}
