package com.app.bestbet.bestbet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by mcouture-cc on 12/4/2015.
 */
public class BestBetDB
{
    // database constants


    // database constants
    public static final String DB_NAME = "bestbet.db";
    public static final int    DB_VERSION = 1;

    // person table constants
    public static final String PERSON_TABLE = "person";

    public static final String PERSON_ID = "_id";
    public static final int    PERSON_ID_COL = 0;

    public static final String PERSON_NAME = "person_name";
    public static final int    PERSON_NAME_COL = 1;

    public static final String PERSON_WINS = "wins";
    public static final int    PERSON_WINS_COL = 2;

    public static final String PERSON_LOSSES = "losses";
    public static final int    PERSON_LOSSES_COL = 3;

    public static final String PERSON_GAINS = "gains";
    public static final int    PERSON_GAINS_COL = 4;

    // bet table constants
    public static final String BET_TABLE = "task";

    public static final String BET_ID = "_id";
    public static final int    BET_ID_COL = 0;

    public static final String BET_PERSON_ID = "person_id";
    public static final int    BET_PERSON_ID_COL = 1;

    public static final String BET_DESCRIPTION = "bet_description";
    public static final int    BET_DESCRIPTION_COL = 2;

    public static final String BET_AMOUNT = "amount";
    public static final int    BET_AMOUNT_COL = 3;

    public static final String BET_DATE = "bet_date";
    public static final int    BET_DATE_COL = 4;

    public static final String BET_COMPLETED = "bet_completed";
    public static final int    BET_COMPLETED_COL = 5;

    public static final String BET_WON = "bet_won";
    public static final int    BET_WON_COL = 6;

    // CREATE and DROP TABLE statements
    public static final String CREATE_PERSON_TABLE =
            "CREATE TABLE " + PERSON_TABLE + " (" +
                    PERSON_ID   + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    PERSON_NAME + " TEXT NOT NULL, " +
                    PERSON_WINS   + " INTEGER, " +
                    PERSON_LOSSES + " INTEGER, " +
                    PERSON_GAINS + " INTEGER);";

    public static final String CREATE_BET_TABLE =
            "CREATE TABLE " + BET_TABLE + " (" +
                    BET_ID         + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    BET_PERSON_ID    + " INTEGER, " +
                    BET_DESCRIPTION       + " TEXT, " +
                    BET_AMOUNT      + " INTEGER, " +
                    BET_DATE  + " TEXT, " +
                    BET_COMPLETED  + " INTEGER, " +
                    BET_WON     + " INTEGER);";

    public static final String DROP_PERSON_TABLE =
            "DROP TABLE IF EXISTS " + PERSON_TABLE;

    public static final String DROP_BET_TABLE =
            "DROP TABLE IF EXISTS " + BET_TABLE;

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // create tables
            db.execSQL(CREATE_PERSON_TABLE);
            db.execSQL(CREATE_BET_TABLE);

            // insert default people
            db.execSQL("INSERT INTO person VALUES (1, 'Ralph Dreadnought', 0, 0, 0)");
            db.execSQL("INSERT INTO person VALUES (2, 'Bob Saget', 0, 0, 0)");

