package dev.ffeusthuber.todoapp.data;

import android.util.Log;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;

public class QuerySelector {
    private static final String TAG = "QuerySelector";

    private String orderOption = "";
    private final CollectionReference tasksCollRef;

    public QuerySelector(){
        tasksCollRef = DBConnectionImpl_Firestore.tasksCollRef;
    }

    public void setOrderOption(String orderOption) {
        this.orderOption = orderOption;
    }

    public Query getQuery(String userId) {
        Query query;
        switch (orderOption){
            case("title"):
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("title", Query.Direction.ASCENDING);
                Log.d(TAG, "getQuery: TITLEQUERRY");
                break;
            default:
                query = tasksCollRef
                        .whereEqualTo("userId", userId)
                        .orderBy("isCompleted", Query.Direction.ASCENDING)
                        .orderBy("dateCompletion", Query.Direction.ASCENDING);
                Log.d(TAG, "getQuery: DEFAULT QUERRY");
        }
        return query;
    }

}
