package ca.thurn.noughts.android;

import org.eclipse.xtext.xbase.lib.Procedures;

import ca.thurn.noughts.shared.Model;

import com.firebase.client.Firebase;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    final Model model = new Model(
        new Firebase("https://incrementer.firebaseio-demo.com"));
    findViewById(R.id.increment).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        model.increment(new Procedures.Procedure1<Long>() {
          @Override
          public void apply(Long numIncrements) {
            Toast.makeText(MainActivity.this, numIncrements + " increments!",
                Toast.LENGTH_SHORT).show();
          }
        });
      }
    });
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

}