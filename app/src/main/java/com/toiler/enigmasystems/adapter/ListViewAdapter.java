package com.toiler.enigmasystems.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.toiler.enigmasystems.R;
import com.toiler.enigmasystems.workermodel.WorkerModel;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Arm_AVI on 4/29/2018.
 */

public class ListViewAdapter extends ArrayAdapter<WorkerModel> {

    public ArrayList<WorkerModel> MainList;
    public ArrayList<WorkerModel> UserListTemp;
    public ListViewAdapter.UserDataFilter userDataFilter ;

    public ListViewAdapter(Context context, int id, ArrayList<WorkerModel> userArrayList) {

        super(context, id, userArrayList);
        this.UserListTemp = new ArrayList<WorkerModel>();
        this.UserListTemp.addAll(userArrayList);
        this.MainList = new ArrayList<WorkerModel>();
        this.MainList.addAll(userArrayList);
    }

    @Override
    public Filter getFilter() {

        if (userDataFilter == null){

            userDataFilter  = new ListViewAdapter.UserDataFilter();
        }
        return userDataFilter;
    }

    public class ViewHolder {
        TextView name, phone, address,
                designation, area_name,
                city, description,
                experience, gender,
                post, account_type, status;
        CircleImageView circleImageViewList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ListViewAdapter.ViewHolder holder = null;
        if (convertView == null) {

            LayoutInflater vi = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.listview_items_layout, null);

            holder = new ListViewAdapter.ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
//            holder.address = (TextView) convertView.findViewById(R.id.address);
            holder.phone = (TextView) convertView.findViewById(R.id.phone);
            holder.designation = (TextView) convertView.findViewById(R.id.designation);
            holder.area_name = (TextView) convertView.findViewById(R.id.areaname);
            holder.city = (TextView) convertView.findViewById(R.id.city);
            holder.description = (TextView) convertView.findViewById(R.id.description);
            holder.experience = (TextView) convertView.findViewById(R.id.experience);
            holder.gender = (TextView) convertView.findViewById(R.id.gender);
            holder.post = (TextView) convertView.findViewById(R.id.post);
            holder.account_type = (TextView) convertView.findViewById(R.id.account_type);
            holder.circleImageViewList = (CircleImageView) convertView.findViewById(R.id.circleImageViewList);
            holder.status = (TextView) convertView.findViewById(R.id.statusText);

            convertView.setTag(holder);

        } else {
            holder = (ListViewAdapter.ViewHolder) convertView.getTag();
        }

        WorkerModel workerModel = UserListTemp.get(position);
        holder.name.setText(workerModel.getName());
//        holder.address.setText(workerModel.getAddress());
        holder.phone.setText(workerModel.getPhone());
        holder.designation.setText(workerModel.getDesignation());
        holder.area_name.setText(workerModel.getArea_name());
        holder.city.setText(workerModel.getCity());

        holder.description.setText(workerModel.getDescription());
        holder.experience.setText(workerModel.getExperience());
//        holder.gender.setText(workerModel.getGender());
        holder.post.setText(workerModel.getPostcode());
        holder.account_type.setText(workerModel.getAccount_type());
        holder.status.setText(workerModel.getStatus());
        if(workerModel.getImage().isEmpty() || workerModel.getImage() == null){
            holder.circleImageViewList.setImageResource(R.drawable.icon_one);
        }else{
            Picasso.with(getContext()).load(workerModel.getImage()).resize(200, 200).into(holder.circleImageViewList);
        }
//        Picasso.with(getContext()).load(workerModel.getImage()).resize(200, 200).into(holder.circleImageViewList);


        return convertView;
    }

    private class UserDataFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            charSequence = charSequence.toString().toLowerCase();
            FilterResults filterResults = new FilterResults();

            if(charSequence != null && charSequence.toString().length() > 0)
            {
                ArrayList<WorkerModel> arrayList1 = new ArrayList<WorkerModel>();
                for(int i = 0, l = MainList.size(); i < l; i++)
                {
                    WorkerModel workerModel = MainList.get(i);
                    if(workerModel.toString().toLowerCase().contains(charSequence))
                        arrayList1.add(workerModel);
                }
                filterResults.count = arrayList1.size();
                filterResults.values = arrayList1;
            }
            else {
                synchronized(this) {
                    filterResults.values = MainList;
                    filterResults.count = MainList.size();
                }
            }
            return filterResults;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            UserListTemp = (ArrayList<WorkerModel>)filterResults.values;
            notifyDataSetChanged();
            clear();
            for(int i = 0, l = UserListTemp.size(); i < l; i++)
                add(UserListTemp.get(i));
                notifyDataSetInvalidated();
        }
    }
}
