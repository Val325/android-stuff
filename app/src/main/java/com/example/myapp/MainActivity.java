package com.example.myapp;
import org.json.JSONException; 
import org.json.JSONObject; 
import android.util.Log; 
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast; 
import com.android.volley.Request; 
import com.android.volley.RequestQueue; 
import com.android.volley.Response; 
import com.android.volley.toolbox.JsonObjectRequest; 
import com.android.volley.toolbox.Volley; 
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;  
import java.util.Date;
import java.io.Serializable;
import com.google.gson.Gson; 
import com.google.gson.reflect.TypeToken; 
import java.lang.reflect.Type;
import android.content.SharedPreferences;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.content.Intent;
import java.util.Calendar;
import android.widget.ImageView;
import android.net.Uri;
import androidx.activity.result.ActivityResultLauncher;
import android.graphics.Bitmap; 
import androidx.activity.result.contract.ActivityResultContracts;
import android.app.Activity;
import android.provider.MediaStore;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;
import java.io.FileOutputStream;
import android.graphics.BitmapFactory;
import android.widget.RelativeLayout; 
import android.widget.TextView; 
import android.view.ContextMenu;
import android.view.MenuInflater;  
import android.widget.RadioButton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import android.widget.Button;  
import android.widget.RadioButton;  
import android.widget.RadioGroup;
import java.util.Locale;
import android.content.res.Configuration;
import android.widget.AutoCompleteTextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentValues;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.SeekBar;
import android.widget.TimePicker;
import android.content.res.Configuration;
import android.util.DisplayMetrics;
import android.view.WindowManager;

class NumberComparatorMinToMax implements Comparator<Note> {
   @Override
   public int compare(Note o1, Note o2) {
        if(o1.getImportance() > o2.getImportance())
            return 1;
        else if(o1.getImportance() < o2.getImportance())
            return -1;
        else
            return 0;
   }
}
class NumberComparatorMaxToMin implements Comparator<Note> {
   @Override
   public int compare(Note o1, Note o2) {
        if(o1.getImportance() > o2.getImportance())
            return -1;
        else if(o1.getImportance() < o2.getImportance())
            return 1;
        else
            return 0;
   }
}
class MinImportanceComparator implements Comparator<Note> {
   @Override
   public int compare(Note o1, Note o2) {
       return o2.getImportance() - o1.getImportance();
   }
}

class Note implements Parcelable{ 
 
    private String title; 
    private String text;  
    private String time;
    private int imgResource;
    private int importance;
    private Bitmap image;  
    SimpleDateFormat formatter;
    Date date;
    private String imagePath;
    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel source) {
            String title = source.readString();
            String text = source.readString();
            String time = source.readString();
            String imagePath = source.readString();
            int importance = source.readInt();
            return new Note(title, text, time, imagePath, importance);
        }
 
        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public Note(String title, String text, String time, int res){
        this.formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
        this.date = new Date();  
        this.title=title;
        this.text=text;
        this.time=formatter.format(date);
        this.imgResource=res;
    }
    public Note(String title, String text, String time, String imagePath, int impr) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.imagePath = imagePath;
        this.importance = impr;
    }
    public Note(String title, String text, String time){
        long currentTimeMillis = System.currentTimeMillis();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(currentTimeMillis));  
        this.title=title;
        this.text=text;
        this.time=timeStamp;

    }
    public Note(String title, String text, int res){
        long currentTimeMillis = System.currentTimeMillis();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date(currentTimeMillis));  
        this.title=title;
        this.text=text;
        this.time=timeStamp;
        this.imgResource=res; 
    }
    public String getImagePath() {
        return this.imagePath;
    }
    public static Comparator<Note> isCompDescend = new Comparator<Note>(){
        @Override
        public int compare(Note noteone, Note notetwo){
            int firstnum = Integer.valueOf(noteone.getImportance());
            int twonum = Integer.valueOf(notetwo.getImportance());
            return Integer.compare(twonum, firstnum);
        }
    };
    public static Comparator<Note> isCompAscend = new Comparator<Note>(){
        @Override
        public int compare(Note noteone, Note notetwo){
            int firstnum = Integer.valueOf(noteone.getImportance());
            int twonum = Integer.valueOf(notetwo.getImportance());
            return Integer.compare(firstnum, twonum);
        }
    };
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public Bitmap getImage() {
        return this.image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
    public String getTitle() {
        return this.title;
    }

    public void setImportance(int importance) {
        this.importance = importance;
    }
    public int getImportance() {
        return this.importance;
    }

    public void setTitle(String title) {
        this.title = title;
    }
 
    public String getText() {
        return this.text;
    }
 
    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return this.time;
    }
 
    public void setTime(String time) {
        this.time = time;
    }

    public int getImgResource() {
        return this.imgResource;
    }
 
    public void setTitle(int res) {
        this.imgResource=res;
    }


    @Override
    public int describeContents() {
        return 0;
    }
