package conrado.gabriel.ragdoll.list.towel

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import conrado.gabriel.ragdoll.R
import conrado.gabriel.ragdoll.activity.HomeActivity
import conrado.gabriel.ragdoll.addedit.towel.AddEditTowelActivity
import conrado.gabriel.ragdoll.data.Towel
import kotlinx.android.synthetic.main.fragment_list_towels.*


class ListTowelsFragment : Fragment(), ListTowelsContract.View{

    private val towelListAdapter = TowelListAdapter(emptyList())

    override lateinit var presenter: ListTowelsContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?)
            : View? = inflater.inflate(R.layout.fragment_list_towels, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_list_towels.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = towelListAdapter
        }
        fab_new_towel.setOnClickListener { showAddTowel() }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun setLoadingIndicator(active: Boolean) {
        if (active) pb_loading_towels.visibility = View.VISIBLE else pb_loading_towels.visibility = View.GONE
    }

    override fun showTowels(towel: List<Towel>) {
        towelListAdapter.towels = towel
        tv_no_towels.visibility = View.GONE
        rv_list_towels.visibility = View.VISIBLE
    }

    override fun showAddTowel() {
        val intent = Intent(context, AddEditTowelActivity::class.java)
        startActivityForResult(intent, AddEditTowelActivity.REQUEST_ADD_TOWEL)
    }

    override fun showNoTowels() {
        tv_no_towels.visibility = View.VISIBLE
        rv_list_towels.visibility = View.GONE
    }

    override fun showAddEditSuccess() {
        val parentActivity = (activity as HomeActivity?)
        parentActivity?.showMessage(getString(R.string.success_add_edit))
    }

    companion object {
        fun newInstance() = ListTowelsFragment()
    }

    /**
     * Adapter to hold the RecyclerView Towel items
     */
    class TowelListAdapter(towels: List<Towel>)
        : RecyclerView.Adapter<TowelListAdapter.TowelListViewHolder>(){

        var towels: List<Towel> = towels
            set(towels) {
                field = towels
                notifyDataSetChanged()
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TowelListViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return TowelListViewHolder(inflater, parent)
        }

        override fun onBindViewHolder(holder: TowelListViewHolder, position: Int) = holder.bind(towels[position])


        override fun getItemCount(): Int = towels.size

        class TowelListViewHolder(inflater: LayoutInflater, parent: ViewGroup)
            : RecyclerView.ViewHolder(inflater.inflate(R.layout.item_towel_list, parent, false)){

            private var towelTitle: TextView? = null

            init {
                towelTitle = itemView.findViewById(R.id.tv_item_towel_title)
            }

            fun bind(towel: Towel){
                towelTitle?.text = towel.type
            }

        }

    }

}