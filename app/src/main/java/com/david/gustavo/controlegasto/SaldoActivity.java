package com.david.gustavo.controlegasto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.david.gustavo.controlegasto.classes.Movimentacao;
import com.david.gustavo.controlegasto.classes.Usuario;
import com.david.gustavo.controlegasto.classes.dao.MovimentacaoDAO;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SaldoActivity extends AppCompatActivity {

    private static final int LOGIN_TELA = 1;
    private static final int CAD_SALDO_TELA = 2;
    private static final int ALTDEL_SALDO_TELA = 3;
    private static final int CATEGORIA_TELA = 4;

    private MovimentacaoDAO mdao;
    private List<Movimentacao> movimentacoes, movimentacoesFiltradas;
    private Usuario usuario;
    private double saldo;
    private NumberPicker pickerMes, pickerAno;
    private Button btnDeslogar, btnCategoria, btnCadSaldo;
    private ListView lstSaldo;
    private TextView txtSaldo;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saldo);

        bind();

        RecuperarParametros();
        CarregarParametros();

        pickerAno.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                CarregarMesesBusca();
            }
        });

        pickerMes.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                FiltrarMovimentacoesPorMes();
                CalcularSaldo(movimentacoesFiltradas);
            }
        });

        btnDeslogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivityForResult(intent, LOGIN_TELA);
            }
        });

        btnCadSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CadSaldoActivity.class);
                intent.putExtra("usuarioLogado", usuario);
                startActivityForResult(intent, CAD_SALDO_TELA);
            }
        });

        btnCategoria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), CategoriaActivity.class);
                intent.putExtra("usuarioLogado", usuario);
                startActivityForResult(intent, CATEGORIA_TELA);
            }
        });

        lstSaldo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent(getApplicationContext(), CategoriaActivity.class);
                intent.putExtra("movimentacao", (Movimentacao) lstSaldo.getSelectedItem());
                intent.putExtra("usuarioLogado", usuario);
                startActivityForResult(intent, ALTDEL_SALDO_TELA);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void FiltrarMovimentacoesPorMes() {
        movimentacoesFiltradas = new ArrayList<>();

        for(Movimentacao m: movimentacoes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(m.getData());

            int mes = cal.get(Calendar.MONTH);
            int ano = cal.get(Calendar.YEAR);

            if (ano == pickerAno.getValue() && mes == pickerMes.getValue()) {
                movimentacoesFiltradas.add(m);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.select_dialog_item, movimentacoesFiltradas);
        lstSaldo.setAdapter(adapter);
    }

    private void CarregarMesesBusca() {
        ArrayList<String> arrMeses = new ArrayList<>();

        for (Movimentacao m : movimentacoes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(m.getData());

            String mes = String.valueOf(cal.get(Calendar.MONTH));
            int ano = cal.get(Calendar.YEAR);

            if (ano == pickerAno.getValue()) {
                if (arrMeses.size() == 0) {
                    arrMeses.add(mes);
                } else {
                    if (!arrMeses.contains(mes)) {
                        arrMeses.add(mes);
                    }
                }
            }
        }

        String[] arrayMeses = new String[arrMeses.size()];
        arrayMeses = arrMeses.toArray(arrayMeses);

        pickerMes.setDisplayedValues(arrayMeses);
    }

    private void bind() {
        pickerMes = (NumberPicker) findViewById(R.id.pickerMes);
        pickerAno = (NumberPicker) findViewById(R.id.pickerAno);
        btnDeslogar = (Button) findViewById(R.id.btnDeslogar);
        btnCategoria = (Button) findViewById(R.id.btnCategoria);
        btnCadSaldo = (Button) findViewById(R.id.btnCadSaldo);
        lstSaldo = (ListView) findViewById(R.id.listSaldo);
        txtSaldo = (TextView) findViewById(R.id.txtSaldo);
    }

    private void CarregarParametros() {
        mdao = new MovimentacaoDAO(getApplicationContext());

        movimentacoes = new ArrayList<Movimentacao>();
        CarregarListaMovimentacoesPorUsuario();
        CarregarAnosBusca();

        CalcularSaldo(movimentacoes);
    }

    private void CarregarAnosBusca() {
        ArrayList<String> arrAnos = new ArrayList<>();

        for (Movimentacao m : movimentacoes) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(m.getData());

            String ano = String.valueOf(cal.get(Calendar.YEAR));

            if (arrAnos.size() == 0) {
                arrAnos.add(ano);
            } else {
                if (!arrAnos.contains(ano)) {
                    arrAnos.add(ano);
                }
            }
        }

        String[] arrayAnos = new String[arrAnos.size()];
        arrayAnos = arrAnos.toArray(arrayAnos);

        pickerAno.setDisplayedValues(arrayAnos);
    }

    private void CalcularSaldo(List<Movimentacao> listaMov) {
        saldo = 0;
        for (Movimentacao m : listaMov) {
            if (m.isEhDespesa())
                saldo -= m.getValor();
            else
                saldo += m.getValor();
        }

        txtSaldo.setText(String.valueOf(saldo));
    }

    private void CarregarListaMovimentacoesPorUsuario() {
        List<Movimentacao> movimentacoesBD = mdao.findAll();

        for (Movimentacao m : movimentacoesBD)
            if (m.getUsuario().equals(usuario))
                movimentacoes.add(m);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(),android.R.layout.select_dialog_item, movimentacoes);
        lstSaldo.setAdapter(adapter);
    }

    private void RecuperarParametros() {
        usuario = (Usuario) getIntent().getExtras().get("usuarioLogado");
    }
}
