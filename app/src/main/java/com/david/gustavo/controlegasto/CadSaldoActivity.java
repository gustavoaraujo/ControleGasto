package com.david.gustavo.controlegasto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.david.gustavo.controlegasto.classes.Categoria;
import com.david.gustavo.controlegasto.classes.Movimentacao;
import com.david.gustavo.controlegasto.classes.Usuario;
import com.david.gustavo.controlegasto.classes.dao.CategoriaDAO;
import com.david.gustavo.controlegasto.classes.dao.MovimentacaoDAO;

import java.util.Calendar;
import java.util.List;

public class CadSaldoActivity extends AppCompatActivity {

    private Usuario usuario;
    private Movimentacao movimentacao;
    private MovimentacaoDAO mdao;
    private CategoriaDAO cdao;
    private List<Categoria> categorias;

    private Button btnCadastraSaldo, btnVoltar;
    private EditText edtValor, edtDescricao;
    private DatePicker dtMovimentacao;
    private CheckBox ckBoxDespesa, ckBoxContinuo;
    private Spinner spCategoria;

    private Intent intent;
    private static final int SALDO_TELA = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cad_saldo);
        bind();
        RecuperarParametros();
        CarregarParametros();

        btnCadastraSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Categoria c = (Categoria) spCategoria.getSelectedItem();

                Calendar cal = Calendar.getInstance();
                cal.set(dtMovimentacao.getYear(),
                        dtMovimentacao.getMonth(),
                        dtMovimentacao.getDayOfMonth());

                if (edtValor.getText().toString() != "" &&
                        edtDescricao.getText().toString() != "" &&
                        c != null) {
                    movimentacao = new Movimentacao(
                            Double.parseDouble(edtValor.getText().toString()),
                            edtDescricao.getText().toString(), cal.getTime(),
                            ckBoxContinuo.isChecked(), ckBoxDespesa.isChecked(),
                            c, usuario);
                    try {
                        mdao.insert(movimentacao);

                        edtValor.setText("");
                        edtDescricao.setText("");

                        ckBoxContinuo.setChecked(false);
                        ckBoxDespesa.setChecked(false);

                        Toast.makeText(getApplicationContext(), "Movimentação cadastrada com sucesso.", Toast.LENGTH_LONG).show();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), "Não foi possível cadastrar a movimentação.", Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),
                            "Um valor obrigatório não foi preenchido. Favor preencher.",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CategoriaActivity.class);
                intent.putExtra("usuarioLogado", usuario);
                startActivityForResult(intent, SALDO_TELA);
            }
        });
    }

    private void RecuperarParametros() {
        usuario = (Usuario) getIntent().getExtras().get("usuarioLogado");
    }

    private void CarregarParametros() {
        mdao = new MovimentacaoDAO(getApplicationContext());
        cdao = new CategoriaDAO(getApplicationContext());

        categorias = cdao.findAll();

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.select_dialog_item, categorias);
        spCategoria.setAdapter(adapter);

    }

    private void bind() {
        btnCadastraSaldo = (Button) findViewById(R.id.btnCadastraSaldo);
        btnVoltar = (Button) findViewById(R.id.btnVoltaSaldo);
        edtValor = (EditText) findViewById(R.id.edtValor);
        edtDescricao = (EditText) findViewById(R.id.edtDescricao);
        dtMovimentacao = (DatePicker) findViewById(R.id.dtMovimentacao);
        ckBoxDespesa = (CheckBox) findViewById(R.id.ckBoxDespesa);
        ckBoxContinuo = (CheckBox) findViewById(R.id.ckBoxContinuo);
        spCategoria = (Spinner) findViewById(R.id.spinnerCategoria);
    }
}
