package edu.csufresno.mycsufi;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.util.Log;

/**
 * @author Anthony
 *
 */
public class DBAdapter {
	private static final String DATABASE_NAME = "myCSUFi.db";
	private static final String DATABASE_TABLE = "classInfo";
	private static final int DATABASE_VERSION = 1;

	public static final String KEY_ID = "_id";
	public static final String KEY_CLASSNAME = "classname";
	public static final int CLASSNAME_COLUMN = 1;
	public static final String KEY_INSTRUCTOR = "instructor";
	public static final int INSTRUCTOR_COLUMN = 2;
	public static final String KEY_ROOM = "classroom";
	public static final int ROOM_COLUMN = 3;
	public static final String KEY_BUILDING = "building";
	public static final int BUILDING_COLUMN = 4;
	public static final String KEY_STARTTIME = "starttime";
	public static final int STARTTIME_COLUMN = 5;
	public static final String KEY_ENDTIME = "endtime";
	public static final int ENDTIME_COLUMN = 6;
	public static final String KEY_DAYS = "days";
	public static final int DAYS_COLUMN = 7;

	private SQLiteDatabase db;
	private final Context context;
	private toDoDBOpenHelper dbHelper;

	public DBAdapter(Context _context) {
		this.context = _context;
		dbHelper = new toDoDBOpenHelper(context, DATABASE_NAME, null,
				DATABASE_VERSION);
		
	}

	public void open() throws SQLiteException {
		try {
			db = dbHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			db = dbHelper.getReadableDatabase();
		}
	}

	public void close() {
		db.close();
	}

	// Insert a new task
	public long insertStudentClass(StudentClass _class) {
		// Create a new row of values to insert.
		ContentValues newValues = new ContentValues();
		// Assign values for each row.
		newValues.put(KEY_CLASSNAME, _class.getName());
		newValues.put(KEY_INSTRUCTOR, _class.getInstructor());
		newValues.put(KEY_ROOM, _class.getRoom());
		newValues.put(KEY_BUILDING, _class.getBuilding());
		newValues.put(KEY_STARTTIME, _class.getStarttime());
		newValues.put(KEY_ENDTIME, _class.getEndtime());
		newValues.put(KEY_DAYS, _class.getDays());

		// Insert the row.
		return db.insert(DATABASE_TABLE, null, newValues);
	}

	// Remove a task based on its index
	public boolean removeStudentClass(long _rowIndex) {
		return db.delete(DATABASE_TABLE, KEY_ID + "=" + _rowIndex, null) > 0;
	}

	public boolean tableIsEmpty () {
		try {
			String count = "SELECT count(*) FROM " + DATABASE_TABLE;
			Cursor mcursor = db.rawQuery(count, null);
			if (mcursor.getInt(0) == 0) return true;
			else return false;
		} catch (SQLiteException e) {
			System.out.println("SQLiteException: " + e);
			return true;
		}
		
	}
	
	// Update a task
	public boolean updateStudentClass(long _rowIndex, StudentClass _class) {
		ContentValues newValues = new ContentValues();
		newValues.put(KEY_CLASSNAME, _class.getName());
		newValues.put(KEY_INSTRUCTOR, _class.getInstructor());
		newValues.put(KEY_ROOM, _class.getRoom());
		newValues.put(KEY_BUILDING, _class.getBuilding());
		newValues.put(KEY_STARTTIME, _class.getStarttime());
		newValues.put(KEY_ENDTIME, _class.getEndtime());
		newValues.put(KEY_DAYS, _class.getDays());
		return db.update(DATABASE_TABLE, newValues, KEY_ID + "=" + _rowIndex,
				null) > 0;
	}

	public void insert_ToDatabase(DBAdapter _db,
			ArrayList<StudentClass> _classes) {

		if (_db.getAllCursor().getCount() == 0) {
			// toast "populating database"

			for (int i = 0; i < _classes.size(); i++) {
				_db.insertStudentClass(_classes.get(i));
			}
		}

		else {
			// toast "updating database"
			for (int i = 0; i < _classes.size(); i++) {
				_db.updateStudentClass(i, _classes.get(i));
			}

		}
	}
	
