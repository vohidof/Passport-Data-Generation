package Adapter

import Entity.Person
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vohidov.passportdata.databinding.ItemRvBinding

class RvAdapter(val list: List<Person>, var itemClick: ItemClick) : RecyclerView.Adapter<RvAdapter.Vh>(){

    inner class Vh(var itemRv: ItemRvBinding): RecyclerView.ViewHolder(itemRv.root){
        fun onBind(person: Person, position: Int){
            itemRv.txtName.text = person.ismi
            itemRv.txtPasportRaqam.text = person.pasportRaqami

            itemRv.cardRv.setOnClickListener {
                itemClick.onClick(list, position, person)
            }
            itemRv.cardRv.setOnLongClickListener {
                itemClick.delete(person)
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size
}
interface ItemClick {
    fun onClick(list: List<Person>, position: Int, person: Person)
    fun delete(person: Person)
}