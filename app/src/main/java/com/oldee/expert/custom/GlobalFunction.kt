package com.oldee.expert.custom

import android.content.ContentResolver
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Resources
import android.graphics.Paint
import android.graphics.Typeface
import android.net.Uri
import android.provider.OpenableColumns
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import android.text.style.UnderlineSpan
import android.util.TypedValue
import android.widget.TextView
import androidx.core.app.ActivityCompat
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 *
 *
 * @param context
 * @param permissions
 * @return
 */
fun checkPermission(context: Context, vararg permissions: String) = permissions.all {
    ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
}

fun getImageBody(key: String, file: File) = MultipartBody.Part.createFormData(
    name = key,
    filename = file.name,
    body = file.asRequestBody("image/*".toMediaType())
)

fun getTextBody(value: String): RequestBody {
    return value.toRequestBody("text/plan".toMediaType())
}

fun getImageBody(key: String, files: List<File>): List<MultipartBody.Part> {
    val list = arrayListOf<MultipartBody.Part>()

    files.forEach {
        val body = getImageBody(key, it)
        list.add(body)
    }

    return list
}

fun getImageBodyUri(key: String, uries: List<Uri>): List<MultipartBody.Part> {
    val list = arrayListOf<MultipartBody.Part>()

    uries.forEach {
        val body = getImageBody(key, File(it.path ?: ""))
        list.add(body)
    }

    return list
}

fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    returnCursor?.let {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }

    return name
}

fun getBoldText(
    inputText: String
): SpannableString {
    val sb = SpannableString(inputText)
    val end = if (inputText.length < 3) 2 else 3
    sb.setSpan(StyleSpan(Typeface.BOLD), 0, end, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

    return sb
}

fun getUnderLineText(
    inputText: String,
    targetText:String = ""
):SpannableString{
    val sb = SpannableString(inputText)
    val startIdx = inputText.indexOf(targetText)
    sb.setSpan(UnderlineSpan(), startIdx, startIdx + targetText.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return sb
}

//fun dpToPx(context: Context?, dp: Float): Float {
//    val resources: Resources? = context?.resources
//
//    return TypedValue.applyDimension(
//        TypedValue.COMPLEX_UNIT_DIP,
//        dp,
//        resources?.displayMetrics
//    )
//}

fun dpToPx(context: Context?, dp: Float): Int {
    val resources: Resources? = context?.resources

    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        dp,
        resources?.displayMetrics
    ).toInt()
}

fun TextView.setUnderLine(view:TextView){
    view.paintFlags = Paint.UNDERLINE_TEXT_FLAG
}

