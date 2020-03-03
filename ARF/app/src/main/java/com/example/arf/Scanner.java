package com.example.arf;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
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
public class Scanner extends Activity implements ZXingScannerView.ResultHandler {
    public ZXingScannerView sxscannerview;
    private TextView TxtResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);
        sxscannerview=(ZXingScannerView)findViewById(R.id.zxscan);
        TxtResult=(TextView)findViewById(R.id.TxtResult);
        // on demande les permissions à l'utilisateur
        Dexter.withActivity(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()

                        // on overide pour permettre de lunch correctment
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(Scanner.this,"Bon chient ",Toast.LENGTH_SHORT);
                        Intent intent = getIntent();
                        sxscannerview.setResultHandler(Scanner.this);
                        sxscannerview.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Toast.makeText(Scanner.this,"accepte les permissions pd",Toast.LENGTH_SHORT);
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
        //appelle de la fonction de la bdd (voir plus bas )
        super.onDestroy();
    }

    @Override
    public void handleResult(Result rawResult) {
        // reception des resultats sans traitement
        TxtResult.setText(rawResult.getText());
        /*
        *  On fout le produit dans un fichier text
        * la bdd l'interprète et en fait qque chose dans le onDestroy
       */
    }
}
