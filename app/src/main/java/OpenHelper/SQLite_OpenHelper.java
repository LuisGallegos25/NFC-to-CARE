package OpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class SQLite_OpenHelper  extends SQLiteOpenHelper {


    public SQLite_OpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query="create table usuario(id_usuario integer primary key autoincrement," +
                "nombres text, usuario text, password text);";

        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

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
        mcursor = this.getReadableDatabase().query("usuario",new String[]{"id_usuario","nombres","usuario","password"},"usuario like '"+usuario+"' " +
                "and password like '"+password+"'",null,null,null,null);

        return mcursor;
    }
}
