package com.david.gustavo.controlegasto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.david.gustavo.controlegasto.classes.Usuario;
import com.david.gustavo.controlegasto.classes.dao.UsuarioDAO;

import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    private static final int SALDO_TELA = 2;

    private EditText txtLogin, txtSenha;
    private Button btnCadastrar, btnLogar;
    private Usuario usuario;
    private UsuarioDAO udao;
    private Intent intent;
    private List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        udao = new UsuarioDAO(getApplicationContext());

        bind();
        CadastrarClick();
        LogarClick();
    }

    private void CadastrarClick()
    {
        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario(txtLogin.getText().toString(), txtSenha.getText().toString());

                usuarios = udao.findAll();
                boolean isUsuarioExistente = false;
                for (Usuario u : usuarios) {
                    if (u.getLogin() == usuario.getLogin())
                    {
                        isUsuarioExistente = true;
                        break;
                    }
                }

                if (isUsuarioExistente) {
                    Toast.makeText(getApplicationContext(),
                            "Usuário já existente! Escolha outro nome de usuário.",
                            Toast.LENGTH_LONG).show();
                } else {
                    try {
                        udao.insert(usuario);

                        Toast.makeText(getApplicationContext(),
                                "Usuário cadastrado! Faça seu login.",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(),
                                "Erro ao tentar cadastrar usuário.",
                                Toast.LENGTH_LONG).show();
                    }

                    txtLogin.setText("");
                    txtSenha.setText("");
                }
            }
        });
    }

    private void LogarClick()
    {
        btnLogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String login = txtLogin.getText().toString();
                String senha = txtSenha.getText().toString();
                usuario = new Usuario(0, login, senha);
                usuarios = udao.findAll();

                for(Usuario u : usuarios)
                    if (u.getLogin().equals(login) && u.getSenha().equals(senha)) usuario = u;

                if (usuario.getId() != 0)
                {
                    intent = new Intent(getApplicationContext(), SaldoActivity.class);

                    // É passado o usuário logado porque precisamos saber de qual
                    // usuário vamos carregar os dados.
                    intent.putExtra("usuarioLogado", usuario);

                    startActivityForResult(intent, SALDO_TELA);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Usuário não foi encontrado. Tente novamente.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void bind()
    {
        txtLogin = (EditText) findViewById(R.id.txtLogin);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrarUsuario);
        btnLogar = (Button) findViewById(R.id.btnLogarUsuario);
    }
}
