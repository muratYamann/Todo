package com.yaman.todolist.pages.mainfragment.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.work.WorkManager
import com.yaman.api.response.model.todo.TodoItem
import com.yaman.core.utils.utils
import com.yaman.todolist.R
import com.yaman.todolist.pages.mainfragment.viewmodel.MainFragmentViewModel

class MainFragmentListAdapter internal constructor(
        context: Context?
) : RecyclerView.Adapter<MainFragmentListAdapter.MainFragmentAdapterViewHoleder>() {


    var interactor: MainFragmnetItemInteractor? = null

    interface MainFragmnetItemInteractor {
        fun onItemClick1(

        )
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = ArrayList<TodoItem>()
    private lateinit var ctx: Context
    private lateinit var view: View
    private lateinit var mainFragmentViewModel: MainFragmentViewModel

    inner class MainFragmentAdapterViewHoleder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val wordItemView: TextView = itemView.findViewById(R.id.tvTaskTitle)
        val timeItemView: TextView = itemView.findViewById(R.id.tvTimeRow)
        val relcard: RelativeLayout = itemView.findViewById(R.id.rlTodoItemCard)
        val completionToggle: CheckBox = itemView.findViewById(R.id.chCompletion)
        val img: ImageView = itemView.findViewById(R.id.imgItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainFragmentAdapterViewHoleder {
        val itemView = inflater.inflate(R.layout.row_layout, parent, false)
        return MainFragmentAdapterViewHoleder(itemView)
    }

    override fun onBindViewHolder(holder: MainFragmentAdapterViewHoleder, position: Int) {
        val current = words[position]
        holder.wordItemView.text = current.title
        holder.img.setBackgroundColor(Color.parseColor(current.color))

        if (current.isComplete) {
            holder.wordItemView.setTextColor(ctx.resources.getColor(R.color.colorAccent))
            holder.wordItemView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.wordItemView.setTextColor(ctx.resources.getColor(R.color.textColor))
            holder.wordItemView.paintFlags =
                    holder.wordItemView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }

        holder.timeItemView.text = current.time
        holder.completionToggle.isChecked = current.isComplete
        holder.completionToggle.setOnCheckedChangeListener { _, isChecked ->
            toggleCompletion(current.id, isChecked)
        }

        holder.relcard.setOnClickListener {
            val mDialogView =
                    LayoutInflater.from(ctx).inflate(R.layout.task_detail, null)
            val edit: TextView = mDialogView.findViewById(R.id.edit)
            val save: Button = mDialogView.findViewById(R.id.btnSaveDesctription)
            val desc: EditText = mDialogView.findViewById(R.id.tvTaskdescriptionDialog)
            val itemTitle: EditText = mDialogView.findViewById(R.id.tvTaskTitleDailog)


            itemTitle.setText(current.title)
            if (current.description.isNotEmpty()) {
                desc.setText(current.description)
            } else {
                desc.visibility = View.GONE
            }
            mDialogView.findViewById<TextView>(R.id.tvTime).text = current.time
            mDialogView.findViewById<TextView>(R.id.status).text =
                    if (current.isComplete) ctx.getString(R.string.completed) else ctx.getString(R.string.not_completed)
            mDialogView.findViewById<TextView>(R.id.status).setTextColor(
                    ContextCompat.getColor(
                            ctx,
                            if (current.isComplete) R.color.colorAccent else R.color.textColor
                    )
            )
            val mBuilder = AlertDialog.Builder(ctx).setView(mDialogView)
            val mAlertDialog = mBuilder.show()

            edit.setOnClickListener {
                save.visibility = View.VISIBLE
                edit.visibility = View.GONE
                itemTitle.isEnabled = true
                desc.isEnabled = true
                itemTitle.requestFocus()
                utils.showKeyboard(ctx)
            }
            save.setOnClickListener {
                this.mainFragmentViewModel.update(current.id, itemTitle.text.toString(), desc.text.toString())
                mAlertDialog.dismiss()
                utils.closeKeyboard(ctx)
            }

            mDialogView.findViewById<Button>(R.id.close).setOnClickListener {
                mAlertDialog.dismiss()
                utils.closeKeyboard(ctx)
            }
        }
    }

    internal fun setWords(
            todoItems: List<TodoItem>,
            ctx: Context?,
            mainFragmentViewModel: MainFragmentViewModel,
            view: View
    ) {
        this.words = todoItems as ArrayList<TodoItem>
        this.ctx = ctx!!
        this.mainFragmentViewModel = mainFragmentViewModel
        this.view = view
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size

    fun getList() = words

    private fun toggleCompletion(id: Int, mark: Boolean) {
        mainFragmentViewModel.markAsComplete(id, mark)
    }

    fun removeitem(position: Int) {
        mainFragmentViewModel.delete(words[position])
        WorkManager.getInstance().cancelAllWorkByTag(words[position].tag)
        notifyItemRemoved(position)
    }

    fun restoreItem(todoItem: TodoItem, position: Int) {
        words.add(position, todoItem)
        notifyItemChanged(position)
        mainFragmentViewModel.insert(todoItem)
    }
}

