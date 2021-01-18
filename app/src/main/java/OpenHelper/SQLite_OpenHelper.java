package OpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;
import android.widget.Toast;


public class SQLite_OpenHelper  extends SQLiteOpenHelper {

    public static String table_call = "create table call(id integer primary key , nombres text, numero text)";
    public static String table_usuario="create table usuario(id integer primary key ,nombres text, usuario text, password text)";




    public SQLite_OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(table_usuario);
        sqLiteDatabase.execSQL(table_call);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.setVersion(oldVersion);

    }

    //METODO PARA ABRIR DB
    public void abrir(){
        this.getWritableDatabase();
    }

    //METODO PARA CERRAR DB
    public void cerrar(){
        this.close();
    }

    //METODO PARA INSERTAR REGISTRO EN TABLA USUARIO
    public void insertarRegistro(String nombres, String usuario, String password)
    {
        ContentValues valores=new ContentValues();
        valores.put("Nombres",nombres);
        valores.put("Usuario",usuario);
        valores.put("Password",password);

        this.getWritableDatabase().insert("usuario", null,valores);
    }

    //METODO PARA VALIDAR SI EL USUARIO EXISTE
    public Cursor ConsultarUsuPass(String usuario, String password) throws SQLException{

        Cursor mcursor=null;
        mcursor = this.getReadableDatabase().query("usuario",new String[]{"id","nombres","usuario","password"},"usuario like '"+usuario+"' " +
                "and password like '"+password+"'",null,null,null,null);

        return mcursor;
    }

//INSERTAR CALL
    public void insertarCall(String nombre, String numero)
    {
        ContentValues valores=new ContentValues();
        valores.put("nombres",nombre);
        valores.put("numero",numero);


        this.getWritableDatabase().insert("call", null,valores);
    }


}
