package Fragment

import Entity.Person
import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.vohidov.passportdata.R
import com.vohidov.passportdata.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    lateinit var binding:FragmentInfoBinding
    lateinit var person: Person

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInfoBinding.inflate(LayoutInflater.from(context))

        onResume()

        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()

        person = arguments?.getSerializable("person") as Person

        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.barchaFuqarolarFragment)
        }

        binding.txtIsmi.text = "Ismi: ${person.ismi}"
        binding.txtFamiliyasi.text = "Familiyasi: ${person.familiyasi}"
        binding.txtOtasiningIsmi.text = "Otasining ismi: ${person.otasiningIsmi}"
        binding.txtShahar.text = "Shahri: ${person.shahar}"
        binding.txtManzil.text = "Uy manzili: ${person.manzili}"
        binding.txtPassportRaqami.text = "Passport raqami: ${person.pasportRaqami}"

        if(person.jinsi == 0){
            binding.txtJinsi.text = "Jinsi: Erkak"
        }else{
            binding.txtJinsi.text = "Jinsi: Ayol"
        }

        when(person.viloyati){
            0 -> binding.txtViloyat.text = "Viloyati: Toshkent"
            1 -> binding.txtViloyat.text = "Viloyati: Andijon"
            2 -> binding.txtViloyat.text = "Viloyati: Farg'ona"
            3 -> binding.txtViloyat.text = "Viloyati: Namangan"
            4 -> binding.txtViloyat.text = "Viloyati: Jizzax"
            5 -> binding.txtViloyat.text = "Viloyati: Sirdaryo"
            6 -> binding.txtViloyat.text = "Viloyati: Navoiy"
            7 -> binding.txtViloyat.text = "Viloyati: Surxondaryo"
            8 -> binding.txtViloyat.text = "Viloyati: Qashqadaryo"
            9 -> binding.txtViloyat.text = "Viloyati: Samarqand"
            10 -> binding.txtViloyat.text = "Viloyati: Buxoro"
            11-> binding.txtViloyat.text = "Viloyati: Xorazm"
            12 -> binding.txtViloyat.text = "Viloyati: Qoraqalpog'iston respublikasi"
        }

        binding.imgFuqaro.setImageURI(Uri.parse(person.imagePath))
    }
}