	/* params:
	 * @day : two char string of the day from which to pull classes
	 *	        (Mo, Tu, We, Th, Fr, Sa, Su)
	 * Return : ArrayList of StudentClass objects
	*/
	public ArrayList<StudentClass> getClassesByDay(String day) {
		ArrayList<StudentClass> _classes = new ArrayList<StudentClass>();
		
		Cursor _cur = getAllCursor();
		_cur.requery();
		_cur.moveToFirst();
		
		while (_cur.isAfterLast() == false) {
			if (_cur.getString(DAYS_COLUMN).contains(day)) {
				StudentClass _class = new StudentClass(
						_cur.getString(CLASSNAME_COLUMN),
						_cur.getString(INSTRUCTOR_COLUMN),
						_cur.getString(ROOM_COLUMN),
						_cur.getString(BUILDING_COLUMN),
						_cur.getString(STARTTIME_COLUMN),
						_cur.getString(ENDTIME_COLUMN),
						_cur.getString(DAYS_COLUMN)
						);
				_classes.add(_class);
			}
			_cur.moveToNext();					
		}
		_cur.close();		
		return _classes;
	}
	
	public ArrayList<StudentClass> getClasses() {
		ArrayList<StudentClass> _classes = new ArrayList<StudentClass>();
		
		Cursor _cur = getAllCursor();
		_cur.requery();
		_cur.moveToFirst();
		
		while (_cur.isAfterLast() == false) {
			StudentClass _class = new StudentClass(
					_cur.getString(CLASSNAME_COLUMN),
					_cur.getString(INSTRUCTOR_COLUMN),
					_cur.getString(ROOM_COLUMN),
					_cur.getString(BUILDING_COLUMN),
					_cur.getString(STARTTIME_COLUMN),
					_cur.getString(ENDTIME_COLUMN),
					_cur.getString(DAYS_COLUMN)
					);
			_classes.add(_class);			
			_cur.moveToNext();					
		}
		_cur.close();		
		return _classes;
	}
	
	public Cursor getAllCursor() {
		return db.query(DATABASE_TABLE, new String[] { KEY_ID, KEY_CLASSNAME,
				KEY_INSTRUCTOR, KEY_ROOM, KEY_BUILDING, KEY_STARTTIME,
				KEY_ENDTIME, KEY_DAYS }, null, null, null, null, null);
	}

	public Cursor setCursorToRow(long _rowIndex) throws SQLException {
		Cursor result = db.query(true, DATABASE_TABLE, new String[] { KEY_ID,
				KEY_CLASSNAME, KEY_INSTRUCTOR, KEY_ROOM, KEY_BUILDING,
				KEY_STARTTIME, KEY_ENDTIME, KEY_DAYS }, KEY_ID + "="
				+ _rowIndex, null, null, null, null, null);
		if ((result.getCount() == 0) || !result.moveToFirst()) {
			throw new SQLException("No classes found for row: " + _rowIndex);
		}
		return result;
	}

	private static class toDoDBOpenHelper extends SQLiteOpenHelper {

		public toDoDBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		// SQL Statement to create a new database.
		private static final String DATABASE_CREATE = "create table "
				+ DATABASE_TABLE + " (" + KEY_ID
				+ " integer primary key autoincrement, " + KEY_CLASSNAME
				+ " text not null, " + KEY_INSTRUCTOR + " text not null, "
				+ KEY_ROOM + " text not null, " + KEY_BUILDING
				+ " text not null, " + KEY_STARTTIME + " text not null, "
				+ KEY_ENDTIME + " text not null, " + KEY_DAYS
				+ " text not null);";

		@Override
		public void onCreate(SQLiteDatabase _db) {
			_db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase _db, int _oldVersion,
				int _newVersion) {
			Log.w("TaskDBAdapter", "Upgrading from version " + _oldVersion
					+ " to " + _newVersion
					+ ", which will destroy all old data");

			// Drop the old table.
			_db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
			// Create a new one.
			onCreate(_db);
		}
	}

}