@Override
public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(title);
    dest.writeString(text);
    dest.writeString(time);
    dest.writeString(imagePath);  
    dest.writeInt(importance);
}

protected Note(Parcel in) {
    title = in.readString();
    text = in.readString();
    time = in.readString();
    imagePath = in.readString();  
    importance = in.readInt();  
}
}

class StateAdapter extends ArrayAdapter<Note> {
 
    private LayoutInflater inflater;
    private int layout;
    private List<Note> states;
 
    public StateAdapter(Context context, int resource, List<Note> states) {
        super(context, resource, states);
        this.states = states;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

     
    public View getView(int position, View convertView, ViewGroup parent) {
 
        View view=inflater.inflate(this.layout, parent, false);
 
        TextView titleView = view.findViewById(R.id.title);
        TextView textView = view.findViewById(R.id.text);
        TextView dataView = view.findViewById(R.id.time);
        TextView impView = view.findViewById(R.id.importance);
        ImageView imgView = view.findViewById(R.id.icon);
 
        Note state = states.get(position);
 
        titleView.setText(state.getTitle());
        textView.setText(state.getText());
        dataView.setText(state.getTime());
        impView.setText("importance: " + state.getImportance());
        if (state.getImagePath() != null) {
            Bitmap image = BitmapFactory.decodeFile(state.getImagePath());
            imgView.setImageBitmap(image); 
        }

        return view;
    }
}

class DBHandler extends SQLiteOpenHelper {
 
    // creating a constant variables for our database.
    // below variable is for our database name.
    private static final String DB_NAME = "postsdb";
     
    // below int is our database version
    private static final int DB_VERSION = 1;
    private static final String DATABASE_NAME = "app.db"; // название бд
    static final String TABLE = "posts"; // название таблицы в бд
    
    SQLiteDatabase db; 
    // creating a constructor for our database handler.
    public DBHandler(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
 
    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {

        String query = "CREATE TABLE IF NOT EXISTS " + TABLE + " (title TEXT, text TEXT, time TEXT, importance INTEGER, pathimg TEXT);"; 
        db.execSQL(query);
    }
 
    // this method is use to add new course to our sqlite database.
    public void addPost(String titleData, String textData, String timeData, String pathimgData, int important) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", titleData);
        cv.put("text", textData);
        cv.put("time", timeData);
        cv.put("importance", important);
        cv.put("pathimg", pathimgData);
        db.insert(TABLE, null, cv);
        db.close();
    }
    public void editPost(String namePost, Note selNote){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("title", selNote.getTitle());
        cv.put("text", selNote.getText());
        cv.put("time", selNote.getTime());
        cv.put("importance", selNote.getImportance());
        cv.put("pathimg", selNote.getImagePath());
        db.update(TABLE, cv, "title=?", new String[]{namePost});
        db.close();
    }
    public void deletePost(String namePost){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE, "title=?", new String[]{namePost});
        db.close();
    }
    public ArrayList<Note> getDataPosts(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursorPosts = db.rawQuery("SELECT * FROM " + TABLE, null);
        ArrayList<Note> postsData = new ArrayList<>();
        if (cursorPosts.moveToFirst()) {
            do {
                postsData.add(new Note(
                        cursorPosts.getString(cursorPosts.getColumnIndex("title")), 
                        cursorPosts.getString(cursorPosts.getColumnIndex("text")), 
                        cursorPosts.getString(cursorPosts.getColumnIndex("time")), 
                        cursorPosts.getString(cursorPosts.getColumnIndex("pathimg")), 
                        cursorPosts.getInt(cursorPosts.getColumnIndex("importance"))
                        ));
            } while (cursorPosts.moveToNext());
        }
        cursorPosts.close();
        return postsData;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }
}

