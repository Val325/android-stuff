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
class Note implements Parcelable { 
 
    private String title; 
    private String text;  
    private String time;
    private int imgResource;
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
            return new Note(title, text, time, imagePath);
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
    public Note(String title, String text, String time, String imagePath) {
        this.title = title;
        this.text = text;
        this.time = time;
        this.imagePath = imagePath; 
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
    dest.writeString(imagePath);  // Save image path instead of Bitmap
}

protected Note(Parcel in) {
    title = in.readString();
    text = in.readString();
    time = in.readString();
    imagePath = in.readString();  // Read image path instead of Bitmap
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
        ImageView imgView = view.findViewById(R.id.icon);
 
        Note state = states.get(position);
 
        titleView.setText(state.getTitle());
        textView.setText(state.getText());
        dataView.setText(state.getTime());
        if (state.getImagePath() != null) {
            Bitmap image = BitmapFactory.decodeFile(state.getImagePath());
            imgView.setImageBitmap(image); 
        }
        //Bitmap image = state.getImage(); 
        


        return view;
    }
}


public class MainActivity extends AppCompatActivity {
    ArrayList<Note> states = new ArrayList<Note>();
    ListView notesList;
    StateAdapter stateAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        states = getIntent().getParcelableArrayListExtra("state");
        String imagePath = getIntent().getStringExtra("path");

 
        if (states == null) {
            states = new ArrayList<>();
        } 
        
        EditText editTitle = findViewById(R.id.title_text);
        EditText editNote = findViewById(R.id.note_text);

        long currentTimeMillis = System.currentTimeMillis();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(new Date(currentTimeMillis)); 
        
        states.add(new Note(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath));

        notesList = findViewById(R.id.notesList);
        stateAdapter = new StateAdapter(this, R.layout.list_item, states);
        notesList.setAdapter(stateAdapter);
        AdapterView.OnItemClickListener itemListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
 
                Note selectedState = (Note)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, "You selected " + selectedState.getTitle(),
                        Toast.LENGTH_SHORT).show();
            }
        };
        notesList.setOnItemClickListener(itemListener);
        notesList.setAdapter(stateAdapter);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        ListView headerView = findViewById(R.id.notesList);
        
        if (id == R.id.add_note){
            setContentView(R.layout.activity_text);
            return true;
        }


        return super.onOptionsItemSelected(item);
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

    if (resultCode == RESULT_OK && requestCode == 3) { // pick image
        
        Uri selectedImageUri = data.getData();
        Bitmap selectedImageBitmap = null; 
        String imagePath = null;        
        states = data.getParcelableArrayListExtra("state"); 

        try {
            selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
            imagePath = saveBitmapToFile(selectedImageBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }


        if (selectedImageBitmap != null){
            if (states == null) {
                states = new ArrayList<>();
            } 
            //states.get(states.size() - 1).setImagePath(imagePath);
            
            //EditText editTitle = findViewById(R.id.title_text);
            //EditText editNote = findViewById(R.id.note_text);

            //long currentTimeMillis = System.currentTimeMillis();
            //String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(new Date(currentTimeMillis)); 
        
            //states.add(new Note(editTitle.getText().toString(), editNote.getText().toString(), timeStamp, imagePath));
            //stateAdapter.notifyDataSetChanged();
            
           


        }
            Intent intent = new Intent(this, MainActivity.class);
            //intent.putParcelableArrayListExtra("state", states); getString()
            intent.putExtra("path", imagePath);
            startActivity(intent); 

    }
} 




    public void sendMessage(View view) {
        if (states == null) {
            states = new ArrayList<>();
        } 
        //setContentView(R.layout.activity_text);
        /*
        EditText editTitle = findViewById(R.id.title_text);
        EditText editNote = findViewById(R.id.note_text);

        long currentTimeMillis = System.currentTimeMillis();
        String timeStamp = new SimpleDateFormat("dd/MM/yyyy").format(new Date(currentTimeMillis)); 

        states.add(new Note(editTitle.getText().toString(), editNote.getText().toString(), timeStamp));
        */
        //Intent intent = new Intent(this, MainActivity.class);
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putParcelableArrayListExtra("state", states);
        intent.setType("image/*");
        startActivityForResult(intent, 3); // 3 - PICK IMAGE
        //intent.setAction(Intent.ACTION_PICK);
        //launchSomeActivity.launch(intent);
        //startActivity(intent);
    }


}
