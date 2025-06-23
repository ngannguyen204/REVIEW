package connectors;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import models.Account;

public class AccountConnector {

    Activity context;
    String DATABASE_NAME="Review.sql";
    private static final String DB_PATH_SUFFIX = "/databases/";
    SQLiteDatabase database=null;

    public AccountConnector() {
    }

    public AccountConnector (Activity context)
    {
        this.context=context;
    }
    public Account login(Activity context, String usr, String pwd)
    {
        database=context.openOrCreateDatabase(DATABASE_NAME,
                context.MODE_PRIVATE,null);

        Cursor cursor = database.rawQuery(
                "SELECT * FROM Accounts WHERE Username= ? AND Password=?",
                new String[]{usr,pwd});
        Account acc=null;
        if(cursor.moveToNext())
        {
            int id=cursor.getInt(0);
            String username=cursor.getString(1);
            String password=cursor.getString(2);
            int saveInfor=cursor.getInt(3);
            acc=new Account();
            acc.setUsername(username);
            acc.setPassword(password);
            acc.setSaveInfor(saveInfor==1?true:false);

        }
        cursor.close();

        return acc;
    }
}
