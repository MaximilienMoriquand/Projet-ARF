package com.example.arf;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    public ZXingScannerView sxscannerview;
    private TextView TxtResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sxscannerview=(ZXingScannerView)findViewById(R.id.zxscan);
        TxtResult=(TextView)findViewById(R.id.TxtResult);
        // on demande les permissions à l'utilisateur
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()

                // on overide pour permettre de lunch correctment
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(MainActivity.this,"Bon chient ",Toast.LENGTH_SHORT);
                        sxscannerview.setResultHandler(MainActivity.this);
                        sxscannerview.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Toast.makeText(MainActivity.this,"accepte les permissions pd",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();
    }

    @Override
    protected void onDestroy()
    { // on ferme la camera quand l'activité est stop
        sxscannerview.stopCamera();
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        // reception des resultats sans traitement
        TxtResult.setText(rawResult.getText());
    }
}
