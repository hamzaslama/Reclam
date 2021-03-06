package ia2.moduleproject.eniso.reclam.Activities


import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ia2.moduleproject.eniso.reclam.Models.UserInformationSerialized
import ia2.moduleproject.eniso.reclam.R
import ia2.moduleproject.eniso.reclam.Services.AuthService
import kotlinx.android.synthetic.main.activity_main.*
import  android.widget.*
import ia2.moduleproject.eniso.reclam.Constant.sessionIdHeader
import ia2.moduleproject.eniso.reclam.Dialogs.AlertDialogShowReponse

class LoginActivity : AppCompatActivity() {

    var urlLogin = ""
    var authService = AuthService(this)
    var currentUser: UserInformationSerialized? = null
    var self: LoginActivity = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadLoginUser()


        //M2V
        //CBO
        //V2M
        submit.setOnClickListener {
            //V2M
            var user = user.text.toString()
            var pass = pass.text.toString()
            //DAL
            authService.showProgress()
            authService.login(user, pass, object : ia2.moduleproject.eniso.reclam.Common.Callback<UserInformationSerialized?> {
                override fun onNotNetworkAwailable() {
                    authService.progressDismiss()
                    AlertDialogShowReponse(self)
                }

                override fun onReceive() {
                    authService.progressDismiss()
                }

                override fun onSuccess(result: UserInformationSerialized?) {
                    currentUser = result
                    Toast.makeText(self, currentUser.toString(), Toast.LENGTH_LONG).show()
                    val intent = Intent(self, historique::class.java)
                    intent.putExtra(sessionIdHeader, currentUser!!.sessionId.toString())
                    startActivity(intent)
                }

                override fun onError(errorMessage: String) {
                    Toast.makeText(self, errorMessage, Toast.LENGTH_LONG).show()
                }
            })
            if (currentUser != null) {

                if (remember_me!!.isChecked == true) {
                    authService.saveLoginUser(user, pass, currentUser!!.sessionId.toString())
                    loadLoginUser()
                }



            }
//            RememberMe()
        }

    }



    fun loadLoginUser() {
        //V2M

        //CB
        val d = authService.getUserInfo()
        //M2V
        user!!.setText(d.login)
        pass!!.setText(d.password)
    }


//    //    fun saveLoginUser() {
//        val infofile = getSharedPreferences(MyUserLoginAndPassword, Context.MODE_PRIVATE)
//        val editor = infofile.edit()
//        editor.putString("username", user!!.text.toString())
//        editor.putString("pass", pass!!.text.toString())
//        editor.putString("sessionId", UserSer!!.sessionId)
//        editor.apply()
//
//    }
//
//    fun RememberMe() {
//        if (remember_me!!.isChecked == true) {
//            saveLoginUser()
////            loadLoginUser()
//        }
//    }
}

