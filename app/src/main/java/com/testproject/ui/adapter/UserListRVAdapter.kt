import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.testproject.R
import com.testproject.model.User

class UserListRVAdapter : ListAdapter<User, UserListRVAdapter.NameViewHolder>(NameComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NameViewHolder {
        return NameViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NameViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.firstName)
    }

    class NameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val nameItemView: AppCompatTextView = itemView.findViewById(R.id.tv_name)

        fun bind(text: String?) {
            nameItemView.text = text
        }

        companion object {
            fun create(parent: ViewGroup): NameViewHolder {
                val view: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
                return NameViewHolder(view)
            }
        }
    }

    class NameComparator : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.firstName == newItem.firstName
        }
    }
}