public class MainActivity extends AppCompatActivity {
    ArrayList<Note> states = new ArrayList<Note>();
    ArrayList<String> texts = new ArrayList<String>();
    ListView notesList;
    StateAdapter stateAdapter;
    Note selectedNote;
    Button button;  
    RadioButton genderradioButton;  
    RadioGroup radioGroup;
    Locale locale;
    SQLiteDatabase db;
    private static int sTheme;
    private DBHandler dbHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //setTheme(R.style.AppTheme);
        //setTheme(R.style.OrangeTheme);
        if(sTheme!=0){
            setTheme(sTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //states = getIntent().getParcelableArrayListExtra("state"); 

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            selectedNote = extras.getParcelable("selectedNote");
            
        }

        dbHandler = new DBHandler(MainActivity.this);
        states = dbHandler.getDataPosts();
        if (states == null) {
            states = new ArrayList<Note>();
        }

        notesList = findViewById(R.id.notesList);
        stateAdapter = new StateAdapter(this, R.layout.list_item, states);
        notesList.setAdapter(stateAdapter);
        search();



        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
 
                Note selectedState = (Note)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "You selected " + selectedState.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        notesList.setOnItemClickListener(itemListener);
  
        registerForContextMenu(notesList);  
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
 
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("state", states);
        super.onSaveInstanceState(outState);
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        states = savedInstanceState.getParcelableArrayList("state");
    }
    @Override  
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo)  
    {  
        super.onCreateContextMenu(menu, v, menuInfo);  
        MenuInflater inflater = getMenuInflater();  
        inflater.inflate(R.menu.context_menu, menu);  
        menu.setHeaderTitle("Select The Action");  
    }
    @Override  
    public boolean onContextItemSelected(MenuItem item){
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

        selectedNote = states.get(position);
        
        if(item.getItemId()==R.id.edit){
            Toast.makeText(getApplicationContext(),"Edit " + selectedNote.getText(),Toast.LENGTH_LONG).show();
            setContentView(R.layout.activity_edit);

            return true;
        }  
        else if(item.getItemId()==R.id.delete){  
            Toast.makeText(getApplicationContext(),"Delete elem " + Integer.toString(position),Toast.LENGTH_LONG).show();
            dbHandler.deletePost(states.get(position).getTitle());
            states.remove(position);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putParcelableArrayListExtra("state", states); 
            startActivity(intent); 
        }else{  
            return false;  
        }  
        return true;  
    }  
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ListView headerView = findViewById(R.id.notesList);
        
        if (id == R.id.add_note){
            setContentView(R.layout.activity_text);
            return true;
        }else if (id == R.id.settings){
            setContentView(R.layout.settings);
            fontsizeOption();
            return true;
        }else if (id == R.id.filter_min){
            sortStatesByImportanceDescending();
            return true;
        }else if (id == R.id.filter_max){
            sortStatesByImportanceAscending();             
            return true;
        }else if (id == R.id.lan_select_one){
            locale = new Locale("en");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, null);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putParcelableArrayListExtra("state", states); 
            startActivity(intent); 
        }else if (id == R.id.lan_select_two){
            locale = new Locale("ua");
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, null);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putParcelableArrayListExtra("state", states); 
            startActivity(intent); 
        }else if (id == R.id.theme_select_one){
            //setTheme(R.style.OrangeTheme);
            sTheme = R.style.OrangeTheme;
            recreate();
            //setContentView(R.layout.activity_main);
        }else if (id == R.id.theme_select_two){
            //setTheme(R.style.AppTheme);
            sTheme = R.style.AppTheme;
            recreate();
            //setContentView(R.layout.activity_main);
        }


        return super.onOptionsItemSelected(item);
    }
    public void fontsizeOption(){
        SeekBar seekBar = findViewById(R.id.seekBar);
        TextView textView = findViewById(R.id.seekBarValue);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                adjustFontScale(getResources().getConfiguration(), (float)progress);
                textView.setText(String.valueOf(progress));
            }
 
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
 
            }
 
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
 
            }
        });
    }
    // configuration -- getResources().getConfiguration()
    public void adjustFontScale(Configuration configuration,float scale) {

        configuration.fontScale = scale * 0.1f; //coefficient for adjustment
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(metrics);
        metrics.scaledDensity = configuration.fontScale * metrics.density;
        getBaseContext().getResources().updateConfiguration(configuration, metrics);
    }
private String saveBitmapToFile(Bitmap bitmap) {
    File filesDir = getFilesDir();
    File imageFile = new File(filesDir, "image" + System.currentTimeMillis() + ".png");
    
    try (FileOutputStream out = new FileOutputStream(imageFile)) {
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
        return imageFile.getAbsolutePath();
    } catch (IOException e) {
        e.printStackTrace();
        return null;
    }
}

