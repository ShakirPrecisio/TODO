package com.todo.app.dashboard

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.JsonObject
import com.todo.app.R
import com.todo.app.databinding.FragmentTaskListBinding
import com.todo.app.model.TaskModel
import com.todo.app.util.SessionManager
import org.json.JSONException
import org.json.JSONObject


class TaskListFragment : Fragment() {

    private lateinit var binding : FragmentTaskListBinding
    private var arrayList = ArrayList<TaskModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arrayList.clear()


        binding.plus.setOnClickListener {
            findNavController().navigate(R.id.addTaskFragment)
        }


        if(!TextUtils.isEmpty(SessionManager.getInstance().tasks)){
            var tasks = SessionManager.getInstance().tasks
            Log.e("tasks-->>", tasks)
            var taskArray = tasks.split("^&*(")
            for (index in 0 until taskArray.size){
                val data = taskArray[index]
                Log.e("data-->>", data)

                try {
                    val json = JSONObject(data.trim())
                    val model = TaskModel()
                    model.name = json.optString("name")
                    model.description = json.optString("description")
                    arrayList.add(model)
                }catch (e: JSONException){

                }

            }
        }




        val model1 = TaskModel()
        model1.description = "Shopping list, food for the week..."
        model1.type = "Shopping"
        val model2 = TaskModel()
        model2.description = "Play basketball with Billy and..."
        model2.type = "games"
        val model4 = TaskModel()
        model4.description = "Go to Richmond Park to get the..."
        model4.type = "location"
        val model5 = TaskModel()
        model5.description = "Prepare the Jennifer's birthday..."
        model5.type = "party"
        val model6 = TaskModel()
        model6.description = "Buy some gifts for the Jennifer's..."
        model6.type = "Shopping"
        val model7 = TaskModel()
        model7.description = "Gym time, rutine 2 ,week B..."
        model7.type = "gym"
        val model8 = TaskModel()
        model8.description = "Visit_the_zoo_with_the_Martha_s_..."
        model8.type = "location"
        val model9 = TaskModel()
        model9.description = "Bar with the guys on party Sat"
        model9.type = "party"


        arrayList.add(model1)
        arrayList.add(model2)
        arrayList.add(model4)
        arrayList.add(model5)
        arrayList.add(model6)
        arrayList.add(model7)
        arrayList.add(model8)
        arrayList.add(model9)




        val adapter = TaskListAdapter(arrayList)
        callTaskListAdapter(adapter)




    }

    private fun callTaskListAdapter(adapter: TaskListAdapter) {
        val layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }


}