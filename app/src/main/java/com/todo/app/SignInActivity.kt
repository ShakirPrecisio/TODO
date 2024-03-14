package com.todo.app

import android.accounts.*
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.Scopes
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.tasks.Tasks
import com.google.api.services.tasks.TasksScopes
import com.google.api.services.tasks.model.Task
import com.google.api.services.tasks.model.TaskList
import com.todo.app.dashboard.FragmentHolderActivity
import com.todo.app.databinding.ActivitySignInBinding
import com.todo.app.util.ShowError
import com.todo.app.util.ShowMessage
import java.io.IOException
import java.lang.Exception

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 1
    private lateinit var accountManager: AccountManager
    private lateinit var looper: Looper
    private lateinit var handler: Handler
    private lateinit var handlerThread: HandlerThread


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        accountManager = AccountManager.get(this)
        val gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                // .requestScopes(Scope(TasksScopes.TASKS))
               // .requestIdToken("574137674774-rii7072tejtshid564dsabp8dluhn6j2.apps.googleusercontent.com")
                //  .requestIdToken("1060444234082-m5qerpc8gl0tmelcn5tt7j9rugum6ekc.apps.googleusercontent.com")
                //   .requestServerAuthCode("574137674774-rii7072tejtshid564dsabp8dluhn6j2.apps.googleusercontent.com")
                .requestEmail()
                .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        binding.continueButton.setOnClickListener {
            signIn()
        }



    }

    private fun signIn() {
        val intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(intent, RC_SIGN_IN)



    }


    private fun openDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Select a Google account")
        //GoogleAccountManager googleAccountManager = new GoogleAccountManager(this);
        //Account[] accounts = googleAccountManager.getAccounts();
        val accounts = accountManager.getAccountsByType("com.google")
        val size = accounts.size
        Log.e("ac", "length-->> " + accounts.size)
        val names = arrayOfNulls<String>(size)
        for (i in 0 until size) {
            names[i] = accounts[i].name
        }
        builder.setItems(names) { dialog, which -> // Stuff to do when the account is selected by the user
            getAccount(accounts[which])
        }
        builder.create()
        builder.show()
    }




    private fun getAccount(account: Account) {
        handlerThread = HandlerThread("thread1")
        handlerThread.start()
        looper = handlerThread.getLooper()
        handler = Handler(looper)
        val AUTH_TOKEN_TYPE = "oauth2:https://www.googleapis.com/auth/tasks"
        accountManager!!.getAuthToken(account, AUTH_TOKEN_TYPE, null, this,
            { future ->
                try {
                    // If the user has authorized your application to use the tasks API
                    // a token is available.
                    val token = future.result.getString(AccountManager.KEY_AUTHTOKEN)
                    Log.e("token-->", token!!)
                    // Now you can use the Tasks API...
                    handler.postDelayed(Runnable { useTasksAPI(token) }, 1)
                } catch (e: OperationCanceledException) {
                } catch (e: Exception) {
                    handleException(e)
                }
            }, null
        )
    }


    private fun useTasksAPI(accessToken: String) {
        // Setting up the Tasks API Service
        val credential = GoogleCredential().setAccessToken(accessToken)
        val service = Tasks.Builder(NetHttpTransport(), JacksonFactory(), credential)
            .setApplicationName("Todo")
            .build()
        val service2 = Tasks(NetHttpTransport(), JacksonFactory(), credential)

        // service2.accessKey = INSERT_YOUR_API_KEY;
        try {
            val taskLists: List<*> = service2.tasklists().list().execute().items
            for (i in taskLists.indices) {
                val model = taskLists[i] as TaskList
                val title = model.title
                Log.e("list title-->>", title)
                val tasks: List<*> = service2.tasks().list(model.id).execute().items
                for (j in tasks.indices) {
                    val task = tasks[j] as Task
                    Log.e("task title-->>", task.title)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        Log.e("service-->", service.toString())
        //   List taskLists = service.tasklists.list().execute().items;
    }


    private fun handleException(e: Exception) {
        Log.e("ex-->", "" + e)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task =
                GoogleSignIn.getSignedInAccountFromIntent(data)

            try {
                val account : GoogleSignInAccount? = task.getResult(ApiException::class.java)
                if(account!=null){

                    ShowMessage().showMessaeOnSnakeBar("SignIn Successful", binding.root)
                   // startActivity(Intent(this, FragmentHolderActivity::class.java))
                  //  getAccount(account as Account)
                    openDialog()

                }else{

                    ShowError().showErrorOnSnakeBar("Unable to Signin using gmail, please try again", binding.root)
                }

                val acc = account?.account

                Log.e("email-->", account?.email+"")
                Log.e("code-->>", account?.idToken+"")
                Log.e("scode-->>", account?.serverAuthCode+"")
                // fetchTaskList(account?.email+"")

            } catch (e: ApiException) {
                // The ApiException status code indicates the detailed failure reason.
                // Please refer to the GoogleSignInStatusCodes class reference for more information.
                Log.e("TAG","signInResult:failed code=" + e.statusCode)
                ShowError().showErrorOnSnakeBar("Unable to Signin using gmail, please try again", binding.root)
            }
        }
    }

    override fun onBackPressed() {

    }
}