@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    if (resultCode == RESULT_OK && requestCode == 3) { 
        radioGroup=(RadioGroup)findViewById(R.id.select_importance);   
        Uri selectedImageUri = data.getData();
        Bitmap selectedImageBitmap = null; 
        String imagePath = null;        
        try {
            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            imagePath = saveBitmapToFile(selectedImageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (imagePath != null){
            if (states == null) {
                states = new ArrayList<Note>();
            } 
            
            EditText editTitle = findViewById(R.id.title_text);
            EditText editNote = findViewById(R.id.note_text);

            int selectedId = radioGroup.getCheckedRadioButtonId();  
            genderradioButton = (RadioButton) findViewById(selectedId);

            long currentTimeMillis = System.currentTimeMillis();
            String timeStamp = new SimpleDateFormat("dd/MM/yyyy-HH-mm-ss").format(new Date(currentTimeMillis)); 
            
            
            if (genderradioButton.getText().toString().equals("low important") 
                    || genderradioButton.getText().toString().equals("Найменш важніше")){
                dbHandler.addPost(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath, 1);
                selectedNote = new Note(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath, 1);
                Toast.makeText(MainActivity.this,"low", Toast.LENGTH_SHORT).show(); 
            }else if (genderradioButton.getText().toString().equals("middle important") 
                    || genderradioButton.getText().toString().equals("Менш важніше")){
                dbHandler.addPost(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath, 2);
                selectedNote = new Note(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath, 2);                
                Toast.makeText(MainActivity.this,"mid", Toast.LENGTH_SHORT).show(); 
            }else if (genderradioButton.getText().toString().equals("top important") 
                    || genderradioButton.getText().toString().equals("Найважніше")){
                dbHandler.addPost(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath, 3);
                selectedNote = new Note(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath, 3);                
                Toast.makeText(MainActivity.this,"top", Toast.LENGTH_SHORT).show(); 
            } 

            states.add(selectedNote);
            findViewById(R.id.low_importance).setOnClickListener((view)->onRadioButtonClicked(view));
            findViewById(R.id.middle_importance).setOnClickListener((view)->onRadioButtonClicked(view));
            findViewById(R.id.top_importance).setOnClickListener((view)->onRadioButtonClicked(view));

            stateAdapter.notifyDataSetChanged();
        
        }
        if (states.get(states.size() - 1) != null){
            Intent intent = new Intent(this, MainActivity.class);
            intent.putParcelableArrayListExtra("state", states); 
            startActivity(intent); 
        }else{
            Toast.makeText(getApplicationContext(),"Select importance ",Toast.LENGTH_LONG).show();

        }
    }
}
    public void search() {
        for(int i = 0; i < states.size(); i++) {
            texts.add(states.get(i).getText());
        }
        
        //Creating the instance of ArrayAdapter containing list of language names  
        ArrayAdapter<String> adapter = new ArrayAdapter<String> (this,android.R.layout.select_dialog_item,texts);  
        //Getting the instance of AutoCompleteTextView  
        AutoCompleteTextView actv =  (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);  
        actv.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView  
    }
    public void sortStatesByImportanceDescending() {
        Comparator numComparator = new NumberComparatorMaxToMin(); 
        Collections.sort(states, numComparator);

        stateAdapter = new StateAdapter(this, R.layout.list_item, states);
        notesList.setAdapter(stateAdapter);

    }

    public void sortStatesByImportanceAscending() {
        Comparator numComparator = new NumberComparatorMinToMax(); 
        Collections.sort(states, numComparator);

        stateAdapter = new StateAdapter(this, R.layout.list_item, states);
        notesList.setAdapter(stateAdapter);
    }

    public void onRadioButtonClicked(View view) {
        RadioButton radio = (RadioButton) view;
        boolean checked = radio.isChecked();
        String text = radio.getText().toString();

    }

    public void editMessage(View view) {
        
        EditText editTitle = findViewById(R.id.title_edit);
        EditText editNote = findViewById(R.id.note_edit);
        
        String titleBefore = selectedNote.getTitle(); 
        selectedNote.setTitle(editTitle.getText().toString());
        selectedNote.setText(editNote.getText().toString());
    
        dbHandler.editPost(titleBefore,selectedNote);          

        
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("selectedNote", selectedNote);
        intent.putParcelableArrayListExtra("state", states); 
        startActivity(intent); 
    }


    public void sendMessage(View view) {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putParcelableArrayListExtra("state", states);
        intent.setType("image/*");
        startActivityForResult(intent, 3); 
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключение и курсор
        //db.close();

    }
}
