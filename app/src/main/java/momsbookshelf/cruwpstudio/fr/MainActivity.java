/*
 * MainActivity
 * Activité principale de Mom's Bookshelf
 */

package momsbookshelf.cruwpstudio.fr;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import momsbookshelf.cruwpstudio.fr.fragments.HomeFragment;

/**
 * Activité principale de l'appli
 * Affiche une liste des derniers livres ajoutés
 */
public class MainActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        // Support pour les android non compatible avec la police dans la vue XML
        // Change la police du titre
        Typeface wolfInTheCity = ResourcesCompat.getFont(this, R.font.wolf_in_the_city);
        ((TextView)findViewById(R.id.title)).setTypeface(wolfInTheCity);

        // Le fragment par défaut est le HomeFragment
        switchFragment(new HomeFragment());

    }

    private void switchFragment(Fragment fragment) {
        // Gestionnaire de fragments
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        transaction.add(R.id.content, fragment, null);
        transaction.commit();
    }
}
