package ir.avarche.android.app.util

import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController


fun Fragment.alert(message:String,title:String = "")
{
    val alertDialog = AlertDialog.Builder(this.context!!).create()
    alertDialog.setTitle(title)
    alertDialog.setMessage(message)
    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ -> dialog.dismiss() }
    alertDialog.show()
}

fun View.tellNavControllerToNavigate(id:Int) = findNavController().navigate(id)