package ir.avarche.android.app.util

import android.content.Context
import androidx.appcompat.app.AlertDialog


fun alert(context:Context,title:String,message:String)
{
    val alertDialog = AlertDialog.Builder(context).create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ -> dialog.dismiss() }
    alertDialog.show()
}