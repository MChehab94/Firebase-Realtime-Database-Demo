package mchehab.com.firebasedemo;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by muhammadchehab on 1/11/18.
 */

public class ListViewAdapter extends BaseAdapter {

    private Activity activity;
    private List<Person> listPerson;

    public ListViewAdapter(Activity activity, List<Person> listPerson){
        this.activity = activity;
        this.listPerson = listPerson;
    }

    @Override
    public int getCount() {
        return listPerson.size();
    }

    @Override
    public Object getItem(int position) {
        return listPerson.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            convertView = activity.getLayoutInflater().inflate(R.layout.row_listview, null);

            holder = new ViewHolder();
            holder.textViewFirstName = convertView.findViewById(R.id.textViewFirstName);
            holder.textViewLastName = convertView.findViewById(R.id.textViewLastName);
            holder.textViewAge = convertView.findViewById(R.id.textViewAge);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolder)convertView.getTag();
        }

        holder.textViewFirstName.setText(listPerson.get(position).getFirstName());
        holder.textViewLastName.setText(listPerson.get(position).getLastName());
        holder.textViewAge.setText(listPerson.get(position).getAge() + "");

        return convertView;
    }

    class ViewHolder{
        TextView textViewFirstName;
        TextView textViewLastName;
        TextView textViewAge;
    }
}