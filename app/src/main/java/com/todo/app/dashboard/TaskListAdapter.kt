package com.todo.app.dashboard

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView

import com.todo.app.R
import com.todo.app.model.TaskModel


class TaskListAdapter(
    private var arrayList: List<TaskModel>
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var parentContext: Context
    private lateinit var activity: Activity



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        parentContext = parent.context
        activity = parentContext as Activity

        return PagerVH(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_task_list,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val view = holder as PagerVH
        val model = arrayList[position]

        view.taskText.text = model.description
     //   view.description.text = model.description
        if(model.type.equals("gym")){
            view.image.setImageResource(R.drawable.gym)
        }
        if(model.type.equals("games")){
            view.image.setImageResource(R.drawable.games)
        }
        if(model.type.equals("location")){
            view.image.setImageResource(R.drawable.location)
        }

        if(model.type.equals("party")){
            view.image.setImageResource(R.drawable.party)
        }



    }


    inner class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val image: ImageView = itemView.findViewById(R.id.image)
        val taskText: TextView = itemView.findViewById(R.id.taskText)
    }


}
