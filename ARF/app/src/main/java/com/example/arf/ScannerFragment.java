package com.example.arf;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScannerFragment extends Fragment implements ZXingScannerView.ResultHandler{
    public ZXingScannerView sxscannerview;
    private TextView TxtResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.scanner, container, false);

        sxscannerview=(ZXingScannerView)v.findViewById(R.id.zxscan);
        TxtResult=(TextView)v.findViewById(R.id.TxtResult);
        // on demande les permissions à l'utilisateur
        Dexter.withActivity(this.getActivity()).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener()

                        // on overide pour permettre de lunch correctment
                {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        Toast.makeText(getActivity(),"Bon chient ",Toast.LENGTH_SHORT);
                        Intent intent = getActivity().getIntent();
                        sxscannerview.setResultHandler(ScannerFragment.this);
                        sxscannerview.startCamera();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response)
                    {
                        Toast.makeText(getActivity() ,"accepte les permissions pd",Toast.LENGTH_SHORT);
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {

                    }
                })
                .check();

        return v;
    }

    @Override
    public void onDestroy()
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
