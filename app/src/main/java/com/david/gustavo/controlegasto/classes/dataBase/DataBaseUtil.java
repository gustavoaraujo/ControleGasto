package com.david.gustavo.controlegasto.classes.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Cliente on 07/09/2016.
 */
public class DataBaseUtil extends SQLiteOpenHelper {
    final static String nomeDB = "academy.db";
    final static int versaoDB = 1;

    public DataBaseUtil(Context context) {
        super(context, nomeDB, null, versaoDB);
    }

    // Categoria
    public final static String tableCategoria = "categoria";
    public final static String categoriaId = "id";
    public final static String categoriaNome = "nome";
    final static String createTableAluno = "Create Table " + tableCategoria + "(" +
            categoriaId + " integer primary key autoincrement, " +
            categoriaNome + " text not null) ";

    // Usuário
    public final static String tableUsuario = "categoria";
    public final static String usuarioId = "id";
    public final static String usuarioLogin = "login";
    public final static String usuarioSenha = "senha";
    final static String createTableUsuario = "Create Table " + tableUsuario + "(" +
            usuarioId + " integer primary key autoincrement, " +
            usuarioLogin + " text not null, " +
            usuarioSenha + " text not null) ";

    // Movimentação
    public final static String tableMovimentacao = "categoria";
    public final static String movimentacaoId = "id";
    public final static String movimentacaoValor = "valor";
    public final static String movimentacaoDescricao = "descricao";
    public final static String movimentacaoData = "data";
    public final static String movimentacaoEhContinuo = "ehContinuo";
    public final static String movimentacaoEhDespesa = "ehDespesa";
    public final static String movimentacaoCategoria = "categoria";
    public final static String movimentacaoUsuario = "usuario";
    final static String createTableMovimentacao = "Create Table " + tableMovimentacao + "(" +
            movimentacaoId + " integer primary key autoincrement, " +
            movimentacaoValor + " float not null, " +
            movimentacaoDescricao + " string not null, " +
            movimentacaoData + " date not null, " +
            movimentacaoEhContinuo + " int not null, " +
            movimentacaoEhDespesa + " int not null, " +
            movimentacaoCategoria + " int not null, " +
            movimentacaoUsuario + " int not null) ";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableAluno);
        db.execSQL(createTableUsuario);
        db.execSQL(createTableMovimentacao);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + tableCategoria);
        db.execSQL("DROP TABLE " + tableUsuario);
        db.execSQL("DROP TABLE " + tableMovimentacao);
        onCreate(db);
    }
}
