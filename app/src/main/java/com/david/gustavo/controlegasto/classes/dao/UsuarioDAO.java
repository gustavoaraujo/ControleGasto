package com.david.gustavo.controlegasto.classes.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import com.david.gustavo.controlegasto.classes.Usuario;
import com.david.gustavo.controlegasto.classes.dataBase.DataBaseUtil;

/**
 * Created by Cliente on 07/09/2016.
 */
public class UsuarioDAO {
    private DataBaseUtil dbu;
    private SQLiteDatabase db;

    public UsuarioDAO(Context context)
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

    public void insert(Usuario a)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseUtil.usuarioId, a.getId());
        cv.put(DataBaseUtil.usuarioLogin, a.getLogin());
        cv.put(DataBaseUtil.usuarioSenha, a.getSenha());

        db.insert(DataBaseUtil.tableUsuario, null, cv);
        close();
    }

    public void edit(Usuario a)
    {
        open();
        ContentValues cv = new ContentValues();
        cv.put(DataBaseUtil.usuarioLogin, a.getLogin());
        cv.put(DataBaseUtil.usuarioSenha, a.getSenha());

        db.update(DataBaseUtil.tableUsuario, cv, DataBaseUtil.usuarioId +"="+a.getId(), null);
        close();
    }

    public void delete(Usuario a)
    {
        open();
        db.delete(DataBaseUtil.tableUsuario, DataBaseUtil.usuarioId +"="+a.getId(), null);
        close();
    }

    public Usuario findById(int id)
    {
        open();
        Cursor c = db.rawQuery("SELECT "+dbu.usuarioId+","+dbu.usuarioLogin+","+dbu.usuarioSenha+" FROM "+ dbu.tableUsuario +" where "+dbu.usuarioId+"="+id, null);

        c.moveToFirst();
        Usuario aluno = null;

        if(c.isFirst())
        {
            aluno = new Usuario(c.getInt(0), c.getString(1), c.getString(2));
        }
        c.close();
        close();
        return aluno;
    }

    public List<Usuario> findAll()
    {
        open();

        String[] colunas = {dbu.usuarioId, dbu.usuarioLogin, dbu.usuarioSenha};

        Cursor c = db.query(dbu.tableUsuario, colunas, null, null, null, null, null);

        c.moveToFirst();
        List<Usuario> alunos = new ArrayList<Usuario>();

        while(!c.isAfterLast())
        {
            alunos.add(new Usuario(c.getInt(0),c.getString(1), c.getString(2)));
            c.moveToNext();
        }
        c.close();
        close();
        return alunos;
    }
}