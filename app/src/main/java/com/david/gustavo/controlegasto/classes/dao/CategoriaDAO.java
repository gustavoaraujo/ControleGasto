package com.david.gustavo.controlegasto.classes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.david.gustavo.controlegasto.classes.Categoria;
import com.david.gustavo.controlegasto.classes.dataBase.DataBaseUtil;

/**
 * Created by Cliente on 07/09/2016.
 */
public class CategoriaDAO {
    private DataBaseUtil dbu;
    private SQLiteDatabase db;

    public CategoriaDAO(Context context)
    {
        this.dbu = new DataBaseUtil(context);
    }

    public void open()
    {
        db = dbu.getWritableDatabase();
    }

    public void close()
    {
        db.close();
    }

    public void insert(Categoria a)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseUtil.categoriaId, a.getId());
        cv.put(DataBaseUtil.categoriaNome, a.getNome());

        db.insert(DataBaseUtil.tableCategoria, null, cv);
        close();
    }

    public void edit(Categoria a)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseUtil.categoriaNome, a.getNome());

        db.update(DataBaseUtil.tableCategoria, cv, DataBaseUtil.categoriaId +"="+a.getId(), null);
        close();
    }

    public void delete(Categoria a)
    {
        open();
        db.delete(DataBaseUtil.tableCategoria, DataBaseUtil.categoriaId +"="+a.getId(), null);
        close();
    }

    public Categoria findById(int id)
    {
        open();
        Cursor c = db.rawQuery("SELECT "+dbu.categoriaId+","+dbu.categoriaNome+" FROM "+ dbu.tableCategoria +" where "+dbu.categoriaId+"="+id, null);

        c.moveToFirst();
        Categoria aluno = null;

        if(c.isFirst())
        {
            aluno = new Categoria(c.getInt(0), c.getString(1));
        }
        c.close();
        close();
        return aluno;
    }

    public List<Categoria> findAll()
    {
        open();

        String[] colunas = {dbu.categoriaId, dbu.categoriaNome};

        Cursor c = db.query(dbu.tableCategoria, colunas, null, null, null, null, null);

        c.moveToFirst();
        List<Categoria> alunos = new ArrayList<Categoria>();

        while(!c.isAfterLast())
        {
            alunos.add(new Categoria(c.getInt(0),c.getString(1)));
            c.moveToNext();
        }
        c.close();
        close();
        return alunos;
    }
}
