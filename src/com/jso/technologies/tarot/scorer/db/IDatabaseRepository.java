package com.jso.technologies.tarot.scorer.db;

import java.util.List;

import android.database.Cursor;

/**
 * Interface d'acc�s � la base de donn�es
 * @author Jimmy
 *
 * @param <T> le bean manipul�
 */
public interface IDatabaseRepository<T> {
	public List<T> getAll();
	public T getById(int id);
	public void save(T entite);
	public void update(T entite);
	public void delete(int id);
	
	public List<T> convertCursorListToBeanList(Cursor c);
	public T convertCursorToBean(Cursor c);
	public T getBeanFromCurrentCursor(Cursor c);
}
