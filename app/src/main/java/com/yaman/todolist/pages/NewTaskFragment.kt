package com.yaman.todolist.pages


import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.material.snackbar.Snackbar
import com.yaman.api.response.model.todo.TodoItem
import com.yaman.core.application.BaseFragment
import com.yaman.todolist.ComponentManager
import com.yaman.todolist.R
import com.yaman.todolist.pages.mainfragment.viewmodel.MainFragmentViewModel
import com.yaman.todolist.pages.mainfragment.viewmodel.MainFragmentViewModelFactory
import kotlinx.android.synthetic.main.fragment_new_task.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class NewTaskFragment : BaseFragment() {

    var checked: Boolean = false
    var year = 0
    var month = 0
    var day = 0
    var hr = 0
    var min = 0
    val formatter by lazy { SimpleDateFormat("EEE, d MMM yyyy HH:mm", Locale.US) }


    @Inject
    lateinit var viewModelFactory: MainFragmentViewModelFactory


    private val component by lazy { ComponentManager.mainFragmentComponent(this.activity!!) }

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory)
                .get(MainFragmentViewModel::class.java)
    }


    private val colors by lazy { resources.getStringArray(R.array.colors) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_new_task, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        component.injectNewTastkComponent(this)

        hasRemind.setOnCheckedChangeListener { _, isChecked ->
            checkRemind(isChecked)
        }

        etTime.setOnClickListener {
            enterTime()
        }

        etDate.setOnClickListener {
            enterDate()
        }

        btnsubmit.setOnClickListener {
            submit(it)
        }


    }

    private fun submit(it: View) {
        if (TextUtils.isEmpty(taskTitle.text)) {
            val snackbar = Snackbar.make(parentLayout, "Task field cannot be empty", Snackbar.LENGTH_SHORT)
            snackbar.show()
        } else {
            var tagMili: String? = "0"
            val task = taskTitle.text.toString()
            val description = description.text.toString()
            val tm = formatter.format(Date(etDate.text.toString()))
            if (checked) {
                val c = Calendar.getInstance()
                c.set(year, month, day, hr, min)
                c.set(Calendar.SECOND, 0)
                c.set(Calendar.MILLISECOND, 0)
                val data = Data.Builder()
                data.putString("Task Name", taskTitle.toString())

                val notifyManager = OneTimeWorkRequest.Builder(Notify::class.java)
                        .setInputData(data.build())
                        .setInitialDelay(
                                c.timeInMillis - System.currentTimeMillis(),
                                TimeUnit.MILLISECONDS
                        )
                        .addTag(c.timeInMillis.toString())
                        .build()
                tagMili = c.timeInMillis.toString()
                WorkManager.getInstance(this.context!!).enqueue(notifyManager)
            }

            val colorCode = (colors.indices).random()
            viewModel.insert(
                    TodoItem(
                            task,
                            tm,
                            tagMili!!,
                            false,
                            description,
                            colors[colorCode]
                    )
            )

            it.findNavController().navigate(NewTaskFragmentDirections.actionNewTaskFragmentToMainFragment())
        }
    }

    private fun checkRemind(isChecked: Boolean) {
        if (!isChecked) {
            lyDateTimeArea.visibility = View.INVISIBLE
            checked = false

        } else {
            lyDateTimeArea.visibility = View.VISIBLE
            checked = true
            enterDate()
        }
    }

    private fun enterTime() {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        val mTimePicker = TimePickerDialog(
                context,
                TimePickerDialog.OnTimeSetListener { _, i, i1 ->
                    etTime.setText("$i:$i1")
                    hr = i
                    min = i1
                }, hour, minute, false
        )
        mTimePicker.show()
    }

    private fun enterDate() {
        val mcurrentDate = Calendar.getInstance()
        val mYear = mcurrentDate.get(Calendar.YEAR)
        val mMonth = mcurrentDate.get(Calendar.MONTH)
        val mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH)
        val mDatePicker = DatePickerDialog(
                this.context!!,
                DatePickerDialog.OnDateSetListener { _, selectedyear, selectedmonth, selectedday ->
                    etDate.setText(selectedday.toString() + "/" + (selectedmonth + 1) + "/" + selectedyear)
                    year = selectedyear
                    month = selectedmonth
                    day = selectedday
                }, mYear, mMonth, mDay
        )
        mDatePicker.show()
    }

}


