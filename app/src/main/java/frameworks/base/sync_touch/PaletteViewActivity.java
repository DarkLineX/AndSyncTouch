package frameworks.base.sync_touch;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import frameworks.base.sync_touch.view.PaletteView;

public class PaletteViewActivity extends AppCompatActivity {

    private PaletteView paletteView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette_view);
    }
}