            // insert sample bets
            db.execSQL("INSERT INTO task VALUES (1, 1, 'Bet Bob he can eat a box of nails', 100, date('now'), 1, 1)");
            db.execSQL("INSERT INTO task VALUES (2, 2, 'Bet that the Seahawks will lose tonight', 20, date('now'), 0, 0)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db,
                              int oldVersion, int newVersion) {

            Log.d("Task list", "Upgrading db from version "
                    + oldVersion + " to " + newVersion);

            Log.d("Task list", "Deleting all data!");
            db.execSQL(BestBetDB.DROP_PERSON_TABLE);
            db.execSQL(BestBetDB.DROP_BET_TABLE);
            onCreate(db);
        }
    }

    // database object and database helper object
    private SQLiteDatabase db;
    private DBHelper dbHelper;

    // constructor
    public BestBetDB(Context context) {
        dbHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    // private methods
    private void openReadableDB() {
        db = dbHelper.getReadableDatabase();
    }

    private void openWriteableDB() {
        db = dbHelper.getWritableDatabase();
    }

    private void closeDB() {
        if (db != null)
            db.close();
    }

    // public methods
    public ArrayList<Person> getPeople() {
        ArrayList<Person> personList = new ArrayList<Person>();
        openReadableDB();
        Cursor cursor = db.query(PERSON_TABLE,
                null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            Person person = new Person();
            person.setId(cursor.getInt(PERSON_ID_COL));
            person.setName(cursor.getString(PERSON_NAME_COL));
            person.setWins(cursor.getInt(PERSON_WINS_COL));
            person.setLosses(cursor.getInt(PERSON_LOSSES_COL));
            person.setGains(cursor.getInt(PERSON_GAINS_COL));

            personList.add(person);
        }
        cursor.close();
        closeDB();
        return personList;
    }

    public Person getPerson(String name) {
        String where = PERSON_NAME + "= ?";
        String[] whereArgs = { name };

        openReadableDB();
        Cursor cursor = db.query(PERSON_TABLE, null,
                where, whereArgs, null, null, null);
        Person person = null;
        cursor.moveToFirst();
        person = new Person(cursor.getInt(PERSON_ID_COL),
                cursor.getString(PERSON_NAME_COL), cursor.getInt(PERSON_WINS_COL),
                cursor.getInt(PERSON_LOSSES_COL), cursor.getInt(PERSON_GAINS_COL));
        cursor.close();
        this.closeDB();

        return person;
    }

    public long insertPerson(Person person) {
        ContentValues cv = new ContentValues();
        cv.put(PERSON_NAME, person.getName());
        cv.put(PERSON_WINS, person.getWins());
        cv.put(PERSON_LOSSES, person.getLosses());
        cv.put(PERSON_GAINS, person.getGains());

        this.openWriteableDB();
        long rowID = db.insert(PERSON_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }


    public ArrayList<Bet> getActiveBets() {
        String where = BET_COMPLETED + "!='1'";;

        this.openReadableDB();
        Cursor cursor = db.query(BET_TABLE, null,
                where, null,
                null, null, null);
        ArrayList<Bet> bets = new ArrayList<Bet>();
        while (cursor.moveToNext()) {
            bets.add(getBetFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return bets;
    }

    public ArrayList<Bet> getCompletedBets() {
        String where = BET_COMPLETED + "='1'";

        this.openReadableDB();
        Cursor cursor = db.query(BET_TABLE, null,
                where, null,
                null, null, null);
        ArrayList<Bet> bets = new ArrayList<Bet>();
        while (cursor.moveToNext()) {
            bets.add(getBetFromCursor(cursor));
        }
        if (cursor != null)
            cursor.close();
        this.closeDB();
        return bets;
    }

    public Bet getBet(String id) {
        String where = BET_ID + "= ?";
        String[] whereArgs = { id };

        // handle exceptions?
        this.openReadableDB();
        Cursor cursor = db.query(BET_TABLE,
                null, where, whereArgs, null, null, null);
        cursor.moveToFirst();
        Bet bet = getBetFromCursor(cursor);
        if (cursor != null)
            cursor.close();
        this.closeDB();

        return bet;
    }

    private static Bet getBetFromCursor(Cursor cursor) {
        if (cursor == null || cursor.getCount() == 0){
            return null;
        }
        else {
            try {
                Bet bet = new Bet(
                        cursor.getInt(BET_ID_COL),
                        cursor.getInt(BET_PERSON_ID_COL),
                        cursor.getString(BET_DESCRIPTION_COL),
                        cursor.getInt(BET_AMOUNT_COL),
                        cursor.getString(BET_DATE_COL),
                        cursor.getInt(BET_COMPLETED_COL),
                        cursor.getInt(BET_WON_COL));
                return bet;
            }
            catch(Exception e) {
                return null;
            }
        }
    }

    public long insertBet(Bet bet) {
        ContentValues cv = new ContentValues();
        cv.put(BET_PERSON_ID, bet.getPersonId());
        cv.put(BET_DESCRIPTION, bet.getDescription());
        cv.put(BET_AMOUNT, bet.getAmount());
        cv.put(BET_DATE, bet.getDate());
        cv.put(BET_COMPLETED, bet.getCompleted());
        cv.put(BET_WON, bet.getWon());

        this.openWriteableDB();
        long rowID = db.insert(BET_TABLE, null, cv);
        this.closeDB();

        return rowID;
    }

    public int updateBet(Bet bet) {
        ContentValues cv = new ContentValues();
        cv.put(BET_PERSON_ID, bet.getPersonId());
        cv.put(BET_DESCRIPTION, bet.getDescription());
        cv.put(BET_AMOUNT, bet.getAmount());
        cv.put(BET_DATE, bet.getDate());
        cv.put(BET_COMPLETED, bet.getCompleted());
        cv.put(BET_WON, bet.getWon());

        String where = BET_ID + "= ?";
        String[] whereArgs = { String.valueOf(bet.getId()) };

        this.openWriteableDB();
        int rowCount = db.update(BET_TABLE, cv, where, whereArgs);
        this.closeDB();

        return rowCount;
    }

    public int deleteBet(long id) {
        String where = BET_ID + "= ?";
        String[] whereArgs = { String.valueOf(id) };

        this.openWriteableDB();
        int rowCount = db.delete(BET_TABLE, where, whereArgs);
        this.closeDB();

        return rowCount;
    }
}