package com.david.gustavo.controlegasto.classes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.david.gustavo.controlegasto.classes.Movimentacao;
import com.david.gustavo.controlegasto.classes.dataBase.DataBaseUtil;

/**
 * Created by Cliente on 07/09/2016.
 */
public class MovimentacaoDAO {
    private DataBaseUtil dbu;
    private SQLiteDatabase db;
    private CategoriaDAO cdao;
    private final UsuarioDAO udao;
    private final SimpleDateFormat s = new SimpleDateFormat();

    public MovimentacaoDAO(Context context)
    {
        this.dbu = new DataBaseUtil(context);
        cdao = new CategoriaDAO(context);
        udao = new UsuarioDAO(context);
    }

    public void open()
    {
        db = dbu.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public void insert(Movimentacao a)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseUtil.movimentacaoId, a.getId());
        cv.put(DataBaseUtil.movimentacaoValor, a.getValor());
        cv.put(DataBaseUtil.movimentacaoDescricao, a.getDescricao());
        cv.put(DataBaseUtil.movimentacaoData, String.valueOf(a.getData()));
        cv.put(DataBaseUtil.movimentacaoEhContinuo, a.isEhContinuo());
        cv.put(DataBaseUtil.movimentacaoEhDespesa, a.isEhDespesa());
        cv.put(DataBaseUtil.movimentacaoCategoria, a.getCategoria().getId());
        cv.put(DataBaseUtil.movimentacaoUsuario, a.getUsuario().getId());

        db.insert(DataBaseUtil.tableMovimentacao, null, cv);
        close();
    }

    public void edit(Movimentacao a)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseUtil.movimentacaoValor, a.getValor());
        cv.put(DataBaseUtil.movimentacaoDescricao, a.getDescricao());
        cv.put(DataBaseUtil.movimentacaoData, String.valueOf(a.getData()));
        cv.put(DataBaseUtil.movimentacaoEhContinuo, a.isEhContinuo());
        cv.put(DataBaseUtil.movimentacaoEhDespesa, a.isEhDespesa());
        cv.put(DataBaseUtil.movimentacaoCategoria, a.getCategoria().getId());
        cv.put(DataBaseUtil.movimentacaoUsuario, a.getUsuario().getId());

        db.update(DataBaseUtil.tableMovimentacao, cv, DataBaseUtil.movimentacaoId +"="+a.getId(), null);
        close();
    }

    public void delete(Movimentacao a)
    {
        open();
        db.delete(DataBaseUtil.tableMovimentacao, DataBaseUtil.movimentacaoId +"="+a.getId(), null);
        close();
    }

    public Movimentacao findById(int id)
    {
        open();
        Cursor c = db.rawQuery("SELECT "+dbu.categoriaId+","+dbu.categoriaNome+" FROM "+ dbu.tableCategoria +" where "+dbu.categoriaId+"="+id, null);

        c.moveToFirst();
        Movimentacao aluno = null;

        if(c.isFirst())
        {
            try {
                aluno = new Movimentacao(
                        c.getInt(0),
                        c.getDouble(1),
                        c.getString(2),
                        s.parse(c.getString(3)),
                        c.getInt(4) == 1? true:false,
                        c.getInt(5) == 1? true:false,
                        cdao.findById(c.getInt(6)),
                        udao.findById(c.getInt(7)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        c.close();
        close();
        return aluno;
    }

    public List<Movimentacao> findAll()
    {
        open();

        String[] colunas = {dbu.categoriaId, dbu.categoriaNome};

        Cursor c = db.query(dbu.tableCategoria, colunas, null, null, null, null, null);

        c.moveToFirst();
        List<Movimentacao> alunos = new ArrayList<Movimentacao>();

        while(!c.isAfterLast())
        {
            try {
                alunos.add(new Movimentacao(
                        c.getInt(0),
                        c.getDouble(1),
                        c.getString(2),
                        s.parse(c.getString(3)),
                        c.getInt(4) == 1? true:false,
                        c.getInt(5) == 1? true:false,
                        cdao.findById(c.getInt(6)),
                        udao.findById(c.getInt(7))));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.moveToNext();
        }
        c.close();
        close();
        return alunos;
    }
}
