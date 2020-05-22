package sg.edu.np.mad.mad_recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {


    MyRecyclerViewAdapter adapter;

    private Button button;
    private EditText input_data;
    final ArrayList<String> toDoList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.submit_input);
        toDoList.add("Buy Milk");
        toDoList.add("Buy Groceries");


        final RecyclerView recyclerView = findViewById(R.id.recyclerView);
       recyclerView.setLayoutManager(new LinearLayoutManager(this));
       adapter = new MyRecyclerViewAdapter(this,toDoList);
       adapter.setClickListener(this);
       recyclerView.setAdapter(adapter);

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               input_data = (EditText)findViewById(R.id.inputText);
               String input = input_data.getText().toString();
               toDoList.add(input);
               adapter.notifyItemInserted(toDoList.size());
               showNewEntry(recyclerView,toDoList );
               input_data.setText("");
           }
       });
    }



    /**
     * Upon calling this method, the keyboard will retract
     * and the recyclerview will scroll to the last item
     *
     * @param rv RecyclerView for scrolling to
     * @param data ArrayList that was passed into RecyclerView
     */
    private void showNewEntry(RecyclerView rv, ArrayList data){
        //scroll to the last item of the recyclerview
        rv.scrollToPosition(data.size() - 1);

        //auto hide keyboard after entry
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(rv.getWindowToken(), 0);
    }

    @Override
    public void onItemClick(View view, int position) {
        //Toast.makeText(this,"You Clicked " + adapter.getItem(position) + "row number" + position, Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final int index = position;

        LayoutInflater inflater= getLayoutInflater();
        View layout = inflater.inflate(R.layout.dialog, null);
        TextView msg = layout.findViewById(R.id.textView2);

        builder.setTitle("Delete");
        msg.setText(adapter.getItem(position));

        ImageView img = layout.findViewById(R.id.imgbin);
        //img.setImageResource(R.drawable.trash);

        builder.setCancelable(true);
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toDoList.remove(index);
                adapter.notifyItemRemoved(index);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setView(layout);
        builder.show();
        //msg.setText(Html.fromHtml("Are you sure you want to delete<br/>" + "<b>" + adapter.getItem(position) + "<b>"));



    }
}
