package Fragment

import Adapter.ItemClick
import Adapter.RvAdapter
import Database.AppDatabase
import Entity.Person
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.vohidov.passportdata.R
import com.vohidov.passportdata.databinding.FragmentBarchaFuqarolarBinding


class BarchaFuqarolarFragment : Fragment() {

    lateinit var binding: FragmentBarchaFuqarolarBinding
    lateinit var appDatabase: AppDatabase
    lateinit var adapter: RvAdapter
    lateinit var list: ArrayList<Person>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBarchaFuqarolarBinding.inflate(LayoutInflater.from(context))

        onResume()

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.homeFragment)
        }
        appDatabase = AppDatabase.getInstance(binding.root.context)
        list = ArrayList()
        list.addAll(appDatabase.personDao().getAllPersons())
        adapter = RvAdapter(list, object : ItemClick {
            override fun onClick(list: List<Person>, position: Int, person: Person) {
                findNavController().navigate(R.id.infoFragment, bundleOf("person" to person))
            }


            override fun delete(person: Person) {
                val dialog = AlertDialog.Builder(binding.root.context)

                dialog.setMessage("Pasportni ochirmoqchimisiz?")

                dialog.setPositiveButton("No", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        dialog?.cancel()
                    }
                })

                dialog.setNegativeButton("Yes", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        appDatabase.personDao().deletePerson(person)
                        onResume()
                    }
                })
                dialog.show()
            }

        })
        binding.rvFuqarolar.adapter = adapter
    }
}