package com.marcosviniciusferreira.olx.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ConfiguracaoFirebase {

    private static DatabaseReference referenciaFirebase;
    private static FirebaseAuth referenciaAuth;
    private static StorageReference referenciaStorage;

    public static String getIdUsuario() {

        FirebaseAuth auth = getFirebaseAuth();
        return auth.getCurrentUser().getUid();
    }


    //Retorna a referencia do database
    public static DatabaseReference getFirebase() {
        if (referenciaFirebase == null) {
            referenciaFirebase = FirebaseDatabase.getInstance().getReference();
        }
        return referenciaFirebase;
    }

    //Retorna a instancia do FirebaseAuth
    public static FirebaseAuth getFirebaseAuth() {
        if (referenciaAuth == null) {
            referenciaAuth = referenciaAuth.getInstance();
        }
        return referenciaAuth;
    }

    //Retorna a instancia do FirebaseStorage
    public static StorageReference getFirebaseStorage() {
        if (referenciaStorage == null) {
            referenciaStorage = FirebaseStorage.getInstance().getReference();
        }
        return referenciaStorage;
    }

}
