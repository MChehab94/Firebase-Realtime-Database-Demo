package mchehab.com.kotlinapp

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

/**
 * Created by muhammadchehab on 1/11/18.
 */

class ListViewAdapter(private val activity: Activity, private val listPerson: List<Person>) : BaseAdapter() {

    override fun getCount(): Int {
        return listPerson.size
    }

    override fun getItem(position: Int): Any {
        return listPerson[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView
        val holder: ViewHolder
        if (convertView == null) {
            convertView = activity.layoutInflater.inflate(R.layout.row_listview, null)

            holder = ViewHolder()
            holder.textViewFirstName = convertView!!.findViewById(R.id.textViewFirstName)
            holder.textViewLastName = convertView.findViewById(R.id.textViewLastName)
            holder.textViewAge = convertView.findViewById(R.id.textViewAge)

            convertView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
        }

        holder.textViewFirstName!!.setText(listPerson[position].firstName)
        holder.textViewLastName!!.setText(listPerson[position].lastName)
        holder.textViewAge!!.setText("${listPerson[position].age} ")

        return convertView
    }

    internal inner class ViewHolder {
        var textViewFirstName: TextView? = null
        var textViewLastName: TextView? = null
        var textViewAge: TextView? = null
    }
}