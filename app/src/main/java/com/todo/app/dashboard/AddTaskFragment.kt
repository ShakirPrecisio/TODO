package com.todo.app.dashboard

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.todo.app.R
import com.todo.app.databinding.FragmentAddTaskBinding
import com.todo.app.model.TaskModel
import com.todo.app.util.SessionManager
import com.todo.app.util.ShowMessage
import java.text.SimpleDateFormat
import java.util.*


class AddTaskFragment : Fragment() {
    private lateinit var binding: FragmentAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTaskBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // binding.editDate.isEnabled = false



        var calen = Calendar.getInstance()
        var cal = Calendar.getInstance()

        val myFormat = "dd.MM.yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        val dateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            calen.set(Calendar.YEAR, year)
            calen.set(Calendar.MONTH, monthOfYear)
            calen.set(Calendar.DAY_OF_MONTH, dayOfMonth)


            val date = sdf.format(calen.time)
            binding.tvDateValue.text = date

        }

        binding.tvDateValue.text = sdf.format(calen.time).toString()
        binding.tvTimeValue.text = SimpleDateFormat("hh:mm aa").format(cal.time)

        binding.tvDateValue.setOnClickListener {
            DatePickerDialog(requireContext(), dateSetListener,
                calen.get(Calendar.YEAR),
                calen.get(Calendar.MONTH),
                calen.get(Calendar.DAY_OF_MONTH)).show()
        }

        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)

            binding.tvTimeValue.text = SimpleDateFormat("hh:mm aa").format(cal.time)
        }

        binding.tvTimeValue.setOnClickListener {
            TimePickerDialog(context, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), false).show()
        }
        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.taskListFragment)
        }

        binding.continueButton.setOnClickListener {
            if(checkValidation()){
                val name = binding.editName.text

                val json = "{\"name\":\"$name\"," +
                        "\"description\":\"${binding.editDescription.text}\"}"
                var tasks = SessionManager.getInstance().tasks
                tasks = json+ "^&*("+tasks
                SessionManager.getInstance().tasks = tasks
                ShowMessage().showMessaeOnSnakeBar("Task Added Successfully", binding.root)
                findNavController().navigate(R.id.taskListFragment)
            }
        }

    }

    private fun checkValidation():Boolean{
        var flag = true
        if(TextUtils.isEmpty(binding.editName.text)){
            flag = false
            binding.editName.error = "Please enter task name"
        }
        if(TextUtils.isEmpty(binding.editDescription.text)){
            flag = false
            binding.editDescription.error = "Please enter task description"
        }

        return flag
    }


}