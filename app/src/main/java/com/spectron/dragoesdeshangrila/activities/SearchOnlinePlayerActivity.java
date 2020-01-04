package com.spectron.dragoesdeshangrila.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.spectron.dragoesdeshangrila.R;
import com.spectron.dragoesdeshangrila.database.models.MatchModel;
import com.spectron.dragoesdeshangrila.database.models.PlayerModel;
import com.spectron.dragoesdeshangrila.database.models.SearchModel;
import com.spectron.dragoesdeshangrila.database.repositories.MatchRepository;
import com.spectron.dragoesdeshangrila.database.repositories.PlayerRepository;
import com.spectron.dragoesdeshangrila.database.repositories.SearchRepository;

import me.ibrahimsn.lib.CirclesLoadingView;

import static android.view.WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS;

public class SearchOnlinePlayerActivity extends AppCompatActivity {

    private final int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton loginButton;
    private AppCompatButton logoutButton;
    private TextView tvStatusLogin;
    private TextView tvMessage;
    private CirclesLoadingView loading;

    private PlayerModel currentPlayer;

    private SearchRepository searchRepository;
    private PlayerRepository playerRepository;

    private SearchModel searchModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_online_player);

        getWindow().setFlags(FLAG_LAYOUT_NO_LIMITS, FLAG_LAYOUT_NO_LIMITS);

        initComponents();
        initGoogleClient();
        initRepositories();
    }

    private void initComponents() {
        loginButton = findViewById(R.id.login_button);
        logoutButton = findViewById(R.id.logout_button);
        tvStatusLogin = findViewById(R.id.tvStatusLogin);
        tvMessage = findViewById(R.id.tvMessage);
        loading = findViewById(R.id.loading);

        loginButton.setOnClickListener(view -> this.signIn());
    }

    private void initGoogleClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void initRepositories() {
        searchRepository = new SearchRepository();
        playerRepository = new PlayerRepository();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            if (account != null) {
                searchPlayers(account);
            }

        } catch (ApiException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void changeComponentsVisibility(boolean isAuthenticated) {
        int visibilityAuthenticated;
        int visibilityUnauthenticated;
        String textStatus;

        if (isAuthenticated) {
            visibilityAuthenticated = View.VISIBLE;
            visibilityUnauthenticated = View.GONE;
            textStatus = "Logado com: " + currentPlayer.getName();
        } else {
            visibilityAuthenticated = View.GONE;
            visibilityUnauthenticated = View.VISIBLE;
            textStatus = "VOCÊ NÃO ESTÁ LOGADO";
        }

        loginButton.setVisibility(visibilityUnauthenticated);

        logoutButton.setVisibility(visibilityAuthenticated);
        loading.setVisibility(visibilityAuthenticated);
        tvMessage.setVisibility(visibilityAuthenticated);
        tvStatusLogin.setText(textStatus);
    }

    private void searchPlayers(GoogleSignInAccount account) {
        currentPlayer = new PlayerModel(account.getEmail(), account.getDisplayName(), account.getId());

        changeComponentsVisibility(true);

        playerRepository.create(currentPlayer);
        search();
    }

    private void search() {
        searchModel = new SearchModel(currentPlayer);
        searchRepository.create(searchModel);

        Handler handler = new Handler();

        handler.postDelayed(() -> searchRepository.findAny(possiblePlayer -> {
            if (possiblePlayer.getId().equals(searchModel.getId())) return;

            if (possiblePlayer.getPossiblePlayer() != null) return;

            searchModel.setPossiblePlayer(possiblePlayer.getId());

            searchRepository.update(searchModel);
            searchRepository.deleteById(searchModel.getId());
            searchRepository.deleteById(searchModel.getPossiblePlayer());

            playerRepository.findById(searchModel.getPossiblePlayer(), this::initMatch);
        }), 2000);
    }

    private void initMatch(PlayerModel secondPlayer) {
        PlayerModel player1;
        PlayerModel player2;

        if (currentPlayer.getLoggedIn().getTime() > secondPlayer.getLoggedIn().getTime()) {
            player1 = currentPlayer;
            player2 = secondPlayer;
        } else {
            player1 = secondPlayer;
            player2 = currentPlayer;
        }

        MatchModel matchModel = new MatchModel(player1, player2);
        new MatchRepository(this).create(matchModel);

        Intent matchIntent = new Intent(this, MatchActivity.class);
        matchIntent.putExtra("matchId", matchModel.getId());

        startActivity(matchIntent);
    }

    @Override
    protected void onStart() {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            searchPlayers(account);
        }

        super.onStart();
    }

    private void stopSearch() {
        searchRepository.deleteById(searchModel.getId());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSearch();
        this.finish();
    }

    public void logout(final View view) {
        mGoogleSignInClient.signOut().addOnCompleteListener(task -> {
            changeComponentsVisibility(false);
            stopSearch();
            Snackbar.make(view, "Desconectado com sucesso", Snackbar.LENGTH_SHORT).show();
        });
    }

}
