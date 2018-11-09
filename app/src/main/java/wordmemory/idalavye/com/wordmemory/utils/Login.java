package wordmemory.idalavye.com.wordmemory.utils;

import com.facebook.CallbackManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login {
    public static CallbackManager mCallbackManager;
    public static FirebaseAuth mAuth;

    public static String getUserId(){
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        return currentUser.getUid();
    }
}
