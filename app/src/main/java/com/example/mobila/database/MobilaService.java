package com.example.mobila.database;

import android.content.Context;

import com.example.mobila.data.Mobila;
import com.example.mobila.network.AsyncTaskRunner;
import com.example.mobila.network.Callback;

import java.util.List;
import java.util.concurrent.Callable;

public class MobilaService {
    private MobilaDao mobilaDao;
    private AsyncTaskRunner asyncTaskRunner = new AsyncTaskRunner();

    public MobilaService(Context context) {
        mobilaDao = DatabaseManager.getInstance(context).getMobilaDao();
    }

    public void getAll(Callback<List<Mobila>> callback) {
        Callable<List<Mobila>> callable = () -> mobilaDao.getAll();
    }

    public void insertAll(List<Mobila> lista, Callback<List<Mobila>> callback) {
        Callable<List<Mobila>> callable = () -> {
            List<Long> ids = mobilaDao.insertAll(lista);

            for (int i = 0; i < lista.size(); i++) {
                lista.get(i).setId(ids.get(i));
            }
            return lista;
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }

    public void delete(List<Mobila> lista, Callback<List<Mobila>> callback) {
        Callable<List<Mobila>> callable = () -> {
          int count = mobilaDao.delete(lista);

          if (count <= 0) {
              return null;
          }
          return lista;
        };
        asyncTaskRunner.executeAsync(callable, callback);
    }
}
