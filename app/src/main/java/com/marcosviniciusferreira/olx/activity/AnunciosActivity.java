package com.marcosviniciusferreira.olx.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.marcosviniciusferreira.olx.R;
import com.marcosviniciusferreira.olx.helper.ConfiguracaoFirebase;

public class AnunciosActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anuncios);

        autenticacao = ConfiguracaoFirebase.getFirebaseAuth();


    }

    //Método que cria as opções do menu (ocorre apenas 1 vez)
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    //Método que prepara as opções do menu
    //ocorre antes dos opções aparecerem no createOptionsMenu
    //E acontece cada vez que o menu aparece para o usuário
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        if (autenticacao.getCurrentUser() == null) {                    //usuario deslogado
            menu.setGroupVisible(R.id.group_deslogado, true);
        } else {                                                        //usuario logado
            menu.setGroupVisible(R.id.group_logado, true);

        }

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_sair:
                autenticacao.signOut();

                //Ao invalidar os itens de menu, e então ele prepara o menu novamente (onPrepateOptionsMenu)
                invalidateOptionsMenu();

                break;
            case R.id.menu_cadastrar:
                startActivity(new Intent(getApplicationContext(), CadastroActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
